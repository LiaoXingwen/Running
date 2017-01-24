package tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lxw.baidumapdemo.MainHandleTool;

import viewtools.StartLayout;
import android.content.Context;
import android.graphics.LinearGradient;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;



/** 
 * 中央气象台的天气预报API  
 * */  
public class GetWeather extends AsyncTask<Void, String, String>{  
	//城市Id

	static String cityId = "101010100";

	public static String cityIds = "北京:101010100朝阳:101010300顺义:101010400" +
			"怀柔:101010500通州:101010600昌平:101010700延庆:101010800丰台:101010900" +
			"石景山:101011000大兴:101011100房山:101011200密云:101011300门头沟:101011400" +
			"平谷:101011500八达岭:101011600佛爷顶:101011700汤河口:101011800密云上甸子:101011900" +
			"斋堂:101012000霞云岭:101012100北京城区:101012200海淀:101010200天津:101030100" +
			"宝坻:101030300东丽:101030400西青:101030500北辰:101030600蓟县:101031400汉沽:101030800" +
			"静海:101030900津南:101031000塘沽:101031100大港:101031200武清:101030200宁河:101030700" +
			"上海:101020100宝山:101020300嘉定:101020500南汇:101020600浦东:101021300青浦:101020800" +
			"松江:101020900奉贤:101021000崇明:101021100徐家汇:101021200闵行:101020200金山:101020700" +
			"石家庄:101090101张家口:101090301承德:101090402唐山:101090501秦皇岛:101091101沧州:101090701" +
			"衡水:101090801邢台:101090901邯郸:101091001保定:101090201廊坊:101090601郑州:101180101" +
			"新乡:101180301许昌:101180401平顶山:101180501信阳:101180601南阳:101180701开封:101180801" +
			"洛阳:101180901商丘:101181001焦作:101181101鹤壁:101181201濮阳:101181301周口:101181401" +
			"漯河:101181501驻马店:101181601三门峡:101181701济源:101181801安阳:101180201合肥:101220101" +
			"芜湖:101220301淮南:101220401马鞍山:101220501安庆:101220601宿州:101220701阜阳:101220801" +
			"亳州:101220901黄山:101221001滁州:101221101淮北:101221201铜陵:101221301宣城:101221401" +
			"六安:101221501巢湖:101221601池州:101221701蚌埠:101220201杭州:101210101舟山:101211101" +
			"湖州:101210201嘉兴:101210301金华:101210901绍兴:101210501台州:101210601温州:101210701" +
			"丽水:101210801衢州:101211001宁波:101210401重庆:101040100合川:101040300南川:101040400" +
			"江津:101040500万盛:101040600渝北:101040700北碚:101040800巴南:101040900长寿:101041000" +
			"黔江:101041100万州天城:101041200万州龙宝:101041300涪陵:101041400开县:101041500城口:101041600" +
			"云阳:101041700巫溪:101041800奉节:101041900巫山:101042000潼南:101042100垫江:101042200" +
			"梁平:101042300忠县:101042400石柱:101042500大足:101042600荣昌:101042700铜梁:101042800" +
			"璧山:101042900丰都:101043000武隆:101043100彭水:101043200綦江:101043300酉阳:101043400" +
			"秀山:101043600沙坪坝:101043700永川:101040200福州:101230101泉州:101230501漳州:101230601" +
			"龙岩:101230701晋江:101230509南平:101230901厦门:101230201宁德:101230301莆田:101230401" +
			"三明:101230801兰州:101160101平凉:101160301庆阳:101160401武威:101160501金昌:101160601" +
			"嘉峪关:101161401酒泉:101160801天水:101160901武都:101161001临夏:101161101合作:101161201" +
			"白银:101161301定西:101160201张掖:101160701广州:101280101惠州:101280301梅州:101280401" +
			"汕头:101280501深圳:101280601珠海:101280701佛山:101280800肇庆:101280901湛江:101281001" +
			"江门:101281101河源:101281201清远:101281301云浮:101281401潮州:101281501东莞:101281601" +
			"中山:101281701阳江:101281801揭阳:101281901茂名:101282001汕尾:101282101韶关:101280201" +
			"南宁:101300101柳州:101300301来宾:101300401桂林:101300501梧州:101300601防城港:101301401" +
			"贵港:101300801玉林:101300901百色:101301001钦州:101301101河池:101301201北海:101301301" +
			"崇左:101300201贺州:101300701贵阳:101260101安顺:101260301都匀:101260401兴义:101260906" +
			"铜仁:101260601毕节:101260701六盘水:101260801遵义:101260201凯里:101260501昆明:101290101" +
			"红河:101290301文山:101290601玉溪:101290701楚雄:101290801普洱:101290901昭通:101291001" +
			"临沧:101291101怒江:101291201香格里拉:101291301丽江:101291401德宏:101291501景洪:101291601" +
			"大理:101290201曲靖:101290401保山:101290501呼和浩特:101080101乌海:101080301集宁:101080401" +
			"通辽:101080501阿拉善左旗:101081201鄂尔多斯:101080701临河:101080801锡林浩特:101080901" +
			"呼伦贝尔:101081000乌兰浩特:101081101包头:101080201赤峰:101080601南昌:101240101" +
			"上饶:101240301抚州:101240401宜春:101240501鹰潭:101241101赣州:101240701景德镇:101240801" +
			"萍乡:101240901新余:101241001九江:101240201吉安:101240601武汉:101200101黄冈:101200501" +
			"荆州:101200801宜昌:101200901恩施:101201001十堰:101201101神农架:101201201随州:101201301" +
			"荆门:101201401天门:101201501仙桃:101201601潜江:101201701襄樊:101200201鄂州:101200301" +
			"孝感:101200401黄石:101200601咸宁:101200701成都:101270101自贡:101270301绵阳:101270401" +
			"南充:101270501达州:101270601遂宁:101270701广安:101270801巴中:101270901泸州:101271001" +
			"宜宾:101271101内江:101271201资阳:101271301乐山:101271401眉山:101271501凉山:101271601" +
			"雅安:101271701甘孜:101271801阿坝:101271901德阳:101272001广元:101272101攀枝花:101270201" +
			"银川:101170101中卫:101170501固原:101170401石嘴山:101170201吴忠:101170301西宁:101150101" +
			"黄南:101150301海北:101150801果洛:101150501玉树:101150601海西:101150701海东:101150201" +
			"海南:101150401济南:101120101潍坊:101120601临沂:101120901菏泽:101121001滨州:101121101" +
			"东营:101121201威海:101121301枣庄:101121401日照:101121501莱芜:101121601聊城:101121701" +
			"青岛:101120201淄博:101120301德州:101120401烟台:101120501济宁:101120701泰安:101120801" +
			"西安:101110101延安:101110300榆林:101110401铜川:101111001商洛:101110601安康:101110701" +
			"汉中:101110801宝鸡:101110901咸阳:101110200渭南:101110501太原:101100101临汾:101100701" +
			"运城:101100801朔州:101100901忻州:101101001长治:101100501大同:101100201阳泉:101100301" +
			"晋中:101100401晋城:101100601吕梁:101101100乌鲁木齐:101130101石河子:101130301昌吉:101130401" +
			"吐鲁番:101130501库尔勒:101130601阿拉尔:101130701阿克苏:101130801喀什:101130901" +
			"伊宁:101131001塔城:101131101哈密:101131201和田:101131301阿勒泰:101131401阿图什:101131501" +
			"博乐:101131601克拉玛依:101130201拉萨:101140101山南:101140301阿里:101140701昌都:101140501" +
			"那曲:101140601日喀则:101140201林芝:101140401台北县:101340101高雄:101340201台中:101340401" +
			"海口:101310101三亚:101310201东方:101310202临高:101310203澄迈:101310204儋州:101310205" +
			"昌江:101310206白沙:101310207琼中:101310208定安:101310209屯昌:101310210琼海:101310211" +
			"文昌:101310212保亭:101310214万宁:101310215陵水:101310216西沙:101310217南沙岛:101310220" +
			"乐东:101310221五指山:101310222琼山:101310102长沙:101250101株洲:101250301衡阳:101250401" +
			"郴州:101250501常德:101250601益阳:101250700娄底:101250801邵阳:101250901岳阳:101251001" +
			"张家界:101251101怀化:101251201黔阳:101251301永州:101251401吉首:101251501湘潭:101250201" +
			"南京:101190101镇江:101190301苏州:101190401南通:101190501扬州:101190601宿迁:101191301" +
			"徐州:101190801淮安:101190901连云港:101191001常州:101191101泰州:101191201无锡:101190201" +
			"盐城:101190701哈尔滨:101050101牡丹江:101050301佳木斯:101050401绥化:101050501黑河:101050601" +
			"双鸭山:101051301伊春:101050801大庆:101050901七台河:101051002鸡西:101051101鹤岗:101051201" +
			"齐齐哈尔:101050201大兴安岭:101050701长春:101060101延吉:101060301四平:101060401白山:101060901" +
			"白城:101060601辽源:101060701松原:101060801吉林:101060201通化:101060501沈阳:101070101" +
			"鞍山:101070301抚顺:101070401本溪:101070501丹东:101070601葫芦岛:101071401营口:101070801" +
			"阜新:101070901辽阳:101071001铁岭:101071101朝阳:101071201盘锦:101071301大连:101070201" +
			"锦州:101070701 ";


