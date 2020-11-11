/*
 * Copyright (C) 2018 Drake, Inc.
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

package com.drake.serialize

/**
 * 如果为空字符串将返回null
 */
fun String?.orNull(): String? {
    return if (this == null || this.isBlank()) null else this
}

/**
 * 如果集合为空返回null
 */
fun List<Any?>?.orNull(): List<Any?>? {
    return if (this == null || this.isEmpty()) null else this
}