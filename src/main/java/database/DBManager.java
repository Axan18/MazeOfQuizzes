package database;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
* Database manager class that handles all database operations.
* Singleton pattern is used to ensure that only one instance of the class is created.
*/
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

    /**
     * DBManager instance getter
     * @return instance of the DBManager
     */
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

    /**
     * Method that returns a connection to the database
     * @return Connection object
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Method that closes the connection to the database
     * @param connection current open Connection
     */
    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that creates all tables in the database
     */
    public void createTables() {
        //could use string builder here but it doesn't really matter
        String categories = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " (" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CATEGORY_NAME + " TEXT NOT NULL UNIQUE, " +
                COLUMN_CATEGORY_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_CATEGORY_NUMBER_OF_QUIZZES + " INTEGER NOT NULL)";
        String quizzes =
                "CREATE TABLE IF NOT EXISTS " + TABLE_QUIZZES + " (" +
                        COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_QUIZ_NAME + " TEXT NOT NULL UNIQUE , " +
                        COLUMN_QUIZ_DESCRIPTION + " TEXT NOT NULL, " +
                        COLUMN_QUIZ_NUMBER_OF_QUESTIONS + " INTEGER NOT NULL, " +
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
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
                statement.execute(categories);
                statement.execute(quizzes);
                statement.execute(questions);
                statement.execute(answers);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    /**
     * Method that adds a new category to the database
     * @param category Category object to be added
     */
    public void createCategory(Category category) {
        String query = "Insert INTO " + TABLE_CATEGORIES + "("+COLUMN_CATEGORY_ID+ ", " + COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_DESCRIPTION + ", " + COLUMN_CATEGORY_NUMBER_OF_QUIZZES + ") VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, category.getId());
                preparedStatement.setString(2, category.getName());
                preparedStatement.setString(3, category.getDescription());
                preparedStatement.setInt(4, category.getNumberOfQuizzes());
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

    /**
     * Method that adds a new quiz to the database
     * @param quiz Quiz object to be added
     */
    public void createQuiz(Quiz quiz) throws CreatingException{
        String query = "INSERT INTO " + TABLE_QUIZZES + "(" + COLUMN_QUIZ_ID + ", " + COLUMN_QUIZ_NAME + ", " + COLUMN_QUIZ_DESCRIPTION + ", " + COLUMN_QUIZ_NUMBER_OF_QUESTIONS + ", " + COLUMN_QUIZ_TIME_LIMIT + ", " + COLUMN_QUIZ_IS_DONE + ", " + COLUMN_QUIZ_IN_ORDER + ", " + COLUMN_QUIZ_HIGH_SCORE + ", " + COLUMN_QUIZ_CATEGORY_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, quiz.getId());
                preparedStatement.setString(2, quiz.getName());
                preparedStatement.setString(3, quiz.getDescription());
                preparedStatement.setInt(4, quiz.getNumberOfQuestions());
                preparedStatement.setInt(5, quiz.getTimeLimit());
                preparedStatement.setInt(6, quiz.isDone()?1:0);
                preparedStatement.setInt(7, quiz.isInOrder()?1:0);
                preparedStatement.setInt(8, quiz.getHighScore());
                preparedStatement.setInt(9, quiz.getCategoryId());
                preparedStatement.executeUpdate();
                connection.commit();
            }catch (SQLException e) {
                if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) { // quiz with the same name already exists
                    throw new UniqueRecordException("Quiz with the same name already exists");
                } else {
                    e.printStackTrace();
                }
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that adds a new question to the database
     * @param question
     */
    public void createQuestion(Question question) {
        //TODO: add checking if quiz exists

        String query = "INSERT INTO " + TABLE_QUESTIONS + "(" + COLUMN_QUESTION_ID+ ", " + COLUMN_QUESTION_QUESTION + ", " + COLUMN_QUESTION_QUIZ_ID + ") VALUES (?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, question.getId());
                preparedStatement.setString(2, question.getQuestion());
                preparedStatement.setInt(3, question.getQuizId());
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

    /**
     * Method that adds a new answer to the database
     * @param answer Answer object to be added
     */
    public void createAnswer(Answer answer) {
        //TODO: add checking if question exists

        String query = "INSERT INTO " + TABLE_ANSWERS + "("+ COLUMN_ANSWER_ID+ ", " + COLUMN_ANSWER_ANSWER + ", " + COLUMN_ANSWER_IS_CORRECT + ", " + COLUMN_ANSWER_QUESTION_ID + ") VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, answer.getId());
                preparedStatement.setString(2, answer.getAnswer());
                preparedStatement.setInt(3, answer.isCorrect());
                preparedStatement.setInt(4, answer.getQuestionId());
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
    /**
     * Method that returns all quizzes for given categoryID from the database
     * @param categoryID category id to filter quizzes by, if null all quizzes are returned
     * @return List of Quiz objects
     */
    public List<Quiz> getQuizzesByCategory(Integer categoryID) throws CreatingException{
        String query = "SELECT * FROM " + TABLE_QUIZZES;
        if(categoryID != null) {
            query += " WHERE " + COLUMN_QUIZ_CATEGORY_ID + " = " + categoryID;
        }
        List<Quiz> quizzes = new LinkedList<>();
        try (Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)){
            while(resultSet.next())
            {
                Quiz quiz = new Quiz(resultSet.getInt(COLUMN_QUIZ_ID), resultSet.getString(COLUMN_QUIZ_NAME), resultSet.getString(COLUMN_QUIZ_DESCRIPTION), resultSet.getInt(COLUMN_QUIZ_NUMBER_OF_QUESTIONS), resultSet.getInt(COLUMN_QUIZ_TIME_LIMIT), resultSet.getInt(COLUMN_QUIZ_IS_DONE)==1, resultSet.getInt(COLUMN_QUIZ_IN_ORDER)==1, resultSet.getInt(COLUMN_QUIZ_HIGH_SCORE), resultSet.getInt(COLUMN_QUIZ_CATEGORY_ID));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }
    /**
     * Method that returns all quizzes from the database
     * @return List of Quiz objects
     */
    public List<Quiz> getQuizzes() throws CreatingException{
        return getQuizzesByCategory(null);
    }
    //TODO: check if it is needed
    public Quiz getQuiz(String name) throws CreatingException{
        String query = "SELECT * FROM " + TABLE_QUIZZES + " WHERE " + COLUMN_QUIZ_NAME + " = ?";
        Quiz quiz = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    quiz = new Quiz(resultSet.getInt(COLUMN_QUIZ_ID), resultSet.getString(COLUMN_QUIZ_NAME), resultSet.getString(COLUMN_QUIZ_DESCRIPTION), resultSet.getInt(COLUMN_QUIZ_NUMBER_OF_QUESTIONS), resultSet.getInt(COLUMN_QUIZ_TIME_LIMIT), resultSet.getInt(COLUMN_QUIZ_IS_DONE)==1, resultSet.getInt(COLUMN_QUIZ_IN_ORDER)==1, resultSet.getInt(COLUMN_QUIZ_HIGH_SCORE), resultSet.getInt(COLUMN_QUIZ_CATEGORY_ID));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }
    /**
     * Method that returns all categories from the database
     * @return List of Category objects
     */
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
    public Category getCategory(String name) {
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + COLUMN_CATEGORY_NAME + " = ?";
        Category category = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    category = new Category(resultSet.getInt(COLUMN_CATEGORY_ID), resultSet.getString(COLUMN_CATEGORY_NAME), resultSet.getString(COLUMN_CATEGORY_DESCRIPTION), resultSet.getInt(COLUMN_CATEGORY_NUMBER_OF_QUIZZES));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
    /**
     * Method that returns all questions for given quizID from the database
     * @param quizID quiz id to filter questions by
     * @return List of Question objects
     */
    public List<Question> getQuestions(Integer quizID) {
        String query = "SELECT * FROM " + TABLE_QUESTIONS;
        if (quizID != null) {
            query += " WHERE " + COLUMN_QUESTION_QUIZ_ID + " = " + quizID;
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
    /**
     * Method that returns all answers for given questionID from the database
     * @param questionId question id to filter answers by
     * @return List of Answer objects
     */
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

    /**
     * Method that returns the highest ID from the given table
     * @param table
     * @return
     */
    public int getNextID(@NotNull String table)
    {
        String query;
        int id=0;
        switch (table)
        {
            case TABLE_CATEGORIES:
                query = "SELECT MAX(" + COLUMN_CATEGORY_ID + ") FROM " + TABLE_CATEGORIES;
                break;
            case TABLE_QUIZZES:
                query = "SELECT MAX(" + COLUMN_QUIZ_ID + ") FROM " + TABLE_QUIZZES;
                break;
            case TABLE_QUESTIONS:
                query = "SELECT MAX(" + COLUMN_QUESTION_ID + ") FROM " + TABLE_QUESTIONS;
                break;
            case TABLE_ANSWERS:
                query = "SELECT MAX(" + COLUMN_ANSWER_ID + ") FROM " + TABLE_ANSWERS;
                break;
            default:
                return -1;
        }
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
             id = resultSet.getInt(1);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return id+1;
    }
}
