package com.wandou.clientudp;

import java.net.DatagramSocket;
import java.net.SocketException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";

	private Button btnLeft, btnRight, btnUp, btnDown, btnKey,btnSet;
	private EditText serverIPText, portNumText;
	
	private String ADD, serverIP = "116.57.86.70";
	private int PORT, portNum = 20155;
	private int oldX,oldY;
	private int btnFlag = 0;
	private SendThread sendThread;
	InputMethodManager imm;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	 
        init();   
	}

	private void init() {
		// TODO Auto-generated method stub
		
		btnDown = (Button) findViewById(R.id.down);
		btnLeft = (Button) findViewById(R.id.left);
		btnRight = (Button) findViewById(R.id.right);
		btnUp = (Button) findViewById(R.id.up);
		btnKey = (Button) findViewById(R.id.key);
		btnSet = (Button) findViewById(R.id.set);
		
		btnDown.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		btnUp.setOnClickListener(this);
		btnKey.setOnClickListener(this);
		btnSet.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.down:
			sendThread.clickedDown();
			break;
		case R.id.up:
			sendThread.clickedUp();
			break;
		case R.id.left:
			sendThread.clickedLeft();
			break;
		case R.id.right:
			sendThread.clickedRight();
			break;
		case R.id.key:
			pop_keyboard();
			break;
		case R.id.set:
			config_server();
			break;
		}
	}

	// 弹出软键盘
	public void pop_keyboard() {

		imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

	}

	public boolean onKeyDown(int keyCode, KeyEvent e) {

		// Log.i("keyCode", String.valueOf(keyCode));
		// Log.i("e.getUnicodeChar", String.valueOf(e.getUnicodeChar()));
		// Log.i("toString()", e.toString());
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this)
					.setTitle("Quit")
					.setMessage("Quit?")
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,	int which) {
									
									// TODO Auto-generated method stub

								}
							})

					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int whichButton) {
									Intent exit = new Intent(Intent.ACTION_MAIN);
									exit.addCategory(Intent.CATEGORY_HOME);
									exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(exit);
									System.exit(0);
								}
							}).show();
		} else {
			sendThread.setKeyValue((char) e.getUnicodeChar());
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (sendThread == null){
			Toast.makeText(MainActivity.this, "请先点击Set按钮进行设置", Toast.LENGTH_LONG).show();
			return false;
		}

		int x = (int) event.getX();
		int y = (int) event.getY();
		Log.e(TAG, "primary location: x:" + x + " Y:" + y);

		if (btnLeft.isPressed()) {
			Log.e(TAG, "leftBtn.isPressed:" + btnLeft.isPressed());
			x = (int) event.getX(1);
			y = (int) event.getY(1);
			Log.e(TAG, "leftBtn.isPressed: X:" + x + " Y:" + y);
		}
		int distanceX = 0;
		int distanceY = 0;
		
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			oldX = x;
			oldY = y;
		//	sendThread.setPointX(oldX);
		//	sendThread.setPointY(oldY);
			Log.e(TAG,"ACTION_DOWN "+oldX+":"+oldY);
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE){
			++btnFlag;
			if (btnFlag == 1){
				oldX = x;
				oldY = y;
			}
			distanceX = x-oldX;
			distanceY = y-oldY;
			Log.e(TAG,"ACTION_MOVE "+distanceX+":"+distanceY);
			sendThread.setPointX(distanceX);
			sendThread.setPointY(distanceY);
			oldX = x;
			oldY = y;
		}
		if (event.getAction() == MotionEvent.ACTION_UP){
			btnFlag = 0;
			distanceX = x-oldX;
			distanceY = y-oldY;
			oldX = x;
			oldY = y;
			Log.e(TAG,"ACTION_UP "+distanceX+":"+distanceY);
			sendThread.setPointX(distanceX);
			sendThread.setPointY(distanceY);
		}
		return super.onTouchEvent(event);
	}
	
	 // 设置服务器IP和Port
	   public void config_server(){

		   LayoutInflater inflater = (LayoutInflater) MainActivity.this.
				   getSystemService(LAYOUT_INFLATER_SERVICE);
		   final View view = inflater.inflate(R.layout.dialog, null);
		   
		   serverIPText = (EditText)view.findViewById(R.id.editText1);
			portNumText = (EditText)view.findViewById(R.id.editText2);
		
			read_ip();
			if (!ADD.equals("")) {
				serverIPText.setText(ADD);
				portNumText.setText(String.valueOf(PORT));
			}

		   new AlertDialog.Builder(MainActivity.this).setTitle("set PC")
		   		.setView(view)
		   		.setPositiveButton("OK",new DialogInterface.OnClickListener() {   
		               
		            @Override  
		            public void onClick(DialogInterface v, int arg1){ 
		            	serverIP = serverIPText.getText().toString();
						portNum = Integer.parseInt(portNumText.getText().toString());
						save_ip();
						ConnectivityManager con=(ConnectivityManager)getSystemService(MainActivity.CONNECTIVITY_SERVICE);  
						boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
						boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
						if(wifi|internet){  
							// 执行相关操作
							try {
								sendThread = new SendThread(serverIP, portNum);
								new Thread(sendThread).start();
								Log.e(TAG, "sendthread run");
							} catch (SocketException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}else{  
						    Toast.makeText(getApplicationContext(),  
						            "亲，网络连了么？", Toast.LENGTH_LONG)  
						            .show();  
						}  
		            } 	   
		   		}).show();
	   	}
	
	
	private void save_ip() {
		// TODO Auto-generated method stub
		SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("ADD", serverIP);
		editor.putInt("PORT", portNum);
		editor.commit();
	}

	private void read_ip() {
		// TODO Auto-generated method stub
		SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
		ADD = sp.getString("ADD", "");
		PORT = sp.getInt("PORT", 0);
	}

}
