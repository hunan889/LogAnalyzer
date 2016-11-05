package com.duowan.loganalyzer.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author carlosliu on 2016/10/29.
 */
public class Rule {
    private int mId;
    private String mRule;
    private String mName;
    private Map<Result, List<Result>> mResults = new HashMap<>();
    private List<Rule> mChildren = new ArrayList<>();
    private Rule mParent;

    public Rule(int id) {
        this(id, null);
    }

    public Rule(int id, String rule) {
        mRule = rule;
        mId = id;
    }

    public Rule(Rule parent, String rule) {
        mParent = parent;
        mId = mParent.generateChildId();
        mRule = rule;
    }

    public Map<Result, List<Result>> getResults() {
        return mResults;
    }

    public int generateChildId() {
        return mChildren.size();
    }

    public void addChild(Rule rule) {
        rule.mParent = this;
        mChildren.add(rule);
    }

    public List<Rule> getChildren() {
        return mChildren;
    }

    public Rule getParent() {
        return mParent;
    }

    public String getIdDesc() {
        if (mParent != null) {
            return mParent.getIdDesc() + "." + mId;
        }
        return "" + mId;
    }

    public void execute(Result filterResult) {
        if (mResults == null) {
            mResults = new LinkedHashMap<>();
        }
        List<Result> results = null;
        if (mRule != null) {
            results = doExecute(filterResult);
            mResults.put(filterResult, results);
        } else {
            results = new ArrayList<>();
            results.add(filterResult);
        }
        if (results != null && results.size() > 0) {
            for (Result result : results) {
                if (result.mIsMatched) {
                    for (Rule child : mChildren) {
                        child.execute(result);
                    }
                }
            }
        }
    }

    private List<Result> doExecute(Result result) {
        Pattern pattern = Pattern.compile(mRule);
        Matcher matcher = pattern.matcher(result.mFilterResult);
        List<String> matchedResult = new ArrayList<>();
        while (matcher.find()) {
            String matched = matcher.group();
            matchedResult.add(matched);
        }

        List<String> mismatchedResult = new ArrayList<>();
        String[] mismatched = pattern.split(result.mFilterResult);
        Collections.addAll(mismatchedResult, mismatched);
        return onExecuteFinished(result, matchedResult, mismatchedResult);
    }

    private List<Result> onExecuteFinished(Result parentResult, List<String> matchedResults, List<String> mismatchedResults) {
        List<Result> result = new ArrayList<>();
        int mismatchedSize = mismatchedResults.size();
        int matchedSize = matchedResults.size();
        int size = mismatchedSize > matchedSize ? mismatchedSize : matchedSize;
        for (int i = 0; i < size; i++) {
            if (i < mismatchedSize) {
                result.add(new Result(this, parentResult, mismatchedResults.get(i), false));

            }

            if (i < matchedSize) {
                result.add(new Result(this, parentResult, matchedResults.get(i), true));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"mId\":").append(mId);
        sb.append(", \"mRule\":").append(mRule);
        sb.append(", \"mName\":\"").append(mName).append('\"');
        sb.append(", \"mChildren\":").append(mChildren);
        sb.append('}');
        return sb.toString();
    }
}
