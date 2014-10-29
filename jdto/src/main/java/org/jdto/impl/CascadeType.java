/*
 *    Copyright 2011 Juan Alberto López Cavallotti
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

package org.jdto.impl;

/**
 * Describe the cascade types that can be applied to DTOs
 * @author Juan Alberto Lopez Cavallotti
 */
public enum CascadeType {
    /**
     * Single field conversion
     */
    SINGLE,
    /**
     * Arrays should be treated as lists and then converted to arrays again.
     */
    ARRAY,
    /**
     * The only collection supported at the moment will be the list but for
     * futures sake.
     */
    LIST,
    SET
}
