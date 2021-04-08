package com.InterfaceWordPanel;
/**
 * @author Pham Nguyen My Diem
 * @date 4/8/21
 * @version 1.0
 */

import com.SlangDictionary.MapController;

import javax.swing.*;
import java.awt.*;

public class AddSlangWord extends JPanel {
    private JTextField newSlangInput = null;
    private JTextArea descInput = null;
    private JButton submitBtn = null;
    public AddSlangWord(){
        setLayout(new BorderLayout());

        JPanel slangPanel = new JPanel();
        slangPanel.setBorder(BorderFactory.createEmptyBorder(23, 0, 23, 0));
        newSlangInput = new JTextField(16);
        newSlangInput.setFont(new Font("SF Mono", Font.PLAIN, 16));
        JLabel inputLabel = new JLabel("Slang word ");
        newSlangInput.setBorder(BorderFactory.createLineBorder(Color.decode("#ff7675")));
        slangPanel.add(inputLabel);
        slangPanel.add(newSlangInput);

        JPanel descPanel = new JPanel();
        descInput = new JTextArea(3, 23);
        descInput.setLineWrap(true);
        descInput.setWrapStyleWord(true);
        descInput.setFont(new Font("SF Mono", Font.PLAIN, 16));
        JLabel descLabel = new JLabel("Definition  ");
        descInput.setBorder(BorderFactory.createLineBorder(Color.decode("#ff7675")));
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

    }
}
