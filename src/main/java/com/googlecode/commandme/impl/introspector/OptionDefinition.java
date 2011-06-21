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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.commandme.impl.interrogator.PropertyVivisector;
import com.googlecode.commandme.impl.interrogator.PropertyVivisectorFactory;

/**
 * @author Dmitry Sidorenko
 */
public final class OptionDefinition {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionDefinition.class);

    private String             longName;
    private boolean            required;
    private String             shortName;
    private Class              type;
    private String             description;
    private Method             writerMethod;
    private PropertyVivisector vivisector;

    public OptionDefinition() {
    }

    public void setLongName(String longName) {
        if (this.longName != null) {
            throw new IllegalStateException("LongName can't be set twice");
        }
        this.longName = longName;
    }

    public void setShortName(String shortName) {
        if (this.shortName != null) {
            throw new IllegalStateException("ShortName can't be set twice");
        }
        this.shortName = shortName;
    }

    public void setType(Class type) {
        this.type = type;
        vivisector = PropertyVivisectorFactory.createInterrogator(this);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public Class getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OptionDefinition that = (OptionDefinition) o;

        return !(longName != null ? !longName.equals(that.longName) : that.longName != null);

    }

    @Override
    public int hashCode() {
        return longName != null ? longName.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OptionDefinition");
        sb.append("{longName='").append(longName).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", type=").append(type);
        sb.append(", required=").append(required);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Method getWriterMethod() {
        return writerMethod;
    }

    public void setWriterMethod(Method writerMethod) {
        this.writerMethod = writerMethod;
    }

    public PropertyVivisector getVivisector() {
        return vivisector;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}