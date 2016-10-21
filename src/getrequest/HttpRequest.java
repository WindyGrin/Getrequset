package getrequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
	static int maxid=0;
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
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

    public static int getMaxID(){
    	return maxid;
    }
    public void fromJson(String result) {
        String sTotalString = result;
        JSONArray arr;
        try {            
            arr = new JSONArray(sTotalString);
            for (int i = 0; i < arr.length(); i++) {
                SurveyVO surveyVO = new SurveyVO();
                JSONObject array =  arr.getJSONObject(i);
                maxid=Integer.parseInt(array.getString("id"));
                System.out.println(array.getString("send")+":"+array.getString("content")
                +"("+array.getString("time")+"中国湖北武汉市联通）");
                surveyVO.setsend(array.getString("send"));
                surveyVO.setcontent(array.getString("content"));
                surveyVO.settime(array.getString("time"));
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    static int i = 287;
    public static void main(String[] args) {
    	HttpRequest htp = new HttpRequest();
    	Timer timer = new Timer();
    	System.out.println(HttpRequest.getMaxID());
    	
    	
    	
        timer.schedule(new TimerTask() {
        @Override
        public void run() {
        	//你要执行的操作

        	String result = htp.sendGet("http://varyshare.cn/talk/action.php", "maxid="+htp.getMaxID());
        	if(result.equals("[]"));
        		else
        		{	
        			htp.fromJson(result);
        			System.out.println(result);   
        		}   	
            }
        }, 0, 1000);
    	
     }
}