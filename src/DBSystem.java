
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBSystem {
    private Connection con;

    public List<Club> getClubList() {

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT c.club_name, c.club_id, u.name, c.description, c.est_date " +
                            "FROM clubs c JOIN users u ON c.president_id = u.user_id;");

            List<Club> clubs = new ArrayList<>();

            while (rs.next()) {
                String name = rs.getString("club_name");
                int id = rs.getInt("club_id");
                String presidentName = rs.getString("name");
                String description = rs.getString("description");
                String openingDate = rs.getString("est_date");

                // Club 객체 생성 후 리스트에 추가
                Club club = new Club(name, id, presidentName, description, openingDate);
                clubs.add(club);
            }

            return clubs;
        } catch (Exception e) {
            System.out.println("LOG (E) : Failed to Register New Member");
            return null;
        }
    }

    public User registerUser(int num, String name, String role, String join_date) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO users (user_id, name, role, join_date) VALUES (" + num + " ,\'" + name + "\', \'"
                            + role + "\', \'" + join_date + "\')");

            return getUserInfo(num);
        } catch (Exception e) {
            System.out.println("LOG (E) : Failed to Register New Member");
            return null;
        }
    }

    public Club getClubInfo(int id) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT c.club_name, c.club_id, u.name, c.description, c.est_date FROM clubs c JOIN users u ON c.president_id = u.user_id WHERE c.club_id = "
                            + id + ";");
            System.out.println(rs.getInt(1) + " " + rs.getString(2) +
                    " " + rs.getString(3));
            return new Club(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5));
        } catch (Exception e) {
            System.out.println("LOG (E) : Failed to Look Up Member Information");
            return null;
        }
    }

    public User getUserInfo(int num) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT u.name, u.role, c.club_name , m.role FROM users u JOIN memberships m ON u.user_id = m.user_id JOIN clubs c ON m.club_id = c.club_id WHERE u.user_id = "
                            + num + ";");
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