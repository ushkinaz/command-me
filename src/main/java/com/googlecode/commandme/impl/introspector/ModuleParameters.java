package com.googlecode.commandme.impl.introspector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Definition of options.
 * Created from annotated module class.
 *
 * @author Dmitry Sidorenko
 */
public class ModuleParameters {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleParameters.class);

    private List<ParameterDefinition> parameterDefinitions;
    private Map<String, ParameterDefinition> shortNamesMap;
    private Map<String, ParameterDefinition> longNamesMap;


    public ModuleParameters() {
        parameterDefinitions = new LinkedList<ParameterDefinition>();
        shortNamesMap = new HashMap<String, ParameterDefinition>();
        longNamesMap = new HashMap<String, ParameterDefinition>();
    }

    /**
     * Adds new parameter definition.
     *
     * @param parameterDefinition parameter
     */
    public void addParameter(ParameterDefinition parameterDefinition) {
        if (parameterDefinition == null) {
            return;
        }
        parameterDefinitions.add(parameterDefinition);
        shortNamesMap.put(parameterDefinition.getShortName(), parameterDefinition);
        longNamesMap.put(parameterDefinition.getLongName(), parameterDefinition);
    }

    /**
     * @return unmodifiable list of {@link com.googlecode.commandme.impl.introspector.ParameterDefinition}
     */
    public List<ParameterDefinition> getParameterDefinitions() {
        return Collections.unmodifiableList(parameterDefinitions);
    }

    /**
     * @param name long name of a parameter
     * @return ParameterDefinition or null
     */
    public ParameterDefinition getByLongName(String name) {
        return longNamesMap.get(name);
    }

    /**
     * @param name short name of a parameter
     * @return ParameterDefinition or null
     */
    public ParameterDefinition getByShortName(String name) {
        return shortNamesMap.get(name);
    }
}
