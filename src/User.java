public class User {
    public String name;
    public int student_num;
    public String role;
    public String club;
    public String club_role;

    User(int student_num, String name, String role, String club, String club_role) {
        this.name = name;
        this.student_num = student_num;
        this.role = role;
        this.club_role = club_role;
        this.club = club;
    }
}