	String weatherBaseUrl = "http://www.weather.com.cn/data/sk/";

	private StringBuilder sb;

	private BufferedReader br;

	
	@Override
	protected String doInBackground(Void... params) {
		
	        try { 
	        	
	        	URL url = new URL(weatherBaseUrl + cityId + ".html"); 
		        URLConnection connectionData = url.openConnection(); 
		        connectionData.setConnectTimeout(1000); 

		        connectionData.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		        //设置none为不压缩，gzip则压缩，乱码要解码
		        connectionData.setRequestProperty("Accept-Encoding", "none");
		        connectionData.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		        connectionData.setRequestProperty("Host", "www.weather.com.cn");
		        connectionData.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 UBrowser/5.7.16400.15 Safari/537.36");

		        br =new BufferedReader(new InputStreamReader(connectionData.getInputStream()));
		        
	            sb = new StringBuilder(); 
	            String line = null; 
	            while ((line = br.readLine()) != null) 
	                sb.append(line); 
	        } catch (SocketTimeoutException e) { 
	        	e.printStackTrace();
	        	MainHandleTool.sendMessage(MainHandleTool.UPDATE_TOAST, new String[]{"连接超时"});
	        } catch (FileNotFoundException e) {
	        	e.printStackTrace();
	        	MainHandleTool.sendMessage(MainHandleTool.UPDATE_TOAST, new String[]{"加载文件出错"});
	        } catch (MalformedURLException e) {
	        	e.printStackTrace();
	        	MainHandleTool.sendMessage(MainHandleTool.UPDATE_TOAST, new String[]{"地址有误"});
			} catch (IOException e) {
				e.printStackTrace();
				MainHandleTool.sendMessage(MainHandleTool.UPDATE_TOAST, new String[]{"获取异常"});
			}
	        String datas = null;
	            if (sb!=null) {
	            	datas =sb.toString();
	            	try {
						setWeather(datas);
					} catch (JSONException e) {
						MainHandleTool.sendMessage(MainHandleTool.UPDATE_TOAST, new String[]{"解析失败"});
					}
					
				}
				return datas;  
	}
	
	
	
