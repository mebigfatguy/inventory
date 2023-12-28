package com.mebigfatguy.inventory.core;

import java.util.Map;
import java.util.Set;

public interface InventoryReport {

    Map<String, Set<String>> getJarInventory();

    Map<String, Set<String>> getPackagedUsed();
    
    Map<String, Set<MethodDesc>> getMethodsUsed();
    
    Map<String, Set<MemberDesc>> getMembersUsed();
}
