package com.googlecode.commandme.impl.interrogator;

import com.googlecode.commandme.impl.introspector.ActionDefinition;
import com.googlecode.commandme.impl.introspector.OptionDefinition;

import java.text.MessageFormat;
import java.util.Collection;

class DefaultHelpStrategy implements HelpPrintStrategy {
    private String                       applicationName;
    private Collection<OptionDefinition> optionDefinitions;
    private Collection<ActionDefinition> actionDefinitions;

    public DefaultHelpStrategy(String applicationName, Collection<OptionDefinition> optionDefinitions, Collection<ActionDefinition> actionDefinitions) {
        this.applicationName = applicationName;
        this.optionDefinitions = optionDefinitions;
        this.actionDefinitions = actionDefinitions;
    }

    @Override
    public void printHelp() {
        System.out.println(applicationName);
        System.out.println("Usage: " + applicationName + "[options] [actions]");

        System.out.println("\tOptions:");
        for (OptionDefinition optionDefinition : optionDefinitions) {
            System.out
                    .println(MessageFormat.format("\t\t-{0}, --{1}: {2}", optionDefinition
                            .getShortName(), optionDefinition.getLongName(), optionDefinition.getDescription()));
        }

        System.out.println("\tActions:");
        for (ActionDefinition actionDefinition : actionDefinitions) {
            System.out
                    .println(MessageFormat.format("\t\t{0}, {1}: {2}", actionDefinition.getShortName(), actionDefinition
                            .getLongName(), actionDefinition
                            .getDescription()));
        }
    }
}
