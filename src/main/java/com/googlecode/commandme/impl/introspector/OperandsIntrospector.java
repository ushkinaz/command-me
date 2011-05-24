package com.googlecode.commandme.impl.introspector;

import com.googlecode.commandme.OperandDefinitionException;
import com.googlecode.commandme.annotations.Operand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Dmitry Sidorenko
 */
public class OperandsIntrospector<T> implements ModuleOperands {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperandsIntrospector.class);

    private final Class<T>                      clz;
    private final Set<OperandDefinition>         actions;
    private final Map<String, OperandDefinition> shortNames;
    private final Map<String, OperandDefinition> longNames;

    public OperandsIntrospector(Class<T> clz) {
        this.clz = clz;
        actions = new HashSet<OperandDefinition>();
        shortNames = new HashMap<String, OperandDefinition>();
        longNames = new HashMap<String, OperandDefinition>();
    }

    private void addAction(OperandDefinition actionDefinition) {
        actions.add(actionDefinition);
        OperandDefinition previous = longNames.put(actionDefinition.getLongName(), actionDefinition);
        checkClash(actionDefinition, previous);
        if (actionDefinition.getShortName() != null) {
            previous = shortNames.put(actionDefinition.getShortName(), actionDefinition);
            checkClash(actionDefinition, previous);
        }
    }

    private void checkClash(OperandDefinition actionDefinition, OperandDefinition previousDefinition) {
        if (previousDefinition != null) {
            LOGGER.error("Duplicate actions definitions");
            throw new OperandDefinitionException("Actions clashed: new=" + actionDefinition + ", old=" + previousDefinition, actionDefinition);
        }
    }

    @Override
    public Set<OperandDefinition> getActions() {
        return actions;
    }

    @Override
    public OperandDefinition getByLongName(String name) {
        return longNames.get(name);
    }

    @Override
    public OperandDefinition getByShortName(String name) {
        //We suppose that there is only one possible short name
        return shortNames.get(name);
    }

    @Override
    public void inspect() {
        for (Method method : clz.getMethods()) {
            Operand actionAnnotation = method.getAnnotation(Operand.class);
            if (actionAnnotation == null) {
                continue;
            }

            if (method.getParameterTypes().length > 0) {
                LOGGER.warn("Actions with parameters are not yet supported: {}", method);
                continue;
            }

            OperandDefinition definition = new OperandDefinition();
            definition.setAction(method);
            if (actionAnnotation.name().length() > 0) {
                definition.setLongName(actionAnnotation.name());
            } else {
                definition.setLongName(method.getName());
            }
            if (actionAnnotation.shortName().length() > 0) {
                definition.setShortName(actionAnnotation.shortName());
            }
            if (actionAnnotation.description().length() > 0) {
                definition.setDescription(actionAnnotation.description());
            }
            addAction(definition);
        }
    }
}
