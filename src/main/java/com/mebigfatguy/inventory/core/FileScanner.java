/*
 * inventory - an ear/war inventory recorder
 * Copyright 2016-2019 MeBigFatGuy.com
 * Copyright 2016-2019 Dave Brosius
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
import java.util.Locale;

public class FileScanner implements ArchiveScanner {

    @Override
    public void scan(String name, Inventory inventory) throws IOException {
        inventory.getEventFirer().fireScanningFile(name, ScanStatus.START);
        try {
            String extension = getExtension(name);

            ArchiveScanner scanner;
            switch (extension) {
                case "class":
                    scanner = new ClassScanner();
                break;

                case "xml":
                    scanner = new XMLScanner();
                break;
                
                case "properties":
                	String parent = name.substring(0, name.lastIndexOf("/"));
                	if (!parent.endsWith("META-INF/services")) {
                		return;
                	}
                	
               		scanner = new PropertiesScanner();
                break;

                default:
                    return;
            }

            scanner.scan(name, inventory);
        } finally {
            inventory.getEventFirer().fireScanningFile(name, ScanStatus.END);
        }
    }

    private String getExtension(String name) {
        int dotPos = name.lastIndexOf('.');
        if (dotPos < 0) {
            return "";
        }
        return name.substring(dotPos + 1).toLowerCase(Locale.ENGLISH);
    }
}
