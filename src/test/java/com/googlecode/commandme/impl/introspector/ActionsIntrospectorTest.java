package com.googlecode.commandme.impl.introspector;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
 */
public class ActionsIntrospectorTest {
    private ActionsIntrospector actionsIntrospector;

    @Before
    public void setUp() throws Exception {
        actionsIntrospector = new ActionsIntrospector();
        ActionDefinition actionDefinition = new ActionDefinition();
        Method method = TestModule1.class.getMethod("greet");
        actionDefinition.setAction(method);
        actionDefinition.setLongName("greet");
        actionDefinition.setShortName("gr");
        actionsIntrospector.addAction(actionDefinition);
    }

    @Test
    public void testGetActions() throws Exception {
        assertThat(actionsIntrospector.getActions().size(), is(1));
    }

    @Test
    public void testGetByLongName() throws Exception {
        assertThat(actionsIntrospector.getByLongName("greet").getLongName(), is("greet"));
        assertThat(actionsIntrospector.getByLongName("gr").getLongName(), nullValue());
    }

    @Test
    public void testGetByShortName() throws Exception {
        assertThat(actionsIntrospector.getByShortName("gr").getLongName(), is("gr"));
        assertThat(actionsIntrospector.getByShortName("g").getLongName(), nullValue());
    }
}
