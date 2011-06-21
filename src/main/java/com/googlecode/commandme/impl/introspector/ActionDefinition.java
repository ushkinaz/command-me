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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionDefinition that = (ActionDefinition) o;

        return !(longName != null ? !longName.equals(that.longName) : that.longName != null);

    }

    @Override
    public int hashCode() {
        return longName != null ? longName.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ActionDefinition");
        sb.append(", longName='").append(longName).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
