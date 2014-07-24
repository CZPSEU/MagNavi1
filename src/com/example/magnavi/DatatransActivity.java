package com.example.magnavi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DatatransActivity extends Activity
{
  TextView result_text,sendmessage;
  Button start,clear;
//  mag_protocol mag_protocol;
  BluetoothAdapter adapter;
  private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP����UUID��
  BluetoothDevice _device = null;     //�����豸
  BluetoothSocket _socket = null;      //����ͨ��socket
  private InputStream blueStream;    //������������������������
  private OutputStream outstream;
  myHandler mmhandler;
  String rec="";
  public String mydatabuffer="";
  private static String readMessage="";
	public double []data=new double[1000];//����1000���������ʾ
	public String mysubstring,sendstring;
	public static final String PACKHEAD = "00ff1";
	private Timer timer = new Timer();
    private GraphicalView chart;
    private TextView textview;
    private TimerTask task;
    private int addY = -1;
	private long addX;
	/**��������*/
    private static final int SERIES_NR=1;
    private static final String TAG = "message";
    private TimeSeries series1;
    private XYMultipleSeriesDataset dataset1;
    private Handler handler;
    private Random random=new Random();
    
    /**ʱ������*/
    Date[] xcache = new Date[20];
	/**����*/
    int[] ycache = new int[20];
    
    
  public boolean flag_rec_thread=false;
  public static byte[] result = new byte[1024];

  
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datatrans_);
		result_text=(TextView)findViewById(R.id.result_text);
		start = (Button) findViewById(R.id.start);
		clear = (Button) findViewById(R.id.clear);
//		sendmessage=(TextView)findViewById(R.id.setmessage);
//		mag_protocol=new mag_protocol();
		
//		��ȡϵͳĬ������
		adapter = BluetoothAdapter.getDefaultAdapter();
		getThread.start();//�߳�����  
		mmhandler = new myHandler();
		
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.linearlayout);
        //����ͼ��
		chart = ChartFactory.getTimeChartView(this, getDateDemoDataset(), getDemoRenderer(), "hh:mm:ss");
		layout.addView(chart, new LayoutParams(LayoutParams.WRAP_CONTENT,380));
		
//		handler = new Handler() {
//        	@Override
//        	public void handleMessage(Message msg) {
//        		//ˢ��ͼ��
//        		updateChart();
//        		super.handleMessage(msg);
//        	}
//        };
		
		
  /*      task = new TimerTask() {
        	@Override
        	public void run() {
        		
                flag_rec_thread=true;
        		if (flag_rec_thread)
        		{
        			result_text.setText("���ڽ���");
        		}else {
        			result_text.setText("ֹͣ����");
        		}
        		
        		
        		Message message = new Message();
        	    message.what = 200;
        	    handler.sendMessage(message);
        	}
        };
        timer.schedule(task, 2*1000,100000);
		*/
	}
	
	
	private void updateChart(int ydata) {
	    //�趨����Ϊ20
	    int length = series1.getItemCount();
	    if(length>=20) length = 20;
	    addY=ydata;
	    addX=new Date().getTime();
	    
	    //��ǰ��ĵ���뻺��
		for (int i = 0; i < length; i++) {
			xcache[i] =  new Date((long)series1.getX(i));
			ycache[i] = (int) series1.getY(i);
		}
	    
		series1.clear();
		//���²����ĵ����ȼ��뵽�㼯�У�Ȼ����ѭ�����н�����任���һϵ�е㶼���¼��뵽�㼯��
		//�����������һ�°�˳��ߵ�������ʲôЧ������������ѭ���壬������²����ĵ�
		series1.add(new Date(addX), addY);
		for (int k = 0; k < length; k++) {
    		series1.add(xcache[k], ycache[k]);
    	}
		//�����ݼ�������µĵ㼯
		dataset1.removeSeries(series1);
		dataset1.addSeries(series1);
		//���߸���
		chart.invalidate();
    }
