package com.googlecode.commandme.impl.introspector;

import com.googlecode.commandme.ActionDefinitionException;
import com.googlecode.commandme.annotations.Action;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * @author Dmitry Sidorenko
 */
public class ActionsIntrospectorTest {
    private ActionsIntrospector actionsIntrospector;

    @Before
    public void setUp() throws Exception {
        actionsIntrospector = new ActionsIntrospector<TestModule>(TestModule.class);
        actionsIntrospector.inspect();
    }

    @Test
    public void testGetActions() throws Exception {
        assertThat(actionsIntrospector.getActions().size(), is(2));
    }

    @Test
    public void testGetByLongName() throws Exception {
        assertThat(actionsIntrospector.getByLongName("greet").getLongName(), is("greet"));
        assertThat(actionsIntrospector.getByLongName("gr"), nullValue());
    }

    @Test
    public void testNotAccessibleAction() throws Exception {
        assertThat(actionsIntrospector.getByLongName("privateer"), nullValue());
    }

    @Test
    public void testActionWithParams() throws Exception {
        assertThat(actionsIntrospector.getByLongName("params"), nullValue());
    }

    @Test
    public void testSpecifyName() throws Exception {
        assumeThat(actionsIntrospector.getActions(), notNullValue());

        assertThat(actionsIntrospector.getByLongName("ciao"), notNullValue());
        assertThat(actionsIntrospector.getByShortName("bb"), notNullValue());
    }

    @Test(expected = ActionDefinitionException.class)
    public void testDuplicateLongNames() throws Exception {
        actionsIntrospector = new ActionsIntrospector<TestModuleDupLongNames>(TestModuleDupLongNames.class);
        actionsIntrospector.inspect();
    }

    @Test(expected = ActionDefinitionException.class)
    public void testDuplicateShortNames() throws Exception {
        actionsIntrospector = new ActionsIntrospector<TestModuleDupShortNames>(TestModuleDupShortNames.class);
        actionsIntrospector.inspect();
    }

    @Test
    @Ignore("Short names not fully supported yet")
    public void testGetByShortName() throws Exception {
        assertThat(actionsIntrospector.getByShortName("gr").getLongName(), is("gr"));
        assertThat(actionsIntrospector.getByShortName("g"), nullValue());
    }

    @Test
    public void testInspect() throws Exception {
        assertThat(actionsIntrospector.getActions(), notNullValue());
    }


    class TestModule {

        @Action
        public void greet() {
        }

        @Action(name = "ciao", shortName = "bb")
        public void bye() {
        }

        @Action
        void privateer() {
        }

        @Action
        public void params(String name) {
        }
    }

    class TestModuleDupLongNames {

        @Action(name = "bye")
        public void greet() {
        }

        @Action(name = "bye")
        public void bye() {
        }
    }

    class TestModuleDupShortNames {

        @Action(shortName = "bb")
        public void greet() {
        }

        @Action(shortName = "bb")
        public void bye() {
        }
    }
}
