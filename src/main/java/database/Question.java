package database;

public class Question {
    private int id;
    private String question;
    private int quizId;

    public Question(int id, String question, int quizId) {
        this.id = id;
        this.question = question;
        this.quizId = quizId;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuizId() {
        return quizId;
    }
}
