package com.googlecode.commandme.story;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Dmitry Sidorenko
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Story_ExecuteAction.class,
        Story_StringParameterAsFullName.class,
        Story_WrongAction.class})
public class StoriesTestSuite {
}
