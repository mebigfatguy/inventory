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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.mebigfatguy.inventory.utils.LengthLimitedInputStream;
import com.mebigfatguy.inventory.utils.NonClosingZipInputStream;

public class WarScanner implements ArchiveScanner {

    @Override
    public void scan(Inventory inventory) throws IOException {
        try (ZipInputStream zis = new NonClosingZipInputStream(inventory.getStream())) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String name = entry.getName();
                if (name.endsWith(".jar")) {
                    inventory.getEventFirer().fireScanningJar(name);
                    try (LengthLimitedInputStream is = new LengthLimitedInputStream(zis, entry.getSize())) {
                        JarScanner scanner = new JarScanner();
                        inventory.setStream(is);
                        scanner.scan(inventory);
                    } finally {
                        inventory.resetStream();
                    }
                } else {
                    inventory.getEventFirer().fireScanningFile(name);
                    try (LengthLimitedInputStream is = new LengthLimitedInputStream(zis, entry.getSize())) {
                        FileScanner scanner = new FileScanner();
                        inventory.setStream(is);
                        scanner.scan(inventory);
                    } finally {
                        inventory.resetStream();
                    }
                }
                entry = zis.getNextEntry();
            }
        }
    }
}
