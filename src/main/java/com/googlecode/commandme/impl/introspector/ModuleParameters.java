package com.googlecode.commandme.impl.introspector;

import java.util.List;

/**
 * @author Dmitry Sidorenko
 */
public interface ModuleParameters {
    /**
     * @return unmodifiable list of {@link com.googlecode.commandme.impl.introspector.ParameterDefinition}
     */
    List<ParameterDefinition> getParameterDefinitions();

    /**
     * @param name long name of a parameter
     * @return ParameterDefinition or null
     */
    ParameterDefinition getByLongName(String name);

    /**
     * @param name short name of a parameter
     * @return ParameterDefinition or null
     */
    ParameterDefinition getByShortName(String name);
}
