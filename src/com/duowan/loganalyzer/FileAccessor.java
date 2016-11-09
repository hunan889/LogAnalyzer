package com.duowan.loganalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
}