/**
 * �趨ͼ����ʽ
 * @return
 */
   private XYMultipleSeriesRenderer getDemoRenderer() {
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setChartTitle("ʵʱ����");//����
	    renderer.setChartTitleTextSize(30);
	    renderer.setXTitle("ʱ��");    //x��˵��
	    renderer.setYTitle("������Ϣ");
	    renderer.setAxisTitleTextSize(30);
	    renderer.setAxesColor(Color.BLACK);
	    renderer.setLabelsTextSize(30);    //����̶������С
	    renderer.setLabelsColor(Color.BLACK);
	    renderer.setLegendTextSize(30);    //����˵��
	    renderer.setXLabelsColor(Color.BLACK);
	    renderer.setYLabelsColor(0,Color.BLACK);
	    renderer.setXLabelsAlign(Align.RIGHT); // ����x��̶ȵ���ֵ��ʾ��x����·�  
        renderer.setYLabelsAlign(Align.RIGHT); // ����y��̶ȵ���ֵ��ʾ��y�����  
        renderer.setZoomButtonsVisible(true); // ���ÿ�������
     	renderer.setShowLegend(false);
	    renderer.setMargins(new int[] {50, 80, 100, 50});
	    XYSeriesRenderer r = new XYSeriesRenderer();
	    r.setColor(Color.BLUE);
	    r.setChartValuesTextSize(30);
	    r.setChartValuesSpacing(3);
	    r.setPointStyle(PointStyle.CIRCLE);
	    r.setFillBelowLine(true);
	    r.setFillBelowLineColor(Color.WHITE);
	    r.setFillPoints(true);
	    renderer.addSeriesRenderer(r);
	    renderer.setMarginsColor(Color.WHITE);
	    renderer.setPanEnabled(true,false);
	    renderer.setShowGrid(true);
 	    renderer.setGridColor(Color.GRAY);
	    renderer.setYAxisMax(50);
	    renderer.setYAxisMin(-30);
	    renderer.setInScroll(true);  //������С
	    return renderer;
	  }
   /**
    * ���ݶ���
    * @return
    */
   private XYMultipleSeriesDataset getDateDemoDataset() {
	    dataset1 = new XYMultipleSeriesDataset();
	    final int nr = 10;
	    long value = new Date().getTime();
//	    Random r = new Random();
	    int r=0;
	    for (int i = 0; i < SERIES_NR; i++) {
	      series1 = new TimeSeries("Demo series " + (i + 1));
	      for (int k = 0; k < nr; k++) {
	        series1.add(new Date(value+k*1000), 20 +r);
	      }
	      dataset1.addSeries(series1);
	    }
	    Log.i(TAG, dataset1.toString());
	    return dataset1;
	  }
//    @Override
//    public void onDestroy() {
//    	//����������ʱ�ص�Timer
//    	timer.cancel();
//    	super.onDestroy();
//    };

	public void onstart(View v)
	{
//		ֱ�Ӵ�����
		adapter.enable();
//		��ʼ����
	//	adapter.startDiscovery();
		_device = adapter.getRemoteDevice("81:F2:6D:98:0E:A0");
        // �÷���ŵõ�socket
        try{
        	_socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
        }catch(IOException e){
//        	Toast.makeText(this, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
        }
        try
		{	
			_socket.connect();
			Log.i("SOCKET", "����"+_device.getName()+"�ɹ���");
			result_text.setText("���ӳɹ�");
			//Toast.makeText(this, "����"+_device.getName()+"�ɹ���", Toast.LENGTH_SHORT).show();
		} catch (IOException e)
		{
			
    		try
			{
//    		Toast.makeText(this, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
			_socket.close();
			result_text.setText("��������");
			_socket = null;
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
//				Toast.makeText(this, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();	
			}            		
			// TODO Auto-generated catch block
			return;
		}

        //�򿪽����߳�
        try{
    		blueStream = _socket.getInputStream();   //�õ���������������
    		//blueoutOutputStream=_socket.getOutputStream();//�õ������������
//    		Toast.makeText(this, "���������ɹ�", Toast.LENGTH_SHORT).show();
    		}catch(IOException e){
//    			Toast.makeText(this, "��������ʧ�ܣ�", Toast.LENGTH_SHORT).show();
    			return;
    		}
        
        flag_rec_thread=true;
		if (flag_rec_thread)
		{
			result_text.setText("���ڽ���");
		}else {
			result_text.setText("ֹͣ����");
		}
		 
	}
	
	public void onclear(View v){
		result_text.setText("");

	}
	
	public static String ByteToString(byte[] bytes)

	{

	String returnString="";

	for (int i = 0; i < bytes.length; i++)
	{
		returnString+= Integer.toHexString(bytes[i]&0xff)+" ";
	}
	
		return returnString ;

	}
	
	public class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {

			String text = "";
				if(msg.what == 0x123){
					
					text = (String) msg.obj;
					int ydata = Integer.parseInt(text);
					updateChart(ydata);
			
				}
			super.handleMessage(msg);
		}
	}
//��ȡ�������ݵ����߳�
	Thread getThread =new Thread(){
		@Override
		public void run() {
			
			while (!currentThread().isInterrupted()) {
				if(flag_rec_thread)
				{
					try{
						blueStream = _socket.getInputStream();
						int num;

						byte[] buffer =new byte[1024];
						num = blueStream.read(buffer);
							for(int i = 0 ; i < num; i++)
							{ 		  
							//readMessage[i]=String.format("%2x", bytes[i-count]);	
							mydatabuffer +=Integer.toHexString(buffer[i]&0xff);
							}
							sendstring = dealwithstring(mydatabuffer);

						Message message = mmhandler.obtainMessage();  
			            message.what = 0x123;  

			            message.obj = sendstring;  

			            mmhandler.sendMessage(message);  
					}catch(IOException e) {  
		                break;  
		            }  
					
					try
					{
						sleep(100);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			
			
			 
		}
	};
	private byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }
//	���ݽ���
	private String dealwithstring(String databuffer){
//		int index;
//		index = readMessage.indexOf("f0f0f1");
//		end = readMessage.substring(index+6, index+8);
//		return end;
		int length = databuffer.length();
		int index = databuffer.indexOf(PACKHEAD);
		if((length-index)>=4){
			mysubstring = databuffer.substring(index+5, index+7);
			mydatabuffer = "";
		}else{
			
		}
		return mysubstring;
	 }
//	����ͼ��
	
	
	}

