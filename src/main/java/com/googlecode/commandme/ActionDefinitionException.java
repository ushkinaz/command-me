package com.googlecode.commandme;

import com.googlecode.commandme.impl.introspector.ActionDefinition;

/**
 * @author Dmitry Sidorenko
 */
public class ActionDefinitionException extends CliException {

    private final ActionDefinition actionDefinition;

    public ActionDefinitionException(String message, ActionDefinition actionDefinition) {
        super(message);
        this.actionDefinition = actionDefinition;
    }

    public ActionDefinition getActionDefinition() {
        return actionDefinition;
    }
}
