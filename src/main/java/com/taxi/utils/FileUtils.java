package com.taxi.utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileUtils {

    public static ArrayList<String> readAllLines(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            while (true){
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                lines.add(line);
            }

            return lines;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToFile(String filename, String data, boolean append){
        try{
            FileWriter fw = new FileWriter(filename, append);
            fw.write(data);
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean isFileExists(String fileName){
        File file = new File(fileName);
        return file.isFile();
    }

    public static void createFile(String fileName){
        File file = new File(fileName);
        try{
            file.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}