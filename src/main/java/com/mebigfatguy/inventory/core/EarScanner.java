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

public class EarScanner implements ArchiveScanner {

    @Override
    public void scan(String name, Inventory inventory) throws IOException {
        inventory.getEventFirer().fireScanningWar(name);
        try (ZipInputStream zis = new ZipInputStream(inventory.getStream())) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String fileName = entry.getName();
                if (fileName.endsWith(".war")) {
                    try (LengthLimitedInputStream is = new LengthLimitedInputStream(zis, entry.getSize())) {
                        WarScanner scanner = new WarScanner();
                        inventory.setStream(is);
                        scanner.scan(fileName, inventory);
                    } finally {
                        inventory.resetStream();
                    }
                } else if (fileName.endsWith(".jar")) {
                    try (LengthLimitedInputStream is = new LengthLimitedInputStream(zis, entry.getSize())) {
                        JarScanner scanner = new JarScanner();
                        inventory.setStream(is);
                        scanner.scan(fileName, inventory);
                    } finally {
                        inventory.resetStream();
                    }
                } else if (fileName.endsWith("/")) {

                } else {
                    try (LengthLimitedInputStream is = new LengthLimitedInputStream(zis, entry.getSize())) {
                        FileScanner scanner = new FileScanner();
                        inventory.setStream(is);
                        scanner.scan(fileName, inventory);
                    } finally {
                        inventory.resetStream();
                    }
                }
                entry = zis.getNextEntry();
            }
        }
    }
}
