import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClubManagementSystem {
    private JFrame frame;
    private JPanel panel;
    private JPanel topPanel;
    private JComboBox<String> userRoleComboBox;
    private JTextArea outputArea;
    private JButton performAction;

    private int userID = 0;

    public ClubManagementSystem() {

        // UI 설정
        initialUI();

        // 사용자 권한 선택 콤보박스
        String[] roles = { "학부생", "임원", "운영자" };

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

        if (userID == 0) {
            loginUI();
        }

        panel.add(topPanel, BorderLayout.NORTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void loginUI() {
        JButton loginButton;
        loginButton = new JButton("로그인");
        loginButton.addActionListener(e -> loginAction());
        loginButton.setSize(5, 2);

        panel.add(loginButton, BorderLayout.CENTER);

        topPanel.add(new JLabel("로그인을 해주세요!"));
    }

    private void executeAction() {

        outputArea.append("\n[작업 수행]\n");
        // 실제 작업 로직은 여기에 구현
    }

    private void loginAction() {
        outputArea.append("\n[로그인]\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClubManagementSystem::new);
    }
}
