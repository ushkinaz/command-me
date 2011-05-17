package com.googlecode.commandme.impl.introspector;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
 */
public class ActionsIntrospectorTest {
    private ActionsIntrospector actionsIntrospector;

    @Before
    public void setUp() throws Exception {
        actionsIntrospector = new ActionsIntrospector<TestModule1>(TestModule1.class);
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

    @Test
    public void testInspect() throws Exception {
        actionsIntrospector.inspect();
        assertThat(actionsIntrospector.getActions(), notNullValue());
    }
}
