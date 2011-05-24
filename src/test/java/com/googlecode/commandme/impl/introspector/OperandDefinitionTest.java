package com.googlecode.commandme.impl.introspector;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author Dmitry Sidorenko
 */
public class ActionDefinitionTest {

    public static final String LONG_NAME  = "long";
    public static final String SHORT_NAME = "shorty";
    public static final String DESC       = "desc";

    @Test
    public void testSetAction() throws Exception {
        ActionDefinition definition = new ActionDefinition();
        Method testSetAction = ActionDefinitionTest.class.getMethod("testSetAction");
        definition.setAction(testSetAction);
        Assert.assertThat(definition.getMethod(), CoreMatchers.is(testSetAction));
    }

    @Test
    public void testSetLongName() throws Exception {
        ActionDefinition definition = new ActionDefinition();
        definition.setLongName(LONG_NAME);
        Assert.assertThat(definition.getLongName(), CoreMatchers.is(LONG_NAME));
    }

    @Test
    public void testSetShortName() throws Exception {
        ActionDefinition definition = new ActionDefinition();
        definition.setShortName(SHORT_NAME);
        Assert.assertThat(definition.getShortName(), CoreMatchers.is(SHORT_NAME));
    }

    @Test
    public void testSetDescription() throws Exception {
        ActionDefinition definition = new ActionDefinition();
        definition.setDescription(DESC);
        Assert.assertThat(definition.getDescription(), CoreMatchers.is(DESC));
    }

}
