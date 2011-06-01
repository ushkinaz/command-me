package com.googlecode.commandme.impl.introspector;

import java.lang.reflect.Method;

/**
 * Definition of an operand
 *
 * @author Dmitry Sidorenko
 */
public final class OperandDefinition {

    private Method operand;

    private String longName;
    private String shortName;

    private String description;

    OperandDefinition() {
    }

    public Method getMethod() {
        return operand;
    }

    void setOperand(Method operand) {
        this.operand = operand;
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

        if (!operand.equals(that.operand)) {
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
        int result = operand.hashCode();
        result = 31 * result + longName.hashCode();
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OperandDefinition{" +
                "operand=" + operand.getName() +
                ", longName='" + longName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
