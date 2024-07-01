import UI.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static ScreenManager screenManager;
    public static void main(String[] args) {
        JFrame window = new JFrame("Maze Of Quizzes");
        window.setSize(1920, 1080);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setTitle("Maze Of Quizzes");
        window.setLayout(new BorderLayout());
        screenManager = ScreenManager.getInstance();
        screenManager.setWindow(window);
        screenManager.setCurrentScreen(Screens.START_MENU);
        SwingUtilities.invokeLater(() -> screenManager.showScreen());
        window.setVisible(true);
    }

}
