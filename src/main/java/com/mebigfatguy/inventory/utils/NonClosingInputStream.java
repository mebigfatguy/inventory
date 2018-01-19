/*
 * inventory - an ear/war inventory recorder
 * Copyright 2016-2018 MeBigFatGuy.com
 * Copyright 2016-2018 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.inventory.utils;

import java.io.FilterInputStream;
import java.io.InputStream;

public class NonClosingInputStream extends FilterInputStream {

    public NonClosingInputStream(InputStream is) {
        super(is);
    }

    @Override
    public void close() {
    }
}
