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

    private String parameterString1;
    private String parameterString2;

    private int     parameterInt1;
    private Integer parameterInt2;

    private Double parameterDouble1;
    private double parameterDouble2;

    private Date parameterDate1;

    @Option
    public void setParameterString1(String parameterString1) {
        this.parameterString1 = parameterString1;
    }

    @Option
    public void setParameterString2(String parameterString2) {
        this.parameterString2 = parameterString2;
    }

    @Option
    public void setParameterInt1(int parameterInt1) {
        this.parameterInt1 = parameterInt1;
    }

    @Option
    public void setParameterInt2(Integer parameterInt2) {
        this.parameterInt2 = parameterInt2;
    }

    @Option
    public void setParameterDouble1(Double parameterDouble1) {
        this.parameterDouble1 = parameterDouble1;
    }

    @Option
    public void setParameterDouble2(double parameterDouble2) {
        this.parameterDouble2 = parameterDouble2;
    }

    @Option
    public void setParameterDate1(Date parameterDate1) {
        this.parameterDate1 = parameterDate1;
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
