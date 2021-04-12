package com.InterfaceWordPanel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;

/**
 * @author Pham Nguyen My Diem
 * @version 1.0
 * @date 4/12/2021 11:01 AM
 */

public class SlangQuizWord extends JPanel {
    private final int LINE_SIZE = 10;
    private JLabel quizLabel;
    private JLabel countLabel;
    private JLabel quizQuestion;
    private JButton[] btn = new JButton[4];
    private String[] data;
    private String answer = "";
    private int answerCount = 1;
    private int rightAnsCount = 0;
    private Callable<String[]> getQuizData;

    private String wrapText(String text) {
        String[] split = text.split(" ");
        if (text.length() >= LINE_SIZE) {
            int line = 0;
            for (int i = 0; i < split.length; i++) {
                line += split[i].length();
                if (line > LINE_SIZE) {
                    split[i] += "<br>";
                    line = 0;
                }
            }
        }
        return "<html>" + String.join(" ", split) + "</html>";
    }

    private String[] getKeysAndAnswer() {
        try {
            String[] rawString = getQuizData.call();
            String[] temp = rawString[0].split(",");
            answer = temp[0];
            rawString[0] = temp[1];
            return rawString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SlangQuizWord(String label, Callable<String[]> generateKeys) {
        getQuizData = generateKeys;
        data = getKeysAndAnswer();
        if (data == null) add(new JLabel("ERROR"));
        setLayout(new BorderLayout());

        quizLabel = new JLabel("Quiz#1", SwingConstants.CENTER);
        quizLabel.setFont(new Font("Arial", Font.BOLD, 16));
        quizLabel.setForeground(Color.decode("#192a56"));
        countLabel = new JLabel("Answer: 0/1", SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.BOLD, 16));
        countLabel.setForeground(Color.decode("#192a56"));

        JPanel quizContent = new JPanel();
        quizContent.setLayout(new BorderLayout());
        quizQuestion = new JLabel("<html>What is the " + label + " of <b><i>"
                + data[0] + "</i></b>?</html>", SwingConstants.CENTER);
        quizQuestion.setFont(new Font("Arial", Font.BOLD, 16));
        quizQuestion.setForeground(Color.decode("#192a56"));
        quizContent.setAlignmentX(CENTER_ALIGNMENT);
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new GridLayout(2, 2));
        for (int i = 0; i < 4; i++) {
            JPanel wrapper = new JPanel();
            wrapper.setLayout(new FlowLayout());
            btn[i] = new JButton(wrapText(data[i + 1]));
            //btn[i].setFont(new Font("Arial", Font.PLAIN, 14));
            btn[i].setMargin(new Insets(0, 0, 0, 0));
            btn[i].setPreferredSize(new Dimension(200, 100));
            btn[i].setBackground(Color.decode("#ff7675"));
            btn[i].setFont(new Font("Arial", Font.BOLD, 14));
            btn[i].setForeground(Color.white);
            wrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            wrapper.add(btn[i]);
            answerPanel.add(wrapper);
        }

        quizContent.add(quizQuestion, BorderLayout.PAGE_START);
        quizContent.add(answerPanel, BorderLayout.CENTER);

        add(quizLabel, BorderLayout.PAGE_START);
        add(quizContent, BorderLayout.CENTER);
        add(countLabel, BorderLayout.PAGE_END);

        addActionEvent();
    }

    private void addActionEvent() {
        for (int i = 0; i < btn.length; i++) {
            JButton _btn = btn[i];
            int finalI = i;
            _btn.addActionListener(actionEvent -> {
                JButton btn1 = (JButton) actionEvent.getSource();

                if (data[finalI + 1].equals(answer)) {
                    popup("Amazing! You get it right!\n");
                    rightAnsCount++;
                } else popup("Wrong answer!\nThe answer is " + answer +"!");
                changQuiz();
            });
        }
    }

    private void changQuiz() {
        answerCount++;
        quizLabel.setText(("Quiz#" + answerCount));
        quizLabel.setFont(new Font("Arial", Font.BOLD, 16));
        quizLabel.setForeground(Color.decode("#192a56"));
        data = getKeysAndAnswer();
        if (data == null) add(new JLabel("ERROR"));
        quizQuestion.setText("<html>What is the definition of <i><b>"
                + data[0] + "</i></b>?</html>");
        quizQuestion.setFont(new Font("Arial", Font.BOLD, 16));
        quizQuestion.setForeground(Color.decode("#192a56"));
        for (int i = 0; i < btn.length; i++) {
            btn[i].setText(wrapText(data[i + 1]));
        }
        countLabel.setText("Right Answer: " + rightAnsCount + "/" + answerCount);
        countLabel.setFont(new Font("Arial", Font.BOLD, 16));
        countLabel.setForeground(Color.decode("#192a56"));
    }

    private void popup(String s) {
        JFrame frame = (JFrame) SwingUtilities.getRoot(this);
        JOptionPane.showMessageDialog(frame, s, "Quiz", JOptionPane.INFORMATION_MESSAGE);
    }

}
