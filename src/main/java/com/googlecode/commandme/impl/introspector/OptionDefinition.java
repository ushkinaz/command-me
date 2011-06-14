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

import com.googlecode.commandme.impl.interrogator.PropertyInterrogator;
import com.googlecode.commandme.impl.interrogator.PropertyInterrogatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Dmitry Sidorenko
 */
public class OptionDefinition {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionDefinition.class);

    private String               longName;
    private String               shortName;
    private Class                type;
    private String               description;
    private Method               writerMethod;
    private PropertyInterrogator interrogator;

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
        interrogator = PropertyInterrogatorFactory.createInterrogator(this);
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
    @SuppressWarnings({"RedundantIfStatement"})
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OptionDefinition that = (OptionDefinition) o;

        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (longName != null ? !longName.equals(that.longName) : that.longName != null) {
            return false;
        }
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }

        return true;
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

    public PropertyInterrogator getInterrogator() {
        return interrogator;
    }
}