package com.SlangDictionary;
/**
 * @author Pham Nguyen My Diem
 * @version 1.0
 * @date 4/8/2021
 */

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
                String[] split = line.split("`", 0);

                if (split.length == 2) {
                    String key = split[0].toLowerCase();
                    String value = split[1];
                    keys.add(key);
                    map.put(key, value);
                    addToDefList(key, value);
                } else System.out.println("INVALID LINE: " + line);

            }
            readerEx.readLine();
            while ((line = readerEx.readLine()) != null) {
                String[] split = line.split("`", 0);

                if (split.length == 2) {
                    String key = split[0].toLowerCase();
                    String value = split[1];

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
            System.out.print("EXCEPTION CATCH: ");
            System.out.println(error.getMessage());
        }

    }

    MapController() {
        history = new ArrayList<>();
        if (!(new File("./ex.txt")).exists()) {
            try {
                createExFile();
                System.out.println("Create ex file successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("USING EXISTING EX FILE!");
            readDictionary();
            System.out.println(map.size() + " USING EX FILE: " + ASSETS_FILES_EX_TXT);
        }
    }


    private void removeFromDefList(String key, String s) {
    }

    private void addToDefList(String key, String value) {
    }

    private void createExFile() throws IOException {
        FileOutputStream oos = new FileOutputStream("./ex.txt");
        oos.write(FILE_HEADER.getBytes(), 0, FILE_HEADER.length());
        oos.close();
    }

    public boolean hasKey(String slang) {
        return map.containsKey(slang.toLowerCase());
    }

    public boolean addSlang(String slang, String mean) {
        String data = "\n" + slang + '`' + mean;
        String key = slang.toLowerCase();

        if (map.containsKey(key)) {
            map.replace(key, mean);
            keys.add(key);
            removeFromDefList(slang, mean);
            addToDefList(slang, mean);
        } else {
            addToDefList(slang, mean);
            map.put(key, mean);
        }

        return fileWriteHelper(data);
    }

    private boolean fileWriteHelper(String data) {
        try {
            FileOutputStream fos = new FileOutputStream(ASSETS_FILES_EX_TXT, true);
            fos.write(data.getBytes(), 0, data.length());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getDefinition(String slang) {
        String key = slang.toLowerCase();
        return hasKey(key) ? map.get(key) : "";
    }

    public String getDefinitionWithRecord(String slang) {
        String key = slang.toLowerCase();
        if (map.containsKey(key)) {
            String description = map.get(key);
            history.add(slang + " - " + description);
            return description;
        }
        history.add(slang + " - " + "Not Found!");
        return "";
    }

    public String[] getRandomKeys(int n) {
        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String randomKey = keys.get(new Random().nextInt(keys.size()));
            if (!arr.contains(randomKey))
                arr.add(randomKey);
            else i--;
        }

        return arr.toArray(new String[n]);
    }

    public String[] getSlangWordsByDef(String keyword) {
        String[] keys = keyword.toLowerCase().split("");
        Set<String> retainSet = null;
        for (String key : keys){
            if(!defList.containsKey(key)){
                history.add(keyword + " - Not Found!");
                return null;
            }

            Set<String> slang = new HashSet<>((defList.get(key)));
            if (retainSet != null)
                retainSet.retainAll(slang);
            else retainSet = slang;
        }

        String[] res = new String[retainSet.size()];
        retainSet.toArray(res);
        history.add(keyword + " - " + String.join(", ", res));
        return res;
    }
}
