package com.duowan.loganalyzer;

import com.duowan.loganalyzer.rule.Result;
import com.duowan.loganalyzer.rule.Rule;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static final String TAG_FILE = "-f";
    private static final String TAG_REG_FILE = "-r";

    private static final String OPTION_INPUT_RULE = "-ir";
    private static final String TAG_TEST_REGEX = "test";
    private static final String TAG_NEXT = "next";
    private static final String TAG_LAST = "last";
    private static final String TAG_PRINT = "print";
    private static final String TAG_SAVE = "save";
    private static final String TAG_LOAD_RGX = "load";
    private static final String TAG_EXECUTE = "execute";
    private static final String OPTION_PRINT_ALL = "-a";
    private static final Rule sHead = new Rule(0);

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入文件路径");
        String filePath = scanner.next();
        String fileContent = FileAccessor.getFileContent(filePath);
        File inputFile = new File(filePath);
        System.out.println(fileContent);

        String input;

        Rule currentRule = sHead;
//        sHead.getResults().put(new Result(null, null, fileContent, true), new ArrayList<>());
        Rule testingRule = null;
        while (!"exit".equals(input = scanner.nextLine())) {
            if ("".equals(input)) {
                continue;
            }
            System.out.println("input = " + input);
            String[] commandSplits = input.split(" ");
            String command = commandSplits[0];
            for (String str : commandSplits) {
                System.out.println(str);
            }
            switch (command) {
                case TAG_LOAD_RGX: {
                    String rgxFilePath = commandSplits[1];
                    currentRule = new Gson().fromJson(FileAccessor.getFileContent(rgxFilePath), Rule.class);

                    System.out.println("加载成功，currentRule = " + currentRule);
                    break;
                }
                case TAG_TEST_REGEX: {
                    testingRule = new Rule(currentRule, commandSplits[1]);
                    Map<Result, List<Result>> resultMap = currentRule.getResults();
                    execute(fileContent, testingRule, resultMap);
                    printResults(testingRule);
                    break;
                }

                case TAG_NEXT: {
                    if (testingRule == null) {
                        System.out.println("当前没有规则，请先添加");
                    } else {
                        currentRule.addChild(testingRule);
                        currentRule = testingRule;
                        testingRule = null;
                        System.out.println("进入下一级");
                    }
                    break;
                }
                case TAG_LAST: {
                    Rule parent = currentRule.getParent();
                    if (parent == null) {
                        System.out.println("已经是最上一级");
                    } else {
                        currentRule = parent;
                        System.out.println("进入上一级");
                    }
                    break;
                }
                case TAG_EXECUTE: {
                    Rule group = currentRule;
                    Map<Result, List<Result>> resultMap = null;
                    if (commandSplits.length > 1) {
                        if (OPTION_PRINT_ALL.equals(commandSplits[1])) {
                            //打印所有
                            group = getRootRule(group);
                        }
                    } else {
                        Rule parent = group.getParent();
                        if (parent != null) {
                            resultMap = parent.getResults();
                        }
                    }
                    execute(fileContent, group, resultMap);
                    printResults(group);
                    break;
                }
                case TAG_PRINT: {
                    Rule group = currentRule;
                    if (commandSplits.length > 1) {
                        if (OPTION_PRINT_ALL.equals(commandSplits[1])) {
                            //打印所有
                            group = getRootRule(group);
                        }
                    }
                    System.out.println(group);
                    break;
                }
                case TAG_SAVE: {
                    String rgxFileName = inputFile.getAbsolutePath() + ".rgx";
                    FileAccessor.saveToFile(rgxFileName, getRootRule(currentRule).toString());
                    System.out.println("文件已保存到" + rgxFileName);
                    break;
                }
            }
        }
    }

    private static void execute(String fileContent, Rule rule, Map<Result, List<Result>> resultMap) {
        if (resultMap != null && resultMap.values().size() > 0) {
            for (List<Result> results : resultMap.values()) {
                for (Result result : results) {
                    if (result.mIsMatched) {
                        rule.execute(result);
                    }
                }
            }
        } else {
            rule.execute(new Result(rule, null, fileContent, true));
        }
    }

    private static Rule getRootRule(Rule group) {
        while (group.getParent() != null) {
            group = group.getParent();
        }
        return group;
    }

    private static void printResults(Rule rule) {
        if (rule == sHead) {
            for (Rule child : sHead.getChildren()) {
                printResults(child);
            }
            return;
        }
        System.out.println("#########" + rule.getIdDesc() + "#########");

        Map<Result, List<Result>> resultListMap = rule.getResults();
        Set<Result> keys = rule.getResults().keySet();

        for (Result key : keys) {
            List<Result> results = resultListMap.get(key);
            if (results != null && results.size() > 0) {
                System.out.println("result from parent" + key.getPositionInfo());
                for (Result result : results) {
                    if (result.mIsMatched) {
                        System.out.println(result.mFilterResult);
                    }
                }
            }
        }

        for (Rule child : rule.getChildren()) {
            printResults(child);
        }
    }
}
