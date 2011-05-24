package com.googlecode.commandme.impl.introspector;

import java.util.List;

/**
 * @author Dmitry Sidorenko
 */
public interface ModuleOptions {
    /**
     * @return unmodifiable list of {@link OptionDefinition}
     */
    List<OptionDefinition> getParameterDefinitions();

    /**
     * @param name long name of a parameter
     * @return ParameterDefinition or null
     */
    OptionDefinition getByLongName(String name);

    /**
     * @param name short name of a parameter
     * @return ParameterDefinition or null
     */
    OptionDefinition getByShortName(String name);

    /**
     * Inspects class for parameters
     */
    void inspect();
}
