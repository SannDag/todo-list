package todolist.src.main.java;

import javax.swing.*;
import java.awt.*;

public class ToDoList extends JFrame {
    private JTextArea taskAreaText;
    private JPanel mainPanel;
    private JPanel panelContainer;
    private JPanel buttonPanel;
    private JPanel todoPanel;
    private JPanel inProgressPanel;
    private JPanel donePanel;
    private JScrollPane todoScrollPane;
    private JScrollPane inProgressScrollPane;
    private JScrollPane doneScrollPane;
    private JButton addTaskBtn;
    private JButton moveRightBtn;
    private JButton moveLeftBtn;
    private JButton deleteBtn;
    private DefaultListModel<String> todoModel;
    private DefaultListModel<String> inProgressModel;
    private DefaultListModel<String> doneModel;
    private JList<String> todoList;
    private JList<String> inProgressList;
    private JList<String> doneList;



    public ToDoList() {
        setTitle("To-Do list app v1.0");
        setSize(650, 400);
        setResizable(false);

        taskAreaText = new JTextArea();
        taskAreaText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Enter Task"),
                BorderFactory.createEmptyBorder(2,10,10,10)));
        add(taskAreaText, BorderLayout.NORTH);

        //TO-DO list
        todoModel = new DefaultListModel<>();
        todoList = new JList<>(todoModel);
        todoList.addListSelectionListener(e -> {
            todoList.setSelectedIndex(todoList.getSelectedIndex());
            inProgressList.clearSelection();
            doneList.clearSelection();
        });

        //IN-PROGRESS list
        inProgressModel = new DefaultListModel<>();
        inProgressList = new JList<>(inProgressModel);
        inProgressList.addListSelectionListener(e -> {
            inProgressList.setSelectedIndex(inProgressList.getSelectedIndex());
            todoList.clearSelection();
            doneList.clearSelection();
        });


        //DONE list
        doneModel = new DefaultListModel<>();
        doneList = new JList<>(doneModel);
        doneList.addListSelectionListener(e -> {
            doneList.setSelectedIndex(doneList.getSelectedIndex());
            todoList.clearSelection();
            inProgressList.clearSelection();
        });

        //TO-DO panel
        todoScrollPane = new JScrollPane(todoList);
        todoScrollPane.setPreferredSize(new Dimension(200, 220));
        todoPanel = new JPanel(new BorderLayout());
        todoPanel.add(new JLabel("To-Do"), BorderLayout.NORTH);
        todoPanel.add(todoScrollPane);

        //IN-PROGRESS panel
        inProgressScrollPane = new JScrollPane(inProgressList);
        inProgressScrollPane.setPreferredSize(new Dimension(200, 220));
        inProgressPanel = new JPanel(new BorderLayout());
        inProgressPanel.add(new JLabel("In Progress"), BorderLayout.NORTH);
        inProgressPanel.add(inProgressScrollPane);

        //DONE panel
        doneScrollPane = new JScrollPane(doneList);
        doneScrollPane.setPreferredSize(new Dimension(200, 220));
        donePanel = new JPanel(new BorderLayout());
        donePanel.add(new JLabel("Done"), BorderLayout.NORTH);
        donePanel.add(doneScrollPane);

        //ADD-TASK button
        addTaskBtn = new JButton("Add task");
        addTaskBtn.addActionListener(e -> addTask());

        //DELETE button
        deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> {
            if(!todoList.isSelectionEmpty()){
                todoModel.removeElement(todoList.getSelectedValue());
            } else if(!inProgressList.isSelectionEmpty()){
                inProgressModel.removeElement(inProgressList.getSelectedValue());
            } else if(!doneList.isSelectionEmpty()){
                doneModel.removeElement(doneList.getSelectedValue());
            }
        });

        //MOVE RIGHT button
        moveRightBtn = new JButton(">");
        moveRightBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        moveRightBtn.addActionListener(e -> {
            if(!todoList.isSelectionEmpty()) {
                moveTask(todoList, inProgressModel, todoModel);
            } else if(!inProgressList.isSelectionEmpty()){
                moveTask(inProgressList, doneModel, inProgressModel);
            }
        });


        //MOVE LEFT button
        moveLeftBtn = new JButton("<");
        moveLeftBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        moveLeftBtn.addActionListener(e -> {
                if(!doneList.isSelectionEmpty()) {
                    moveTask(doneList, inProgressModel, doneModel);
                } else if(!inProgressList.isSelectionEmpty()){
                    moveTask(inProgressList, todoModel, inProgressModel);
                }

        });

        //adding components to the panels
        panelContainer = new JPanel();
        panelContainer.add(todoPanel);
        panelContainer.add(inProgressPanel);
        panelContainer.add(donePanel);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.add(moveLeftBtn);
        buttonPanel.add(addTaskBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(moveRightBtn);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panelContainer);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
    }

    private void addTask() {
        String task = taskAreaText.getText().trim();
        if (!task.isEmpty()) {
            todoModel.addElement(task);
            taskAreaText.setText("");
        }
    }

    private void moveTask(JList<String> source, DefaultListModel<String> sourceModel, DefaultListModel<String> targetModel) {
        String selectedTask = source.getSelectedValue();
        if(!source.isSelectionEmpty()){
            sourceModel.addElement(selectedTask);
            targetModel.removeElement(selectedTask);
        }
    }
}




