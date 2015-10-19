package com.wandou.clientudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.R.integer;
import android.util.Log;

/**
 *Title:
 *@author 豌豆先生 jitsiang@163.com
 *@date 2015年6月4日
 *@version 
 */
public class SendThread implements Runnable{
	private  String msg;
	
	private String press;
	private String key;
	private String pointX,pointY;
	
	private  DatagramPacket outPacket = null;
	private DatagramSocket socket= null;
	
	private byte[] outCache = new byte[256];
	private InetAddress addr;
	
	private int portNum;
	public SendThread(String ip,int port) throws SocketException{
		try {
			this.addr = InetAddress.getByName(ip);
			this.portNum = port;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} ;
 
	}

	public void  initDatagram(){
		try {
			this.socket = new DatagramSocket();
			this.outPacket = new DatagramPacket(outCache, outCache.length);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}	
	}
	public void clickedLeft() {
		press = Constant.LEFT_BUTTON_CLICKED;
	}

	public void clickedRight() {
		press = Constant.RIGHT_BUTTON_CLICKED;
	}

	public void clickedUp() {
		press = Constant.MOUSE_WHEEL_UP;
	}

	public void clickedDown() {
		press = Constant.MOUSE_WHEEL_DOWM;
	}

	public void setKeyValue(char keyValue) {
		Log.e("keyvalue","keyvalue:"+keyValue);
		key = String.valueOf(keyValue);
	}
	
	public void setPointX(int x){
		pointX = String.valueOf(x);
	}
	public void setPointY(int y){
		pointY = String.valueOf(y);
	}
	
	public void sendMsg() throws UnknownHostException{
		outPacket.setAddress(addr);
		outPacket.setPort(portNum);	
		String msgSent = press+"/"+key+"/"+pointX+"/"+pointY+"/";
		outPacket.setData(msgSent.getBytes());
		try {
			socket.send(outPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		press = null;
		key = null;
		pointX = null;
		pointY = null;
		
	}

	@Override
	public void run(){		
		initDatagram();
		while (true) {
		try {
				sendMsg();
				Thread.sleep(50);
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
