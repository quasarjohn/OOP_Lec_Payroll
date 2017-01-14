package driver;

import model.dataReader.EmpReader;
import model.dataWriter.DatabaseStructure;
import values.Strings;

/**
 * Created by John on 1/11/2017.
 */
public class Test {

    public static void main(String[] args) {
//
//        Employee emp = new Employee();
//        emp.setPre_empNo(1611);
//        emp.setLastName("Cuaresma");
//        emp.setFirstName("Gilbert John");
//        emp.setMiddleName("Aban");
//        emp.setGender("M");
//        emp.setPhoneNumber("09053952701");
//        emp.setAddress("Quezon City");
//        emp.setContactPerson("Vangie");
//        emp.setContactPersonNumber("09323886901");
//        emp.setContactPersonAddress("Manila");
//        emp.setBirthDate("1994-12-18");
//        emp.setHireDate("2017-1-11");
//        emp.setSchedule("M-T-W-T-F-S");
//        emp.setTime("13.30.05");
//
//        EmployeeWriter employeeAdder = new EmployeeWriter();
//        employeeAdder.addEmployee(emp);
//
        DatabaseStructure.buildDatabaseStructure();

//        int[] array = EmpReader.buildPrimaryKey(Integer.toString(1201338531));
//
//        System.out.println(array[0]);
//        System.out.println(array[1]);

    }
}
