package com.lxw.baidumapdemo;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
//假如用到位置提醒功能，需要import该类
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import entity.RunJsonTool;


public class MapActivity extends Activity {  
	static MapView mapView = null;
	static BaiduMap mBaiduMap = null;  

	ImageView refoucs = null;
	private Marker overlay;
	private MapStatusUpdate msUpdate;
	private BitmapDescriptor bmStart;
	private MarkerOptions startMarker;
	private MarkerOptions endMarker;
	private PolylineOptions polyline;
	private MarkerOptions markerOptions;
	private BitmapDescriptor bmEnd;
	String data[] ;


	@Override  
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.viewmap);  

		bmStart = BitmapDescriptorFactory.fromResource(R.drawable.icon_start);
		bmEnd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
		//获取地图控件引用  
		mapView = (MapView) findViewById(R.id.VmapView);  
		initMap();

		data = getIntent().getStringArrayExtra("data");
		if (data.length==3) {
			try {
				List<LatLng> list = RunJsonTool.getLatlng(data);
				if (list!=null&&list.size()>0) {
					drawHistoryTrack(list);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(this, "json数据异常", 0).show();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "数据异常", 0).show();
			}

		}

	}

	private void initMap(){


		mBaiduMap = mapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(false);

		// 开启交通图
		mBaiduMap.setTrafficEnabled(false);

		// 开启定位图层  
		mBaiduMap.setMyLocationEnabled(false);
		mBaiduMap.setMapStatus(      
		           MapStatusUpdateFactory.zoomOut());
	}

	/**
	 * 绘制历史轨迹
	 * 
	 * @param points
	 */
	private void drawHistoryTrack(final List<LatLng> points) {
		
		// 绘制新覆盖物前，清空之前的覆盖物
		mBaiduMap.clear();

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

			// 添加起点图标
			startMarker = new MarkerOptions()
			.position(points.get(0)).icon(bmStart)
			.zIndex(12).draggable(true);

			// 添加终点图标
			endMarker = new MarkerOptions().position(points.get(points.size() - 1))
					.icon(bmEnd).zIndex(13).draggable(true);

			// 添加路线（轨迹）
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
	 * 添加覆盖物
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
	 * 重置覆盖物
	 */
	private void resetMarker() {
		startMarker = null;
		endMarker = null;
		polyline = null;
	}

	@Override  
	protected void onDestroy() {  
		super.onDestroy();  
		//在activity执行onDestroy时执行mapView.onDestroy()，实现地图生命周期管理  
		mapView.onDestroy();  
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  
		//在activity执行onResume时执行mapView. onResume ()，实现地图生命周期管理  
		mapView.onResume();  
	}  
	@Override  
	protected void onPause() {  
		super.onPause();  
		//在activity执行onPause时执行mapView. onPause ()，实现地图生命周期管理  
		mapView.onPause();  
	}  
}