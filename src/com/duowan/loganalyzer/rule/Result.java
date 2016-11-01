package com.duowan.loganalyzer.rule;

/**
 * @author carlosliu on 2016/10/27.
 */
public class Result {
    public final String mFilterResult;
    public final Result mParent;
    public final boolean mIsMatched;
    public final Rule mRule;

    public Result(Rule rule, Result parentResult, String filterResult, boolean matched) {
        mFilterResult = filterResult;
        mIsMatched = matched;
        mRule = rule;
        mParent = parentResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (mIsMatched != result.mIsMatched) return false;
        return mFilterResult != null ? mFilterResult.equals(result.mFilterResult) : result.mFilterResult == null;

    }

    @Override
    public int hashCode() {
        int result = mFilterResult != null ? mFilterResult.hashCode() : 0;
        result = 31 * result + (mIsMatched ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"mFilterResult\":\"").append(mFilterResult).append('\"');
        sb.append(", \"mIsMatched\":").append(mIsMatched);
        sb.append(", \"mRule\":").append(mRule);
        sb.append('}');
        return sb.toString();
    }

    public String getPositionInfo() {

        Rule parentRule = mRule.getParent();
        int ruleIndex = 0;
        if (parentRule != null) {
            ruleIndex = parentRule.getChildren().indexOf(mRule);
        }

        String parentResultIndex = mParent == null ? "head" : mParent.getPositionInfo();


        int currentIndex = mRule.getResults().get(mParent).indexOf(this);

        return String.format("[parent%d#parent_result%s#current_%d]", ruleIndex, parentResultIndex, currentIndex);
    }
}
