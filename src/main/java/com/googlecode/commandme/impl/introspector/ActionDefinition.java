/*
 * Copyright (c) 2010-2011, Dmitry Sidorenko. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    ActionDefinition() {
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
