package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingView extends JFrame {
    JButton withMachine, twoPlayer, guide;

    public StartingView() {
        // Thiết lập JFrame
        setTitle("GAME CỜ TƯỚNG");
        setSize(400, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Tạo các nút
        withMachine = new JButton();
        twoPlayer = new JButton("2 Người Chơi");
        guide = new JButton("Hướng Dẫn");

        // Đặt vị trí cho các nút
        withMachine.setBounds(100, 200, 200, 50);
        twoPlayer.setBounds(100, 300, 200, 50);
        guide.setBounds(100, 400, 200, 50);

        // Thêm các nút vào JFrame
        add(withMachine);
        add(twoPlayer);
        add(guide);

        // Khởi tạo các hành động
        init();

        // Hiển thị JFrame
        setVisible(true);
    }

    private void init() {
        twoPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame();
                dispose(); // Đóng cửa sổ StartingScreen khi mở GameScreen
            }
        });
    }

    public static void main(String[] args) {
        new StartingView();
    }
}
