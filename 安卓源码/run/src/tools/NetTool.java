package tools;

import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.ProtocolException;
import java.net.URL;  
import java.net.URLEncoder;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  


import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  


/** 
 * NetTool:��װһ����㶨90%��׿�ͻ�����������˽��� 
 *  
 * @author 
 */  
public class NetTool {  





	





	private static final int TIMEOUT = 20000;// 10��  

	/** 
	 * �����ı�,����Json,xml�� 
	 */  
	public static String sendTxt(String urlPath, String txt, String encoding)  
	{  
		byte[] sendData = txt.getBytes();  
		URL url;
		try {
			url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
			conn.setRequestMethod("POST");  
			conn.setConnectTimeout(TIMEOUT);  
			// ���ͨ��post�ύ���ݣ�����������������������  
			conn.setDoOutput(true);  
			conn.setRequestProperty("Content-Type", "text/xml");  
			conn.setRequestProperty("Charset", encoding);  
			conn.setRequestProperty("Content-Length", String  
					.valueOf(sendData.length));  
			OutputStream outStream = conn.getOutputStream();  
			outStream.write(sendData);  
			outStream.flush();  
			outStream.close();  
			if (conn.getResponseCode() == 200) {  
				// ��÷�������Ӧ������  
				BufferedReader in = new BufferedReader(new InputStreamReader(conn  
						.getInputStream(), encoding));  
				// ����  
				String retData = null;  
				String responseData = "";  
				while ((retData = in.readLine()) != null) {  
					responseData += retData;  
				}  
				in.close();  
				return responseData;  
			}  



		} catch (MalformedURLException e) {
			return "404"; 
		} catch (IOException e) {

			return "403"; 
		}  

		return "404";  
	}  

