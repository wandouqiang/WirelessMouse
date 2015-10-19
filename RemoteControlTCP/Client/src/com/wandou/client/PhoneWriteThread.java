package com.wandou.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.util.Log;


public class PhoneWriteThread extends Thread {
	private static final String TAG = "WriteThread TAG";
	private DataOutputStream dos = null;
	private int phoneScreenWidth, phoneScreenHeight;

	private int pointX, pointY;

	private boolean isLeftBtnClicked = false;
	private boolean isRightBtnClicked = false;
	private boolean mouseClicked = false;

	private boolean mRunning, mQuit;

	public PhoneWriteThread(OutputStream os, int mWidth, int mHeight) {
		this.dos = new DataOutputStream(os);
		this.phoneScreenWidth = mWidth;
		this.phoneScreenHeight = mHeight;
	}

	public void leftBtnClicked() {
		isLeftBtnClicked = true;
	}

	public void rightBtnClicked() {
		isRightBtnClicked = true;
	}

	public void setPoint(int x,int y) {
		mouseClicked = true;
		pointX = x;
		pointY = y;
		Log.e(TAG,x+":"+y);
	}

	private void _sendMsg() {
		mRunning = true;
		mQuit = false;
		while (mRunning) {
			if (mQuit)
				break;
			try {
				if (isLeftBtnClicked) {
					dos.writeInt(Constant.BUTTON_LEFT_CLICKED);
					isLeftBtnClicked= false;
				}
				if (isRightBtnClicked) {
					dos.writeInt(Constant.BUTTON_RIGHT_CLICKED);
					isRightBtnClicked = false;
				}
				if (mouseClicked) {
					dos.writeInt(Constant.MOUSE_CLICKED);
					dos.writeByte((byte) 5) ;
					dos.writeInt(pointX);
					dos.writeByte((byte) 5) ;
					dos.writeInt(pointY);
					mouseClicked = false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mRunning = false;
	}

	public void quit() {
		try {
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mRunning = false;
		mQuit = true;
	}

	@Override
	public void run() {
		mRunning = true;
		mQuit = false;
		while (mRunning) {
			if (mQuit)
				break;
			try {
				dos.writeByte((byte) 5); 
				dos.writeInt(Constant.START);
				dos.writeByte((byte) 5);
				dos.writeInt(phoneScreenWidth);
				dos.writeByte((byte) 5);
				dos.writeInt(phoneScreenHeight);
				_sendMsg();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
       mRunning = false;
	}
}
