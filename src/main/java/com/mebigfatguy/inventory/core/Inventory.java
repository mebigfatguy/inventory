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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Inventory {

    private File archive;
    private Set<InventoryEventListener> listeners;

    public Inventory(File archive) {
        this.archive = archive;
        listeners = new HashSet<>();
    }

    public void addInventoryEventListener(InventoryEventListener listener) {
        listeners.add(listener);
    }

    public void removeInventoryEventListener(InventoryEventListener listener) {
        listeners.remove(listener);
    }

    public void takeInventory() {
    }

}
