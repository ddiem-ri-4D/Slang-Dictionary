package com.InterfaceWordPanel;

import com.SlangDictionary.MapController;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pham Nguyen My Diem
 * @version 1.0
 * @date 4/10/2021 5:56 PM
 */
public class ModifySlangWord extends JPanel {
    private JTextField slangInput;
    private JTextArea descInput;
    private JButton inputBtn;
    private JButton submitBtn;
    private JButton deleteBtn;
    private JPanel descPanel;

    public ModifySlangWord(){
        setLayout(new BorderLayout());

        JPanel slangPanel = new JPanel();
        slangPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        slangInput = new JTextField(20);
        slangInput.setFont(new Font("Arial", Font.BOLD, 16));
        slangInput.setForeground(Color.decode("#192a56"));
        //slangInput.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel inputLabel = new JLabel("Slang word ");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputLabel.setForeground(Color.decode("#192a56"));
        slangInput.setBorder(BorderFactory.createLineBorder(Color.decode("#192a56")));
        inputBtn = new JButton("Search");
        inputBtn.setFont(new Font("Arial", Font.BOLD, 16));
        inputBtn.setForeground(Color.decode("#192a56"));
        inputBtn.setPreferredSize(new Dimension(100, 26));
        slangPanel.add(inputLabel);
        slangPanel.add(slangInput);
        slangPanel.add(inputBtn);

        descPanel = new JPanel();
        descPanel.setVisible(false);
        descInput = new JTextArea(3, 20);
        descInput.setFont(new Font("Arial", Font.BOLD, 16));
        descInput.setForeground(Color.decode("#192a56"));
        descInput.setLineWrap(true);
        descInput.setWrapStyleWord(true);
        descInput.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel descLabel = new JLabel("Meaning ");
        descLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descLabel.setForeground(Color.decode("#192a56"));
        descInput.setBorder(BorderFactory.createLineBorder(Color.decode("#192a56")));
        descPanel.add(descLabel);
        descPanel.add(descInput);

        JPanel btnPanel = new JPanel();
        submitBtn = new JButton("Save");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setForeground(Color.decode("#192a56"));
        submitBtn.setPreferredSize(new Dimension(200, 40));
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setBackground(Color.decode("#192a56"));
        submitBtn.setForeground(Color.white);

        submitBtn.setOpaque(true);
        submitBtn.setVisible(false);

        deleteBtn = new JButton("Delete");
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 16));
        deleteBtn.setForeground(Color.decode("#192a56"));
        deleteBtn.setPreferredSize(new Dimension(220, 40));
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
        deleteBtn.setBackground(Color.decode("#FF4500"));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setOpaque(true);
        deleteBtn.setVisible(false);
        btnPanel.add(submitBtn);
        btnPanel.add(deleteBtn);

        add(slangPanel, BorderLayout.PAGE_START);
        add(descPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);
    }
    private void toggle(){
        submitBtn.setVisible(!submitBtn.isVisible());
        deleteBtn.setVisible(!deleteBtn.isVisible());
        descPanel.setVisible(!descPanel.isVisible());
    }

    public void addActionEvent(MapController map) {
        JFrame frame = (JFrame) SwingUtilities.getRoot(this);
        inputBtn.addActionListener(actionEvent -> {
            String slang = slangInput.getText();
            if(map.hasKey(slang)){
                descInput.setText(map.getDefinition(slang));
                toggle();
            }
            else{
                JOptionPane.showMessageDialog(frame,
                        "Cannot find this word!",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        submitBtn.addActionListener(actionEvent -> {
            String slang = slangInput.getText();
            String mean = descInput.getText();
            if (map.getDefinition(slang).equals(mean)) {
                JOptionPane.showMessageDialog(frame,
                        "Detect no change in meaning!",
                        "Status",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int c = JOptionPane.showConfirmDialog(
                    frame,
                    "Update this word?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if(c == JOptionPane.YES_OPTION) {
                boolean succeed = map.addSlang(slang, mean);

                if (succeed) {
                    JOptionPane.showMessageDialog(frame,
                            "Successfully update this word!",
                            "Status",
                            JOptionPane.INFORMATION_MESSAGE);
                    toggle();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Failed to update this word! Please try again!",
                            "Status",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteBtn.addActionListener(actionEvent ->{
            String slang = slangInput.getText();

            int c = JOptionPane.showConfirmDialog(frame,
                    "Delete this word?",
                    "Confirmation",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            if (c == JOptionPane.YES_OPTION){
                boolean succeed = map.removeAWord(slang);

                if(succeed){
                    JOptionPane.showMessageDialog(frame,
                    "Successfully delete this word!",
                            "Status",
                            JOptionPane.INFORMATION_MESSAGE);
                    toggle();
                }
                else {
                    JOptionPane.showMessageDialog(frame,
                            "Failed to delete this word! Please try again!",
                            "Status",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
