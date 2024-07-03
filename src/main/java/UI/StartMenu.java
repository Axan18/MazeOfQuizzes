package UI;

import javax.swing.*;
import java.awt.*;
/**
 * Class for the start menu.
 * @see Menu
 */
public class StartMenu extends JPanel implements Menu{
    JLabel label = new JLabel("Maze Of Quizzes");
    JButton creator = new JButton("Quiz creator");
    JButton quizzes = new JButton("Quizzes");
    ScreenManager screenManager;

    public StartMenu() {
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.RED);
        screenManager = ScreenManager.getInstance();
        //screenManager.setCurrentScreen(Screens.START_MENU);
    }

    /**
     * Shows the start menu.
     */
    @Override
    public void showMenu() {
        this.removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLUE);
        buttonAdjustments(panel, label, creator, quizzes);
        creator.setActionCommand("creator");
        quizzes.setActionCommand("quizzes");
        creator.addActionListener(screenManager);
        quizzes.addActionListener(screenManager);
        label.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font and size
        label.setForeground(Color.WHITE); // Set text color

        this.add(panel, BorderLayout.CENTER);
        this.revalidate(); // re-layout the components
        this.repaint(); // repaint the JPanel
        this.setVisible(true);
    }
}
