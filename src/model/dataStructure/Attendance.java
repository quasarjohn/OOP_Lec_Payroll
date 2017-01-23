package model.dataStructure;

/**
 * Created by John on 1/20/2017.
 */
public class Attendance {

    private int pre_empno, post_empno;

    private String workDate, timeIn, timeSched, status;

    public int getPre_empno() {
        return pre_empno;
    }

    public void setPre_empno(int pre_empno) {
        this.pre_empno = pre_empno;
    }

    public int getPost_empno() {
        return post_empno;
    }

    public void setPost_empno(int post_empno) {
        this.post_empno = post_empno;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeSched() {
        return timeSched;
    }

    public void setTimeSched(String timeSched) {
        this.timeSched = timeSched;
    }
}
