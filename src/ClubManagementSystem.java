import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClubManagementSystem {
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> userRoleComboBox;
    private JTextArea outputArea;
    private JButton performAction;

    public ClubManagementSystem() {
        // Frame 설정
        frame = new JFrame("학부 동아리 관리 시스템");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel 설정
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 사용자 권한 선택 콤보박스
        String[] roles = { "학부생", "임원", "운영자" };
        userRoleComboBox = new JComboBox<>(roles);
        userRoleComboBox.addActionListener(e -> updateOptions());

        // 출력 영역
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // 액션 버튼
        performAction = new JButton("선택한 작업 수행");
        performAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeAction();
            }
        });

        // 상단 레이아웃
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("권한 선택: "));
        topPanel.add(userRoleComboBox);

        // Panel에 구성 요소 추가
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(performAction, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void updateOptions() {
        String role = (String) userRoleComboBox.getSelectedItem();
        outputArea.setText(""); // 이전 출력 초기화
        outputArea.append("현재 권한: " + role + "\n");
        switch (role) {
            case "학부생":
                outputArea.append("가능한 작업:\n - 회원 정보 조회/수정\n - 동아리 조회\n - 게시물 작성/수정/삭제\n - 댓글 작성/수정/삭제\n");
                outputArea.append(" - 동아리 지원서 제출\n - 회비 납부\n");
                break;
            case "임원":
                outputArea.append("가능한 작업:\n - 회원 정보 조회/수정\n - 동아리 조회\n - 게시물 작성/수정/삭제\n - 댓글 작성/수정/삭제\n");
                outputArea.append(" - 동아리 활동 기록\n - 동아리 가입 승인\n");
                break;
            case "운영자":
                outputArea.append("가능한 작업:\n - 회원 정보 조회/수정\n - 동아리 조회\n - 게시물 작성/수정/삭제\n - 댓글 작성/수정/삭제\n");
                outputArea.append(" - 사용자 목록 조회\n - 동아리 개설\n - 특정 회원 권한 수정\n");
                break;
        }
    }

    private void executeAction() {
        String role = (String) userRoleComboBox.getSelectedItem();
        outputArea.append("\n[작업 수행]\n");
        outputArea.append(role + " 권한의 작업이 수행됩니다.\n");
        // 실제 작업 로직은 여기에 구현
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClubManagementSystem::new);
    }
}
