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
package com.mebigfatguy.inventory.ant;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.mebigfatguy.inventory.core.Inventory;

public class InventoryTask extends Task {

    private File archive;

    public void setEar(File earFile) {
        archive = earFile;
    }

    public void setWar(File warFile) {
        archive = warFile;
    }

    @Override
    public void execute() {

        if ((archive == null) || !archive.isFile()) {
            throw new BuildException("Property 'ear' or 'war' was not set or was invalid");
        }

        try (Inventory inventory = new Inventory(archive)) {

            inventory.addInventoryEventListener(new AntEventLogger(getProject()));

            inventory.takeInventory();
        } catch (Exception e) {
            throw new BuildException("Failed to take inventory of " + archive, e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
