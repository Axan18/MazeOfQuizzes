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
        for(int i = numberOfFields+1; i < numberOfFields*2+1; i++)
        {
            gbc = addComponent(panel, components[i], gbc);
        }
        if(components[numberOfFields*2+1] instanceof JCheckBox)
        {
            gbc = addComponent(panel, components[numberOfFields*2+1], gbc); //addition for checkbox
            components[numberOfFields*2+1].setBackground(Color.GREEN);
        }
        gbc.gridx = 2;
        gbc.gridy--;
        for(int i = numberOfFields*2+2; i < components.length; i++)
        {
            gbc = addComponent(panel, components[i], gbc);
        }
        return panel;
    }
    default JPanel questionsComponentsSetter(@NotNull JPanel panel,int numberOfFields,  @NotNull JComponent... components)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc = addComponent(panel, components[0], gbc); //main label
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 30, 20, 10);
        gbc = addComponent(panel, components[1], gbc); //question label
        gbc.gridx = 1; //go to the next column for text fields
        gbc.gridy = 1;
        gbc = addComponent(panel, components[2], gbc); //question text field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc = addComponent(panel, components[3], gbc); //answer label
        for(int i = 4; i < components.length-2; i+=2)
        {
            gbc = addComponent(panel, components[i], gbc);
        }
        gbc.gridx = 1;
        gbc.gridy = 3;
        for(int i = 5; i < components.length-2; i+=2)
        {
            gbc = addComponent(panel, components[i], gbc);
            components[i].setBackground(Color.GREEN);
        }
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc = addComponent(panel, components[components.length-2], gbc); //create question button
        gbc = addComponent(panel, components[components.length-1], gbc); //back to menu button
        return panel;
    }
    private GridBagConstraints addComponent(JPanel panel, JComponent component, GridBagConstraints gbc) {
        if(component instanceof JButton)
        {
            component.setPreferredSize(new Dimension(300, 50));
            component.setFont(new Font("Arial", Font.PLAIN, 20));
        }
        else if(component instanceof JCheckBox)
        {
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
