package UI;
import javax.swing.*;
import java.awt.*;

public class StartMenu extends JFrame{
    JLabel label = new JLabel("Maze Of Quizzes");
    JButton creator = new JButton("Quiz creator");
    JButton quizzes = new JButton("Quizzes");
    public StartMenu(){
        this.setTitle("Quiz App");
        this.setSize(1920, 1080);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.RED);
        //add(QuizzesMenu, "QuizzesMenu");
    }
    public void showMenu(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLUE);
        Dimension d = new Dimension(200, 100);
        label.setPreferredSize(d);
        label.setMaximumSize(d); // Set the maximum size
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center the text within the JLabel
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        creator.setPreferredSize(d);
        creator.setMaximumSize(d); // Set the maximum size
        creator.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizzes.setPreferredSize(d);
        quizzes.setMaximumSize(d); // Set the maximum size
        quizzes.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(creator);
        panel.add(quizzes);

        // Set the preferred size of the panel
        panel.setPreferredSize(new Dimension(600, 300));
        panel.setMaximumSize(panel.getPreferredSize()); // Set the maximum size of the panel

        // Create a new panel with FlowLayout and add the original panel to it
        JPanel outerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outerPanel.add(panel);

        this.add(outerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
