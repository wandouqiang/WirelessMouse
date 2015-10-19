package Server;

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
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.imageio.ImageIO;

/**
 *Title:
 *@author 豌豆先生 jitsiang@163.com
 *@date 2015年6月3日
 *@version 
 */
public class MsgSendThread extends Thread{
	DatagramSocket mSocket = null;
	private DatagramPacket inPacket = null;
	private DatagramPacket outPacket = null;
	
	private byte[] outCache = new byte[256];
	
	public MsgSendThread(DatagramSocket mSocket,DatagramPacket packet) {
		this.outPacket = new DatagramPacket(outCache, outCache.length);
		this.mSocket = mSocket;
		this.inPacket = packet;
	}	
	
	private void doResponse(){
		Point mousePoint = MouseInfo.getPointerInfo().getLocation(); 
		System.out.println(mousePoint.x+"and Y:"+mousePoint.y);
		
		InetAddress addr = inPacket.getAddress();
		int port = inPacket.getPort();
		outPacket.setAddress(addr);
		outPacket.setPort(port);
		String responseMsg = mousePoint.x+"/"+mousePoint.y+"/";
		System.out.println(responseMsg);
		outPacket.setData(responseMsg.getBytes());
		try {
			mSocket.send(outPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		doResponse();
	}
}
	
	/*
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
	

		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.pcScreenWidth = dimension.width;
		this.pcScreenHeight = dimension.height;
	//	Constant.pcScreenWidth = pcScreenWidth;
	//	Constant.pcScreenHeight = pcScreenHeight;
	 //   System.out.println("pc ="+pcScreenWidth+":"+pcScreenHeight);
		this.screenRect = new Rectangle(pcScreenWidth,pcScreenHeight);
		this.imgPath = this.getClass().getResource("").getFile()+"mouse.png";
		this.imageOutStream = new ByteArrayOutputStream();
	}
	
	public void setBitmapSize(int w, int h){
		 
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
		String msg = new String(tagInfo);//从byte转化为String不会出错但从string转化为Byte就会出错
		
		
		
	 
		//System.out.println("Write image successfully!");
	  	try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	*/
	
	
	

