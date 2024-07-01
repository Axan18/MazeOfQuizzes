package UI;

import javax.swing.*;
import java.awt.*;

public class CreatorEntryMenu extends JPanel implements Menu {
    ScreenManager screenManager;
    JButton createQuiz = new JButton("Create Quiz");
    JButton createCategory = new JButton("Create Category");
    JButton backToMenu = new JButton("Back to Menu");
    public CreatorEntryMenu() {
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
        screenManager = ScreenManager.getInstance();
//screenManager.setCurrentScreen(Screens.QUIZ_CREATOR); ??
    }
    @Override
    public void showMenu()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.RED);
        buttonAdjustments(panel, createQuiz, createCategory, backToMenu);
        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
