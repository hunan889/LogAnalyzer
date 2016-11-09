package com.duowan.loganalyzer;

/**
 * @author carlosliu on 2016/11/5.
 */
public class Commands {

    public static Command getLoadRgxCommand() {
        return new Command() {
            {
                setParamsCount(1);
            }

            @Override
            protected String initName() {
                return "load";
            }

            @Override
            protected Option[] initSupportOptions() {
                return new Option[0];
            }

            @Override
            public void execute() {

            }
        };
    }
}
