package com.duowan.loganalyzer.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author carlosliu on 2016/11/5.
 */
public class Regex {
    private final String mRegex;
    private final Pattern mPattern;

    public Regex(String regex) {
        mRegex = regex;
        mPattern = Pattern.compile(mRegex);
    }

    public RegexResult execute(String text) {
        Matcher matcher = mPattern.matcher(text);
        List<String> matchedResult = new ArrayList<>();
        while (matcher.find()) {
            String matched = matcher.group();
            matchedResult.add(matched);
        }

        List<String> mismatchedResult = new ArrayList<>();
        String[] mismatched = mPattern.split(text);
        Collections.addAll(mismatchedResult, mismatched);
        return new RegexResult(text, mRegex, matchedResult, mismatchedResult);
    }

    public static class RegexResult {
        public final String mText;
        public final String mRegex;
        public final List<String> mMatchList;
        public final List<String> mMismatchList;

        public RegexResult(String text, String regex, List<String> matchList, List<String> mismatchList) {
            mText = text;
            mRegex = regex;
            mMatchList = matchList;
            mMismatchList = mismatchList;
        }
    }
}
