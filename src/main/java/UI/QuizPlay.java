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
    double totalScore = 0;
    ScreenManager screenManager = ScreenManager.getInstance();

    public QuizPlay(Quiz quiz) {
        this.setSize(1920, 1080);
        this.setBackground(java.awt.Color.GREEN);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.quiz = quiz;
        quizName.setText(quiz.getName());
        quizName.setFont(new Font("Arial", Font.PLAIN, 30));
        time.setTime(quiz.getTimeLimit());
        questions = db.getQuestions(quiz.getId());
        if(!quiz.isInOrder())
        {
            Collections.shuffle(questions);
        }
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
        for (ActionListener al : next.getActionListeners()) {
            next.removeActionListener(al);
        }
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
            answersPanel.add(answerOptions.get(0).get(answerLabels.get(0)), gbc);
            gbc.gridx = 1;
            answersPanel.add(answerOptions.get(1).get(answerLabels.get(1)), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            answersPanel.add(answerOptions.get(2).get(answerLabels.get(2)), gbc);
            gbc.gridx = 1;
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
        this.revalidate();
        this.repaint();
    }

    private void nextQuestion() {
        currentQuestion++;
        answerOptions.clear();
        question.setText(questions.get(currentQuestion).getQuestion());
        int id = questions.get(currentQuestion).getId();
        List<Answer> ans = answers.get(id);
        for(Answer answer: ans)
        {
            JCheckBox cb = new JCheckBox(answer.getAnswer());
            answerOptions.add(Map.of(new JLabel(answer.getAnswer()),cb));
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
        double fraction = 1.0/correctAnswers.size(); //fraction of correct answers
        double correct = (correctAnswers.stream().filter(selectedAnswers::contains).count()); //number of selected correct answers
        double score = (fraction*correct - fraction*(selectedAnswers.size()-correct)); //score for this question
        totalScore += score>=0?score:0;
        if(currentQuestion == quiz.getNumberOfQuestions()-1)
        {
            currentQuestion = -1;
            int decision = JOptionPane.showConfirmDialog(this, "Your score is: " + totalScore+'/'+ quiz.getNumberOfQuestions() + "\n Do you want to retake quiz?", "Results", JOptionPane.YES_NO_OPTION);
            if(decision == JOptionPane.YES_OPTION)
            {
                time.stop();
                totalScore = 0;
                nextQuestion();
                screenManager.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "playQuiz"+quiz.getId()));
            }
            else
            {
                screenManager.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "backToMenu"));
            }
        }
        else
        {
            nextQuestion();
            showQuiz();
        }
    }
}