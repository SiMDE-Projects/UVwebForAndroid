package fr.utc.assos.uvweb.data;

import fr.utc.assos.uvweb.util.DateUtils;
import org.joda.time.DateTime;

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
	 * UV title format pattern, consistent accross the application.
	 */
	public static final String UV_TITLE_FORMAT = "<font color='#000000'>%1$s</font>%2$s";
	public static final String UV_TITLE_FORMAT_LIGHT = "<font color='#ffffff'>%1$s</font>%2$s";
	public static final String NEWSFEED_ACTION_FORMAT = "<b>%1$s</b><font color='#000000'>%2$s</font>";
	public static final String UV_SUCCESS_RATE_FORMAT = "%1$s<b>%2$s</b>";
	/**
	 * Used to format grades.
	 */
	private static final DecimalFormat sDecimalFormat = new DecimalFormat("0");
	/**
	 * An array of UVs.
	 */
	public static List<UV> UVS = new ArrayList<UV>();
	/**
	 * A map of UVs, by ID.
	 */
	public static Map<String, UV> UV_MAP = new HashMap<String, UV>();
	/**
	 * An array of comments.
	 */
	public static ArrayList<UVComment> COMMENTS = new ArrayList<UVComment>();
	/**
	 * An array of news feed entries.
	 */
	public static ArrayList<NewsFeedEntry> NEWS_ENTRIES = new ArrayList<NewsFeedEntry>();

	public static void addItem(UV item) {
		UVS.add(item);
		UV_MAP.put(item.getName(), item);
	}

	public static void addComment(UVComment comment) {
		COMMENTS.add(comment);
	}

	public static void addNewsFeedEntry(NewsFeedEntry entry) {
		NEWS_ENTRIES.add(entry);
	}

	static {
		final String newsFeedEntryString = "You think water moves fast? You should see ice. It moves like it has a mind. " +
				"Like it knows it killed the world once and got a taste for murder. After the avalanche, it took us " +
				"a week to climb out. Now, I don't know exactly when we turned on each other, but I know that seven of" +
				" us survived the slide... and only five made it out. Now we took an oath, that I'm breaking now." +
				" We said we'd say it was the snow that killed the other two, but it wasn't. Nature is lethal but" +
				" it doesn't hold a candle to man.";
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2013, 04, 20, 17, 49, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("tkeunebr", new DateTime(2013, 04, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2013, 04, 20, 9, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2013, 04, 19, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2013, 04, 17, 11, 3, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("tkeunebr", new DateTime(2013, 04, 3, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("tkeunebr", new DateTime(2013, 03, 26, 22, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2013, 01, 2, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("tkeunebr", new DateTime(2012, 11, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2012, 07, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("tkeunebr", new DateTime(2011, 04, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2013, 04, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2013, 04, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("tkeunebr", new DateTime(2013, 04, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("tkeunebr", new DateTime(2013, 04, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("amasciul", new DateTime(2013, 04, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
		addNewsFeedEntry(new NewsFeedEntry("tkeunebr", new DateTime(2013, 04, 20, 14, 20, 12), newsFeedEntryString,
				"a publié un nouveau commentaire"));
	}

	/**
	 * A UV representing a piece of content.
	 */
	public static class UV implements Comparable<UV> {
		private String mName;
		private String mDescription;
		private double mRate;
		private double mSuccessRate;

		public UV(String name, String description, double rate, double successRate) {
			mName = name;
			mDescription = description;
			mRate = rate;
			mSuccessRate = successRate;
		}

		@Override
		public String toString() {
			return mName;
		}

		public String getName() {
			return mName;
		}

		public void setName(String name) {
			mName = name;
		}

		public String getDescription() {
			return mDescription;
		}

		public void setDescription(String description) {
			mDescription = description;
		}

		public double getRate() {
			return mRate;
		}

		public void setRate(double rate) {
			mRate = rate;
		}

		public double getSuccessRate() {
			return mSuccessRate;
		}

		public void setSuccessRate(double successRate) {
			mSuccessRate = successRate;
		}

		public String getLetterCode() {
			return mName.substring(0, 2);
		}

		public String getNumberCode() {
			return mName.substring(2, 4);
		}

		public String getFormattedSuccessRate() {
			return sDecimalFormat.format(mSuccessRate) + "%";
		}

		public String getFormattedRate() {
			return sDecimalFormat.format(mRate) + "/10";
		}

		@Override
		public int compareTo(UV uv) {
			return mName.compareTo(uv.getName());
		}
	}

	public static class UVComment {
		private String mAuthor;
		private DateTime mDate;
		private String mComment;
		private int mGlobalRate;
		private String mSemester;

		public UVComment(String author, DateTime date, String comment, int globalRate, String semester) {
			mAuthor = author;
			mDate = date;
			mComment = comment;
			mGlobalRate = globalRate;
			mSemester = semester;
		}

		public UVComment(String author, String date, String comment, int globalRate, String semester) {
			this(author, DateUtils.getDateFromString(date), comment, globalRate, semester);
		}

		public String getAuthor() {
			return mAuthor;
		}

		public void setAuthor(String author) {
			mAuthor = author;
		}

		public DateTime getDate() {
			return mDate;
		}

		public void setDate(DateTime date) {
			mDate = date;
		}

		public String getComment() {
			return mComment;
		}

		public void setComment(String comment) {
			mComment = comment;
		}

		public int getGlobalRate() {
			return mGlobalRate;
		}

		public void setGlobalRate(int globalRate) {
			mGlobalRate = globalRate;
		}

		public String getSemester() {
			return mSemester;
		}

		public void setSemester(String semester) {
			mSemester = semester;
		}

		public String getFormattedRate() {
			return sDecimalFormat.format(mGlobalRate) + "/10";
		}

		public String getFormattedDate() {
			return DateUtils.getFormattedDate(mDate);
		}
	}

	public static class NewsFeedEntry {
		private String mAuthor;
		private DateTime mDate;
		private String mComment;
		private String mAction;

		public NewsFeedEntry(String author, DateTime date, String comment, String action) {
			mAuthor = author;
			mDate = date;
			mComment = comment;
			mAction = action;
		}

		public NewsFeedEntry(String author, String date, String comment, String action) {
			this(author, DateUtils.getDateFromString(date), comment, action);
		}

		public String getAuthor() {
			return mAuthor;
		}

		public void setAuthor(String author) {
			mAuthor = author;
		}

		public String getAction() {
			return mAction;
		}

		public DateTime getDate() {
			return mDate;
		}

		public void setDate(DateTime date) {
			mDate = date;
		}

		public String getComment() {
			return mComment;
		}

		public void setComment(String comment) {
			mComment = comment;
		}

		public String getTimeDifference() {
			return DateUtils.getFormattedTimeDifference(mDate, new DateTime());
		}
	}
}
