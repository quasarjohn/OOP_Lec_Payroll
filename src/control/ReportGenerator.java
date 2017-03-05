package control;

import connection.AppConnection;
import javafx.embed.swing.SwingNode;
import model.dataStructure.Employee;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JRViewer;
import utils.DateUtils;
import values.ResourcePaths;


import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by John on 3/4/2017.
 */
public class ReportGenerator extends SwingNode {

    private AppConnection connection;
    private JasperReport report;

    public ReportGenerator() {
        connection = new AppConnection();
        connection.loadDriver();
        connection.connectToDB();
    }

    public void generateIndivPayroll(Employee employee, String date) {
        writeIndivPayrollTemp(employee, date);

        try {
            report = JasperCompileManager.compileReport(ResourcePaths.tempJRFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, connection.getConn());
            JRViewer viewer = new JRViewer(jasperPrint);

            setContent(viewer);
            this.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void writeIndivPayrollTemp(Employee employee, String date) {
        StringBuilder builder = new StringBuilder();
        String str = "";
        try {
            Scanner scanner = new Scanner(new File(ResourcePaths.indivPayrollPath));
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append("\n");
            }
            str = builder.toString();
            str = str.replace("preplaceholder", employee.getPre_empNo() + "");
            str = str.replace("postplaceholder", employee.getPost_empNo() + "");
            str = str.replace("gendateplaceholder", date);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] data = str.getBytes();
        try {
            Files.write(Paths.get(ResourcePaths.tempJRFile), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
