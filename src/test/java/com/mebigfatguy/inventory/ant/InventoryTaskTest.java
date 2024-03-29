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

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;
import org.junit.jupiter.api.Test;

public class InventoryTaskTest {

    @Test
    public void testEar() {

        InventoryTask t = new InventoryTask();
        Project p = new Project();
        t.setProject(p);

        t.setEar(new File("./target/sample.ear"));

        p.addBuildListener(new DefaultBuildListener() {

            @Override
            public void messageLogged(BuildEvent event) {
                System.out.println(event.getMessage());
            }
        });

        t.execute();

    }
}
