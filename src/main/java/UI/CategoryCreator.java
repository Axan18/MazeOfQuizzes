package UI;

import database.Category;
import database.DBManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CategoryCreator class is a class that creates a category creation menu screen.
 * Used to create a category and add it to the database.
 * @see JPanel
 * @see Creator
 * @see ScreenManager
 */
public class CategoryCreator extends JPanel implements Creator, ActionListener {
    JLabel categoryBuilder = new JLabel("Category Builder");
    JLabel categoryNameLabel = new JLabel("Category Name");
    JTextField categoryName = new JTextField();
    JLabel categoryDescriptionLabel = new JLabel("Category Description");
    JTextArea categoryDescription = new JTextArea();
    JScrollPane categoryDesc = new JScrollPane(categoryDescription);
    JButton createCategory = new JButton("Create Category");
    JButton createCategoryAndAddQuiz = new JButton("Create and Add Quiz");
    JButton backToMenu = new JButton("Back to Menu");
    ScreenManager screenManager = ScreenManager.getInstance();

    public CategoryCreator() {
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
    }

    /**
     * Method to show the creator screen.
     * A new panel is created and components are set on the panel.
     * @see Creator
     */
    @Override
    public void showCreator()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(new GridBagLayout());

        categoryDescription.setLineWrap(true);
        categoryDescription.setWrapStyleWord(true);
        panel = componentsSetter(panel, 2,categoryBuilder, categoryNameLabel,categoryDescriptionLabel, categoryName, categoryDesc, createCategory, createCategoryAndAddQuiz, backToMenu);

        //adjustments
        categoryBuilder.setFont(new Font("Arial", Font.BOLD, 40));
        createCategoryAndAddQuiz.setActionCommand("createQuiz");
        backToMenu.setActionCommand("backToMenu");
        backToMenu.addActionListener(screenManager);
        createCategoryAndAddQuiz.addActionListener(screenManager);
        createCategoryAndAddQuiz.addActionListener(this);

        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("createQuiz")) {
            DBManager dbManager = DBManager.getInstance();
            int id = dbManager.getNextID(DBManager.TABLE_CATEGORIES);
            Category category = new Category(id,categoryName.getText(), categoryDescription.getText(), 0);
            dbManager.createCategory(category);
        }
    }
}
