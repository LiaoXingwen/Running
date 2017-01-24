package entity;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.PublicSrc;

import android.os.Environment;
import android.util.JsonWriter;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.lxw.baidumapdemo.RunHanbleTool;

public  class RunJsonTool {


	public static String filePath ;
	public static String runPath ;

	public RunJsonTool() {
		if (isExternalStorageWritable()) {
			filePath = getExternalStorage()+"/RunData";
			runPath = filePath +"/rundata";
			File file = new File(runPath);
			//目录不存在就创建
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}



	/**
	 * 文件
	 * 
	 * @param @param runData
	 * @param @return    
	 * @return boolean   
	 * 
	 */
	public static boolean saveFile(RunData runData){
		try {
			String json = makeJson(runData);
			File file = new File(runPath+"/"+runData.getRunNum()+"p"+timePaserFile(runData.getStartTime())+"p"+timePaserFile(runData.getSpaceTime()));
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.flush();
			writer.close();
			return true ;
		} catch (IOException e) {
			e.printStackTrace();
			return false ;
		}
	}




	/**
	 * 生成json数据
	 * 
	 * @param @param runData
	 * @param @return    
	 * @return String   
	 * 
	 */
	public static String makeJson(RunData runData) {


		JSONObject all = new JSONObject();//创建一个总的对象，这个对象对整个json串 
		try {
			JSONArray jsonarray = new JSONArray();
			JSONObject object = new JSONObject();
			object.put("spaceTime", runData.getSpaceTime());
			object.put("runNum", runData.getRunNum());
			object.put("startTime", runData.getStartTime());
			object.put("endTime", runData.getEndTime());
			object.put("runSpeed", runData.getRunSpeed());
			jsonarray.put(object);
			all.put("RunData", jsonarray);
			all.put("gps", listToJson( runData.getList()));
			return all.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "失败";
	}

	public static  JSONArray listToJson(List<LatLng> list) throws JSONException {
		JSONArray jsonarray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			LatLng latLng = list.get(i);
			JSONObject object = new JSONObject();
			object.put("lat", latLng.latitude);
			object.put("lng", latLng.longitude);
			jsonarray.put(object);
		}
		return jsonarray;
	}

	//判断外部存储是否可以读写
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	//获取外部存储
	public String getExternalStorage() {
		return Environment.getExternalStorageDirectory().getPath();
	}


	public static String timePaserFile(String time) {
		return time.replace(":", "m").replace(" ", "s");

	}


	public static List<String[]> readFileList(){
		List<String[]> list = new ArrayList<String[]>();
		File file = new File(runPath);
		if (file.isDirectory()) {
			String[] lists = file.list();
			for (int i = 0; i < lists.length; i++) {
				if (fileParseRunData(lists[i]).split("p").length==3) {
					list.add(fileParseRunData(lists[i]).split("p"));
				}
			}
		}
		return list;
	}

	/**
	 * 将文件名还原
	 * 
	 * @param @param filename
	 * @param @return    
	 * @return String   
	 * 
	 */
	public static String fileParseRunData(String filename){
		return filename.replace("m", ":").replace("s", " ");
	}
	/**
	 * 将文件名还原
	 * 
	 * @param @param filename
	 * @param @return    
	 * @return String   
	 * 
	 */
	public static String reFileName(String[] data){
		String data1 = data[0]+"p"+data[1]+"p"+data[2];
		return runPath+"/"+data1.replace(":", "m").replace(" ", "s");
	}

	public static List<LatLng> getLatlng(String[] data) throws JSONException, IOException{
		List<LatLng> latLngs = new LinkedList<LatLng>();
		JSONObject jsonObject = new JSONObject(readFileOnLine(reFileName(data)));
		JSONArray rArray = jsonObject.getJSONArray("gps");
		
		for (int i = 0; i < rArray.length(); i++) {
			try {
				
				JSONObject jsonArray = rArray.getJSONObject(i);
				latLngs.add(new LatLng(jsonArray.getDouble("lat"), jsonArray.getDouble("lng")))	;	
			} catch (Exception e) {
				continue;
			}
		}
		return latLngs;

	}
	public static String  readFileOnLine(String filePath) throws IOException{//输入文件路径
		FileInputStream fis = new FileInputStream(new File(filePath));//打开文件输入流
		StringBuffer sBuffer = new StringBuffer();
		DataInputStream dataIO = new DataInputStream(fis);//读取文件数据流
		String strLine = null;
		while((strLine =  dataIO.readLine()) != null) {//通过readline按行读取
			sBuffer.append(strLine + "\n");//strLine就是一行的内容
		}
		dataIO.close();
		fis.close();
		return sBuffer.toString();
	}

}
