package com.googlecode.commandme.examples;

import com.googlecode.commandme.CLIParser;
import com.googlecode.commandme.annotations.Operand;
import com.googlecode.commandme.annotations.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Dmitry Sidorenko
 */
public class TestModule {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(TestModule.class);

    private String optionString1;
    private String optionString2;

    private int     optionInt1;
    private Integer optionInt2;

    private Double optionDouble1;
    private double optionDouble2;

    private Date optionDate1;

    @Option
    public void setOptionString1(String optionString1) {
        this.optionString1 = optionString1;
    }

    @Option
    public void setOptionString2(String optionString2) {
        this.optionString2 = optionString2;
    }

    @Option
    public void setOptionInt1(int optionInt1) {
        this.optionInt1 = optionInt1;
    }

    @Option
    public void setOptionInt2(Integer optionInt2) {
        this.optionInt2 = optionInt2;
    }

    @Option
    public void setOptionDouble1(Double optionDouble1) {
        this.optionDouble1 = optionDouble1;
    }

    @Option
    public void setOptionDouble2(double optionDouble2) {
        this.optionDouble2 = optionDouble2;
    }

    @Option
    public void setOptionDate1(Date optionDate1) {
        this.optionDate1 = optionDate1;
    }

    @Operand
    public void doParse() {

    }

    @Operand
    public void makeLove() {

    }

    public static void main(String[] args) {
        CLIParser.createModule(TestModule.class).execute(args);
    }
}
