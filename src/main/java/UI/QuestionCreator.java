package UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuestionCreator extends JPanel implements Creator {
    JLabel questionBuilder = new JLabel("Question Builder"); //TODO: change it to name of the quiz
    JLabel questionLabel = new JLabel("Question");
    JTextField question = new JTextField();
    JLabel answerLabel = new JLabel("Answer");
    JButton createQuestion = new JButton("Create Question");
    JButton backToMenu = new JButton("Back to Menu");
    QuestionCreator() {
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
    }
    @Override
    public void showCreator() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(new GridBagLayout());
        int numberOfQuestions = 0;
        while(numberOfQuestions > 4 || numberOfQuestions < 1)
        {
            try {
                numberOfQuestions = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of questions (1-4)"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 4");
            }
        }
        ArrayList<JComponent> fields = new ArrayList<>();
        fields.add(questionBuilder);
        fields.add(questionLabel);
        fields.add(question);
        fields.add(answerLabel);
        for (int i = 0; i < numberOfQuestions; i++) {
            JTextField answer = new JTextField();
            JCheckBox correct = new JCheckBox("Correct");
            fields.add(answer);  // Add the JTextField to the list
            fields.add(correct); // Add the JCheckBox to the list
        }
        fields.add(createQuestion);
        fields.add(backToMenu);
        JComponent[] components = fields.toArray(new JComponent[0]);
        panel = questionsComponentsSetter(panel, components);

        questionBuilder.setFont(new Font("Arial", Font.BOLD, 40));
        backToMenu.setActionCommand("backToMenu");
        backToMenu.addActionListener(ScreenManager.getInstance());
        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
