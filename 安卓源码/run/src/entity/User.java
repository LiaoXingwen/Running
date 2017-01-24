package entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import tools.PublicSrc;

import SqlLiteDAO.DatabaseHelper;
import android.R.string;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**   
 *    
 * ��Ŀ���ƣ�Running   
 * �����ƣ�Uers   
 * ��������   
 * �����ˣ�������
 * ����ʱ�䣺2016��11��21�� ����2:57:16   
 * �޸��ˣ�lxw   
 * �޸�ʱ�䣺2016��11��21�� ����2:57:16   
 * �޸ı�ע��   
 * @version 1.0.0   
 *    
 */
public class User {
	private long phone =0; //�ֻ�����
	private String name ="��";//�û���
	private String sex ="��";
	private String code = "��";
	private String register ="��";

	public User() {
	}







	public String getRegister() {
		return register;
	}







	public void setRegister(String register) {
		this.register = register;
	}







	public long getPhone() {
		return phone;
	}


	public void setPhone(long phone) {
		this.phone = phone;

	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;

	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;

	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public static User sharedPreferencesParseUser() {
		User u = new User();
		u.setPhone(PublicSrc.userPreferences.getLong("phone", 0));
		u.setName(PublicSrc.userPreferences.getString("name", "��"));
		u.setSex(PublicSrc.userPreferences.getString("sex", "��"));
		u.setCode(PublicSrc.userPreferences.getString("code", "��"));
		u.setRegister(PublicSrc.userPreferences.getString("register", "��"));
		return u;
	}


	public  void saveUser() {
		Editor e =  PublicSrc.userPreferences.edit();
		e.putLong("phone", getPhone());
		e.putString("name", getName());
		e.putString("sex", getSex());
		e.putString("code", getCode());
		e.putString("register", getRegister());
		e.commit();
	}
	
	public static User jsonParseUser(String json) throws JSONException {
		User u = new User();
		JSONObject jsonObject = new JSONObject(json);
		u.setPhone(jsonObject.getLong("phone"));
		u.setName(jsonObject.getString("name"));
		u.setSex(jsonObject.getString("sex"));
		u.setCode(jsonObject.getString("code"));
		u.setRegister(jsonObject.getString("register"));
		u.saveUser();
		return u;
	}
	
	
	
	@Override
	public String toString() {
		return "User [phone=" + phone + ", name=" + name + ", sex=" + sex
				+ ", code=" + code + ", register=" + register + "]";
	}







	public Map<String, String> paserMap() {
		Map<String, String> map  = new HashMap<String, String>();
		map.put("name", getName());
		map.put("phone", String.valueOf(getPhone()));
		map.put("sex", getSex());
		map.put("code", getCode());
		return map;
		
	}
}
