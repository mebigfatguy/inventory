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
package com.mebigfatguy.inventory.ant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.tools.ant.Project;

import com.mebigfatguy.inventory.core.InventoryEventListener;
import com.mebigfatguy.inventory.core.ScanStatus;

public class AntEventLogger implements InventoryEventListener {

    private Project project;

    public AntEventLogger(Project p) {
        project = p;
    }

    @Override
    public void scanningWar(String warName, ScanStatus status) {
        project.log(status + " Scanning War: " + warName);
    }

    @Override
    public void scanningJar(String jarName, ScanStatus status) {
        project.log(status + " Scanning Jar: " + jarName);
    }

    @Override
    public void scanningFile(String fileName, ScanStatus status) {
        project.log(status + " Scanning File: " + fileName);
    }

    @Override
    public void jarRecorded(String jarName) {
        project.log("Recorded Jar: " + jarName);
    }

    @Override
    public void classRecorded(String className) {
        project.log("Recorded Class: " + className);
    }

    @Override
    public void jarUsed(String jarName, String byFile) {
        project.log(jarName + "jar used by: " + byFile);
    }

    @Override
    public void classUsed(String className, String byFile) {
        project.log(className + " class used by: " + byFile);
    }

    @Override
    public void failure(String info) {
        project.log("ERROR: " + info, Project.MSG_ERR);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
