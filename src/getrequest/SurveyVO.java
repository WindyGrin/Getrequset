package getrequest;

public class SurveyVO {
	private String send;
    private String content;
    private String time;
    public String getsend() {
        return send;
    }
    public void setsend(String send) {
        this.send = send;
    }
    public String getcontent() {
        return content;
    }
    public void setcontent(String content) {
        this.content = content;
    }
    public String gettime() {
        return time;
    }
    public void settime(String time) {
        this.time = time;
    }
}
