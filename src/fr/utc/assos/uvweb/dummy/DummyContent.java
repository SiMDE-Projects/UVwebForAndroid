package fr.utc.assos.uvweb.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	static {
		// Add sample items.
		addItem(new DummyItem("1", "MT23"));
		addItem(new DummyItem("2", "CM11"));
		addItem(new DummyItem("3", "PS91"));
		addItem(new DummyItem("4", "PS04"));
		addItem(new DummyItem("5", "NF16"));
		addItem(new DummyItem("6", "HE03"));
		addItem(new DummyItem("7", "MT91"));
		addItem(new DummyItem("8", "IA01"));
		addItem(new DummyItem("9", "MI01"));
		addItem(new DummyItem("10", "SY01"));
		addItem(new DummyItem("11", "LO21"));
		addItem(new DummyItem("12", "SI07"));
		addItem(new DummyItem("13", "SC22"));
		addItem(new DummyItem("14", "TN04"));
		addItem(new DummyItem("15", "AR03"));
		addItem(new DummyItem("16", "CM12"));
		addItem(new DummyItem("17", "BL01"));
		addItem(new DummyItem("18", "NF17"));
		addItem(new DummyItem("19", "BL09"));
		addItem(new DummyItem("20", "MT12"));
	}

	private static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		public String id;
		public String content;

		public DummyItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
