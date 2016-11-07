package com.duowan.loganalyzer;

import com.duowan.loganalyzer.rule.Regex;

import java.util.Arrays;
import java.util.List;

/**
 * @author carlosliu on 2016/11/4.
 */
public class CommandParser {

    String regex = "(\\\"[^\\\"\\n]*?([^\\\"\\n]*?\\\\\\\"[^\\\"\\n]*?)*\\\")";

    public Command parse(String input) {
        Regex.RegexResult result = new Regex(regex).execute(input);
        List<String> inputList = result.mMatchList;
        String[] array = new String[inputList.size()];
        inputList.toArray(array);
        return parse(input, array);
    }

    public Command parse(String input, String[] args) {
        if (args == null || args.length <= 0) {
            throw new IllegalArgumentException("输入有误，请检查 " + input);
        }
        Command command = null;
        switch (args[0]) {

        }

        command.parse(Arrays.copyOfRange(args, 1, args.length));
        return command;
    }

}
