package com.InterfaceWordPanel;

/**
 * @author Pham Nguyen My Diem
 * @version 1.0
 * @date 4/8/2021
 */

import com.SlangDictionary.MapController;

import javax.swing.*;
import java.awt.*;

public class AddSlangWord extends JPanel {
    private JTextField newSlangInput = null;
    private JTextArea descInput = null;
    private JButton submitBtn = null;

    public AddSlangWord() {
        setLayout(new BorderLayout());

        JPanel slangPanel = new JPanel();
        slangPanel.setBorder(BorderFactory.createEmptyBorder(23, 0, 23, 0));
        newSlangInput = new JTextField(16);
        newSlangInput.setFont(new Font("Arial", Font.BOLD, 16));
        newSlangInput.setForeground(Color.decode("#192a56"));
        newSlangInput.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel inputLabel = new JLabel("Slang word ");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputLabel.setForeground(Color.decode("#192a56"));
        //inputLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        newSlangInput.setBorder(BorderFactory.createLineBorder(Color.decode("#192a56")));
        slangPanel.add(inputLabel);
        slangPanel.add(newSlangInput);

        JPanel descPanel = new JPanel();
        descInput = new JTextArea(3, 23);
        descInput.setFont(new Font("Arial", Font.BOLD, 16));
        descInput.setForeground(Color.decode("#192a56"));
        descInput.setLineWrap(true);
        descInput.setWrapStyleWord(true);
        descInput.setFont(new Font("SF Mono", Font.BOLD, 16));
        JLabel descLabel = new JLabel("Definition  ");
        descLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descLabel.setForeground(Color.decode("#192a56"));
        //descLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descInput.setBorder(BorderFactory.createLineBorder(Color.decode("#192a56")));
        descPanel.add(descLabel);
        descPanel.add(descInput);

        JPanel btnPanel = new JPanel();

        submitBtn = new JButton("Add slang word");
        submitBtn.setPreferredSize(new Dimension(200, 40));
        submitBtn.setBackground(Color.decode("#192a56"));
        submitBtn.setForeground(Color.white);
        submitBtn.setOpaque(true);
        btnPanel.add(submitBtn);

        add(slangPanel, BorderLayout.PAGE_START);
        add(descPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);
    }

    public void addActionEvent(MapController map) {
        JFrame frame = (JFrame) SwingUtilities.getRoot(this);
        submitBtn.addActionListener(actionEvent -> {
            String slang = newSlangInput.getText();
            String mean = descInput.getText();
            boolean succeed = false;

            if (slang.equals("") || mean.equals("")) {
                JOptionPane.showMessageDialog(frame,
                        "Input is not supposed to be empty!",
                        "Empty Error",
                        JOptionPane.ERROR_MESSAGE);

            } else {
                final String[] options = {
                        "Overwrite", "Add duplicate", "Cancel"
                };
                boolean res = map.hasKey(slang);

                if (res) {
                    int c = JOptionPane.showOptionDialog(null,
                            "This slang word exists!\nChoose your option?", "Existing Word",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, options, options[0]);
                    if (c == 0) {
                        succeed = map.addSlang(slang, mean);
                    } else if (c == 1) {
                        mean = map.getDefinition(slang) + "| " + mean;
                        succeed = map.addSlang(slang, mean);
                    }
                } else {
                    succeed = map.addSlang(slang, mean);
                }
            }

            if (succeed) {
                JOptionPane.showMessageDialog(frame,
                        "Successfully and this word!",
                        "Status",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Add word failed! Please try again!",
                        "Status",
                        JOptionPane.ERROR_MESSAGE);
            }
            //submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
            //submitBtn.setForeground(Color.decode("#192a56"));

        });
    }
}
