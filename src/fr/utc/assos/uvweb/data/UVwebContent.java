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
		addItem(new UV("MT23", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("CM11", "Algèbre linéaire", 2.7, 56.89));
		addItem(new UV("PS91", "Algèbre linéaire", 4.5, 56.89));
		addItem(new UV("PS04", "Algèbre linéaire", 5.2, 56.89));
		addItem(new UV("NF16", "Algèbre linéaire", 6.45, 56.89));
		addItem(new UV("HE03", "Algèbre linéaire", 7.97, 56.89));
		addItem(new UV("MT91", "Algèbre linéaire", 8.34, 56.89));
		addItem(new UV("IA01", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("MI01", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("SY01", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("LO21", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("SI07", "Algèbre linéaire", 8, 56.89));
		addItem(new UV("SC22", "Algèbre linéaire", 8, 100));
		addItem(new UV("TN04", "Algèbre linéaire", 8, 100));
		addItem(new UV("AR03", "Algèbre linéaire", 8, 100));
		addItem(new UV("CM12", "Algèbre linéaire", 8, 100));
		addItem(new UV("BL01", "Algèbre linéaire", 8, 100));
		addItem(new UV("NF17", "Algèbre linéaire", 8, 100));
		addItem(new UV("BL09", "Algèbre linéaire", 8, 100));
		addItem(new UV("MT12", "Algèbre linéaire", 8, 100));
	}

	public static void addItem(UV item) {
		UVS.add(item);
		UV_MAP.put(item.getName(), item);
	}

	/**
	 * A UV representing a piece of content.
	 */
	public static class UV {
        private String mName;
        private String mDescription;
        private double mRate;
        private double mSuccessRate;

		public UV(String name, String description, double rate, double successRate) {
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
