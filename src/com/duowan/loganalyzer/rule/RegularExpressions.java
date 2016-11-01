package com.duowan.loganalyzer.rule;

/**
 * @author carlosliu on 2016/10/27.
 */
public class RegularExpressions {
    private static final String FORMAT_CONTAINS = "[\\S\\s\\w]*%s[\\S\\s\\w]*";//在任意字符之间有A
    private static final String FORMAT_BEFORE = "(?=%s)";
    private static final String FORMAT_AFTER = "(?<=%s)";
    private static final String EX_TIME = "(^|(?<=\\n))\\d{4}:\\d{2}:\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3} ";
    private static final String EX_END_OF_FILE = "\\s*\\z";//后面全是空白字符且结束后没有换行
    private static final String EX_LOG_LINE = time() + any() + "?" + before(or(time(), endOfFile()));//每一行开头是时间，在下一行开始之前，或者在结尾之前

    private static final String EX_ANY = "[\\S\\s\\w]*";//任意字符
    private static final String EX_ANY_ONE_LINE = ".*";//不包括换行

    public static String range(String rangeRegex, String... args) {
        if (args == null || args.length < 2) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(args[0])
                .append(rangeRegex)
                .append(args[1]);
        for (int i = 2; i < args.length; i++) {
            builder.append(rangeRegex).append(args[i]);
        }

        return builder.toString();
    }

    public static String range(boolean lazy, boolean multiLine, String... ranges) {
        return range(String.format(lazy ? "%s?" : "%s", multiLine ? EX_ANY : EX_ANY_ONE_LINE), ranges);
    }

    public static String contains(boolean multiLine, String text) {
        String wildcard = multiLine ? EX_ANY : EX_ANY_ONE_LINE;
        return wildcard + text + wildcard;
    }

    public static String time() {
        return EX_TIME;
    }

    public static String any() {
        return EX_ANY;
    }

    public static String anyChar() {
        return EX_ANY_ONE_LINE;
    }

    public static String before(String s) {
        return String.format(FORMAT_BEFORE, s);
    }

    public static String after(String s) {
        return String.format(FORMAT_AFTER, s);
    }

    public static String or(String... args) {
        if (args == null || args.length == 0) {
            return "";
        }
        if (args.length == 1) {
            return args[0];
        }
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(args[0]);
        for (int i = 1; i < args.length; i++) {
            builder.append("|").append(args[i]);
        }
        builder.append(")");
        return builder.toString();
    }

    public static String endOfFile() {
        return EX_END_OF_FILE;
    }

    public static String logLine() {
        return EX_LOG_LINE;
    }
}
