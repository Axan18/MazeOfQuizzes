package UI;

import database.Category;
import database.DBManager;
import database.Quiz;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class QuizList extends JPanel implements ActionListener {
    JComponent sortBy = new JLabel("Sort by:");
    JComboBox<String> sortOptions;
    JComponent category = new JLabel("Category:");
    JComboBox<String> categoryOptions;

    List<JPanel> quizzesPanels = new ArrayList<>(); //list of quizzes to be displayed
    List<Quiz> quizzes = new ArrayList<>();
    DBManager dbManager = DBManager.getInstance();
    List<Category> categories = dbManager.getCategories();
    JScrollPane scrollPane = new JScrollPane();
    JButton back = new JButton("Back to menu");
    List<Quiz> unfilteredQuizzes = new ArrayList<>();
    boolean isFiltered = false;
    boolean isSorted = false;

    QuizList() {
        this.setSize(1920, 1080);
        this.setLayout(new GridBagLayout());
        sortOptions = new JComboBox<>(new String[]{"ID", "Name", "Category"});
        sortOptions.addActionListener(this);
        categoryOptions = new JComboBox<>();
        categoryOptions.addItem("All categories");
        categories.forEach(cat -> categoryOptions.addItem(cat.getName()));
        categoryOptions.addActionListener(this);
        try{
            quizzes = dbManager.getQuizzes();
            unfilteredQuizzes = quizzes;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Shows the menu, header and list of all quizzes with options.
     */
    public void showQuizzes() //disgusting code here
    {
        quizzesPanels.clear();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;
        JPanel manager = new JPanel();
        manager.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        manager.setLayout(new BorderLayout());
        sortBy.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
        sortBy.setFont(new Font("Arial", Font.PLAIN, 20));
        sortOptions.setBorder(BorderFactory.createEmptyBorder(0,0,0,100));
        category.setFont(new Font("Arial", Font.PLAIN, 20));
        category.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
        categoryOptions.setBorder(BorderFactory.createEmptyBorder(0,0,0,900));
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.setActionCommand("backToMenu");
        back.addActionListener(ScreenManager.getInstance());
        JPanel sorting = new JPanel(new BorderLayout());
        sorting.add(sortBy, BorderLayout.WEST);
        sorting.add(sortOptions, BorderLayout.CENTER);
        manager.add(sorting, BorderLayout.WEST);
        JPanel categoriesSelection = new JPanel(new BorderLayout());
        categoriesSelection.add(category, BorderLayout.WEST);
        categoriesSelection.add(categoryOptions, BorderLayout.CENTER);
        manager.add(categoriesSelection, BorderLayout.CENTER);

        manager.add(back, BorderLayout.EAST);

        this.add(manager, gbc);
        gbc.gridy++;

        JPanel quizHeader = new JPanel();
        quizHeader.setLayout(new GridBagLayout());
        JLabel IDLabel = new JLabel("ID");
        JLabel nameLabel = new JLabel("Name");
        JLabel categoryLabel = new JLabel("Category");
        JLabel highScoreLabel = new JLabel("High score");
        JLabel timeLimitLabel = new JLabel("Time limit");
        JLabel quizNumberOfQuestionsLabel = new JLabel("Number of questions");
        JLabel functionsLabel = new JLabel("Functions");


        //borders and sizes
        Dimension idSize = new Dimension(40, 40);
        Dimension textSize = new Dimension(360, 40);
        IDLabel.setPreferredSize(idSize);
        IDLabel.setMaximumSize(idSize);
        nameLabel.setPreferredSize(textSize);
        nameLabel.setMaximumSize(textSize);
        categoryLabel.setPreferredSize(textSize);
        categoryLabel.setMaximumSize(textSize);
        Dimension otherSize = new Dimension(120, 40);
        highScoreLabel.setPreferredSize(otherSize);
        highScoreLabel.setMaximumSize(otherSize);
        timeLimitLabel.setPreferredSize(otherSize);
        timeLimitLabel.setMaximumSize(otherSize);
        quizNumberOfQuestionsLabel.setPreferredSize(otherSize);
        quizNumberOfQuestionsLabel.setMaximumSize(otherSize);
        functionsLabel.setPreferredSize(textSize);
        functionsLabel.setMaximumSize(textSize);
        Border line = BorderFactory.createLineBorder(Color.black);
        highScoreLabel.setBorder(line);
        timeLimitLabel.setBorder(line);
        quizNumberOfQuestionsLabel.setBorder(line);
        functionsLabel.setBorder(line);
        IDLabel.setBorder(line);
        nameLabel.setBorder(line);
        categoryLabel.setBorder(line);

        //centering the text in the labels
        IDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLimitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quizNumberOfQuestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        functionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        quizHeader.add(IDLabel);
        quizHeader.add(nameLabel);
        quizHeader.add(categoryLabel);
        quizHeader.add(highScoreLabel);
        quizHeader.add(timeLimitLabel);
        quizHeader.add(quizNumberOfQuestionsLabel);
        quizHeader.add(functionsLabel);

        //TODO: change color
        for(Component comp : quizHeader.getComponents())
        {
            ((JComponent)comp).setOpaque(true);
            comp.setBackground(Color.LIGHT_GRAY);
        }
        Dimension headerSize = new Dimension(1920, 40); // adjust the height as needed
        quizHeader.setPreferredSize(headerSize);
        quizHeader.setMaximumSize(headerSize);
        quizHeader.setMinimumSize(headerSize);
        quizHeader.setBorder(BorderFactory.createEmptyBorder(0,0,0,15)); //adjustment for scrolling bar
        gbc.fill = GridBagConstraints.BOTH;
        this.add(quizHeader, gbc);
        gbc.gridy++;
        //list of quizzes
        try {
            if(!isFiltered && !isSorted) quizzes = dbManager.getQuizzes();
            for(Quiz quiz : quizzes)
            {
                JPanel quizPanel = new JPanel();
                quizPanel.setLayout(new GridBagLayout());
                JLabel quizID = new JLabel(Integer.toString(quiz.getId()));
                JLabel quizName = new JLabel(quiz.getName());
                String category = categories.stream().filter(cat -> cat.getId() == quiz.getCategoryId()).findFirst().get().getName();
                JLabel quizCategory = new JLabel(category);
                JLabel quizHighScore = new JLabel(String.valueOf(quiz.getHighScore()));
                JLabel quizTimeLimit = new JLabel(String.valueOf(quiz.getTimeLimit()));
                JLabel quizNumberOfQuestions = new JLabel(String.valueOf(quiz.getNumberOfQuestions()));
                quizPanel.add(quizID);
                quizPanel.add(quizName);
                quizPanel.add(quizCategory);
                quizPanel.add(quizHighScore);
                quizPanel.add(quizTimeLimit);
                quizPanel.add(quizNumberOfQuestions);

                for(Component comp : quizPanel.getComponents())
                {
                    ((JLabel)comp).setHorizontalAlignment(SwingConstants.CENTER);
                }

                //buttons for starting editing and deleting the quiz
                JButton start = new JButton("Start");
                JButton edit = new JButton("Edit");
                JButton delete = new JButton("Delete");
                quizPanel.add(start);
                quizPanel.add(edit);
                quizPanel.add(delete);

                //borders & sizes
                quizID.setBorder(line);
                quizName.setBorder(line);
                quizCategory.setBorder(line);
                quizHighScore.setBorder(line);
                quizTimeLimit.setBorder(line);
                quizNumberOfQuestions.setBorder(line);

                quizID.setPreferredSize(idSize);
                quizID.setMaximumSize(idSize);
                quizName.setPreferredSize(textSize);
                quizName.setMaximumSize(textSize);
                quizCategory.setPreferredSize(textSize);
                quizCategory.setMaximumSize(textSize);
                quizHighScore.setPreferredSize(otherSize);
                quizHighScore.setMaximumSize(otherSize);
                quizTimeLimit.setPreferredSize(otherSize);
                quizTimeLimit.setMaximumSize(otherSize);
                quizNumberOfQuestions.setPreferredSize(otherSize);
                quizNumberOfQuestions.setMaximumSize(otherSize);

                start.setPreferredSize(otherSize);
                start.setMaximumSize(otherSize);
                edit.setPreferredSize(otherSize);
                edit.setMaximumSize(otherSize);
                delete.setPreferredSize(otherSize);
                delete.setMaximumSize(otherSize);

                //TODO: add action listeners managing the buttons
                start.setActionCommand("start " + quiz.getId()); //to change probably
                start.addActionListener(ScreenManager.getInstance());
                delete.addActionListener(this);
                delete.setActionCommand("delete " + quiz.getId());

                quizzesPanels.add(quizPanel);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        JPanel quizPanelContainer = new JPanel();
        quizPanelContainer.setLayout(new BoxLayout(quizPanelContainer, BoxLayout.PAGE_AXIS));
        for(JPanel quizPanel : quizzesPanels)
        {
            quizPanelContainer.add(quizPanel);
        }

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(quizPanelContainer, BorderLayout.NORTH);

        scrollPane.setViewportView(wrapperPanel);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH; // add this line
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, gbc);
    }
    private void sortQuizzes(String sortBy)
    {
        switch(sortBy)
        {
            case "ID":
                quizzes.sort(Comparator.comparingInt(Quiz::getId));
                break;
            case "Name":
                quizzes.sort(Comparator.comparing(Quiz::getName));
                break;
            case "Category":
                quizzes.sort((q1, q2) -> categories.stream().filter(cat -> cat.getId() == q1.getCategoryId()).findFirst().get().getName().compareTo(categories.stream().filter(cat -> cat.getId() == q2.getCategoryId()).findFirst().get().getName()));
                break;
        }
        isSorted = true;
    }
    private List<Quiz> filterQuizzes(String category)
    {
        int categoryId = categories.stream().filter(cat -> cat.getName().equals(category)).findFirst().get().getId();
        List<Quiz> temp = unfilteredQuizzes.stream().filter(quiz -> quiz.getCategoryId() == categoryId).collect(Collectors.toCollection(ArrayList::new));
        isFiltered = true;
        return temp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(sortOptions.equals(e.getSource()))
        {
            if(sortOptions.getSelectedItem() != null)
            {
                sortQuizzes((String)sortOptions.getSelectedItem());
                showQuizzes();
            }
        }
        else if(categoryOptions.equals(e.getSource()))
        {
            if(categoryOptions.getSelectedItem() != "All categories")
            {
                quizzes = filterQuizzes((String) categoryOptions.getSelectedItem());
                showQuizzes();
            }
            else {
                isFiltered = false;
                quizzes = unfilteredQuizzes;
                showQuizzes();
            }
        }
        else if(e.getActionCommand().startsWith("delete"))
        {
            int id = Integer.parseInt(e.getActionCommand().split(" ")[1]);
            try {
                dbManager.deleteQuiz(id);
                if(isFiltered) quizzes = filterQuizzes((String) categoryOptions.getSelectedItem());
                else {
                    quizzes = dbManager.getQuizzes();
                    unfilteredQuizzes = quizzes;
                }
                showQuizzes();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
