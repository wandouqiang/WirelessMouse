package Server;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
/**
 *Title:
 *@author 豌豆先生 jitsiang@163.com
 *@date 2015年6月3日
 *@version 
 */
public class MsgReceivedThread extends Thread{

	private Robot mRobot;
	private String info;
	
	private int pointX,pointY;
	private int phoneScrW,phoneScrH;
	
	public MsgReceivedThread(String msg){
		this.info = msg;
		try {
			mRobot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void processInfo(){
		String[] strings = info.split("/");
		if (!strings[0].trim().equals("") && strings[0] != null) {
                   clickedAction(strings[0].trim());
		}
		if (!strings[1].trim().equals("") && strings[1] != null) {
			//int keyCode = Integer.parseInt(strings[1].trim());
			String keyValue = strings[1].trim();
			keyAction(keyValue);      
		}
		if (!strings[2].trim().equals("") && strings[2] != null) {
			int x = Integer.parseInt(strings[2].trim());
			setPointX(x);
		}
		if (!strings[3].trim().equals("") && strings[3] != null) {
			int y = Integer.parseInt(strings[3].trim());
			setPointY(y);
		}
//		if (!strings[4].trim().equals("") && strings[4] != null) {
//			int phoneScrWidth = Integer.parseInt(strings[4].trim());
//			setImgW(phoneScrWidth);
//		}
//		if (!strings[5].trim().equals("") && strings[5] != null) {
//			int phoneScrHeight = Integer.parseInt(strings[5].trim());
//			setImgH(phoneScrHeight);
//		}
		mouseAction();
	}
	
	private void setImgH(int phoneScrHeight) {
		// TODO Auto-generated method stub
		this.phoneScrH = phoneScrHeight;
	}

	private void setImgW(int phoneScrWidth) {
		// TODO Auto-generated method stub
		this.phoneScrW = phoneScrWidth;
	}

	private void setPointX(int x) {
		// TODO Auto-generated method stub
		this.pointX = x;
	}

	private void setPointY(int y) {
		// TODO Auto-generated method stub
		this.pointY = y;
	}

	public void mouseAction(){
		//mRobot.mouseMove(pointX, pointY);
		Point mousePoint = MouseInfo.getPointerInfo().getLocation(); 
		System.out.println("x : y = "+pointX+":"+pointX);
		mRobot.mouseMove(mousePoint.x+pointX, mousePoint.y+pointY);//(pointX,pointY)鼠标的位移
	}
	

	private void clickedAction(String trim) {
		// TODO Auto-generated method stub
		if (trim.equals(Constant.LEFT_BUTTON_CLICKED)){
			mRobot.mousePress(InputEvent.BUTTON1_MASK);
			mRobot.mouseRelease(InputEvent.BUTTON1_MASK);
		}else if (trim.equals(Constant.RIGHT_BUTTON_CLICKED)){
			mRobot.mousePress(InputEvent.BUTTON3_MASK);
			mRobot.mouseRelease(InputEvent.BUTTON3_MASK);
		}else if (trim.equals(Constant.MOUSE_WHEEL_UP)){
			mRobot.mouseWheel(-4);//负值表示上移
		}else if (trim.equals(Constant.MOUSE_WHEEL_DOWM)){
			mRobot.mouseWheel(4);//负值表示下移
		}

	}

	private void keyAction(String keyCode) {
		// TODO Auto-generated method stub
		System.out.println("keyCode"+keyCode);
		switch (keyCode) {
		case "a":
		mRobot.keyPress(KeyEvent.VK_A);
			mRobot.keyRelease(KeyEvent.VK_A);
			break;
		case "b":
			mRobot.keyPress(KeyEvent.VK_B);
			mRobot.keyRelease(KeyEvent.VK_B);
			break;
		case "c":
			mRobot.keyPress(KeyEvent.VK_C);
			mRobot.keyRelease(KeyEvent.VK_C);
			break;
		case "d":
			mRobot.keyPress(KeyEvent.VK_D);
			mRobot.keyRelease(KeyEvent.VK_D);
			break;
		case "e":
			mRobot.keyPress(KeyEvent.VK_E);
			mRobot.keyRelease(KeyEvent.VK_E);
			break;
		case "f":
			mRobot.keyPress(KeyEvent.VK_F);
			mRobot.keyRelease(KeyEvent.VK_F);
			break;
		case "g":
			mRobot.keyPress(KeyEvent.VK_G);
			mRobot.keyRelease(KeyEvent.VK_G);
			break;
		case "h":
			mRobot.keyPress(KeyEvent.VK_H);
			mRobot.keyRelease(KeyEvent.VK_H);
			break;
		case "i":
			mRobot.keyPress(KeyEvent.VK_I);
			mRobot.keyRelease(KeyEvent.VK_I);
			break;
		case "j":
			mRobot.keyPress(KeyEvent.VK_J);
			mRobot.keyRelease(KeyEvent.VK_J);
			break;
		case "k":
			mRobot.keyPress(KeyEvent.VK_K);
			mRobot.keyRelease(KeyEvent.VK_K);
			break;
		case "l":
			mRobot.keyPress(KeyEvent.VK_L);
			mRobot.keyRelease(KeyEvent.VK_L);
			break;
		case "m":
			mRobot.keyPress(KeyEvent.VK_M);
			mRobot.keyRelease(KeyEvent.VK_M);
			break;
		case "n":
			mRobot.keyPress(KeyEvent.VK_N);
			mRobot.keyRelease(KeyEvent.VK_N);
			break;
		case "o":
			mRobot.keyPress(KeyEvent.VK_O);
			mRobot.keyRelease(KeyEvent.VK_O);
			break;
		case "p":
			mRobot.keyPress(KeyEvent.VK_P);
			mRobot.keyRelease(KeyEvent.VK_P);
			break;
		case "q":
			mRobot.keyPress(KeyEvent.VK_Q);
			mRobot.keyRelease(KeyEvent.VK_Q);
			break;
		case "r":
			mRobot.keyPress(KeyEvent.VK_R);
			mRobot.keyRelease(KeyEvent.VK_R);
			break;
		case "s":
			mRobot.keyPress(KeyEvent.VK_S);
			mRobot.keyRelease(KeyEvent.VK_S);
			break;
		case "t":
			mRobot.keyPress(KeyEvent.VK_T);
			mRobot.keyRelease(KeyEvent.VK_T);
			break;
		case "u":
			mRobot.keyPress(KeyEvent.VK_U);
			mRobot.keyRelease(KeyEvent.VK_U);
			break;
		case "v":
			mRobot.keyPress(KeyEvent.VK_V);
			mRobot.keyRelease(KeyEvent.VK_V);
			break;
		case "w":
			mRobot.keyPress(KeyEvent.VK_W);
			mRobot.keyRelease(KeyEvent.VK_W);
			break;
		case "x":
			mRobot.keyPress(KeyEvent.VK_X);
			mRobot.keyRelease(KeyEvent.VK_X);
			break;
		case "y":
			mRobot.keyPress(KeyEvent.VK_Y);
			mRobot.keyRelease(KeyEvent.VK_Y);
			break;
		case "z":
			mRobot.keyPress(KeyEvent.VK_Z);
			mRobot.keyRelease(KeyEvent.VK_Z);
			break;
		case " ":
			mRobot.keyPress(KeyEvent.VK_SPACE);
			mRobot.keyRelease(KeyEvent.VK_SPACE);
			break;
		case "0":	
			mRobot.keyPress(KeyEvent.VK_0);
			mRobot.keyRelease(KeyEvent.VK_0);
			break;
		case "1":	
			mRobot.keyPress(KeyEvent.VK_1);
			mRobot.keyRelease(KeyEvent.VK_1);
			break;
		case "2":	
			mRobot.keyPress(KeyEvent.VK_2);
			mRobot.keyRelease(KeyEvent.VK_2);
			break;
		case "3":	
			mRobot.keyPress(KeyEvent.VK_3);
			mRobot.keyRelease(KeyEvent.VK_3);
			break;
		case "4":	
			mRobot.keyPress(KeyEvent.VK_4);
			mRobot.keyRelease(KeyEvent.VK_4);
			break;
		case "5":	
			mRobot.keyPress(KeyEvent.VK_5);
			mRobot.keyRelease(KeyEvent.VK_5);
			break;
		case "6":	
			mRobot.keyPress(KeyEvent.VK_6);
			mRobot.keyRelease(KeyEvent.VK_6);
			break;
		case "7":	
			mRobot.keyPress(KeyEvent.VK_7);
			mRobot.keyRelease(KeyEvent.VK_7);
			break;
		case "8":	
			mRobot.keyPress(KeyEvent.VK_8);
			mRobot.keyRelease(KeyEvent.VK_8);
			break;
		case "9":	
			mRobot.keyPress(KeyEvent.VK_9);
			mRobot.keyRelease(KeyEvent.VK_9);
			break;
		case "enter":
			mRobot.keyPress(KeyEvent.VK_ENTER);
			mRobot.keyRelease(KeyEvent.VK_ENTER);
			break;
		}
		
			
	}
	
	@Override
	public void run(){
		processInfo();
	}
}