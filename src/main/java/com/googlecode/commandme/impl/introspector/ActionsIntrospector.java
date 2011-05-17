package com.googlecode.commandme.impl.introspector;

import com.googlecode.commandme.annotations.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Dmitry Sidorenko
 */
public class ActionsIntrospector<T> implements ModuleActions {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionsIntrospector.class);

    private Class<T>                      clz;
    private Set<ActionDefinition>         actions;
    private Map<String, ActionDefinition> shortNames;
    private Map<String, ActionDefinition> longNames;

    public ActionsIntrospector(Class<T> clz) {
        this.clz = clz;
        actions = new HashSet<ActionDefinition>();
        shortNames = new HashMap<String, ActionDefinition>();
        longNames = new HashMap<String, ActionDefinition>();
    }

    private void addAction(ActionDefinition actionDefinition) {
        actions.add(actionDefinition);
        longNames.put(actionDefinition.getLongName(), actionDefinition);
        if (actionDefinition.getShortName() != null) {
            shortNames.put(actionDefinition.getShortName(), actionDefinition);
        }
    }

    @Override
    public Set<ActionDefinition> getActions() {
        return actions;
    }

    @Override
    public ActionDefinition getByLongName(String name) {
        return longNames.get(name);
    }

    @Override
    public ActionDefinition getByShortName(String name) {
        //We suppose that there is only one possible short name
        return shortNames.get(name);
    }

    /**
     * Inspects module
     */
    public void inspect() {
        for (Method method : clz.getMethods()) {
            Action actionAnnotation = method.getAnnotation(Action.class);
            if (actionAnnotation == null) {
                continue;
            }

            if (!Modifier.isPublic(method.getModifiers())) {
                LOGGER.warn("Non public actions are :" + method);
                continue;
            }

            if (method.getParameterTypes().length > 0) {
                LOGGER.warn("Actions with parameters are not yet supported:" + method);
                continue;
            }

            ActionDefinition definition = new ActionDefinition();
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
