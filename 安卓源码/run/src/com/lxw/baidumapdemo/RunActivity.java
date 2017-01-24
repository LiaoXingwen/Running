package com.lxw.baidumapdemo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import locationkit.HandleLocationList;
import locationkit.LocationApplication;
import locationkit.RunLocationListener;
import servicekit.RunAccelerometerListenerService;
import tools.DateTool;
import tools.GetWeather;
import tools.PublicSrc;
import tools.UploadLoaction;
import viewtools.DisplayUtil;
import viewtools.MyDialog;
import viewtools.TimeShow;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import entity.LoactionData;
import entity.RunData;
import entity.RunJsonTool;

public class RunActivity extends Activity {
	public static boolean ISOPENRUN = false ;//�Ƿ����ܲ��У����������ж�

	MapView mapView ;
	HorizontalScrollView buttonScroll;

	LinearLayout showLayout , lockLayout;
	TimeShow timeShow ;
	ImageView lock;
	Button startButton, stopButton ,cacelButton;
	int width,height,mapheight,showheight,buttonheight,lockheight;
	RunData runData ; 
	TextView runNumber ;
	RelativeLayout covering ; 
	private int topMenusHeight;
	boolean isLock = false ;
	private BaiduMap mBaiduMap;
	private Polyline mVirtureRoad;
	Intent runService ; 
	boolean isrun = false ;
	static PowerManager pm ;
	private WakeLock mWakeLock;
	private BDLocation location;
	private TextView speed;
	private TextView spacePower;
	private TextView allSpeed;
	private Marker overlay;
	private MapStatusUpdate msUpdate;
	private BitmapDescriptor bmStart;
	private MarkerOptions startMarker;
	private MarkerOptions endMarker;
	private PolylineOptions polyline;
	private MarkerOptions markerOptions;
	private BitmapDescriptor bmEnd;
	private LocationApplication locationApplication;
	HandleLocationList locationList ;
	private Vector<LatLng> latlngs;
	DecimalFormat decimalFormat=new DecimalFormat("0.00");

