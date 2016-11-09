package com.duowan.loganalyzer;

import com.duowan.loganalyzer.rule.Regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author carlosliu on 2016/11/4.
 */
public class CommandParser {

    String regex = "(\\\"[^\\\"\\n]*?([^\\\"\\n]*?\\\\\\\"[^\\\"\\n]*?)*\\\")|(\\S+)";

    private Map<String, Class<? extends Command>> mCommandCache = new HashMap<>();

    public Command parse(String input) {
        Regex.RegexResult result = new Regex(regex).execute(input);
        List<String> inputList = result.mMatchList;
        String[] array = new String[inputList.size()];
        inputList.toArray(array);
        return parse(input, array);
    }

    private Command parse(String input, String[] args) {
        if (args == null || args.length <= 0) {
            throw new IllegalArgumentException("输入有误，请检查 " + input);
        }

        Class<? extends Command> cmdCls = mCommandCache.get(args[0].trim());
        Command command = null;
        try {
            command = cmdCls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        command.parse(new ArrayList<>(Arrays.asList(Arrays.copyOfRange(args, 1, args.length))));
        return command;
    }

    public void add(Command command) {
        add(command.getName(), command.getClass());
    }

    public void add(String name, Class<? extends Command> cmdCls) {
        mCommandCache.put(name, cmdCls);
    }

    public static void main(String args[]) {
        CommandParser parser = new CommandParser();
        Command command = Commands.getLoadRgxCommand();

        parser.add(command);
        Command command1 = parser.parse("load abc fdjkfl");

        System.out.println(command1);

    }

}
