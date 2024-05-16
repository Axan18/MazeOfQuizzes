package database;

public class Category {
    private int id;
    private String name;
    private String description;
    private int numberOfQuizzes;

    public Category(int id, String name, String description, int numberOfQuizzes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfQuizzes = numberOfQuizzes;
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

    public int getNumberOfQuizzes() {
        return numberOfQuizzes;
    }
}
