package com.duowan.loganalyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carlosliu on 2016/11/4.
 */
public abstract class Command {

    private Option[] mOptions;
    private String mName;
    private List<String> mParams;
    private int mParamsCount;

    public Command() {
        mOptions = initSupportOptions();
        mName = initName();
    }

    public String getName() {
        return mName;
    }

    protected abstract String initName();

    public void setParamsCount(int paramsCount) {
        mParamsCount = paramsCount;
    }

    public void parse(List<String> args) {

        mParams = args.subList(0, mParamsCount);
        Option[] options = mOptions;
        if (options == null || options.length == 0) {
            return;
        }
        for (Option option : options) {
            for (int i = mParamsCount; i < args.size(); i++) {
                String arg = args.get(i);
                if (arg.trim().equals(option.getName())) {
                    List<String> params = args.subList(i + 1, i + 1 + option.getParamsCount());
                    option.setParams(params);
                }
            }
        }
    }

    public Option[] getOptions() {
        return mOptions;
    }

    protected abstract Option[] initSupportOptions();

    public static class Option {
        String mName;
        int mParamsCount;
        boolean mRequired;
        List<String> mParams;

        public List<String> getParams() {
            return mParams;
        }

        public void setParams(List<String> params) {
            mParams = params;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }


        public int getParamsCount() {
            return mParamsCount;
        }

        public void setParamsCount(int paramsCount) {
            mParamsCount = paramsCount;
        }

        public boolean isRequired() {
            return mRequired;
        }

        public void setRequired(boolean required) {
            mRequired = required;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append(" ").append(mName);
            if (mParams != null && mParams.size() > 0) {
                for (String param : mParams) {
                    builder.append(" ").append(param);
                }
            }
            return builder.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(mName);
        if (mParams != null && mParams.size() > 0) {
            for (String param : mParams) {
                builder.append(" ").append(param);
            }
        }
        for (Option option : mOptions) {
            builder.append(option);
        }
        return builder.toString();
    }

    public abstract void execute();

    public static void main(String args[]) {

        Command command = new Command() {
            @Override
            protected String initName() {
                return "test";
            }

            @Override
            protected Option[] initSupportOptions() {
                return new Option[]{
                        new Option() {{
                            mName = "-f";
                            mParamsCount = 2;
                        }}
                };
            }

            @Override
            public void execute() {

                for (Option option : getOptions()) {
                    System.out.println(option);
                }
            }
        };
        command.parse(new ArrayList<String>() {{
            add("-f");
            add("firstParam");
            add("secondParam");
        }});

        System.out.println(command);

    }
}
