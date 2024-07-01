package UI;

import javax.swing.*;
import java.awt.*;

public class QuizCreator extends JPanel{
    JLabel quizBuilder = new JLabel("Quiz Builder");
    JLabel quizNameLabel = new JLabel("Quiz Name");
    JTextField quizName = new JTextField();
    JLabel quizDescriptionLabel = new JLabel("Quiz Description");
    JTextArea quizDescription = new JTextArea();
    JLabel quizCategoryLabel = new JLabel("Quiz Category");
    JTextField quizCategory = new JTextField();
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
    }
    void showCreator() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(new GridBagLayout());

        quizDescription.setLineWrap(true);
        quizDescription.setWrapStyleWord(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 30, 20, 10);
        gbc = addComponent(panel, quizNameLabel, gbc);
        gbc = addComponent(panel, quizDescriptionLabel, gbc);
        gbc = addComponent(panel, quizCategoryLabel, gbc);
        gbc = addComponent(panel, quizTimeLabel, gbc);
        gbc.gridx =1;
        gbc.gridy = 0;
        gbc = addComponent(panel, quizBuilder, gbc);
        gbc = addComponent(panel, quizName, gbc);
        gbc = addComponent(panel, quizDesc, gbc);
        gbc = addComponent(panel, quizCategory, gbc);
        gbc = addComponent(panel, quizTime, gbc);
        gbc = addComponent(panel, quizRandom, gbc);
        gbc.gridx = 2;
        gbc.gridy --;
        gbc = addComponent(panel, createQuiz, gbc);
        gbc = addComponent(panel, createAndAddQuestion, gbc);
        gbc = addComponent(panel, backToMenu, gbc);

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


        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private GridBagConstraints addComponent(JPanel panel, JComponent component, GridBagConstraints gbc) {
        component.setPreferredSize(new Dimension(400, 100));
        component.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(component, gbc);
        gbc.gridy++;
        return gbc;
    }

}
