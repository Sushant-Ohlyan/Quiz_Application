import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UserInfoPanel extends JPanel {
    private JTextField nameField, emailField, phoneField;
    private JButton startButton;
    private JFrame parentFrame;

    public UserInfoPanel(JFrame frame) {
        parentFrame = frame;
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        startButton = new JButton("Start Quiz");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(UserInfoPanel.this, "All fields are required.");
                } else {
                    Connection con = DBUtil.initializeDB();
                    int participantId = DBUtil.storeParticipantInfo(con, name, email, phone);
                    if (participantId != -1) {
                        parentFrame.getContentPane().removeAll();
                        parentFrame.add(new QuestionPanel(participantId, con));
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    }
                }
            }
        });

        add(startButton);
    }
}
