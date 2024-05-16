package database;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DBManager {
    private static final String URL = "jdbc:sqlite:quiz.db";
    public static final String TABLE_CATEGORIES = "Categories";
    public static final String COLUMN_CATEGORY_ID = "id";
    public static final String COLUMN_CATEGORY_NAME = "name";
    public static final String COLUMN_CATEGORY_DESCRIPTION = "description";
    public static final String COLUMN_CATEGORY_NUMBER_OF_QUIZZES = "number_of_quizzes";

    public static final String TABLE_QUIZZES = "Quizzes";
    public static final String COLUMN_QUIZ_ID = "quiz_id";
    public static final String COLUMN_QUIZ_NAME = "name";
    public static final String COLUMN_QUIZ_DESCRIPTION = "description";
    public static final String COLUMN_QUIZ_NUMBER_OF_QUESTIONS = "number_of_questions";
    public static final String COLUMN_QUIZ_QUIZ_TYPE = "quiz_type";
    public static final String COLUMN_QUIZ_IS_IMAGE = "is_image";
    public static final String COLUMN_QUIZ_IS_TEXT = "is_text";
    public static final String COLUMN_QUIZ_TIME_LIMIT = "time_limit";
    public static final String COLUMN_QUIZ_IS_DONE = "is_done";
    public static final String COLUMN_QUIZ_IN_ORDER = "in_order";
    public static final String COLUMN_QUIZ_HIGH_SCORE = "high_score";
    public static final String COLUMN_QUIZ_CATEGORY_ID = "category_id";

    public static final String TABLE_QUESTIONS = "Questions";
    public static final String COLUMN_QUESTION_ID = "question_id";
    public static final String COLUMN_QUESTION_QUESTION = "question";
    public static final String COLUMN_QUESTION_QUIZ_ID = "quiz_id";

    public static final String TABLE_ANSWERS = "Answers";
    public static final String COLUMN_ANSWER_ID = "answer_id";
    public static final String COLUMN_ANSWER_ANSWER = "answer";
    public static final String COLUMN_ANSWER_IS_CORRECT = "is_correct";
    public static final String COLUMN_ANSWER_QUESTION_ID = "question_id";

    private static DBManager instance;

    private DBManager() {
    }

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        //could use string builder here but it doesn't really matter
        String categories = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " (" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CATEGORY_NAME + " TEXT NOT NULL, " +
                COLUMN_CATEGORY_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_CATEGORY_NUMBER_OF_QUIZZES + " INTEGER NOT NULL)";
        String quizzes =
                "CREATE TABLE IF NOT EXISTS " + TABLE_QUIZZES + " (" +
                        COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_QUIZ_NAME + " TEXT NOT NULL, " +
                        COLUMN_QUIZ_DESCRIPTION + " TEXT NOT NULL, " +
                        COLUMN_QUIZ_NUMBER_OF_QUESTIONS + " INTEGER NOT NULL, " +
                        COLUMN_QUIZ_QUIZ_TYPE + " TEXT NOT NULL, " +
                        COLUMN_QUIZ_IS_IMAGE + " INTEGER NOT NULL, " +
                        COLUMN_QUIZ_IS_TEXT + " INTEGER NOT NULL, " +
                        COLUMN_QUIZ_TIME_LIMIT + " INTEGER NOT NULL, " +
                        COLUMN_QUIZ_IS_DONE + " INTEGER NOT NULL, " +
                        COLUMN_QUIZ_IN_ORDER + " INTEGER NOT NULL, " +
                        COLUMN_QUIZ_HIGH_SCORE + " INTEGER NOT NULL, " +
                        COLUMN_QUIZ_CATEGORY_ID + " INTEGER NOT NULL)";
        String questions =
                "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS + " (" +
                        COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_QUESTION_QUESTION + " TEXT NOT NULL, " +
                        COLUMN_QUESTION_QUIZ_ID + " INTEGER NOT NULL)";
        String answers =
                "CREATE TABLE IF NOT EXISTS " + TABLE_ANSWERS + " (" +
                        COLUMN_ANSWER_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_ANSWER_ANSWER + " TEXT NOT NULL, " +
                        COLUMN_ANSWER_IS_CORRECT + " INTEGER NOT NULL, " +
                        COLUMN_ANSWER_QUESTION_ID + " INTEGER NOT NULL)";
    }

    public void addCategory(Category category) {
        String query = "Insert INTO " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_DESCRIPTION + ", " + COLUMN_CATEGORY_NUMBER_OF_QUIZZES + ") VALUES (?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, category.getName());
                preparedStatement.setString(2, category.getDescription());
                preparedStatement.setInt(3, category.getNumberOfQuizzes());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuiz(Quiz quiz) {
        String query = "INSERT INTO " + TABLE_QUIZZES + "(" + COLUMN_QUIZ_NAME + ", " + COLUMN_QUIZ_DESCRIPTION + ", " + COLUMN_QUIZ_NUMBER_OF_QUESTIONS + ", " + COLUMN_QUIZ_QUIZ_TYPE + ", " + COLUMN_QUIZ_IS_IMAGE + ", " + COLUMN_QUIZ_IS_TEXT + ", " + COLUMN_QUIZ_TIME_LIMIT + ", " + COLUMN_QUIZ_IS_DONE + ", " + COLUMN_QUIZ_IN_ORDER + ", " + COLUMN_QUIZ_HIGH_SCORE + ", " + COLUMN_QUIZ_CATEGORY_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, quiz.getName());
                preparedStatement.setString(2, quiz.getDescription());
                preparedStatement.setInt(3, quiz.getNumberOfQuestions());
                preparedStatement.setString(4, quiz.getQuizType());
                preparedStatement.setInt(5, quiz.isImage());
                preparedStatement.setInt(6, quiz.isText());
                preparedStatement.setInt(7, quiz.getTimeLimit());
                preparedStatement.setInt(8, quiz.isDone());
                preparedStatement.setInt(9, quiz.isInOrder());
                preparedStatement.setInt(10, quiz.getHighScore());
                preparedStatement.setInt(11, quiz.getCategoryId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuestion(Question question) {
        //TODO: add checking if quiz exists

        String query = "INSERT INTO " + TABLE_QUESTIONS + "(" + COLUMN_QUESTION_QUESTION + ", " + COLUMN_QUESTION_QUIZ_ID + ") VALUES (?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, question.getQuestion());
                preparedStatement.setInt(2, question.getQuizId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAnswer(Answer answer) {
        //TODO: add checking if question exists

        String query = "INSERT INTO " + TABLE_ANSWERS + "(" + COLUMN_ANSWER_ANSWER + ", " + COLUMN_ANSWER_IS_CORRECT + ", " + COLUMN_ANSWER_QUESTION_ID + ") VALUES (?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, answer.getAnswer());
                preparedStatement.setInt(2, answer.isCorrect());
                preparedStatement.setInt(3, answer.getQuestionId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Quiz> getQuizzes(Integer categoryId) {
        String query = "SELECT * FROM " + TABLE_QUIZZES;
        if(categoryId != null) {
            query += " WHERE " + COLUMN_QUIZ_CATEGORY_ID + " = " + categoryId;
        }
        List<Quiz> quizzes = new LinkedList<>();
        try (Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)){
            while(resultSet.next())
            {
                Quiz quiz = new Quiz(resultSet.getInt(COLUMN_QUIZ_ID), resultSet.getString(COLUMN_QUIZ_NAME), resultSet.getString(COLUMN_QUIZ_DESCRIPTION), resultSet.getInt(COLUMN_QUIZ_NUMBER_OF_QUESTIONS), resultSet.getString(COLUMN_QUIZ_QUIZ_TYPE), resultSet.getInt(COLUMN_QUIZ_IS_IMAGE), resultSet.getInt(COLUMN_QUIZ_IS_TEXT), resultSet.getInt(COLUMN_QUIZ_TIME_LIMIT), resultSet.getInt(COLUMN_QUIZ_IS_DONE), resultSet.getInt(COLUMN_QUIZ_IN_ORDER), resultSet.getInt(COLUMN_QUIZ_HIGH_SCORE), resultSet.getInt(COLUMN_QUIZ_CATEGORY_ID));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }
    public List<Category> getCategories() {
        String query = "SELECT * FROM " + TABLE_CATEGORIES;
        List<Category> categories = new LinkedList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            while(resultSet.next())
            {
                Category category = new Category(resultSet.getInt(COLUMN_CATEGORY_ID), resultSet.getString(COLUMN_CATEGORY_NAME), resultSet.getString(COLUMN_CATEGORY_DESCRIPTION), resultSet.getInt(COLUMN_CATEGORY_NUMBER_OF_QUIZZES));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    public List<Question> getQuestions(Integer quizId) {
        String query = "SELECT * FROM " + TABLE_QUESTIONS;
        if (quizId != null) {
            query += " WHERE " + COLUMN_QUESTION_QUIZ_ID + " = " + quizId;
        }
        List<Question> questions = new LinkedList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Question question = new Question(resultSet.getInt(COLUMN_QUESTION_ID), resultSet.getString(COLUMN_QUESTION_QUESTION), resultSet.getInt(COLUMN_QUESTION_QUIZ_ID));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    public List<Answer> getAnswers(Integer questionId) {
        String query = "SELECT * FROM " + TABLE_ANSWERS;
        if (questionId != null) {
            query += " WHERE " + COLUMN_ANSWER_QUESTION_ID + " = " + questionId;
        }
        List<Answer> answers = new LinkedList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Answer answer = new Answer(resultSet.getInt(COLUMN_ANSWER_ID), resultSet.getString(COLUMN_ANSWER_ANSWER), resultSet.getInt(COLUMN_ANSWER_IS_CORRECT), resultSet.getInt(COLUMN_ANSWER_QUESTION_ID));
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }
    

}
