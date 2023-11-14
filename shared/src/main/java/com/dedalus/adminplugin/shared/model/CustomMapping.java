/*
 * Copyright 2023 Julian Pufler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dedalus.adminplugin.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CustomMapping {
    @Getter
    @Setter
    private String mappingId;

    @Getter
    @Setter
    private String mappingValueId;

    @Getter
    @Setter
    private String mappingName;

    @Getter
    @Setter
    private int priority;

    @Getter
    @Setter
    private String internalValueSet;

    @Getter
    @Setter
    private String externalValueSet;

    @Getter
    @Setter
    private String internalCodeSystem;

    @Getter
    @Setter
    private String externalCodeSystem;

    @Getter
    @Setter
    private String internalValue;

    @Getter
    @Setter
    private String externalValue;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String defaultInternalCodeSystem;

    @Getter
    @Setter
    private String defaultInternalValue;

}
