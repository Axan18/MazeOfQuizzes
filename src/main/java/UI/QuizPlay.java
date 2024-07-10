package UI;

import database.Answer;
import database.DBManager;
import database.Question;
import database.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class QuizPlay extends JPanel implements ActionListener {

    Quiz quiz;
    JLabel quizName = new JLabel();
    TimeLabel time = new TimeLabel();
    JLabel question = new JLabel();
    List<Map<JLabel,JCheckBox>> answerOptions =new ArrayList<>(); //jlabel and checkbox for each answer of one question
    JButton next = new JButton("Next");
    DBManager db = DBManager.getInstance();
    List<Question> questions;
    Map<Integer,List<Answer>> answers;
    int currentQuestion = -1;
    int totalScore = 0;

    public QuizPlay(Quiz quiz) {
        this.setSize(1920, 1080);
        this.setBackground(java.awt.Color.GREEN);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.quiz = quiz;
        quizName.setText(quiz.getName());
        quizName.setFont(new Font("Arial", Font.PLAIN, 30));
        time.setTime(quiz.getTimeLimit());
        questions = db.getQuestions(quiz.getId());
        answers = new HashMap<>();
        for(Question q : questions)
        {
            answers.put(q.getId(), db.getAnswers(q.getId()));// all answers for all question
        }
        question.setFont(new Font("Arial", Font.PLAIN, 20));
        nextQuestion();
    }
    public void showQuiz() {
        this.removeAll();
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.add(quizName, BorderLayout.WEST);
        header.add(time, BorderLayout.EAST);
        header.setBackground(Color.GREEN);
        header.setMaximumSize(new Dimension(1920, 100));
        JPanel questionPanel = new JPanel();
        questionPanel.add(question);
        JPanel answersPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        List<JLabel> answerLabels = answerOptions.stream().collect(ArrayList::new, (list, map) -> list.addAll(map.keySet()), ArrayList::addAll);
        answerLabels.forEach(label -> label.setFont(new Font("Arial", Font.PLAIN, 20)));
        try{
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(100, 100, 100, 100);
            gbc.gridx = 0;
            gbc.gridy = 0;
            //answersPanel.add(answerLabels.get(0), gbc);
            answersPanel.add(answerOptions.get(0).get(answerLabels.get(0)), gbc);
            gbc.gridx = 1;
            //answersPanel.add(answerLabels.get(1), gbc);
            answersPanel.add(answerOptions.get(1).get(answerLabels.get(1)), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            //answersPanel.add(answerLabels.get(2), gbc);
            answersPanel.add(answerOptions.get(2).get(answerLabels.get(2)), gbc);
            gbc.gridx = 1;
            //answersPanel.add(answerLabels.get(3), gbc);
            answersPanel.add(answerOptions.get(3).get(answerLabels.get(3)), gbc);
        }
        catch(IndexOutOfBoundsException e) {/*do nothing*/}

        JPanel footer = new JPanel(new BorderLayout());
        footer.add(next, BorderLayout.EAST);
        next.addActionListener(this);
        //nextQuestion();
        this.add(header);
        this.add(questionPanel);
        this.add(answersPanel);
        this.add(footer);
    }

    private void nextQuestion() {
        currentQuestion++;
        answerOptions.clear();
        if(currentQuestion == quiz.getNumberOfQuestions())
        {
            time.stop();
            //show results
        }
        else
        {
            question.setText(questions.get(currentQuestion).getQuestion());
            int id = questions.get(currentQuestion).getId();
            List<Answer> ans = answers.get(id);
            for(Answer answer: ans)
            {
                JCheckBox cb = new JCheckBox(answer.getAnswer());
                answerOptions.add(Map.of(new JLabel(answer.getAnswer()),cb));
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Set<String> correctAnswers = new HashSet<>();
        for(Answer a : answers.get(questions.get(currentQuestion).getId()))
        {
            if(a.isCorrect()==1)
            {
                correctAnswers.add(a.getAnswer());
            }
        }
        Set<String> selectedAnswers = new HashSet<>();
        for(Map<JLabel,JCheckBox> map : answerOptions)
        {
            for(Map.Entry<JLabel,JCheckBox> entry : map.entrySet())
            {
                if(entry.getValue().isSelected())
                {
                    String selectedAnswer = entry.getKey().getText();
                    selectedAnswers.add(selectedAnswer);
                }
            }
        }
        int correct = (int) (correctAnswers.size()-correctAnswers.stream().filter(selectedAnswers::contains).count());
        int score = correct/correctAnswers.size();
        totalScore += score;
        nextQuestion();
        showQuiz();
    }
}
