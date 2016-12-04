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

import java.util.Set;

public class InventoryEventFirer {

    private Set<InventoryEventListener> listeners;

    public InventoryEventFirer(Set<InventoryEventListener> listeners) {
        this.listeners = listeners;
    }

    public void fireScanningWar(String warName) {
        for (InventoryEventListener listener : listeners) {
            listener.scanningWar(warName);
        }
    }

    public void fireScanningJar(String jarName) {
        for (InventoryEventListener listener : listeners) {
            listener.scanningJar(jarName);
        }
    }

    public void fireScanningFile(String fileName) {
        for (InventoryEventListener listener : listeners) {
            listener.scanningFile(fileName);
        }
    }

    public void fireJarRecorded(String jarName) {
        for (InventoryEventListener listener : listeners) {
            listener.jarRecorded(jarName);
        }
    }

    public void firePackageRecorded(String packageName) {
        for (InventoryEventListener listener : listeners) {
            listener.packageRecorded(packageName);
        }
    }

    public void fireJarUsed(String jarName, String byFile) {
        for (InventoryEventListener listener : listeners) {
            listener.jarUsed(jarName, byFile);
        }
    }

    public void firePackageUsed(String packageName, String byFile) {
        for (InventoryEventListener listener : listeners) {
            listener.packageUsed(packageName, byFile);
        }
    }

    public void fireFailure(String info) {
        for (InventoryEventListener listener : listeners) {
            listener.failure(info);
        }
    }

}
