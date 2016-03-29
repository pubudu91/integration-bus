/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.exceptions;

/**
 * Exception when failing to add type converters due there is already an existing type converter.
 */
public class TypeConverterExistsException extends Exception {

    private final transient Class<?> toType;
    private final transient Class<?> fromType;

    public TypeConverterExistsException(Class<?> toType, Class<?> fromType) {
        super("Failed to add type converter because a type converter exists. " + fromType + " -> " + toType);
        this.toType = toType;
        this.fromType = fromType;
    }

    public Class<?> getToType() {
        return toType;
    }

    public Class<?> getFromType() {
        return fromType;
    }
}

