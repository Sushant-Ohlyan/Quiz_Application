import java.sql.*;

public class DBUtil {
    public static Connection initializeDB() {
        Connection con = null;
        try {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarevise", "root", "9654558088");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static int storeParticipantInfo(Connection con, String name, String email, String phone) {
        int participantId = -1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Participant (name, email, phone) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                participantId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participantId;
    }

    public static void storeTestResult(Connection con, int participantId, String question, String option1, String option2, String option3, String option4, String correctAnswer, String selectedAnswer) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO TestResults (participant_id, question, option1, option2, option3, option4, correct_answer, selected_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, participantId);
            ps.setString(2, question);
            ps.setString(3, option1);
            ps.setString(4, option2);
            ps.setString(5, option3);
            ps.setString(6, option4);
            ps.setString(7, correctAnswer);
            ps.setString(8, selectedAnswer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}