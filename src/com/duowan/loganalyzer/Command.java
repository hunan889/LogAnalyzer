package com.duowan.loganalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author carlosliu on 2016/11/4.
 */
public abstract class Command {

    public void parse(String[] args) {
        Option[] options = getSupportOptions();
    }

    protected Option[] getSupportOptions() {
        return null;
    }

    private static class Option {
        String mName;
        Class<?>[] mParamTypes;
        boolean mRequired;
    }

    public abstract void execute();

    public static void main(String args[]) {

        String text = "\n" +
                "a -xx \n" +
                "a -xx -xx\n" +
                "a -xx xxx -xx xxx\n" +
                "a -xx \"xxx xxx xxx\" -xx \"xxx xxx xxx\" xxx \n" +
                "\n" +
                "\n" +
                "\n" +
                "a xx xx";
        String regex = "(-\\w+)|(\\\".*?\\\")|(\\w+)";
        System.out.println(text);
        System.out.println(regex);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<String> matchedResult = new ArrayList<>();
        while (matcher.find()) {
            String matched = matcher.group();
            matchedResult.add(matched);
        }

        for (String str : matchedResult) {
            System.out.println(str);
        }

    }
}
