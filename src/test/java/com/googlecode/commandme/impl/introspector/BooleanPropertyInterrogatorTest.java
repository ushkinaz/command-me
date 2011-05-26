package com.googlecode.commandme.impl.introspector;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BooleanPropertyInterrogatorTest {
    private BooleanPropertyInterrogator interrogator;
    private Boolean                     name;

    @Before
    public void setUp() throws Exception {
        OptionDefinition optionDefinition = new OptionDefinition();
        optionDefinition.setType(String.class);
        optionDefinition.setWriterMethod(BooleanPropertyInterrogatorTest.class.getMethod("setName", Boolean.class));

        interrogator = new BooleanPropertyInterrogator(optionDefinition);
    }

    @Test
    public void testSetValue() throws Exception {
        interrogator.setValue(this, "");

        assertThat(name, is(true));
    }

    @Test
    public void testNeedValue() throws Exception {
        assertThat(interrogator.needValue(), is(false));
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setName(Boolean name) {
        this.name = name;
    }
}
