package UI;

import database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for creating a quiz.
 */
public class QuizCreator extends JPanel implements Creator, ActionListener {
    JLabel quizBuilder = new JLabel("Quiz Builder");
    JLabel quizNameLabel = new JLabel("Quiz Name");
    JTextField quizName = new JTextField();
    JLabel quizDescriptionLabel = new JLabel("Quiz Description");
    JTextArea quizDescription = new JTextArea();
    JLabel quizCategoryLabel = new JLabel("Quiz Category");
    JComboBox<String> quizCategory;
    JLabel quizTimeLabel = new JLabel("Quiz Time");
    JTextField quizTime = new JTextField();
    JCheckBox quizRandom = new JCheckBox("Randomize Questions");
    JScrollPane quizDesc = new JScrollPane(quizDescription);
    JButton createQuiz = new JButton("Create Quiz");
    JButton createAndAddQuestion = new JButton("Create and Add Question");
    JButton backToMenu = new JButton("Back to Menu");
    ScreenManager screenManager = ScreenManager.getInstance();
    QuizCreator() {
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
        quizCategory = new JComboBox<>(); //placeholder
        DBManager dbManager = DBManager.getInstance();
        dbManager.getCategories().forEach(category -> quizCategory.addItem(category.getName()));
    }
    @Override
    public void showCreator() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(new GridBagLayout());

        quizDescription.setLineWrap(true);
        quizDescription.setWrapStyleWord(true);
        panel = componentsSetter(panel, 4, quizBuilder, quizNameLabel, quizCategoryLabel, quizDescriptionLabel, quizTimeLabel, quizName, quizCategory, quizDesc, quizTime, quizRandom, createQuiz, createAndAddQuestion, backToMenu);

        //adjustments
        quizBuilder.setFont(new Font("Arial", Font.BOLD, 40));
        createAndAddQuestion.setActionCommand("createQuestion");
        createQuiz.setActionCommand("createQuiz");
        backToMenu.setActionCommand("backToMenu");
        backToMenu.addActionListener(screenManager);
        createAndAddQuestion.addActionListener(this);
        createQuiz.addActionListener(this);

        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().contains("create")) { //handling the creation of a quiz with both buttons
            DBManager dbManager = DBManager.getInstance();
            int quizId = dbManager.getNextID(DBManager.TABLE_QUIZZES);
            int categoryId = dbManager.getCategory(quizCategory.getSelectedItem().toString()).getId();
            try{
                Quiz quiz = new Quiz(quizId, quizName.getText(), quizDescription.getText(), 0, Integer.parseInt(quizTime.getText()), false, !quizRandom.isSelected(), 0, categoryId);
                screenManager.setQuiz(quiz);
                dbManager.createQuiz(quiz);
                //if quiz is created, go to the question creator or stay
                if(e.getActionCommand().equals("createQuestion")) {
                    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "createQuestion");
                    screenManager.actionPerformed(event);
                }
            }catch (CreatingException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Please enter a number for the time limit", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
