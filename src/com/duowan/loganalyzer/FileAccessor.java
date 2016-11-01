package com.duowan.loganalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;

/**
 * @author carlosliu on 2016/10/30.
 */
public class FileAccessor {
    public static String getFileContent(String fileName) throws IOException {
        return getFileContent(new File(fileName));
    }

    public static String getFileContent(File file) throws IOException {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }

        reader.close();
        return builder.toString();
    }

    public static void saveToFile(String fileName, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(content, 0, content.length());
        writer.close();
    }

    public static void loadFile(JTextArea area, File file) throws IOException {

        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    StringBuilder builder = new StringBuilder();
                    int lineCount = 0;
                    while ((line = reader.readLine()) != null) {
                        if (lineCount < 30) {

                            builder.append(line);
                            builder.append("\n");
                        } else {
                            builder.append("\n");
                        }
                        lineCount++;
                        if (lineCount % 30 == 0) {
                            area.append(builder.toString());
                            builder.delete(0, builder.length());
                        }
                    }
                    area.append(builder.toString());
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
