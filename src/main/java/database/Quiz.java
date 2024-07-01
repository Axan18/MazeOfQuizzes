package database;

public class Quiz {
    private int id;
    private String name;
    private String description;
    private int numberOfQuestions;
    private int quizType;
    private int timeLimit;
    private int isDone;
    private int inOrder;
    private int highScore;
    private int categoryId;

    public Quiz(int id, String name, String description, int numberOfQuestions, int quizType, int timeLimit, int isDone, int inOrder, int highScore, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfQuestions = numberOfQuestions;
        this.quizType = quizType;
        this.timeLimit = timeLimit;
        this.isDone = isDone;
        this.inOrder = inOrder;
        this.highScore = highScore;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public int getQuizType() {
        return quizType;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int isDone() {
        return isDone;
    }

    public int isInOrder() {
        return inOrder;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
