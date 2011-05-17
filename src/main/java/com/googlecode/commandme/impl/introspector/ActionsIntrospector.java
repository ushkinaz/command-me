package com.googlecode.commandme.impl.introspector;

import java.util.List;

/**
 * @author Dmitry Sidorenko
 */
public class ActionsIntrospector<T> implements ModuleActions {
    private Class<T> clz;

    public ActionsIntrospector(Class<T> clz) {
        this.clz = clz;
    }

    private void addAction(ActionDefinition actionDefinition) {
        //TODO:implement
    }

    @Override
    public List<ActionDefinition> getActions() {
        return null;//TODO: Implement
    }

    @Override
    public ActionDefinition getByLongName(String name) {
        return null;//TODO: Implement
    }

    @Override
    public ActionDefinition getByShortName(String name) {
        return null;//TODO: Implement
    }

    /**
     * Inspects module
     */
    public void inspect() {
        //TODO: implement
    }
}
