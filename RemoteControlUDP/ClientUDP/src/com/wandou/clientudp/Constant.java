package com.wandou.clientudp;

import java.net.Socket;

import android.R.integer;

public class Constant {

	public  static final String LEFT_BUTTON_CLICKED = "leftButtonClicked";
	public static final String RIGHT_BUTTON_CLICKED = "rightButtonClicked";
	public static final String MOUSE_WHEEL_UP = "mouseWheelUp";
	public static final String MOUSE_WHEEL_DOWM = "mouseWheelDown";
	
	public static final int  BUTTON_LEFT_CLICKED  =  0X1111;
	public static final int  BUTTON_RIGHT_CLICKED  =  0X1112;
	public static final int  MOUSE_CLICKED  =  0X1113;
	public static final int  ACK_CMD               = 0XAC;
	public static final int  START               = 0XA0;
	
	public static Socket SOCKET  =  null;	
	
	public static final String FILE_NAME = "filename";
	public static final int PORT = 20155;
	
}