	void setWeather(String json) throws JSONException{
		json = json.replaceAll(" ", "");
		json = json.replaceAll(" ", "");
		json = json.replaceAll("\"", "");
		json = json.replaceAll(":", "");
		json = json.replaceAll(",", "");
		
		String wd = json.substring(json.indexOf("temp")+4, json.indexOf("WD"));
		String content = json.substring(json.indexOf("WD")+2, json.indexOf("WS"));
		content += " "+ json.substring(json.indexOf("WS")+2, json.indexOf("SD"));
		content += " 湿度 " +json.substring(json.indexOf("SD")+2, json.indexOf("WSE"));
		String cityString = json.substring(json.indexOf("city")+4, json.indexOf("cityid"));
		MainHandleTool.sendMessage(MainHandleTool.UPDATE_WEATHER, new String[]{wd,content,cityString});		
	}
	
	
	public String Decompress(String base64String) throws IOException{
	    byte[] t = Base64.decode(base64String, Base64.DEFAULT);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ByteArrayInputStream in = new ByteArrayInputStream(t);
	    GZIPInputStream gzip = new GZIPInputStream(in);
	    try{
	        byte[] buffer = new byte[4096];
	        int n = 0;
	        while((n = gzip.read(buffer, 0, buffer.length)) > 0){
	            out.write(buffer, 0, n);
	        }
	    }finally{
	        gzip.close();
	    }
	    in.close();
	    out.close();
	    return out.toString("UTF-8");  //还原为原始编码的字符串
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
	}



	public boolean isFindCityIds(String name) {
		int index = -1;
		MainHandleTool.sendMessage(MainHandleTool.UPDATE_WEATHER, new String[]{" ","" ,name});
		if ((index = cityIds.indexOf(name))!=-1){
			cityId = cityIds.substring(name.length()+index+1,name.length()+index+10);
			
			MainHandleTool.sendMessage(MainHandleTool.UPDATE_WEATHER, new String[]{" ","" ,name});
			return true;
		}

		return false;
	}
}  