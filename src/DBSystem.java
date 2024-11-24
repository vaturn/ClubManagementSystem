
import java.sql.*;

public class DBSystem {
    private Connection con;

    public User getUserInfo(int num) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Member WHERE id=" + num);
            System.out.println(rs.getInt(1) + " " + rs.getString(2) +
                    " " + rs.getString(3));
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
        } catch (Exception e) {
            System.out.println("LOG (E) : Failed to Look Up Member Information");
            return null;
        }
    }

    public DBSystem() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.56.101:3308/madang", "mskim", "1234");
        } catch (Exception e) {
            // 연결 실패
            System.out.println("LOG (E) : Connection to SQL Failed");
        }

    }

    @Override
    protected void finalize() throws Throwable {
        con.close();
    }

    public static void main(String args[]) {
        try {
            while (rs.next())
                
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}