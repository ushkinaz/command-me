package com.googlecode.commandme.story;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Dmitry Sidorenko
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Story_ExecuteOperand.class,
        Story_StringOptionAsFullName.class,
        Story_WrongOperand.class})
public class StoriesTestSuite {
}
