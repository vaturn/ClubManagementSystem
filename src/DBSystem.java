
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBSystem {
    private Connection con;

    public void makeAccount(String usageDescription, int amount, String usageDate, int clubId) {
        // SQL 쿼리문 준비
        String sql = "INSERT INTO financial_records (usage_description, amount, usage_date, club_id) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            // 사용내역, 금액, 사용날짜, 동아리 ID를 파라미터로 설정
            preparedStatement.setString(1, usageDescription);
            preparedStatement.setInt(2, amount);

            // String으로 들어온 사용날짜를 LocalDate로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(usageDate, formatter);
            preparedStatement.setDate(3, java.sql.Date.valueOf(date));

            preparedStatement.setInt(4, clubId); // 동아리 ID

            // 쿼리 실행
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("장부 작성이 완료되었습니다.");
            } else {
                System.out.println("장부 작성에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.err.println("SQL 오류 발생: " + e.getMessage());
        }
    }

    public List<Club> getClubList() {

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT c.club_name, c.club_id, u.name, c.description, c.est_date " +
                            "FROM clubs c LEFT JOIN users u ON c.president_id = u.user_id");

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
                            + id);
            rs.next();
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
                    "SELECT u.name, u.role, c.club_name, m.role, c.club_id FROM users u LEFT JOIN memberships m ON u.user_id = m.user_id LEFT JOIN clubs c ON m.club_id = c.club_id WHERE u.user_id = "
                            + num);
            rs.next();

            return new User(num, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));

        } catch (Exception e) {
            System.out.println(e);
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