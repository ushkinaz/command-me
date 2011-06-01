package com.googlecode.commandme.impl.introspector;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author Dmitry Sidorenko
 */
public class OperandDefinitionTest {

    public static final String LONG_NAME  = "long";
    public static final String SHORT_NAME = "shorty";
    public static final String DESC       = "desc";

    @Test
    public void testSetOperand() throws Exception {
        OperandDefinition definition = new OperandDefinition();
        Method testSetOperand = OperandDefinitionTest.class.getMethod("testSetOperand");
        definition.setOperand(testSetOperand);
        Assert.assertThat(definition.getMethod(), CoreMatchers.is(testSetOperand));
    }

    @Test
    public void testSetLongName() throws Exception {
        OperandDefinition definition = new OperandDefinition();
        definition.setLongName(LONG_NAME);
        Assert.assertThat(definition.getLongName(), CoreMatchers.is(LONG_NAME));
    }

    @Test
    public void testSetShortName() throws Exception {
        OperandDefinition definition = new OperandDefinition();
        definition.setShortName(SHORT_NAME);
        Assert.assertThat(definition.getShortName(), CoreMatchers.is(SHORT_NAME));
    }

    @Test
    public void testSetDescription() throws Exception {
        OperandDefinition definition = new OperandDefinition();
        definition.setDescription(DESC);
        Assert.assertThat(definition.getDescription(), CoreMatchers.is(DESC));
    }

}
