package connection;

import java.sql.*;

/**
 * Created by John on 1/10/2017.
 */
public class AppConnection {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    int updateCount = 0;
    int count = 0;

    public AppConnection() {
    }

    public void loadDriver() {
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
        } catch (Exception var2) {
            System.out.println("Driver not loaded");
            var2.printStackTrace();
        }

    }

    public void connectToDB() {
        String url = "jdbc:db2://localhost:50000/kfavdb:" +
                "user=john;password=password;";

        try {
            this.conn = DriverManager.getConnection(url);
        } catch (SQLException var4) {
            System.out.println("Connection Failed.");
            var4.printStackTrace();
        }
    }

    public void doSomething(String statement) {
        try {
            this.pst = this.conn.prepareStatement(statement);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void update() throws SQLException {
        this.pst.executeUpdate();
    }

    public void query() throws SQLException {

        this.rs = this.pst.executeQuery();

    }

    public ResultSet getRS() {
        return this.rs;
    }

    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public void execute() {
        try {
            this.pst.execute();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void incrementUpdateCount() {
        ++this.updateCount;
    }

    public int getUpdateCount() {
        try {
            this.count = this.pst.getUpdateCount();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return this.count;
    }
}
