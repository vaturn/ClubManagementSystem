import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.time.LocalDateTime;

public class ClubManagementSystem {
    private JFrame frame;
    private JPanel panel;
    private JPanel topPanel;
    private JTextArea outputArea;

    private User user = null;

    private DBSystem controller;

    private Club[] clubs;

    public ClubManagementSystem() {

        controller = new DBSystem();
        // UI 설정
        initialUI();
    }

    private void initialUI() {
        // 메인 프레임
        frame = new JFrame("학부 동아리 관리 시스템");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 메인 panel 설정
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 상단 panel 설정
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        if (user == null) { // 로그인 상태 확인
            loginUI();
        } else { // 로그인 되어 있음
            showMainUI();
        }

        panel.add(topPanel, BorderLayout.NORTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void loginUI() {
        JButton loginButton = new JButton("로그인");
        loginButton.addActionListener(e -> loginAction()); // 로그인 버튼 클릭 시 loginAction 호출
        panel.add(loginButton, BorderLayout.CENTER);

        // 회원가입 버튼 추가
        JButton registerButton = new JButton("회원가입");
        registerButton.addActionListener(e -> registerAction()); // 회원가입 버튼 클릭 시 registerAction 호출
        panel.add(registerButton, BorderLayout.SOUTH);

        topPanel.add(new JLabel("로그인을 해주세요!"));
    }

    private void loginAction() {
        // 새로운 창 생성
        JFrame loginFrame = new JFrame("로그인 선택");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));

        // 버튼 1: 홍길동(학부생)
        JButton studentButton = new JButton("홍길동(학부생)으로 로그인");
        studentButton.addActionListener(e -> {
            loginFrame.dispose(); // 로그인 창 닫기
            handleLogin(10);
        });

        // 버튼 2: 김철수(임원)
        JButton executiveButton = new JButton("김철수(임원)으로 로그인");
        executiveButton.addActionListener(e -> {
            loginFrame.dispose(); // 로그인 창 닫기
            handleLogin(5);
        });

        // 버튼 3: 김오퍼(운영진)
        JButton adminButton = new JButton("김오퍼(운영진)으로 로그인");
        adminButton.addActionListener(e -> {
            loginFrame.dispose(); // 로그인 창 닫기
            handleLogin(1);
        });

        // 버튼 추가
        buttonPanel.add(studentButton);
        buttonPanel.add(executiveButton);
        buttonPanel.add(adminButton);

        // 창에 버튼 패널 추가
        loginFrame.add(buttonPanel);
        loginFrame.setVisible(true);
    }

    private void registerAction() {
        // 회원가입 창 생성
        JFrame registerFrame = new JFrame("회원가입");
        registerFrame.setSize(300, 200);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 패널 설정
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // 학번 입력
        JLabel idLabel = new JLabel("학번:");
        JTextField idField = new JTextField();

        // 이름 입력
        JLabel nameLabel = new JLabel("이름:");
        JTextField nameField = new JTextField();

        // 완료 버튼
        JButton completeButton = new JButton("완료");
        completeButton.addActionListener(e -> {
            int studentId = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            // 현재 시간 가져오기
            String currentTime = LocalDateTime.now().toString();

            // registerUser 호출
            user = controller.registerUser(studentId, name, "학부생", currentTime);
            registerFrame.dispose(); // 회원가입 창 닫기
        });

        // 패널에 컴포넌트 추가
        registerPanel.add(idLabel);
        registerPanel.add(idField);
        registerPanel.add(nameLabel);
        registerPanel.add(nameField);
        registerPanel.add(completeButton);

        // 회원가입 창에 패널 추가
        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);
    }

    private void showClubList() {
        // Get the list of clubs
        List<Club> clubList = controller.getClubList();

        // Create a new panel to display the clubs
        JPanel clubListPanel = new JPanel();
        clubListPanel.setLayout(new BoxLayout(clubListPanel, BoxLayout.Y_AXIS));

        System.out.println("LOG (E)");
        // Add each club to the panel
        for (Club club : clubList) {
            System.out.println(club.name);
            JLabel clubLabel = new JLabel("동아리 이름: " + club.name + ", 회장: " + club.presidentName);
            clubListPanel.add(clubLabel);
        }

        JScrollPane scrollPane = new JScrollPane(clubListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.removeAll();
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }

    private void handleLogin(int num) {
        // 선택된 역할 값에 따라 처리
        user = controller.getUserInfo(num); // 유저 정보 가져오기
        panel.removeAll(); // 기존 패널 초기화
        showMainUI(); // 메인 UI에 역할 반영
    }

    private void showMainUI() {
        JLabel welcomeLabel = new JLabel("환영합니다! " + user.name + "님!");
        topPanel.removeAll();
        topPanel.add(welcomeLabel);

        // Add club info if user is associated with a club
        if (user.club != null && !user.club.isEmpty()) {
            JLabel clubLabel = new JLabel("소속 동아리: " + user.club);
            topPanel.add(clubLabel);
        }

        panel.add(topPanel);
        panel.revalidate();
        panel.repaint();

        showClubList();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClubManagementSystem::new);
    }
}
