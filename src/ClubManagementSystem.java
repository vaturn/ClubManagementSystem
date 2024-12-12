import javax.swing.*;
import java.awt.*;

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
        topPanel.add(new JLabel("로그인을 해주세요!"));
    }

    private void loginAction() {
        // 새로운 창 생성
        JFrame loginFrame = new JFrame("로그인 선택");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

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
        panel.add(topPanel);
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClubManagementSystem::new);
    }
}
