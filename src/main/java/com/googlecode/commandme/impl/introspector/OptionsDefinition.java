package com.googlecode.commandme.impl.introspector;

/**
 * Definition of options.
 * Created from annotated module class.
 *
 * @author Dmitry Sidorenko
 */
public interface OptionsDefinition {
    /**
     * @return Long name of a parameter
     */
    String getLongName();

    /**
     * @return Short name of a parameter
     */
    String getShortName();

    /**
     * @return type of a parameter
     */
    Class getType();

    /**
     * @return default value
     */
    String getDefaultValue();

    /**
     * @return description
     */
    String getDescription();

    /**
     * @return should be shown in help
     */
    boolean showInHelp();
}
