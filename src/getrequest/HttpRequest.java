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
     * ��ָ��URL����GET����������
     * 
     * @param url
     *            ���������URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return URL ������Զ����Դ����Ӧ���
     */
	static int maxid=0;
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();

            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
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
                +"("+array.getString("time")+"�й������人����ͨ��");
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
        	//��Ҫִ�еĲ���

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