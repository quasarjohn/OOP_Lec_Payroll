package model.dataWriter;

import connection.AppConnection;
import model.dataReader.PayrollReader;
import model.dataStructure.Employee;
import model.dataStructure.Payslip;
import utils.DateUtils;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by John on 3/4/2017.
 */
public class PayrollWriter {

    private AppConnection conn;

    private DecimalFormat df = new DecimalFormat("#.00");
    public PayrollWriter() {
        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();
    }

    public void generate(ArrayList<Employee> employees) {
        PayrollReader reader = new PayrollReader();

        ArrayList<Payslip> payslips = new ArrayList<>();

        for (Employee emp : employees) {

            Payslip payslip = new Payslip();
            payslip.setGenDate(DateUtils.getCurrentDate());
            payslip.setDays_worked(reader.getWorkdateCount(emp));
            payslip.setPre_empno(emp.getPre_empNo());
            payslip.setPost_empno(emp.getPost_empNo());
            payslip.setHours_worked(reader.getHoursWorked(emp));

            payslip.setTotalComission(reader.getTotalCommission(emp));
            payslip.setTotalBasic(reader.getTotalBasic(emp));
            payslip.setSss(reader.getSSS(emp));
            payslip.setPag_ibig(reader.getPagIbig(emp));

            payslips.add(payslip);
        }

        for (Payslip payslip : payslips) {
            writeToPayroll(payslip);
        }

        conn.doSomething("update earnings set date_paid = '" + DateUtils.getCurrentDate() +
                "' where date_paid is null");
        try {
            conn.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.doSomething("update attendance set date_paid = '" + DateUtils.getCurrentDate() +
                "' where date_paid is null");
        conn.closeConnection();
    }

    private void writeToPayroll(Payslip payslip) {
        conn.doSomething("insert into payroll values('" +
                payslip.getGenDate() + "'," +
                payslip.getPre_empno() + "," +
                payslip.getPost_empno() + "," +
                df.format(payslip.getTotalComission()) + "," +
                df.format(payslip.getTotalBasic()) + "," +
                df.format(payslip.getHours_worked()) + "," +
                payslip.getDays_worked() + "," +
                payslip.getPag_ibig() + "," +
                payslip.getSss() + "," +
                df.format(payslip.getNetIncome()) + ")");
        try {
            conn.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
