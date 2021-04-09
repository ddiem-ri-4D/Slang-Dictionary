package com.SlangDictionary;

/**
 * @author Pham Nguyen My Diem
 * @version 1.0
 * @date 4/8/2021
 */

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Function;

import com.InterfaceWordPanel.*;

public class Interface {
    private static int selected = 0;
    private static final Color primaryColor = Color.decode("#192a56");
    private static final Color secondaryColor = Color.decode("#ff7675");
    private static final String[] buttonLabels = {
            "Search by Slang Word",
            "Search by Definition",
            "Search History",
            "Add a new Slang Word",
            "Modify existing Slang Word",
            "Slang Quiz 1",
            "Slang Quiz 2"
    };
    private static MapController map = null;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BorderLayout());
        pane.setSize(new Dimension(500, 600));

        //Panel 1:
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0, 1));

        //Panel 2:
        JPanel mainPanel = new JPanel(new CardLayout());

        for (int i = 0; i < buttonLabels.length; i++) {
            String buttonLabel = buttonLabels[i];
            final int index = i;
            JButton newBtn = new JButton(buttonLabel);
            newBtn.setMargin(new Insets(15, 15, 15, 15));
            newBtn.setMaximumSize(new Dimension(100, 50));
            if (buttonLabel.equals(buttonLabels[selected])) {
                newBtn.setBackground(secondaryColor);
                newBtn.setForeground(Color.white);
            } else {
                newBtn.setBackground(primaryColor);
                newBtn.setForeground(Color.white);
            }

            newBtn.addActionListener(event -> {
                sidebar.getComponent(selected).setBackground(primaryColor);
                ((Component) event.getSource()).setBackground(secondaryColor);
                selected = index;
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, buttonLabel);
            });
            sidebar.add(newBtn);
        }

        JButton resetBtn = new JButton("Reset Dictionary");
        resetBtn.setBackground(Color.decode("#FF4500"));
        resetBtn.setForeground(Color.white);
        resetBtn.setMargin(new Insets(15, 15, 15, 15));
        resetBtn.setMaximumSize(new Dimension(100, 50));
        resetBtn.addActionListener(actionEvent -> {
            JFrame frame = (JFrame) SwingUtilities.getRoot((Component) actionEvent.getSource());
            addResetFunction(frame);
        });
        sidebar.add(resetBtn);

        addSearchBySlangWord(mainPanel);
        addSearchByDefWord(mainPanel);
        addHistoryWord(mainPanel);
        addAddingNewSlangWord(mainPanel);
        addEditNewSlangWord(mainPanel);
        addSlangQuizWord(mainPanel);
        addSlangQuiz2Word(mainPanel);

        pane.add(sidebar, BorderLayout.LINE_START);
        pane.add(mainPanel, BorderLayout.CENTER);
    }

    private static void addSlangQuiz2Word(JPanel mainPanel) {
    }

    private static void addSlangQuizWord(JPanel mainPanel) {
    }

    private static void addEditNewSlangWord(JPanel mainPanel) {
    }

    private static void addAddingNewSlangWord(Container pane) {
        AddSlangWord word = new AddSlangWord();
        word.addActionEvent(map);
        pane.add(word, buttonLabels[3]);
    }

    private static void addHistoryWord(JPanel mainPanel) {
    }

    private static void addSearchByDefWord(Container pane) {
        SearchWord word = new SearchWord(false);
        Function<String, String> getDefFn = (s) -> {
            String[] keys = map.getSlangWordsByDef(s);
            if (keys == null) return "";
            StringBuilder result = new StringBuilder();
            for (String key : keys) {
                String temp = String.format("+ <b>%s</b> - %s <br/>", key, map.getDefinition(key));
                result.append(temp);
            }
            return "<html>" + result + "</html>";
        };

        word.setMapController(getDefFn, null);
        pane.add(word, buttonLabels[1]);
    }

    private static void addSearchBySlangWord(Container pane) {
        SearchWord word = new SearchWord(true);
        Function<String, String> getDefFn = (s) -> {
            String def = map.getDefinitionWithRecord(s);
            if (def.equals(""))
                return "";
            String res = String.join("<br/>+", def.split("\\|"));
            return "<html>+" + res + "</html>";
        };
        Callable<String[]> getRandomValuesFn = () -> {
            String[] res = new String[2];
            res[0] = map.getRandomKeys(1)[0];
            res[1] = map.getDefinition(res[0]);
            return res;
        };

        word.setMapController(getDefFn, getRandomValuesFn);
        pane.add(word, buttonLabels[0]);

    }

    private static void addResetFunction(JFrame frame) {
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Slang Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentsToPane(frame.getContentPane());

        frame.setBackground(Color.BLACK);

        frame.setPreferredSize(new Dimension(900, 400));
        frame.setResizable(false);
        //frame.getContentPane().setBackground(Color.BLUE);
        frame.pack();
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.BLACK);
    }

    public static void main(String[] args) {
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 20));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        javax.swing.SwingUtilities.invokeLater(() -> {
            map = new MapController();
            createAndShowGUI();
        });
    }
}
