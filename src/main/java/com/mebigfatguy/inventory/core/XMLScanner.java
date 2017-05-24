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

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.mebigfatguy.inventory.xml.InventoryNamespaceContext;

public class XMLScanner implements ArchiveScanner {

    private static DocumentBuilderFactory documentBuilderFactory;
    private static XPathExpression beanXPathExpression;

    private static XPathExpression servletXPathExpression;
    private static XPathExpression listenerXPathExpression;

    static {
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            xpath.setNamespaceContext(new InventoryNamespaceContext());

            beanXPathExpression = xpath.compile(InventoryNamespaceContext.springQualified("beans", "bean"));
            servletXPathExpression = xpath.compile(InventoryNamespaceContext.webXmlQualified("web-app", "servlet", "servlet-class") + "/text()");
            listenerXPathExpression = xpath.compile(InventoryNamespaceContext.webXmlQualified("web-app", "listener", "listener-class") + "/text()");
        } catch (XPathExpressionException e) {
            throw new Error(e);
        }
    }

    @Override
    public void scan(String name, Inventory inventory) throws IOException {
        try (InputStream is = inventory.getStream()) {
            DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
            Document document = db.parse(is);

            scanSpringBeans(document, name, inventory);
            scanWebXml(document, name, inventory);

        } catch (ParserConfigurationException | SAXException | XPathExpressionException e) {
            throw new IOException("Failed to parse xml file: " + name, e);
        }
    }

    private void scanSpringBeans(Document document, String name, Inventory inventory) throws XPathExpressionException {
        NodeList beans = (NodeList) beanXPathExpression.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < beans.getLength(); i++) {
            Element bean = (Element) beans.item(i);
            String clsName = bean.getAttribute("class");
            if ((clsName != null) && !clsName.isEmpty()) {
                inventory.getEventFirer().fireClassUsed(clsName, name);
            }
        }
    }

    private void scanWebXml(Document document, String name, Inventory inventory) throws XPathExpressionException {
        NodeList servlets = (NodeList) servletXPathExpression.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < servlets.getLength(); i++) {
            Text classNode = (Text) servlets.item(i);
            if (classNode != null) {
                String clsName = classNode.getTextContent();
                if (!clsName.isEmpty()) {
                    inventory.getEventFirer().fireClassUsed(clsName, name);
                }
            }
        }

        NodeList listeners = (NodeList) listenerXPathExpression.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < listeners.getLength(); i++) {
            Text classNode = (Text) listeners.item(i);
            if (classNode != null) {
                String clsName = classNode.getTextContent();
                if (!clsName.isEmpty()) {
                    inventory.getEventFirer().fireClassUsed(clsName, name);
                }
            }
        }

    }
}
