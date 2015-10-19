package com.wandou.client;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class DisplayActivity extends Activity implements OnClickListener {
	private static final String TAG = "DisplayActivity TAG";

	private MySurfaceView surfaceView;
	private PhoneReadThread readThread;
	private PhoneWriteThread writeThread;
	private DisplayMetrics dm;
	private int phoneScreenWidth, phoneScreenHeight;

	private Button leftButton,rightButton;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		//xml布局文件中相应的需要定义为com.wandou.client.MySurfaceView
        surfaceView = (MySurfaceView)findViewById(R.id.surfaceView);  
       
		leftButton = (Button) findViewById(R.id.buttonLeft);
		rightButton = (Button) findViewById(R.id.buttonRight);

		leftButton.setOnClickListener(this);
		rightButton.setOnClickListener(this);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		phoneScreenWidth = dm.widthPixels;
		phoneScreenHeight = dm.heightPixels;
		Log.e(TAG, "width:" + phoneScreenWidth + "  height:"
				+ phoneScreenHeight);
		// 启动写线程，将鼠标的坐标传回PC
		try {
			writeThread = new PhoneWriteThread(Constant.SOCKET.getOutputStream(),
					                                                              phoneScreenWidth,phoneScreenHeight);
					
			writeThread.start();
			Log.e(TAG, "the WriteThread turn on!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "WriteFuck");
			e.printStackTrace();
		}

		// 启动读线程，将PC屏幕在phone端显示
		try {
			readThread = new PhoneReadThread(Constant.SOCKET.getInputStream());
			readThread.setSurfaceView(surfaceView);
			surfaceView.setRectDst(phoneScreenWidth, phoneScreenHeight);
			readThread.start();
			Log.e(TAG, "the ReadThread turn on!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "ReadFuck");
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonLeft:
			writeThread.leftBtnClicked();
			Log.e(TAG,"left button clicked");
			break;

		case R.id.buttonRight:
			writeThread.rightBtnClicked();
			Log.e(TAG,"right button clicked");
			break;
		}

	}
	// 实现onTouchEvent方法
    public boolean onTouchEvent(MotionEvent event) {
        // 如果是按下操作
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int X = (int)event.getX();
            int Y  = (int)event.getY();
            writeThread.setPoint(X,Y);
    		Log.e(TAG,"a touch happened");
        }
        return super.onTouchEvent(event);
    }

}