	/** 
	 * �ϴ��ļ� 
	 */  
	public static String sendFile(String urlPath, String filePath,  
			String newName) throws Exception {  
		String end = "\r\n";  
		String twoHyphens = "--";  
		String boundary = "*****";  

		URL url = new URL(urlPath);  
		HttpURLConnection con = (HttpURLConnection) url.openConnection();  
		/* ����Input��Output����ʹ��Cache */  
		con.setDoInput(true);  
		con.setDoOutput(true);  
		con.setUseCaches(false);  
		/* ���ô��͵�method=POST */  
		con.setRequestMethod("POST");  
		/* setRequestProperty */  

		con.setRequestProperty("Connection", "Keep-Alive");  
		con.setRequestProperty("Charset", "UTF-8");  
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="  
				+ boundary);  
		/* ����DataOutputStream */  
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());  
		ds.writeBytes(twoHyphens + boundary + end);  
		ds.writeBytes("Content-Disposition: form-data; "  
				+ "name=\"file1\";filename=\"" + newName + "\"" + end);  
		ds.writeBytes(end);  

		/* ȡ���ļ���FileInputStream */  
		FileInputStream fStream = new FileInputStream(filePath);  
		/* ����ÿ��д��1024bytes */  
		int bufferSize = 1024;  
		byte[] buffer = new byte[bufferSize];  

		int length = -1;  
		/* ���ļ���ȡ������������ */  
		while ((length = fStream.read(buffer)) != -1) {  
			/* ������д��DataOutputStream�� */  
			ds.write(buffer, 0, length);  
		}  
		ds.writeBytes(end);  
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);  

		/* close streams */  
		fStream.close();  
		ds.flush();  

		/* ȡ��Response���� */  
		InputStream is = con.getInputStream();  
		int ch;  
		StringBuffer b = new StringBuffer();  
		while ((ch = is.read()) != -1) {  
			b.append((char) ch);  
		}  
		/* �ر�DataOutputStream */  
		ds.close();  
		return b.toString();  
	}  

	/** 
	 * ͨ��get��ʽ�ύ������������ 
	 */  
	public static String sendGetRequest(String urlPath,  
			Map<String, String> params, String encoding) throws Exception {  

		// ʹ��StringBuilder����  
		StringBuilder sb = new StringBuilder(urlPath);  
		sb.append('?');  
		if (params!=null) {


			// ����Map  
			for (Map.Entry<String, String> entry : params.entrySet()) {  
				sb.append(entry.getKey()).append('=').append(  
						URLEncoder.encode(entry.getValue(), encoding)).append('&');  
			}  
			sb.deleteCharAt(sb.length() - 1);

		}
		
		System.out.println(sb);
		// ������  
		URL url = new URL(sb.toString());  
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
		conn.setRequestMethod("GET");  
		conn.setRequestProperty("Content-Type", "text/xml");  
		conn.setRequestProperty("Charset", encoding);  
		conn.setConnectTimeout(TIMEOUT);  
		// ���������Ӧ����200�����ʾ�ɹ�  
		if (conn.getResponseCode() == 200) {  
			// ��÷�������Ӧ������  
			BufferedReader in = new BufferedReader(new InputStreamReader(conn  
					.getInputStream(), encoding));  
			// ����  
			String retData = null;  
			String responseData = "";  
			while ((retData = in.readLine()) != null) {  
				responseData += retData;  
			}  
			in.close();  
			return responseData;  
		}  
		return "404";  

	}  

	/** 
	 * ͨ��Post��ʽ�ύ������������,Ҳ������������json��xml�ļ� 
	 */  
	public static String sendPostRequest(String urlPath,  
			Map<String, String> params, String encoding) {  
		StringBuilder sb = new StringBuilder();  
		// ���������Ϊ��  
		if (params != null && !params.isEmpty()) {  


			for (Map.Entry<String, String> entry : params.entrySet()) {  
				// Post��ʽ�ύ�����Ļ�������ʡ�����������볤��  
				try {
					sb.append(entry.getKey()).append('=').append(  
							URLEncoder.encode(entry.getValue(), encoding)).append(  
									'&');
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					return "404"; 
				}  
			}  
			System.out.println("re:"+sb.toString());
			sb.deleteCharAt(sb.length() - 1); 
		}  
		// �õ�ʵ��Ķ���������  
		byte[] entitydata = sb.toString().getBytes();  
		URL url = null;
		try {
			url = new URL(urlPath);
		} catch (MalformedURLException e1) {
			return "404"; 
		}  
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			return "404"; 
		}  
		try {
			conn.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			return "404"; 
		}  
		conn.setConnectTimeout(TIMEOUT);  
		// ���ͨ��post�ύ���ݣ�����������������������  
		conn.setDoOutput(true);  
		// ����ֻ�����������������ݳ��ȵ�ͷ�ֶ�  
		conn.setRequestProperty("Content-Type",  
				"application/x-www-form-urlencoded");  
		// conn.setRequestProperty("Content-Type", "text/xml");  
		conn.setRequestProperty("Charset", encoding);  
		conn.setRequestProperty("Content-Length", String  
				.valueOf(entitydata.length));  
		OutputStream outStream;
		try {
			outStream = conn.getOutputStream();
		} catch (IOException e) {
			return "404"; 
		}  
		// ��ʵ������д���������  
		try {
			outStream.write(entitydata);
		} catch (IOException e) {
			return "404"; 
		}  
		// �ڴ��е�����ˢ��  
		try {
			outStream.flush();
		} catch (IOException e1) {
			return "404"; 
		}  
		try {
			outStream.close();
		} catch (IOException e) {
			return "404";
		}  
		// ���������Ӧ����200�����ʾ�ɹ�  
		try {
			if (conn.getResponseCode() == 200) {  
				// ��÷�������Ӧ������  
				BufferedReader in = new BufferedReader(new InputStreamReader(conn  
						.getInputStream(), encoding));  
				// ����  
				String retData = null;  
				String responseData = "";  
				while ((retData = in.readLine()) != null) {  
					responseData += retData;  
				}  
				in.close();  
				return responseData;  
			}
		} catch (UnsupportedEncodingException e) {
			return "404"; 
		} catch (IOException e) {
			return "404"; 
		} 
		return "404";  
	}  

	/** 
	 * ������HTTPS��ȫģʽ���߲���cookie��ʱ��ʹ��HTTPclient�᷽��ܶ� ʹ��HTTPClient����Դ��Ŀ����������ύ���� 
	 */  
	public static String sendHttpClientPost(String urlPath,  
			Map<String, String> params, String encoding) throws Exception {  
		// ��Ҫ�Ѳ����ŵ�NameValuePair  
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();  
		if (params != null && !params.isEmpty()) {  
			for (Map.Entry<String, String> entry : params.entrySet()) {  
				paramPairs.add(new BasicNameValuePair(entry.getKey(), entry  
						.getValue()));  
			}  
		}  
		// ������������б��룬�õ�ʵ������  
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,  
				encoding);  
		// ����һ������·��  
		HttpPost post = new HttpPost(urlPath);  
		// ��������ʵ��  
		post.setEntity(entitydata);  
		// ���������  
		DefaultHttpClient client = new DefaultHttpClient();  
		// ִ��post����  
		HttpResponse response = client.execute(post);  
		// ��״̬���л�ȡ״̬�룬�ж���Ӧ���Ƿ����Ҫ��  
		if (response.getStatusLine().getStatusCode() == 200) {  
			HttpEntity entity = response.getEntity();  
			InputStream inputStream = entity.getContent();  
			InputStreamReader inputStreamReader = new InputStreamReader(  
					inputStream, encoding);  
			BufferedReader reader = new BufferedReader(inputStreamReader);// ���ַ����õġ�  
			String s;  
			String responseData = "";  
			while (((s = reader.readLine()) != null)) {  
				responseData += s;  
			}  
			reader.close();// �ر�������  
			return responseData;  
		}  
		return "sendHttpClientPost error!";  
	}  


	/** 
	 * ����URL�õ������� 
	 *  
	 * @param urlStr 
	 * @return 
	 * @throws MalformedURLException 
	 * @throws IOException 
	 */  
	public static InputStream getInputStreamFromUrl(String urlStr)  
			throws MalformedURLException, IOException {  
		URL url = new URL(urlStr);  
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();  
		InputStream inputStream = urlConn.getInputStream();  
		return inputStream;  
	}  

}