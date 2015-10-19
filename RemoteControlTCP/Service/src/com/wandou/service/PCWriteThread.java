package com.wandou.service;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class PCWriteThread extends Thread{
	private DataOutputStream dos = null;
	private Robot robot;
	private Dimension dimension;
	private Rectangle screenRect;
	private int pcScreenWidth,pcScreenHeight;
	private int phoneScreenWidth,phoneScreenHeight;
	private String imgPath;
	private ByteArrayOutputStream imageOutStream;
	private BufferedImage targetImage;
	
	private boolean mRunning,mQuit;
	private boolean writeStart = false;
	
	public PCWriteThread(OutputStream os) throws AWTException {
		// TODO Auto-generated constructor stub
		this.dos = new DataOutputStream(os);
		this.robot = new Robot();
		this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.pcScreenWidth = dimension.width;
		this.pcScreenHeight = dimension.height;
		Constant.pcScreenWidth = pcScreenWidth;
		Constant.pcScreenHeight = pcScreenHeight;
	    System.out.println("pc ="+pcScreenWidth+":"+pcScreenHeight);
		this.screenRect = new Rectangle(pcScreenWidth,pcScreenHeight);
		this.imgPath = this.getClass().getResource("").getFile()+"mouse2.png";
		this.imageOutStream = new ByteArrayOutputStream();
	}
	
	public void setBitmapSize(int w, int h){
		this.writeStart = true;
		this.phoneScreenWidth = w;
		this.phoneScreenHeight = h;
		this.targetImage = new BufferedImage(phoneScreenWidth,phoneScreenHeight,BufferedImage.TYPE_INT_RGB);
	}
	
	private void _sendImg() throws IOException{
		mQuit = false;
		mRunning = true;
		System.out.println(imgPath);
		BufferedImage cursor = ImageIO.read(new File(imgPath));//将图片加载到内存中

		while (mRunning ){
		if (mQuit)  break;
			Point point = MouseInfo.getPointerInfo().getLocation();
			BufferedImage bfImg = robot.createScreenCapture(screenRect);//获取电脑屏幕截图但不包含光标
			bfImg.createGraphics().drawImage(cursor,point.x,point.y,null);//将cursor画到(x,y)的位置		
		   compressImage(bfImg);
	}
		mRunning = false;
	}

	private void compressImage(BufferedImage _bfImg) throws IOException {
		// TODO Auto-generated method stub
		Graphics2D grph = targetImage.createGraphics();
		grph.drawImage(_bfImg, 0, 0, phoneScreenWidth, phoneScreenHeight, null);// 画一个跟手机屏幕一样大的图片
		grph.dispose();
		targetImage.flush();// 更新目标图像
		imageOutStream.reset();// Resets the count field of this byte array output stream to zero
		boolean resultWrite = ImageIO.write(targetImage, "jpg", imageOutStream);
		if (!resultWrite) {
			return;
		}
		byte[] tagInfo = imageOutStream.toByteArray();
		dos.writeInt(tagInfo.length + 5);
		dos.writeByte((byte)5);
		dos.write(tagInfo);
		dos.flush();
		//System.out.println("Write image successfully!");
	  	try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void quit() {
		try {
			dos.close();
			dos = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mQuit = true;
		interrupt();
		while (mRunning) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
		    }
		}
		mRunning = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			dos.write((byte) 5);
			dos.writeInt(Constant.ACK_CMD);	
			System.out.println("ack_cmd send!");
			while (!writeStart) {
				Thread.sleep(200);
			}
			_sendImg();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
