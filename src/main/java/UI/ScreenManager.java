package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenManager implements ActionListener {
    private Screens currentScreen = Screens.START_MENU;
    private static volatile ScreenManager screenManager;
    private JFrame window;
    private JPanel panel;

    private ScreenManager() {
    }

    public static ScreenManager getInstance() {
        if (screenManager == null) {
            synchronized (ScreenManager.class) {
                if (screenManager == null) {
                    screenManager = new ScreenManager();
                }
            }
        }
        return screenManager;
    }
    public void setWindow(JFrame window) {
        this.window = window;
        panel = new StartMenu();
        window.add(panel, BorderLayout.CENTER);
    }
    public Screens getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screens screen) {
        currentScreen = screen;
    }

    public void showScreen() {
        window.remove(panel);
        switch (currentScreen) {
            case START_MENU: {
                if (!(panel instanceof StartMenu)) {
                    panel = new StartMenu();
                }
                ((StartMenu) panel).showMenu();
                break;
            }
            case QUIZ_CREATOR: {
                if (!(panel instanceof CreatorEntryMenu)) {
                    panel = new CreatorEntryMenu();
                }
                ((CreatorEntryMenu) panel).showMenu();
                break;
            }
        }
        window.add(panel, BorderLayout.CENTER);
        window.revalidate(); // re-layout the components
        window.repaint(); // repaint the JFrame
        window.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if("creator".equals(e.getActionCommand()))
        {
            this.setCurrentScreen(Screens.QUIZ_CREATOR);
        } else if ("quizzes".equals(e.getActionCommand())) {
            this.setCurrentScreen(Screens.QUIZZES_MENU);
        }
        this.showScreen();
    }
}