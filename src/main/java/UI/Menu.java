package UI;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public interface Menu {
    void showMenu();
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
