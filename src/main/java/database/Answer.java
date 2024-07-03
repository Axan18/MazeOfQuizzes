package database;

/**
 * Answer class
 */
public class Answer {
    private int id;
    private String answer;
    private int isCorrect;
    private int questionId;

    public Answer(int id, String answer, int isCorrect, int questionId) {
        this.id = id;
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public int isCorrect() {
        return isCorrect;
    }

    public int getQuestionId() {
        return questionId;
    }
}
