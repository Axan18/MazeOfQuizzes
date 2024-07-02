package UI;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public interface Creator {
    void showCreator();
    default JPanel componentsSetter(@NotNull JPanel panel,int numberOfFields,  @NotNull JComponent... components)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc = addComponent(panel, components[0], gbc);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 30, 20, 10);
        for (int i = 1; i <= numberOfFields; i++) {
            gbc = addComponent(panel, components[i], gbc);
        }
        gbc.gridx = 1; //go to the next column for text fields
        gbc.gridy = 1;
        for(int i = numberOfFields+1; i < components.length-3; i++)
        {
            gbc = addComponent(panel, components[i], gbc);
        }
        gbc.gridx = 2;
        gbc.gridy--;
        for(int i = components.length-3; i < components.length; i++)
        {
            gbc = addComponent(panel, components[i], gbc);
        }
        return panel;
    }
    private GridBagConstraints addComponent(JPanel panel, JComponent component, GridBagConstraints gbc) {
        if(component instanceof JButton)
        {
            component.setPreferredSize(new Dimension(300, 50));
            component.setFont(new Font("Arial", Font.PLAIN, 20));
        }
        else
        {
            component.setPreferredSize(new Dimension(400, 100));
            component.setFont(new Font("Arial", Font.PLAIN, 30));
        }
        panel.add(component, gbc);
        gbc.gridy++;
        return gbc;
    }
}
