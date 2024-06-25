import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestionPanel extends JPanel implements ActionListener {
    JLabel l;
    JRadioButton[] jb = new JRadioButton[5];
    JButton b1, b2;
    ButtonGroup bg;
    int count = 0, current = 0, x = 1, y = 1, now = 0;
    int[] m = new int[10];
    String[] selectedAnswers = new String[10];
    String[] correctAnswers = {"Float", "Object", "lang", "lang", "SSS IT", "get", "Actionperformed", "getDocumentBase", "main", "JButtonGroup"};
    String[] questions = new String[10];
    int participantId;
    Connection con;
    PreparedStatement pstmt;

    public QuestionPanel(int participantId, Connection con) {
        this.participantId = participantId;
        this.con = con;
        
        try {
            pstmt = con.prepareStatement(
                "INSERT INTO TestResults (participant_id, question, option1, option2, option3, option4, correct_answer, selected_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        l = new JLabel();
        add(l);
        bg = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            jb[i] = new JRadioButton();
            add(jb[i]);
            bg.add(jb[i]);
        }
        b1 = new JButton("Next");
        b2 = new JButton("Bookmark");
        b1.addActionListener(this);
        b2.addActionListener(this);
        add(b1);
        add(b2);
        setQuestions();
        set();
        l.setBounds(30, 40, 450, 20);
        jb[0].setBounds(50, 80, 100, 20);
        jb[1].setBounds(50, 110, 100, 20);
        jb[2].setBounds(50, 140, 100, 20);
        jb[3].setBounds(50, 170, 100, 20);
        b1.setBounds(100, 240, 100, 30);
        b2.setBounds(270, 240, 100, 30);
        setLayout(null);
        setSize(600, 350);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            if (check()) {
                count = count + 1;
            }
            recordAnswer();
            current++;
            set();
            if (current == 9) {
                b1.setEnabled(false);
                b2.setText("Result");
            }
        }
        if (e.getActionCommand().equals("Bookmark")) {
            JButton bk = new JButton("Bookmark" + x);
            bk.setBounds(480, 20 + 30 * x, 100, 30);
            add(bk);
            bk.addActionListener(this);
            m[x] = current;
            x++;
            recordAnswer();
            current++;
            set();
            if (current == 9)
                b2.setText("Result");
            setVisible(false);
            setVisible(true);
        }
        for (int i = 0, y = 1; i < x; i++, y++) {
            if (e.getActionCommand().equals("Bookmark" + y)) {
                if (check()) {
                    count = count + 1;
                }
                now = current;
                current = m[y];
                set();
                ((JButton) e.getSource()).setEnabled(false);
                current = now;
            }
        }

        if (e.getActionCommand().equals("Result")) {
            if (check()) {
                count = count + 1;
            }
            recordAnswer();
            current++;
            showResults();
        }
    }

    void setQuestions() {
        questions[0] = "Que1: Which one among these is not a datatype";
        questions[1] = "Que2: Which class is available to all the class automatically";
        questions[2] = "Que3: Which package is directly available to our class without importing it";
        questions[3] = "Que4: String class is defined in which package";
        questions[4] = "Que5: Which institute is best for java coaching";
        questions[5] = "Que6: Which one among these is not a keyword";
        questions[6] = "Que7: Which one among these is not a class";
        questions[7] = "Que8: Which one among these is not a function of Object class";
        questions[8] = "Que9: Which function is not present in Applet class";
        questions[9] = "Que10: Which one among these is not a valid component";
    }

    void set() {
        jb[4].setSelected(true);
        l.setText(questions[current]);
        if (current == 0) {
            jb[0].setText("int");
            jb[1].setText("Float");
            jb[2].setText("boolean");
            jb[3].setText("char");
        }
        if (current == 1) {
            jb[0].setText("Swing");
            jb[1].setText("Applet");
            jb[2].setText("Object");
            jb[3].setText("ActionEvent");
        }
        if (current == 2) {
            jb[0].setText("swing");
            jb[1].setText("applet");
            jb[2].setText("net");
            jb[3].setText("lang");
        }
        if (current == 3) {
            jb[0].setText("lang");
            jb[1].setText("Swing");
            jb[2].setText("Applet");
            jb[3].setText("awt");
        }
        if (current == 4) {
            jb[0].setText("Utek");
            jb[1].setText("Aptech");
            jb[2].setText("SSS IT");
            jb[3].setText("jtek");
        }
        if (current == 5) {
            jb[0].setText("class");
            jb[1].setText("int");
            jb[2].setText("get");
            jb[3].setText("if");
        }
        if (current == 6) {
            jb[0].setText("Swing");
            jb[1].setText("Actionperformed");
            jb[2].setText("ActionEvent");
            jb[3].setText("Button");
        }
        if (current == 7) {
            jb[0].setText("toString");
            jb[1].setText("finalize");
            jb[2].setText("equals");
            jb[3].setText("getDocumentBase");
        }
        if (current == 8) {
            jb[0].setText("init");
            jb[1].setText("main");
            jb[2].setText("start");
            jb[3].setText("destroy");
        }
        if (current == 9) {
            jb[0].setText("JButton");
            jb[1].setText("JList");
            jb[2].setText("JButtonGroup");
            jb[3].setText("JTextArea");
        }
        l.setBounds(30, 40, 450, 20);
        for (int i = 0, j = 0; i <= 90; i += 30, j++)
            jb[j].setBounds(50, 80 + i, 200, 20);
    }

    boolean check() {
        if (current == 0)
            return (jb[1].isSelected());
        if (current == 1)
            return (jb[2].isSelected());
        if (current == 2)
            return (jb[3].isSelected());
        if (current == 3)
            return (jb[0].isSelected());
        if (current == 4)
            return (jb[2].isSelected());
        if (current == 5)
            return (jb[2].isSelected());
        if (current == 6)
            return (jb[1].isSelected());
        if (current == 7)
            return (jb[3].isSelected());
        if (current == 8)
            return (jb[1].isSelected());
        if (current == 9)
            return (jb[2].isSelected());
        return false;
    }

    void recordAnswer() {
        if (jb[0].isSelected())
            selectedAnswers[current] = jb[0].getText();
        if (jb[1].isSelected())
            selectedAnswers[current] = jb[1].getText();
        if (jb[2].isSelected())
            selectedAnswers[current] = jb[2].getText();
        if (jb[3].isSelected())
            selectedAnswers[current] = jb[3].getText();
    }

    void showResults() {
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("Participant ID: ").append(participantId).append("\n\n");
        for (int i = 0; i < 10; i++) {
            resultMessage.append(questions[i]).append("\n");
            resultMessage.append("Selected Answer: ").append(selectedAnswers[i]).append("\n");
            resultMessage.append("Correct Answer: ").append(correctAnswers[i]).append("\n\n");
            DBUtil.storeTestResult(con, participantId, questions[i], jb[0].getText(), jb[1].getText(), jb[2].getText(), jb[3].getText(), correctAnswers[i], selectedAnswers[i]);
        }
        resultMessage.append("Correct answers: ").append(count).append("/10\n");
        JOptionPane.showMessageDialog(this, resultMessage.toString());
        System.exit(0);
    }

    void storeResultInDB(int questionIndex) {
        try {
            pstmt.setInt(1, participantId);
            pstmt.setString(2, questions[questionIndex]);
            pstmt.setString(3, jb[0].getText());
            pstmt.setString(4, jb[1].getText());
            pstmt.setString(5, jb[2].getText());
            pstmt.setString(6, jb[3].getText());
            pstmt.setString(7, correctAnswers[questionIndex]);
            pstmt.setString(8, selectedAnswers[questionIndex]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    