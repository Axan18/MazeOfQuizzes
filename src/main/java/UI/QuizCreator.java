package UI;

import javax.swing.*;
import java.awt.*;

public class QuizCreator extends JPanel implements Creator{
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
    QuizCreator() {
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
        quizCategory = new JComboBox<>(); //placeholder
        //TODO: add categories to quizCategory JComboBox and make it usable
    }
    @Override
    public void showCreator() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(new GridBagLayout());

        quizDescription.setLineWrap(true);
        quizDescription.setWrapStyleWord(true);
        panel = componentsSetter(panel, 4,quizBuilder, quizNameLabel,quizCategoryLabel,quizDescriptionLabel,quizTimeLabel, quizName, quizCategory,quizDesc, quizTime, quizRandom, createQuiz, createAndAddQuestion, backToMenu);

        //adjustments
        quizBuilder.setFont(new Font("Arial", Font.BOLD, 40));
        quizRandom.setFont(new Font("Arial", Font.PLAIN, 20));
        quizRandom.setBackground(Color.GREEN);
        quizRandom.setPreferredSize(new Dimension(300, 50));
        createQuiz.setPreferredSize(new Dimension(300, 50));
        createQuiz.setFont(new Font("Arial", Font.PLAIN, 20));
        createAndAddQuestion.setPreferredSize(new Dimension(300, 50));
        createAndAddQuestion.setFont(new Font("Arial", Font.PLAIN, 20));
        backToMenu.setPreferredSize(new Dimension(300, 50));
        backToMenu.setFont(new Font("Arial", Font.PLAIN, 20));
        backToMenu.setActionCommand("backToMenu");
        backToMenu.addActionListener(ScreenManager.getInstance());
        //TODO: add action listeners and adding quiz to DB


        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
