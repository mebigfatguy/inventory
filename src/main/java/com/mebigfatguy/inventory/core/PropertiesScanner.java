package com.mebigfatguy.inventory.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesScanner implements ArchiveScanner {

	@Override
	public void scan(String name, Inventory inventory) throws IOException {
		try (InputStream is = inventory.getStream()) {
			Properties p = new Properties();
			p.load(is);
			InventoryEventFirer f = inventory.getEventFirer();
			for (Object n : p.values()) {
				String clsName = n.toString();
				f.fireClassUsed(clsName, name);
			}
		}
	}
}
