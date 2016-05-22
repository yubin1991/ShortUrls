package com.dave.http; 
/** 
 * @author  E-mail: yubin1991@yeah.net
 * @version Time锛�2015-7-24 涓婂崍11:21:02 
 * @Description
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import com.dave.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class HttpRequest {
	
	 public final static String[] ipArrays = {
		        "66.102.251.", "112.211.0.", "141.8.225.","159.106.121.",
		        "216.58.221.", "61.244.148.", "59.125.39.", "58.30.15.", "114.80.166.",
		        "202.96.134.", "58.19.24.", "119.39.23.", "58.195.128.", "124.236.223.",
		        "183.221.217.", "222.182.90.", "58.194.96.", "211.138.161.",
		        "112.112.13.", "219.159.82.", "202.98.226.", " 61.128.101.",
		        "130.039.000.", "130.039.255.", "131.230.000.","131.230.255.",
		        "144.092.000.", "144.092.255.", "151.000.000.", "152.255.255.",
		        "161.058.000.", "161.058.255.", "169.208.000.", "169.223.255.",
		        "171.208.000.", "171.220.255.", "195.010.040.", "195.010.040.",
		        "195.010.062.", "195.010.063.", "195.010.194.", "195.010.194.",
		        "195.063.159.", "195.063.159.", "195.090.044.", "195.090.046.",
		        "195.090.047.", "195.090.048.", "195.090.049.", "195.090.051.",
		        "195.090.052.", "195.090.053.", "195.100.066.", "195.112.164.",
		        "195.112.172.", "195.112.173."};
   
	/**
     * 鍚戞寚瀹歎RL鍙戦�丟ET鏂规硶鐨勮姹�
     * 
     * @param url
     *            鍙戦�佽姹傜殑URL
     * @param param
     * @return URL 鎵�浠ｈ〃杩滅▼璧勬簮鐨勫搷搴旂粨鏋�
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
        	Random r = new Random();
        	Integer counter = r.nextInt(255);
        	int index = r.nextInt(34);
            String ip = ipArrays[index];
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            if(r.nextInt(10) % 2 == 0)
            	connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.142 Safari/535.19");
            else
            	connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
            connection.setRequestProperty("X-Forwarded-For", ip+counter);
            connection.connect();

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("鍙戦�丟ET璇锋眰鍑虹幇寮傚父锛�" + e);
            e.printStackTrace();
        }
        // 浣跨敤finally鍧楁潵鍏抽棴杈撳叆娴�
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 鍚戞寚瀹� URL 鍙戦�丳OST鏂规硶鐨勮姹�
     * @param url
     * @param param
     * @return 鎵�浠ｈ〃杩滅▼璧勬簮鐨勫搷搴旂粨鏋�
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 鎵撳紑鍜孶RL涔嬮棿鐨勮繛鎺�
            URLConnection conn = realUrl.openConnection();
            // 璁剧疆閫氱敤鐨勮姹傚睘鎬�
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 鍙戦�丳OST璇锋眰蹇呴』璁剧疆濡備笅涓よ
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 鑾峰彇URLConnection瀵硅薄瀵瑰簲鐨勮緭鍑烘祦
            out = new PrintWriter(conn.getOutputStream());
            // 鍙戦�佽姹傚弬鏁�
            out.print(param);
            // flush杈撳嚭娴佺殑缂撳啿
            out.flush();
            // 瀹氫箟BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("鍙戦�� POST 璇锋眰鍑虹幇寮傚父锛�"+e);
            e.printStackTrace();
        }
        //浣跨敤finally鍧楁潵鍏抽棴杈撳嚭娴併�佽緭鍏ユ祦
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }   
    
    /**
     * array
     * @param json
     * @return
     */
    public static String[] getShortUrls(String json){
    	JSONArray object = JsonUtil.toJSONArray(json);
    	String[] result = new String[20];
    	int count=0;
    	if(object!=null){
    		for(Object obj:object){
    			JSONObject j = (JSONObject) obj;
    			if(j.getInt("type")==0){
    				//System.out.println(j.getString("url_short"));
    				result[count++]=j.getString("url_short");
    			}
    		}
    	}
    	return result;
    }
    
    /**
     * single
     * @param json
     * @return
     */
    public static String getShortUrl(String json){
    	JSONArray array = JsonUtil.toJSONArray(json);
    	JSONObject object = array.getJSONObject(0);
    	String result = null;
		if(object.getInt("type")==0){
			//System.out.println(j.getString("url_short"));
			result=object.getString("url_short");
		}
    	return result;
    }
    
    
}