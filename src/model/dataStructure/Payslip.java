package model.dataStructure;

/**
 * Created by John on 3/4/2017.
 */
public class Payslip {

    private String genDate, lastName, firstName, middleName;

    private int pre_empno, post_empno, days_worked;

    private double hours_worked, sss, pag_ibig, totalBasic, totalComission;

    public double getNetIncome() {
        return totalBasic + totalComission - (sss + pag_ibig);
    }

    public String getGenDate() {
        return genDate;
    }

    public void setGenDate(String genDate) {
        this.genDate = genDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

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

    public int getDays_worked() {
        return days_worked;
    }

    public void setDays_worked(int days_worked) {
        this.days_worked = days_worked;
    }

    public double getHours_worked() {
        return hours_worked;
    }

    public void setHours_worked(double hours_worked) {
        this.hours_worked = hours_worked;
    }

    public double getSss() {
        return sss;
    }

    public void setSss(double sss) {
        this.sss = sss;
    }

    public double getPag_ibig() {
        return pag_ibig;
    }

    public void setPag_ibig(double pag_ibig) {
        this.pag_ibig = pag_ibig;
    }

    public double getTotalBasic() {
        return totalBasic;
    }

    public void setTotalBasic(double totalBasic) {
        this.totalBasic = totalBasic;
    }

    public double getTotalComission() {
        return totalComission;
    }

    public void setTotalComission(double totalComission) {
        this.totalComission = totalComission;
    }
}
