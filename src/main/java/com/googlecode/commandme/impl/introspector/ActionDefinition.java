package com.googlecode.commandme.impl.introspector;

import java.lang.reflect.Method;

/**
 * Definition of an action
 *
 * @author Dmitry Sidorenko
 */
public final class ActionDefinition {

    private Method action;

    private String longName;
    private String shortName;

    private String description;

    public ActionDefinition() {
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionDefinition that = (ActionDefinition) o;

        if (!action.equals(that.action)) {
            return false;
        }
        if (!longName.equals(that.longName)) {
            return false;
        }
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = action.hashCode();
        result = 31 * result + longName.hashCode();
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        return result;
    }
}
