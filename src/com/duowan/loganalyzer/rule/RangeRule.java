package com.duowan.loganalyzer.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author carlosliu on 2016/10/27.
 */
public class RangeRule extends Rule {

    public RangeRule(int id) {
        super(id);
    }

    public static void main(String args[]) {
        final String START = "_START_", END = "-END-";
        List<String> textList = new ArrayList<>();
        String TEXT = "abc";
        textList.add(TEXT);
        textList.add("123" + START);
        textList.add(TEXT);
        textList.add(END);
        textList.add(TEXT);
        textList.add(END);

        textList.add(TEXT);
        textList.add(END);
        textList.add(TEXT);
        textList.add(START);
        textList.add(TEXT);
        textList.add(END);
        textList.add(TEXT);
        textList.add(TEXT);
        textList.add(START);
        textList.add(TEXT);
        textList.add(TEXT);
        textList.add(TEXT);
        textList.add(TEXT);
        textList.add(END);
        textList.add(START);
        textList.add(TEXT);
        textList.add(TEXT);
        textList.add(TEXT);
        textList.add(TEXT);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < textList.size(); i++) {
            builder.append(textList.get(i)).append(i).append("\n\t");
        }

        String listString = builder.toString();
        System.out.println("listString = " + listString);


        Rule rule1 = new Rule(0, RegularExpressions.range(true, true, START + "\\d+", END + "\\d+"));
        Rule rule2 = new Rule(0, "\\d+$");

        rule1.addChild(rule2);
        rule1.execute(new Result(null, null, builder.toString(), true));

        Collection<List<Result>> resultCollection1 = rule1.getResults().values();

        System.out.println("rule 1");
        for (List<Result> results : resultCollection1) {
            for (Result result : results) {
                if (result.mIsMatched) {
                    System.out.println(result);
                }
            }
        }

        Collection<List<Result>> resultCollection = rule2.getResults().values();
        System.out.println("rule 2");
        for (List<Result> results : resultCollection) {
            for (Result result : results) {
                if (result.mIsMatched) {
                    System.out.println(result);
                }
            }
        }
    }
}
