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

public interface InventoryEventListener {

    void scanningWar(String warName, ScanStatus status);

    void scanningJar(String jarName, ScanStatus status);

    void scanningFile(String fileName, ScanStatus status);

    void jarRecorded(String jarName);

    void classRecorded(String className);

    void classUsed(String className, String byFile);
    
    void methodUsed(String className, String methodName, String signature, String byFile);
    
    void memberUsed(String className, String memberName, String byFile);

    void failure(String info);

}
