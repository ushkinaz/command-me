package com.googlecode.commandme.impl.introspector;

import java.util.List;

/**
 * @author Dmitry Sidorenko
 */
public interface ModuleOptions {
    /**
     * @return unmodifiable list of {@link OptionDefinition}
     */
    List<OptionDefinition> getOptionDefinitions();

    /**
     * @param name long name of a option
     * @return OptionDefinition or null
     */
    OptionDefinition getByLongName(String name);

    /**
     * @param name short name of a option
     * @return OptionDefinition or null
     */
    OptionDefinition getByShortName(String name);

    /**
     * Inspects class for options
     */
    void inspect();
}
