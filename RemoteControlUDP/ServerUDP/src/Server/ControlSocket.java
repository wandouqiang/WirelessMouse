package Server;
/*
 * 用的是IP协议
 * */
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.xml.ws.handler.MessageContext;

import org.omg.CORBA.PRIVATE_MEMBER;

public class ControlSocket implements Runnable {
	private DatagramSocket mSocket = null;
	private DatagramPacket inPacket = null;
	

	private byte[] inCache;
	
	
	private MsgReceivedThread msgR = null;
	private MsgSendThread msgS = null;
	
	private int portNum;
	private boolean mFlag = true;;

	public ControlSocket(int portNum) {
		this.portNum = portNum;		
		this.inCache = new byte[256];
		this.inPacket = new DatagramPacket(inCache, inCache.length);

		try {
			mSocket = new DatagramSocket(this.portNum);
			System.out.println("服务端启动!");  
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void startListening(){
		while (true) {
			try {
				mSocket.receive(inPacket);// 产生内部阻塞
				System.out.println("receive the message!");
				String receivedMsg = new String(inPacket.getData(), 0,inPacket.getLength());					
			    msgR = new MsgReceivedThread(receivedMsg);
			  //  msgS = new MsgSendThread(mSocket,inPacket);
                this.startAction();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void startAction() {
		// TODO Auto-generated method stub
		if (msgR != null){
			msgR.start();
			System.out.println("ReceivedThread running!");
		}
		if (msgS != null){
			msgS.start();
			System.out.println("SendThread running!");
		}
	}
	
	public  void setImgSize(int w, int h ) {
		
	}
	
	public void setFlag(boolean flag){
		this.mFlag = flag;
	}


	public void quit (){
		if (mSocket != null){
			mSocket.close();
		}
		System.out.println("Service closed!");	
//		writeThread.quit();
//		readThread.quit();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		startListening();
	}
	
	
}
