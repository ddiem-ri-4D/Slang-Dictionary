package com.SlangDictionary;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Function;

import com.InterfaceWordPanel.*;

public class Interface {
    private static int selected = 0;
    private static final Color primaryColor = Color.decode("#ff7675");
    private static final Color secondaryColor = Color.decode("#192a56");
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


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Slang Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //addComponentsToPane(frame.getContentPane());

        frame.setPreferredSize(new Dimension(900, 400));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        UIManager.put("Label.font", new Font("Helvetica Neue", Font.PLAIN, 20));
        UIManager.put("Button.font", new Font("SF Mono", Font.BOLD, 18));
        javax.swing.SwingUtilities.invokeLater(() -> {
            map = new MapController();
            createAndShowGUI();
        });
    }
}
