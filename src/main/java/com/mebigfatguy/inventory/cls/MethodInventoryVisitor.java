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
package com.mebigfatguy.inventory.cls;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.mebigfatguy.inventory.core.Inventory;
import com.mebigfatguy.inventory.core.InventoryEventFirer;

public class MethodInventoryVisitor extends MethodVisitor {

    private Inventory inventory;
    private String owningClass;

    public MethodInventoryVisitor(String clsName, Inventory inventory) {
        super(Opcodes.ASM9);
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
                inventory.getEventFirer().fireClassUsed(clsName, owningClass);
            }
        }
    }

    @Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		
    	InventoryEventFirer firer = inventory.getEventFirer();
    	firer.fireClassUsed(owner, owningClass);
    	firer.fireMethodUsed(owner, name, descriptor, owningClass);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
    	InventoryEventFirer firer = inventory.getEventFirer();
    	firer.fireClassUsed(owner, owningClass);
    	firer.fireMemberUsed(owner, name, owningClass);
	}
	
	

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
    	InventoryEventFirer firer = inventory.getEventFirer();
    	firer.fireClassUsed(descriptor, owningClass);
    	return null;
	}

	@Override
	public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end,
			int index) {
    	InventoryEventFirer firer = inventory.getEventFirer();
    	firer.fireClassUsed(descriptor, owningClass);
	}

	@Override
	public void visitParameter(String name, int access) {
    	InventoryEventFirer firer = inventory.getEventFirer();
    	firer.fireClassUsed(name, owningClass);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
    	InventoryEventFirer firer = inventory.getEventFirer();
    	firer.fireClassUsed(type, owningClass);
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
