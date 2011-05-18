package com.googlecode.commandme.impl.interrogator;

import com.googlecode.commandme.annotations.Parameter;
import com.googlecode.commandme.impl.introspector.ModuleActions;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.ModuleParameters;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InterrogatorTest {
    private static final String JOHN_FROSTER = "John Froster";
    private ModuleIntrospector<InterrogatorTest> moduleIntrospector;
    private ModuleActions                        moduleActions;
    private ModuleParameters                     moduleParameters;

    private String name;
    private String desc;

    @Before
    public void setUp() throws Exception {
        moduleIntrospector = mock(ModuleIntrospector.class);
        moduleActions = mock(ModuleActions.class);
        moduleParameters = mock(ModuleParameters.class);

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

        org.junit.Assert.assertThat(name, CoreMatchers.is(JOHN_FROSTER));
    }

    @Parameter
    public void setName(String name) {
        this.name = name;
    }

    @Parameter
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