	double k1 = 0.1355 ;//һСʱ�˹���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ISOPENRUN =true;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.run_main);
		runData = new RunData();
		locationList = new HandleLocationList();
		runService = new Intent(getApplicationContext(), RunAccelerometerListenerService.class);
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE); 
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "S"); 
		mWakeLock.acquire();

		bmStart = BitmapDescriptorFactory.fromResource(R.drawable.icon_start);
		bmEnd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
		latlngs = new Vector<LatLng>();
		RunHanbleTool hanbleTool = new RunHanbleTool(this);
		initView();
		initSize();
		bindListener();
		initMap();
		initLocationApp();
	}








	/**
	 * ��ʼ���ؼ�
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void initView() {
		mapView = (MapView) findViewById(R.id.RmapView);
		buttonScroll = (HorizontalScrollView) findViewById(R.id.Rbuttoncontent);
		startButton = (Button) findViewById(R.id.Rstartbutton);
		stopButton = (Button) findViewById(R.id.Rstopbutton);
		cacelButton = (Button) findViewById(R.id.Rcacelbutton);
		showLayout = (LinearLayout) findViewById(R.id.Rshowcontent);
		lockLayout = (LinearLayout) findViewById(R.id.Rlockcontent);
		timeShow = (TimeShow) findViewById(R.id.timeShow);
		//����
		runNumber = (TextView) findViewById(R.id.runNumber);

		lock = (ImageView) findViewById(R.id.Rlock);
		covering = (RelativeLayout) findViewById(R.id.lockcovering);

		//�����������¼�����    
		buttonScroll.setHorizontalScrollBarEnabled(false);
		buttonScroll.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
	}
	/**
	 * ��ʼ���ߴ�
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void initSize() {
		width = DisplayUtil.getWidth(getApplicationContext());
		height = DisplayUtil.getHeight(getApplicationContext());

		mapheight = (int) (height*0.65);
		showheight = (int) (height*0.20); 
		buttonheight = (int) (height*0.1);
		lockheight = (int) (height*0.05);

		mapView.setLayoutParams(new LinearLayout.LayoutParams(width, mapheight));


		showLayout.setLayoutParams(new LinearLayout.LayoutParams(width, showheight));
		buttonScroll.setLayoutParams(new LinearLayout.LayoutParams(width, buttonheight));
		lockLayout.setLayoutParams(new LinearLayout.LayoutParams(width, lockheight));

		startButton.setLayoutParams(new LinearLayout.LayoutParams(width, buttonheight));
		stopButton.setLayoutParams(new LinearLayout.LayoutParams((int) (width*0.6), buttonheight));
		cacelButton.setLayoutParams(new LinearLayout.LayoutParams((int) (width*0.4), buttonheight));

		covering.setLayoutParams(new FrameLayout.LayoutParams(width, (int) (height*0.95)));;

		TextView t1 = (TextView) findViewById(R.id.runNumbertip);
		t1.setWidth((int) (width*0.2));
		t1.setHeight(showheight/2);

		TextView t2 = (TextView) findViewById(R.id.newSpeedtip);
		t2.setWidth((int) (width*0.2));
		t2.setHeight(showheight/2);

		runNumber = (TextView) findViewById(R.id.runNumber);
		runNumber.setWidth((int) (width*0.3));
		runNumber.setHeight(showheight/2);

		speed = (TextView) findViewById(R.id.newSpeed);
		speed.setWidth((int) (width*0.3));
		speed.setHeight(showheight/2);


		TextView t5 = (TextView) findViewById(R.id.spacePowertip);
		t5.setWidth((int) (width*0.2));
		t5.setHeight(showheight/2);

		TextView t6 = (TextView) findViewById(R.id.allSpeedtip);
		t6.setWidth((int) (width*0.2));
		t6.setHeight(showheight/2);

		spacePower = (TextView) findViewById(R.id.spacePower);
		spacePower.setWidth((int) (width*0.3));
		spacePower.setHeight(showheight/2);

		allSpeed = (TextView) findViewById(R.id.allSpeed);
		allSpeed.setWidth((int) (width*0.3));
		allSpeed.setHeight(showheight/2);

	}
	int lastLocatoinTime = 0 ;//���һ�ζ�λ�ɹ�
	/**
	 *��ʼ����λ
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void initLocationApp() {


		//������λ
		locationApplication = new LocationApplication(getApplicationContext()) ; 
		locationApplication.setLocationClientOption(LocationMode.Hight_Accuracy, 2000, true, true, true, false);
		locationApplication.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				boolean isLocation = false ;
				if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
					isLocation = true;
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
					isLocation = true;
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
					isLocation = true;
				} 
				if (isLocation) {
					RunActivity.this.location  = location ;
					if (!isrun) {
						setLocatioPoint();
					}else {
						if (location.getRadius()<150) {
							int time = (int) (System.currentTimeMillis()/1000);
							LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
							if (latlngs.size()>0) {
								double distant = HandleLocationList.gps2m(latlngs.lastElement(), latLng);
								if (distant>4) {
									if (location.getRadius()<60) {
										addLatLng(latLng, time,distant);
									}else {
										if ((time-lastLocatoinTime)*10<distant) {
											addLatLng(latLng, time,distant);
										}
									}
								}
							}else {
								addLatLng(latLng, time,0);
								startTime();
							}
						}}
				}
			}
		});

		locationApplication.startLocation();

	}




	double  power = 0;

	void addLatLng(LatLng latLng,int time ,double distant){
		latlngs.add(latLng);
		UploadLoaction uploaction = new UploadLoaction();
		uploaction.execute(location.getLatitude(),location.getLongitude());
		drawHistoryTrack(latlngs);
		double allrun = HandleLocationList.calRun(latlngs);
		setRunNumber(decimalFormat.format(allrun/1000));
		setAllSpeed(decimalFormat.format(allrun/getSpaceTime()));
		if (distant>0) {
			double speed  = distant/(time-lastLocatoinTime) ;
			if (!Double.isNaN(speed)||!Double.isInfinite(speed)) {
				setNewSpeed(decimalFormat.format(speed));
				power += k1*(time-lastLocatoinTime)*(speed/8);
				setSpacePower(decimalFormat.format(power));
			}

		}
		lastLocatoinTime = time ;
	}

	/**
	 * ���¼�
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	void bindListener(){
		//��ʼ��ť
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//�����켣����
				buttonScroll.smoothScrollTo(width, 0);
				runData.setStartTime(DateTool.getNewTime());
				isrun = true ; 
				startService(runService);

				locationApplication.startLocation();
			}
		});


		//������ť
		cacelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonScroll.smoothScrollTo(0, 0);
				stopService(runService);
				runData.setEndTime(DateTool.getNewTime());
				isrun = false ; 
				stopService(runService);
				locationApplication.stopLocation();
				runData.setList(latlngs);
				runData.setSpaceTime(timeShow.getSpaceTime());
				//ֹͣ�켣����
				timeShow.cacel() ;
				final double D =HandleLocationList.calRun(runData.getList());
				if (D>100) {
					final MyDialog savedDialog = new MyDialog(RunActivity.this);
					savedDialog.settitle("���ݱ���");
					savedDialog.setmsg("�Ƿ񱣴�˴����ݣ�");
					savedDialog.setOnPositiveListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (RunJsonTool.saveFile(runData)) {
								Toast.makeText(RunActivity.this, "����ɹ�", 0).show();
								savedDialog.cancel();
								PublicSrc.RUNDATA.addRun(D/1000);
							}else {
								Toast.makeText(getApplicationContext(), "����ʧ��,����sd�Ƿ��д", 0).show();
							}
						}
					});

					savedDialog.setOnNegativeListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(), "�˴����ݽ������浽�ҵ��ܲ���¼�����ܲ���·���Խ����¼������", 1 ).show();
							savedDialog.cancel();
						}
					});	
					savedDialog.show();
				}else {
					Toast.makeText(getApplicationContext(), "����̫�̣��˴����ݽ������浽�ҵ��ܲ���¼�����ܲ���·���Խ����¼������", 1).show();
				}
			}
		});

		//���������ť
		lock.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				ImageView view = (ImageView)v;
				if (!isLock) {
					covering.setVisibility(View.VISIBLE);
					v.setBackgroundColor(0x00ffffff);
					view.setImageResource(R.drawable.lock);
					isLock=true;
				}
			}
		});


		lock.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				ImageView view = (ImageView)v;
				if (isLock) {
					covering.setVisibility(View.GONE);
					v.setBackgroundColor(0x00ffffff);
					view.setImageResource(R.drawable.nolock);
					isLock=false;
					return true;
				}
				return false;
			}
		});

		ImageView locationImageView = (ImageView) findViewById(R.id.location);

		locationImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (location!=null) {
					setLocatioPoint();
				}else {
					Toast.makeText(getApplicationContext(), "��û�л�ȡ��λ��Ϣ", 0).show();
				}
			}
		});
	}






	private void initMap(){


		mBaiduMap = mapView.getMap();
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);

		// ������ͨͼ
		mBaiduMap.setTrafficEnabled(true);

		// ������λͼ��  
		mBaiduMap.setMyLocationEnabled(true); 
		//


	}



	public void setLocatioPoint() {
		// �����¸�����ǰ�����֮ǰ�ĸ�����
		mBaiduMap.clear();
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
		MapStatus mMapStatus = new MapStatus.Builder()
		.target(new LatLng(location.getLatitude(), location.getLongitude()))
		.zoom(20)
		.build();
		//����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯


		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		//�ı��ͼ״̬
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		MarkerOptions ooA = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).icon(bmEnd)
				.zIndex(15);   //����marker���ڲ㼶
		mBaiduMap.addOverlay(ooA);


	}


	public void setRunNumber(String runNumber) {
		runData.setRunNum(runNumber);
		this.runNumber.setText(runNumber+"km");
	}
	/**
	 * ��ǰ�ٶ�
	 * 
	 * @param @param runNumber    
	 * @return void   
	 * 
	 */
	public void setNewSpeed(String runNumber) {
		this.speed.setText(runNumber+"m/s");
	}

	/**
	 * ƽ���ٶ�
	 * 
	 * @param @param runNumber    
	 * @return void   
	 * 
	 */
	public void setAllSpeed(String num) {
		runData.setRunSpeed(num);
		this.allSpeed.setText(num+"m/s");

	}

	/**
	 * ��������
	 * 
	 * @param @param runNumber    
	 * @return void   
	 * 
	 */
	public void setSpacePower(String runNumber) {
		this.spacePower.setText(runNumber+"��");
	}

	@Override
	public void finish() {
		super.finish();
		ISOPENRUN = false;
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		super.onResume();
		//startTimer();
	}



	@Override
	protected void onDestroy() {

		timeShow.cacel();

		ISOPENRUN = false;
		super.onDestroy();
		//�ͷŵ�Դ��
		mWakeLock.release();

		stopService(runService);

	}
	//���һ�ΰ����ؼ���ʱ��
	long lastBack = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//���ΰ����ؼ���������
			if (isrun){
				Toast.makeText(getApplicationContext(), "����ֹͣ�ܲ�", Toast.LENGTH_SHORT).show();
			}else {
				if ((lastBack = System.currentTimeMillis())-lastBack<2000) {
					RunActivity.this.finish();
				}else {
					Toast.makeText(getApplicationContext(), "�ٰ�һ�ν��˳��ܲ�ҳ��", Toast.LENGTH_SHORT).show();
				}
			}
			;
			return false ;
		}
		return super.onKeyDown(keyCode, event);
	}

	public RunData getRunData() {
		return runData;
	}

	public synchronized void startTime(){
		timeShow.start();
	}

	public synchronized int getSpaceTime(){
		return timeShow.getStarttime();
	}








	/**
	 * ������ʷ�켣
	 * 
	 * @param points
	 */
	private void drawHistoryTrack(final List<LatLng> points) {
		// �����¸�����ǰ�����֮ǰ�ĸ�����
		mBaiduMap.clear();

		Toast.makeText(getApplicationContext(), String.valueOf(points.size()), 0).show();
		if (points.size() == 1) {
			points.add(points.get(0));
		}

		if (points == null || points.size() == 0) {
			resetMarker();
		} else if (points.size() > 1) {

			LatLng llC = points.get(0);
			LatLng llD = points.get(points.size() - 1);
			LatLngBounds bounds = new LatLngBounds.Builder()
			.include(llD).build();

			msUpdate = MapStatusUpdateFactory.newLatLngBounds(bounds);

			// ������ͼ��
			startMarker = new MarkerOptions()
			.position(points.get(0)).icon(bmStart)
			.zIndex(12).draggable(true);

			// ����յ�ͼ��
			endMarker = new MarkerOptions().position(points.get(points.size() - 1))
					.icon(bmEnd).zIndex(13).draggable(true);

			// ���·�ߣ��켣��
			polyline = new PolylineOptions().width(10)
					.color(Color.RED).points(points);

			markerOptions = new MarkerOptions();
			markerOptions.flat(true);
			markerOptions.anchor(0.5f, 0.5f);
			markerOptions.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.icon_gcoding));
			markerOptions.position(points.get(points.size() - 1));
			addMarker();
		}

	}

	/**
	 * ��Ӹ�����
	 */
	protected void addMarker() {

		if (null != msUpdate) {
			mBaiduMap.animateMapStatus(msUpdate, 2000);
		}

		if (null != startMarker) {
			mBaiduMap.addOverlay(startMarker);
		}

		if (null != endMarker) {
			mBaiduMap.addOverlay(endMarker);
		}

		if (null != polyline) {
			mBaiduMap.addOverlay(polyline);
		}

	}

	/**
	 * ���ø�����
	 */
	private void resetMarker() {
		startMarker = null;
		endMarker = null;
		polyline = null;
	}
}