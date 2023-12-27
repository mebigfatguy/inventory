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

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InventoryEventFirer {

    private Set<InventoryEventListener> listeners;

    public InventoryEventFirer(Set<InventoryEventListener> listeners) {
        this.listeners = listeners;
    }

    public void fireScanningWar(String warName, ScanStatus status) {
        for (InventoryEventListener listener : listeners) {
            listener.scanningWar(warName, status);
        }
    }

    public void fireScanningJar(String jarName, ScanStatus status) {
        for (InventoryEventListener listener : listeners) {
            listener.scanningJar(jarName, status);
        }
    }

    public void fireScanningFile(String fileName, ScanStatus status) {
        for (InventoryEventListener listener : listeners) {
            listener.scanningFile(fileName, status);
        }
    }

    public void fireJarRecorded(String jarName) {
        for (InventoryEventListener listener : listeners) {
            listener.jarRecorded(jarName);
        }
    }

    public void fireClassRecorded(String className) {
        for (InventoryEventListener listener : listeners) {
            listener.classRecorded(className.replace('/',  '.'));
        }
    }


    public void fireClassUsed(String className, String byFile) {
        for (InventoryEventListener listener : listeners) {
            listener.classUsed(className.replace('/',  '.'), byFile);
        }
    }
    
    public void fireMethodUsed(String className, String methodName, String description, String byFile) {
        for (InventoryEventListener listener : listeners) {
            listener.methodUsed(className.replace('/',  '.'), methodName, description, byFile);
        }
    }
    
    public void fireMemberUsed(String className, String memberName, String byFile) {
        for (InventoryEventListener listener : listeners) {
            listener.memberUsed(className.replace('/',  '.'), memberName, byFile);
        }
    }

    public void fireFailure(String info) {
        for (InventoryEventListener listener : listeners) {
            listener.failure(info);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
