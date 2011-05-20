package com.googlecode.commandme.impl.interrogator;

import com.googlecode.commandme.annotations.Parameter;
import com.googlecode.commandme.impl.introspector.*;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InterrogatorTest {
    private static final String JOHN_FROSTER = "John Froster";
    private static final String DEVELOPER    = "developer";
    private ModuleIntrospector<InterrogatorTest> moduleIntrospector;

    private String name;
    private String desc;

    @Before
    public void setUp() throws Exception {
        moduleIntrospector = mock(ModuleIntrospector.class);
        ModuleActions moduleActions = new ActionsIntrospector<InterrogatorTest>(InterrogatorTest.class);
        moduleActions.inspect();

        ModuleParameters moduleParameters = new ParametersIntrospector<InterrogatorTest>(InterrogatorTest.class);
        moduleParameters.inspect();

        when(moduleIntrospector.getActions()).thenReturn(moduleActions);
        when(moduleIntrospector.getParameters()).thenReturn(moduleParameters);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testTortureSetOneName() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--name", JOHN_FROSTER});
        interrogator.torture();

        assertThat(name, CoreMatchers.is(JOHN_FROSTER));
    }

    @Test
    public void testTortureSetTwoNames() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--name", JOHN_FROSTER, "--desc", DEVELOPER});
        interrogator.torture();

        assertThat(name, CoreMatchers.is(JOHN_FROSTER));
        assertThat(desc, CoreMatchers.is(DEVELOPER));
    }

    @Parameter
    public void setName(String name) {
        this.name = name;
    }

    @Parameter
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
