package UI;

import javax.swing.*;
import java.awt.*;

public class CategoryCreator extends JPanel implements Creator {
    JLabel categoryBuilder = new JLabel("Category Builder");
    JLabel categoryNameLabel = new JLabel("Category Name");
    JTextField categoryName = new JTextField();
    JLabel categoryDescriptionLabel = new JLabel("Category Description");
    JTextArea categoryDescription = new JTextArea();
    JScrollPane categoryDesc = new JScrollPane(categoryDescription);
    JButton createCategory = new JButton("Create Category");
    JButton createCategoryAndAddQuizz = new JButton("Create and Add Question");
    JButton backToMenu = new JButton("Back to Menu");

    public CategoryCreator() {
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
    }
    @Override
    public void showCreator()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(new GridBagLayout());

        categoryDescription.setLineWrap(true);
        categoryDescription.setWrapStyleWord(true);
        panel = componentsSetter(panel, 2,categoryBuilder, categoryNameLabel,categoryDescriptionLabel, categoryName, categoryDesc, createCategory, createCategoryAndAddQuizz, backToMenu);

        //adjustments
        //TODO: add action listeners and adding quiz to DB
        categoryBuilder.setFont(new Font("Arial", Font.BOLD, 40));
        createCategoryAndAddQuizz.setActionCommand("createQuiz");
        backToMenu.setActionCommand("backToMenu");
        backToMenu.addActionListener(ScreenManager.getInstance());

        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
