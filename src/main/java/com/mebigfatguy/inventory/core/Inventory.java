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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mebigfatguy.inventory.utils.NonClosingInputStream;

public class Inventory implements AutoCloseable {

    private File archive;
    private Set<InventoryEventListener> listeners;
    private InputStream stream;
    private InputStream overrideStream;
    private String activeJar;

    public Inventory(File archive) throws IOException {
        this.archive = archive;
        stream = new BufferedInputStream(Files.newInputStream(archive.toPath()));
        listeners = new HashSet<>();
    }

    @Override
    public void close() {
        try {
            stream.close();
        } catch (IOException e) {
        }
    }

    public void addInventoryEventListener(InventoryEventListener listener) {
        listeners.add(listener);
    }

    public void removeInventoryEventListener(InventoryEventListener listener) {
        listeners.remove(listener);
    }

    public InventoryReport takeInventory() throws InventoryException {

        ArchiveScanner scanner;

        if (archive.getName().endsWith(".ear")) {
            scanner = new EarScanner();
        } else if (archive.getName().endsWith(".war")) {
            scanner = new WarScanner();
        } else {
            throw new InventoryException("Unrecognized archive type: " + archive);
        }

        InventoryRecorder recorder = new InventoryRecorder();
        try {
            addInventoryEventListener(recorder);
            scanner.scan(archive.getName(), this);
            return recorder;
        } catch (IOException e) {
            getEventFirer().fireFailure(e.getMessage());
            throw new InventoryException("Failed to read archive completely " + archive.getName(), e);
        } finally {
            removeInventoryEventListener(recorder);
        }
    }

    public InputStream getStream() {
        if (overrideStream != null) {
            return new NonClosingInputStream(overrideStream);
        }
        return new NonClosingInputStream(stream);
    }

    public void setStream(InputStream stream) {
        overrideStream = stream;
    }

    public void resetStream() {
        overrideStream = null;
    }

    public InventoryEventFirer getEventFirer() {
        return new InventoryEventFirer(listeners);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    class InventoryRecorder implements InventoryEventListener, InventoryReport {

        private Map<String, Set<String>> jarInventory = new HashMap<>();
        private Map<String, Set<String>> packagesUsed = new HashMap<>();

        @Override
        public void scanningWar(String warName, ScanStatus status) {
        }

        @Override
        public void scanningJar(String jarName, ScanStatus status) {
            if (status == ScanStatus.START) {
                activeJar = jarName;
            } else {
                activeJar = null;
            }
        }

        @Override
        public void scanningFile(String fileName, ScanStatus status) {
        }

        @Override
        public void jarRecorded(String jarName) {
        }

        @Override
        public void classRecorded(String packageName) {
            Set<String> packages = jarInventory.get(activeJar);
            if (packages == null) {
                packages = new HashSet<>();
                packagesUsed.put(activeJar, packages);
            }

            packages.add(packageName);
        }

        @Override
        public void jarUsed(String jarName, String byFile) {
        }

        @Override
        public void classUsed(String packageName, String byFile) {
            Set<String> packages = packagesUsed.get(byFile);
            if (packages == null) {
                packages = new HashSet<>();
                packagesUsed.put(byFile, packages);
            }

            packages.add(packageName);
        }

        @Override
        public void failure(String info) {
        }
        
        public Map<String, Set<String>> getJarInventory() {
        	return jarInventory;
        }
        
        public Map<String, Set<String>> getPackagedUsed() {
        	return packagesUsed;
        }
    }
}
