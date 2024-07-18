package UI;

import database.Answer;
import database.DBManager;
import database.Question;
import database.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuestionCreator extends JPanel implements Creator, ActionListener {
    JLabel questionBuilder = new JLabel();
    JLabel questionLabel = new JLabel("Question");
    JTextField question = new JTextField();
    JLabel answerLabel = new JLabel("Answer");
    JButton createQuestion = new JButton("Create Question");
    JButton backToMenu = new JButton("Back to Menu");
    Quiz quiz;
    ArrayList<JTextField> answers;
    ArrayList<JCheckBox> correctAnswers = new ArrayList<>();
    int numberOfAnswers;
    QuestionCreator(Quiz quiz) {
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
        this.quiz = quiz;
        questionBuilder.setText(quiz.getName());
    }
    @Override
    public void showCreator() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(new GridBagLayout());
        numberOfAnswers = getNumberOfAnswers();
        ArrayList<JComponent> comps = new ArrayList<>();
        answers = new ArrayList<>();
        comps.add(questionBuilder);
        comps.add(questionLabel);
        comps.add(question);
        comps.add(answerLabel);
        for (int i = 0; i < numberOfAnswers; i++) {
            JTextField answer = new JTextField();
            JCheckBox correct = new JCheckBox("Correct");
            comps.add(answer);  // Add the JTextField to the list
            comps.add(correct); // Add the JCheckBox to the list
            answers.add(answer); // Add the JTextField to the list
            correctAnswers.add(correct); // Add the JCheckBox to the list
        }
        comps.add(createQuestion);
        comps.add(backToMenu);
        JComponent[] components = comps.toArray(new JComponent[0]);
        panel = questionsComponentsSetter(panel, components);

        questionBuilder.setFont(new Font("Arial", Font.BOLD, 40));
        createQuestion.setActionCommand("createQuestion");
        createQuestion.addActionListener(this);
        backToMenu.setActionCommand("backToMenu");
        backToMenu.addActionListener(ScreenManager.getInstance());
        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("createQuestion")){
            DBManager dbManager = DBManager.getInstance();
            int questionId = dbManager.getNextID(DBManager.TABLE_QUESTIONS);
            int quizId = quiz.getId();
            try {
                dbManager.createQuestion(new Question(questionId, question.getText(), quizId));
                for(int i = 0; i < numberOfAnswers; i++) {
                    JTextField answer = answers.get(i);
                    JCheckBox correct = correctAnswers.get(i);
                    dbManager.createAnswer(new Answer(dbManager.getNextID(DBManager.TABLE_ANSWERS), answer.getText(), correct.isSelected()?1:0, questionId));
                    answer.setText("");
                    correct.setSelected(false);
                }
                question.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.removeAll();
            ScreenManager.getInstance().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "createQuestion"));
        }
    }
private int getNumberOfAnswers() {
    Integer numberOfAnswers = 0;
    Object[] options = {"Next question", "End quiz"};
    JPanel panel = new JPanel();
    panel.add(new JLabel("Enter the number of answers (1-4)"));
    JTextField textField = new JTextField(10);
    panel.add(textField);
        while(numberOfAnswers > 4 || numberOfAnswers < 1)
        {
            try {
                int input = JOptionPane.showOptionDialog(null,panel, "Number of answers", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
                if(input == JOptionPane.NO_OPTION)
                {
                    ScreenManager.getInstance().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "backToMenu"));
                    return 0;
                }
                numberOfAnswers = Integer.parseInt(textField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 4");
            }
        }
    return numberOfAnswers;
    }
}
