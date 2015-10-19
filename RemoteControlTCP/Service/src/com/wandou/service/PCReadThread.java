package com.wandou.service;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class PCReadThread extends Thread {
	private static final String TAG = "ReadThread";
	private DataInputStream dis;
	private ControlSocket mSocket;
	private Robot mRobot;
	private boolean mRunning, mQuit;

	public PCReadThread(ControlSocket mSocket, InputStream is) {
		// TODO Auto-generated constructor stub
		try {
			mRobot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mSocket = mSocket;
		dis = new DataInputStream(is);
	}
	
	public void quit() {
		try {
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mRunning = false;
		mQuit = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		mRunning = true;
		mQuit = false;
		try {
			while (mRunning) {
				if (mQuit)
					break;
				System.out.println("Ready to read");
				dis.readByte();
				int start = dis.readInt();
				if (start == Constant.START) {
					System.out.println("start = " + start);
					dis.readByte();
					int phoneScreenWidth = dis.readInt();
					dis.readByte();
					int phoneScreenHeight = dis.readInt();
					mSocket.setImgSize(phoneScreenWidth, phoneScreenHeight);

					while (true) {
						int cmd = dis.readInt();
						if (cmd == Constant.BUTTON_LEFT_CLICKED) {
					//		System.out.println("BUTTON_LEFT_CLICKED");
							mRobot.mousePress(InputEvent.BUTTON1_MASK);// 按下左键
							mRobot.mouseRelease(InputEvent.BUTTON1_MASK);// 释放左键
							mRobot.delay(5);
							mRobot.mousePress(InputEvent.BUTTON1_MASK);// 按下左键
							mRobot.mouseRelease(InputEvent.BUTTON1_MASK);// 释放左键
						} else if (cmd == Constant.BUTTON_RIGHT_CLICKED) {
					//		System.out.println("BUTTON_RIGHT_CLICKED");
							mRobot.mousePress(InputEvent.BUTTON3_MASK);// 按下右键
							mRobot.mouseRelease(InputEvent.BUTTON3_MASK);// 释放右键
						} else if (cmd == Constant.MOUSE_CLICKED) {
					//		System.out.println("MOUSE_CLICKED");
							int X, Y;
							dis.readByte();
							X = dis.readInt();
							dis.readByte();
							Y = dis.readInt();
							mRobot.mouseMove(
									(int) (X * Constant.pcScreenWidth/ phoneScreenWidth ),
									(int) (Y * Constant.pcScreenHeight/ phoneScreenHeight ));
							mRobot.mousePress(InputEvent.BUTTON1_MASK);// 按下左键
							mRobot.mouseRelease(InputEvent.BUTTON1_MASK);// 释放左键
				//			System.out.println(X + ":" + Y + "   MOVED");
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mRunning = false;
	}
//	
//	private class ActionThread extends Thread{
//		private int phoneScreenWidth,phoneScreenHeight;
//		private int cmd;
//
//		public void run() {
//			mousEvent(cmd);
//		}
//
//		private void mousEvent(int cmd) {
//			// TODO Auto-generated method stub
//			switch ( cmd) {
//			case Constant.BUTTON_LEFT_CLICKED:
//				System.out.println("BUTTON_LEFT_CLICKED");
//				mRobot.mousePress(InputEvent.BUTTON1_MASK);// 按下左键
//				mRobot.mouseRelease(InputEvent.BUTTON1_MASK);// 释放左键
//				break;
//
//			case Constant.BUTTON_RIGHT_CLICKED:
//				System.out.println("BUTTON_RIGHT_CLICKED");
//				mRobot.mousePress(InputEvent.BUTTON3_MASK);// 按下右键
//				mRobot.mouseRelease(InputEvent.BUTTON3_MASK);// 释放右键
//				break;
//			case Constant.MOUSE_CLICKED:
//				System.out.println("MOUSE_CLICKED");
//				int X,Y;
//				try {
//					dis.readByte();
//					X = dis.readInt();
//					dis.readByte();
//				    Y = dis.readInt();
//				    mRobot.mouseMove((int) (X / phoneScreenWidth* Constant.pcScreenWidth),
//						 (int) (Y/ phoneScreenHeight*Constant.pcScreenHeight));	
//				    System.out.println(X+":"+Y+"   MOVED");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		     break;
//			}
//		}
//		
//		public void setCmd(int cmd){
//			this.cmd = cmd;
//		}
//		public  void setScreen(int w,int h){
//			this.phoneScreenWidth = w;
//			this.phoneScreenHeight = h;
//		}
//		
//	}
}
