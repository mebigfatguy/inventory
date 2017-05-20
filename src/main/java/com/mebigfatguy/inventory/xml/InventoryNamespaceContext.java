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
package com.mebigfatguy.inventory.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

public class InventoryNamespaceContext implements NamespaceContext {

    private static final String SPRING_PREFIX = "spring";
    private static final String WEBXML_PREFIX = "webxml";

    private static Map<String, String> prefixToUrl = new HashMap<>();
    private static Map<String, String> urlToPrefix = new HashMap<>();
    static {
        prefixToUrl.put(SPRING_PREFIX, "http://www.springframework.org/schema/beans");
        prefixToUrl.put(WEBXML_PREFIX, "http://java.sun.com/xml/ns/javaee");
        urlToPrefix.put("http://www.springframework.org/schema/beans", SPRING_PREFIX);
        urlToPrefix.put("http://java.sun.com/xml/ns/javaee", WEBXML_PREFIX);
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return prefixToUrl.get(prefix);
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return urlToPrefix.get(namespaceURI);
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }
}
