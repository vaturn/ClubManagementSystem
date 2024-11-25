public class User {
    public String name;
    public int student_num;
    public String role;
    public String club;

    User(int student_num, String name, String role_code, String club) {
        this.name = name;
        this.student_num = student_num;
        switch (role_code) {
            case "st":
                this.role = "학부생";
                break;
            case "ex":
                this.role = "임원";
                break;
            case "ad":
                this.role = "운영진";
                break;
            default:
                this.role = "알 수 없음";
                break;
        }

        this.club = club;
    }
}
