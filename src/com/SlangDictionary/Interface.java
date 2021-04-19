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
            } else {
                newBtn.setBackground(primaryColor);
            }
            newBtn.setForeground(Color.white);

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

    private static void addSlangQuiz2Word(Container pane) {
        SlangQuizWord word = new SlangQuizWord("slang word", () -> {
            String[] data = new String[5];
            String[] keys = map.getRandomKeys(4);
            int randomIdx = new Random().nextInt(4);

            data[0] = keys[randomIdx] + "," + map.getDefinition(keys[randomIdx]);
            System.arraycopy(keys, 0, data, 1, 4);
            return data;
        });

        pane.add(word, buttonLabels[6]);
    }

    private static void addSlangQuizWord(Container pane) {
        SlangQuizWord word = new SlangQuizWord("definition", () ->{
            String[] data = new String[5];
            String[] keys = map.getRandomKeys(4);
            int randomIdx = new Random().nextInt(4);

            data[0] = map.getDefinition(keys[randomIdx]) + "," + keys[randomIdx];
            for(int i = 0; i <4; i++){
                data[i+ 1] = map.getDefinition(keys[i]);
            }
            return data;
        });
        pane.add(word, buttonLabels[5]);
    }

    private static void addEditNewSlangWord(Container pane) {
        ModifySlangWord word = new ModifySlangWord();
        word.addActionEvent(map);
        pane.add(word, buttonLabels[4]);
    }

    private static void addAddingNewSlangWord(Container pane) {
        AddSlangWord word = new AddSlangWord();
        word.addActionEvent(map);
        pane.add(word, buttonLabels[3]);
    }

    private static void addHistoryWord(Container panel) {
        HistoryWord word = new HistoryWord(() -> map.getHistory());
        panel.add(word, buttonLabels[2]);
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
            String res = String.join("<br/>+ ", def.split("\\|"));
            return "<html>+ " + res + "</html>";
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
        int res = JOptionPane.showConfirmDialog(frame,
                "By clicking OK all of your additional data will be deleted!\nAre you sure about that?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if(res == JOptionPane.YES_OPTION){
            boolean succeed = map.resetExFile();
            if(succeed){
                JOptionPane.showMessageDialog(frame,
                        "Successfully reset Slang Dictionary!",
                        "Status",
                        JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(frame,
                        "Failed to reset Slang Dictionary! Please try again!",
                        "Status",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Slang Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentsToPane(frame.getContentPane());

        //frame.setBackground(Color.BLACK);
        frame.setPreferredSize(new Dimension(900, 400));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        //frame.getContentPane().setBackground(Color.BLACK);
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
