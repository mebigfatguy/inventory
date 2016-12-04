/*
 * inventory - an ear/war inventory recorder
 * Copyright 2016 MeBigFatGuy.com
 * Copyright 2016 Dave Brosius
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
package com.mebigfatguy.inventory.core;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;

import com.mebigfatguy.inventory.cls.ClassInventoryVisitor;

public class ClassScanner implements ArchiveScanner {

    @Override
    public void scan(String name, Inventory inventory) throws IOException {

        try (InputStream is = inventory.getStream()) {
            ClassInventoryVisitor visitor = new ClassInventoryVisitor(inventory);
            ClassReader cr = new ClassReader(inventory.getStream());
            cr.accept(visitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }

}
