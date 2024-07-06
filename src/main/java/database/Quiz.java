package database;

import java.util.Objects;

public class Quiz {
    private int id;
    private String name;
    private String description;
    private int numberOfQuestions;
    private int quizType;
    private int timeLimit;
    private boolean isDone;
    private boolean inOrder;
    private int highScore;
    private int categoryId;

    public Quiz(int id, String name, String description, int numberOfQuestions, int timeLimit, boolean isDone, boolean inOrder, int highScore, int categoryId) throws EmptyFieldException{
        if(Objects.equals(name, "") || Objects.equals(description, ""))
            throw new EmptyFieldException("Please fill in all fields");
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfQuestions = numberOfQuestions;
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

    public boolean isDone() {
        return isDone;
    }

    public boolean isInOrder() {
        return inOrder;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
