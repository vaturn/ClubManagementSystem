
import java.sql.*;

public class DBSystem {
    private Connection con;

    public User getUserInfo(int num) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT u.name, u.role, c.club_name , m.role FROM users u JOIN memberships m ON u.user_id = m.user_id JOIN clubs c ON m.club_id = c.club_id WHERE u.user_id = "
                            + num);
            System.out.println(rs.getInt(1) + " " + rs.getString(2) +
                    " " + rs.getString(3));
            return new User(num, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
        } catch (Exception e) {
            System.out.println("LOG (E) : Failed to Look Up Member Information");
            return null;
        }
    }

    public DBSystem() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.56.101:3308/club_db", "mskim", "1234");
        } catch (Exception e) {
            // 연결 실패
            System.out.println("LOG (E) : Connection to SQL Failed");
        }

    }

    // 소멸자
    @Override
    protected void finalize() throws Throwable {
        con.close();
    }
}