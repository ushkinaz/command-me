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
    private final Set<OperandDefinition>         operands;
    private final Map<String, OperandDefinition> shortNames;
    private final Map<String, OperandDefinition> longNames;

    public OperandsIntrospector(Class<T> clz) {
        this.clz = clz;
        operands = new HashSet<OperandDefinition>();
        shortNames = new HashMap<String, OperandDefinition>();
        longNames = new HashMap<String, OperandDefinition>();
    }

    private void addOperand(OperandDefinition operandDefinition) {
        operands.add(operandDefinition);
        OperandDefinition previous = longNames.put(operandDefinition.getLongName(), operandDefinition);
        checkClash(operandDefinition, previous);
        if (operandDefinition.getShortName() != null) {
            previous = shortNames.put(operandDefinition.getShortName(), operandDefinition);
            checkClash(operandDefinition, previous);
        }
    }

    private void checkClash(OperandDefinition operandDefinition, OperandDefinition previousDefinition) {
        if (previousDefinition != null) {
            LOGGER.error("Duplicate operands definitions");
            throw new OperandDefinitionException("Operands clashed: new=" + operandDefinition + ", old=" + previousDefinition, operandDefinition);
        }
    }

    @Override
    public Set<OperandDefinition> getOperands() {
        return operands;
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
            Operand operandAnnotation = method.getAnnotation(Operand.class);
            if (operandAnnotation == null) {
                continue;
            }

            if (method.getParameterTypes().length > 0) {
                LOGGER.warn("Operands with options are not yet supported: {}", method);
                continue;
            }

            OperandDefinition definition = new OperandDefinition();
            definition.setOperand(method);
            if (operandAnnotation.name().length() > 0) {
                definition.setLongName(operandAnnotation.name());
            } else {
                definition.setLongName(method.getName());
            }
            if (operandAnnotation.shortName().length() > 0) {
                definition.setShortName(operandAnnotation.shortName());
            }
            if (operandAnnotation.description().length() > 0) {
                definition.setDescription(operandAnnotation.description());
            }
            addOperand(definition);
        }
    }
}
