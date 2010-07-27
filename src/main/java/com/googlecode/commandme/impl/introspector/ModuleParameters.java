package com.googlecode.commandme.impl.introspector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

    public ModuleParameters() {
        parameterDefinitions = new LinkedList<ParameterDefinition>();
    }

    public void addParameter(ParameterDefinition parameterDefinition) {
        parameterDefinitions.add(parameterDefinition);
    }

    /**
     *
     * @return unmodifiable list of {@link com.googlecode.commandme.impl.introspector.ParameterDefinition}
     */
    public List<ParameterDefinition> getParameterDefinitions() {
        return Collections.unmodifiableList(parameterDefinitions);
    }
}
