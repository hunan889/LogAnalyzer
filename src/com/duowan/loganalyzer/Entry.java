package com.duowan.loganalyzer;

import com.duowan.loganalyzer.rule.Rule;

/**
 * @author carlosliu on 2016/10/28.
 */
public interface Entry {
    void analyze(String file, Rule rule);
}
