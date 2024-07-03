package UI;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Interface for the menu classes.
 * Provides a method to show the menu and a default method to adjust the buttons.
 */
public interface Menu {
    void showMenu();

    /**
     * Adjusts the buttons in the menu.
     * The buttons are centered, one below the other, with a fixed size.
     * @param panel
     * @param components
     */
    default void buttonAdjustments(@NotNull JPanel panel, JComponent @NotNull ... components) {
        Dimension d = new Dimension(300, 50);
        panel.add(Box.createVerticalGlue());
        for (JComponent comp : components) {
            comp.setFont(new Font("Arial", Font.PLAIN, 20));
            comp.setPreferredSize(d);
            comp.setMinimumSize(d);
            comp.setMaximumSize(d);
            comp.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(comp);
            panel.add(Box.createRigidArea(new Dimension(0, 50)));
        }
        panel.add(Box.createVerticalGlue());
    }
}
