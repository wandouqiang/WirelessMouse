package com.wandou.client;
//还需要添加一段保存配置的程序
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private static final String TAG = "MainActivity TAG";
	private Button buttonConnect;
	private TextView textView ;
	private EditText serverIPText, portNumText;
    private CheckBox checkBox;
	private String ADD = null,serverIP = "";
	private int PORT,portNum = 20155;
    private boolean mFlag = true;
	
	private Socket socket = null;
	private InputStream in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setTitle("远程控制Phone");
		buttonConnect = (Button) findViewById(R.id.buttonConnect);
		serverIPText = (EditText) findViewById(R.id.serverIP);
		portNumText = (EditText) findViewById(R.id.portNum);
        checkBox = (CheckBox) findViewById(R.id.checkBox1);
        textView = (TextView) findViewById(R.id.textView3);
		textView.setText("使用方法:手机联网输入PC端IP，点击连接PC按钮后投影屏幕，点击屏幕即在相应位置完成一次单击，Left按钮完成一次双击，Right按钮完成一次右键单击");
  
        SharedPreferences preferences = this.getSharedPreferences(Constant.FILE_NAME,MODE_PRIVATE);
        ADD = preferences.getString("ADD", null);  //defValue Value to return if this preference does not exist.
        PORT = preferences.getInt("PORT", 20155);
        if (ADD !=null){ //ADD内有返回值
        	serverIPText.setText(ADD);
        	portNumText.setText(String.valueOf(PORT));
        }
        
		buttonConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				//检查网络状态
				ConnectivityManager con=(ConnectivityManager)getSystemService(LoginActivity.CONNECTIVITY_SERVICE);  
				boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
				boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
				if(wifi|internet){  
				    //执行相关操作  
					new LoginThread().start();
				}else{  
				    Toast.makeText(getApplicationContext(),  
				            "亲，网络连了么？", Toast.LENGTH_LONG)  
				            .show();  
				}  

			}
		});
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mFlag = isChecked;
			}
		});
	}
	private boolean login(String add, int port) {

			try {
				socket = new Socket(add, port);//注意在mannifest中开通INTERNET权限	
				Log.e(TAG, "welldown");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block		
				Toast.makeText(LoginActivity.this, "UnknownHost", Toast.LENGTH_LONG).show();
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Toast.makeText(LoginActivity.this, "unsuccessful", Toast.LENGTH_LONG).show();
				Log.e(TAG,"what's the fuck!");	
				if (socket != null){
					Log.e(TAG,"socket != null");
					try {
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				e.printStackTrace();
				return false;
			}
		Constant.SOCKET = socket;// 保存在全局变量中，以供DisplayActivity中使用
		return true;
	}

	private class LoginThread extends Thread {

		@Override
		public void run() {
			serverIP = serverIPText.getText().toString();
			portNum = Integer.parseInt(portNumText.getText().toString());			
			if (mFlag){
				SharedPreferences sp = getSharedPreferences(
						Constant.FILE_NAME, MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.putString("ADD", serverIP);
				editor.putInt("PORT", portNum);
				editor.commit();				
			}
			Log.e(TAG, "host:" + serverIP + "  portNum:" + portNum);
			if (login(serverIP, portNum)) {
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, DisplayActivity.class);
			startActivity(intent);
		}
		}

	}
}
