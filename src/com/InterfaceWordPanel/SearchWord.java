package com.InterfaceWordPanel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * @author Pham Nguyen My Diem
 * @version 1.0
 * @date 4/9/2021 12:21 AM
 */
public class SearchWord extends JPanel {
    private JTextField textInput = null;
    private JButton inputBtn = null;
    private JButton randomBtn = null;
    private JLabel definition = null;

    private final URL searchIcon = getClass().getClassLoader().getResource("icons/ic_search.png");
    private final URL randomIcon = getClass().getClassLoader().getResource("icons/ic_dice.png");

    public SearchWord(boolean hasRandom){
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        textInput = new JTextField(20);
        textInput.setFont(new Font("Arial", Font.PLAIN, 16));
        textInput.setFont(new Font("Arial", Font.BOLD, 18));
        textInput.setForeground(Color.decode("#192a56"));
        JLabel inputLabel = new JLabel("Input keyword ");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputLabel.setForeground(Color.decode("#192a56"));
        inputBtn = new JButton();
        inputBtn.setIcon(new ImageIcon(searchIcon));
        inputBtn.setPreferredSize(new Dimension(50, 26));
        inputPanel.add(inputLabel);
        inputPanel.add(textInput);
        inputPanel.add(inputBtn);

        if(hasRandom){
            randomBtn = new JButton();
            randomBtn.setIcon(new ImageIcon(randomIcon));
            randomBtn.setPreferredSize(new Dimension(50, 26));
            randomBtn.setToolTipText("Randomly generate an icon!");
            inputPanel.add(randomBtn);
        }

        JPanel descPanel = new JPanel();
        descPanel.setAlignmentX(20);
        descPanel.setLayout(new FlowLayout());
        JLabel descLabel = new JLabel("Result: ");
        descLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descLabel.setForeground(Color.decode("#192a56"));
        definition = new JLabel("");
        definition.setFont(new Font("Arial", Font.BOLD, 16));
        definition.setForeground(Color.decode("#192a56"));
        descPanel.add(descLabel);
        descPanel.add(definition);

        add(inputPanel, BorderLayout.PAGE_START);
        add(new JScrollPane(descPanel), BorderLayout.CENTER);
    }

    public void setMapController(Function<String, String> getDef, Callable<String[]> getRandomHandler) {
        inputBtn.addActionListener(actionEvent -> {
            String def = getDef.apply(textInput.getText());
            if(!def.equals("") && def != null){
                textInput.setBorder(BorderFactory.createLineBorder(Color.decode("#192a56")));
                definition.setText(def);
            }
            else{
                textInput.setBorder(BorderFactory.createLineBorder(Color.decode("#ff7675")));
                definition.setText("Not Found!");
            }
        });

        if(randomBtn != null){
            randomBtn.addActionListener(actionEvent ->{
                String[] randomKey = new String[0];
                try{
                    randomKey = getRandomHandler.call();
                }catch(Exception e){
                    e.printStackTrace();
                }
                textInput.setText(randomKey[0]);
                textInput.setBorder(BorderFactory.createLineBorder(Color.decode("#192a56")));
                definition.setText(randomKey[1]);
            });
        }

        textInput.addActionListener(actionEvent -> inputBtn.doClick());
    }
}
