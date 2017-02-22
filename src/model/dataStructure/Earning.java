package model.dataStructure;

/**
 * Created by John on 1/26/2017.
 */
public class Earning {

    private String pre_empno, post_empno, workdate,
    date_paid, category, notes = "";

   private double amount, commissioPercentage, commission;

    public String getPre_empno() {
        return pre_empno;
    }

    public void setPre_empno(String pre_empno) {
        this.pre_empno = pre_empno;
    }

    public String getPost_empno() {
        return post_empno;
    }

    public void setPost_empno(String post_empno) {
        this.post_empno = post_empno;
    }

    public String getWorkdate() {
        return workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate;
    }

    public String getDate_paid() {
        return date_paid;
    }

    public void setDate_paid(String date_paid) {
        this.date_paid = date_paid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCommissioPercentage() {
        return commissioPercentage;
    }

    public void setCommissioPercentage(double commissioPercentage) {
        this.commissioPercentage = commissioPercentage;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }
}
