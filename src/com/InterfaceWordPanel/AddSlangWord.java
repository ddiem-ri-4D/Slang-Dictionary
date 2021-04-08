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
        slangPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        newSlangInput = new JTextField(20);
        newSlangInput.setFont(new Font("SF Mono", Font.PLAIN, 18));
        JLabel inputLabel = new JLabel("Slang word: ");
        newSlangInput.setBorder(BorderFactory.createLineBorder(Color.red));
        slangPanel.add(inputLabel);
        slangPanel.add(newSlangInput);
    }
    public void addActionEvent(MapController map) {

    }
}
