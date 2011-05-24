package com.googlecode.commandme.impl.introspector;

import java.lang.reflect.Method;

/**
 * Definition of an action
 *
 * @author Dmitry Sidorenko
 */
public final class OperandDefinition {

    private Method action;

    private String longName;
    private String shortName;

    private String description;

    OperandDefinition() {
    }

    public Method getMethod() {
        return action;
    }

    void setAction(Method action) {
        this.action = action;
    }

    public String getLongName() {
        return longName;
    }

    void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings({"RedundantIfStatement"})
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperandDefinition that = (OperandDefinition) o;

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

    @Override
    public String toString() {
        return "ActionDefinition{" +
                "action=" + action.getName() +
                ", longName='" + longName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
