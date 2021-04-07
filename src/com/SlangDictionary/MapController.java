package com.SlangDictionary;

import java.io.*;
import java.util.*;

public class MapController {
    public static final String ASSETS_FILES_BASE_TXT = "/files/slang.txt";
    public static final String ASSETS_FILES_EX_TXT = "./ex.txt";

    public static final String FILE_HEADER = "Slang`Meaning";
    private final ArrayList<String> history;
    private static HashMap<String, String> map = null;
    private static ArrayList<String> keys = null;

    private static HashMap<String, ArrayList<String>> defList = null;

    public void readDictionary() {
        map = new HashMap<>();
        defList = new HashMap<>();
        keys = new ArrayList<>();
        InputStream inBase, inEx;
        BufferedReader readerBase, readerEx;
        try {
            inBase = getClass().getResourceAsStream(ASSETS_FILES_BASE_TXT);
            readerBase = new BufferedReader(new InputStreamReader(inBase));

            inEx = new FileInputStream(ASSETS_FILES_EX_TXT);
            readerEx = new BufferedReader(new InputStreamReader(inEx));

            String line;
            readerBase.readLine();

            while ((line = readerBase.readLine()) != null) {
                String[] splited = line.split("`", 0);

                if (splited.length == 2) {
                    String key = splited[0].toLowerCase();
                    String value = splited[1];
                    keys.add(key);
                    map.put(key, value);
                    addToDefList(key, value);
                } else System.out.println("INVALID LINE: " + line);

            }
            readerEx.readLine();
            while ((line = readerEx.readLine()) != null) {
                String[] splited = line.split("`", 0);

                if (splited.length == 2) {
                    String key = splited[0].toLowerCase();
                    String value = splited[1];

                    if (map.containsKey(key)) {
                        //Test`~ => Test slang word is deleted
                        if (value.equals("~")) {
                            map.remove(key);
                            removeFromDefList(key, map.get(key));
                        }
                        //Test`abc and map has already had this word => Replace def list
                        else {
                            removeFromDefList(key, map.get(key));
                            addToDefList(key, value);
                            map.replace(key, value);
                        }
                    }
                    //Test`abc and map does not have this word yet
                    else {
                        keys.add(key);
                        map.put(key, value);
                        addToDefList(key, value);
                    }
                }
            }

            inBase.close();
            inEx.close();
        } catch (Exception error) {
            System.out.print("EXCEPTION CAUTCH: ");
            System.out.println(error.getMessage());
        }
        
    }

    private void removeFromDefList(String key, String s) {
    }

    private void addToDefList(String key, String value) {
    }

    MapController() {
        history = new ArrayList<>();
    }
}
