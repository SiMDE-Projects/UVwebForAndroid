package fr.utc.assos.uvweb.data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing UV content for user interfaces
 */
public class UVwebContent {

	/**
	 * An array of UVs.
	 */
    public static List<UV> UVS = new ArrayList<UV>();

	/**
	 * A map of UVs, by ID.
	 */
	public static Map<String, UV> UV_MAP = new HashMap<String, UV>();

	static {
		// Add sample items.
		addItem(new UV("1", "MT23", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("2", "CM11", "Algèbre linéaire", 2.7, 56.89));
		addItem(new UV("3", "PS91", "Algèbre linéaire", 4.5, 56.89));
		addItem(new UV("4", "PS04", "Algèbre linéaire", 5.2, 56.89));
		addItem(new UV("5", "NF16", "Algèbre linéaire", 6.45, 56.89));
		addItem(new UV("6", "HE03", "Algèbre linéaire", 7.97, 56.89));
		addItem(new UV("7", "MT91", "Algèbre linéaire", 8.34, 56.89));
		addItem(new UV("8", "IA01", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("9", "MI01", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("10", "SY01", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("11", "LO21", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("12", "SI07", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("13", "SC22", "Algèbre linéaire", 8, 100));
		addItem(new UV("14", "TN04", "Algèbre linéaire", 8, 100));
		addItem(new UV("15", "AR03", "Algèbre linéaire", 8, 100));
		addItem(new UV("16", "CM12", "Algèbre linéaire", 8, 100));
		addItem(new UV("17", "BL01", "Algèbre linéaire", 8, 100));
		addItem(new UV("18", "NF17", "Algèbre linéaire", 8, 100));
		addItem(new UV("19", "BL09", "Algèbre linéaire", 8, 100));
		addItem(new UV("20", "MT12", "Algèbre linéaire", 8, 100));
	}

	public static void addItem(UV item) {
		UVS.add(item);
		UV_MAP.put(item.getId(), item);
	}

	/**
	 * A UV representing a piece of content.
	 */
	public static class UV {
		private String mId;
        private String mName;
        private String mDescription;
        private double mRate;
        private double mSuccessRate;

		public UV(String id, String name, String description, double rate, double successRate) {
			this.mId = id;
            this.mName = name;
			this.mDescription = description;
            this.mRate = rate;
            this.mSuccessRate = successRate;

            //addItem(this);
		}

		@Override
		public String toString() {
			return mName;
		}

        // Getters
        public String getId() {
            return this.mId;
        }

        public String getName() {
            return this.mName;
        }

        public String getDescription() {
            return this.mDescription;
        }

        public double getRate() {
            return this.mRate;
        }

        public double getSuccessRate() {
            return mSuccessRate;
        }

        // Setters
        public void setName(String name) {
            this.mName = name;
        }

        public void setDescription(String description) {
            this.mDescription = description;
        }

        public void setRate(double rate) {
            this.mRate = rate;
        }

        public void setSuccessRate(double successRate) {
            this.mSuccessRate = successRate;
        }

        // Public methods
        public String getLetterCode(){
            return mName.substring(0,2);
        }
        public String getNumberCode(){
            return mName.substring(2,4);
        }
        public String getFormattedSuccessRate(){
            return (new DecimalFormat("0.0")).format(mSuccessRate) + "%";
        }
        public String getFormattedRate(){
            return (new DecimalFormat("0")).format(mRate) + "/10";
        }
	}
}
