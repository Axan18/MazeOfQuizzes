package UI;

import javax.swing.*;
import java.awt.*;

/**
 * CreatorEntryMenu class
 * This class is the menu that allows the user to choose between creating a quiz or a category.
 * @see Menu
 */
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
        createQuiz.setActionCommand("createQuiz");
        createCategory.setActionCommand("createCategory");
        backToMenu.setActionCommand("backToMenu");
        createQuiz.addActionListener(screenManager);
        createCategory.addActionListener(screenManager);
        backToMenu.addActionListener(screenManager);
        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
