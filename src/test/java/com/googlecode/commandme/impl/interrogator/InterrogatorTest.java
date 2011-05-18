package com.googlecode.commandme.impl.interrogator;

import com.googlecode.commandme.impl.introspector.ModuleActions;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.ModuleParameters;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InterrogatorTest {
    private Interrogator<InterrogatorTest>       interrogator;
    private ModuleIntrospector<InterrogatorTest> moduleIntrospector;
    private ModuleActions                        moduleActions;
    private ModuleParameters                     moduleParameters;

    @Before
    public void setUp() throws Exception {
        moduleIntrospector = mock(ModuleIntrospector.class);
        moduleActions = mock(ModuleActions.class);
        moduleParameters = mock(ModuleParameters.class);

        when(moduleIntrospector.getActions()).thenReturn(moduleActions);
        when(moduleIntrospector.getParameters()).thenReturn(moduleParameters);


        interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{});
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testTorture() throws Exception {
        //TODO: implement
        Assert.fail("Not implemented");
        interrogator.torture();
    }
}
