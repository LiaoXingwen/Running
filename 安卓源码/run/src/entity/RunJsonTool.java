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
			//Ŀ¼�����ھʹ���
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}



	/**
	 * �ļ�
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
	 * ����json����
	 * 
	 * @param @param runData
	 * @param @return    
	 * @return String   
	 * 
	 */
	public static String makeJson(RunData runData) {


		JSONObject all = new JSONObject();//����һ���ܵĶ���������������json�� 
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
		return "ʧ��";
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

	//�ж��ⲿ�洢�Ƿ���Զ�д
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	//��ȡ�ⲿ�洢
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
	 * ���ļ�����ԭ
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
	 * ���ļ�����ԭ
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
	public static String  readFileOnLine(String filePath) throws IOException{//�����ļ�·��
		FileInputStream fis = new FileInputStream(new File(filePath));//���ļ�������
		StringBuffer sBuffer = new StringBuffer();
		DataInputStream dataIO = new DataInputStream(fis);//��ȡ�ļ�������
		String strLine = null;
		while((strLine =  dataIO.readLine()) != null) {//ͨ��readline���ж�ȡ
			sBuffer.append(strLine + "\n");//strLine����һ�е�����
		}
		dataIO.close();
		fis.close();
		return sBuffer.toString();
	}

}
