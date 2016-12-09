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
package com.mebigfatguy.inventory.cls;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.mebigfatguy.inventory.core.Inventory;

public class MethodInventoryVisitor extends MethodVisitor {

    private Inventory inventory;
    private String owningClass;

    public MethodInventoryVisitor(String clsName, Inventory inventory) {
        super(Opcodes.ASM5);
        this.owningClass = clsName;
        this.inventory = inventory;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof Type) {
            Type t = (Type) cst;

            int sort = t.getSort();
            if ((sort == Type.OBJECT) || (sort == Type.ARRAY) || (sort == Type.METHOD)) {
                String clsName = t.getClassName();
                inventory.getEventFirer().firePackageUsed(clsName, owningClass);
            }
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
