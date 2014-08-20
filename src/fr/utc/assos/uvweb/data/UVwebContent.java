package fr.utc.assos.uvweb.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.List;

import fr.utc.assos.uvweb.util.DateUtils;

/**
 * Helper class for providing UV content for user interfaces
 */
// TODO: Split data into several files
public class UVwebContent {
	/**
	 * UV title format pattern, consistent across the application.
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
	 * A UV representing a piece of content.
	 */
	public static class UV implements Comparable<UV>, Parcelable {
		public static final Parcelable.Creator<UV> CREATOR = new Parcelable.Creator<UV>() {
			public UV createFromParcel(Parcel in) {
				return new UV(in);
			}

			public UV[] newArray(int size) {
				return new UV[size];
			}
		};
		private String mName;
		private String mDescription;
		private double mRate;
		private double mSuccessRate;

		public UV(String name, String description) {
			mName = name;
			mDescription = description;
		}

		protected UV(Parcel in) {
			mName = in.readString();
			mDescription = in.readString();
			mRate = in.readDouble();
			mSuccessRate = in.readDouble();
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
			final int size = mName.length();
			return mName.substring(size - 2, size);
		}

		public String getFormattedSuccessRate() {
			return sDecimalFormat.format(mSuccessRate) + "%";
		}

		public String getFormattedRate() {
			return sDecimalFormat.format(mRate) + "/10";
		}

		@Override
		public int compareTo(UV uv) {
			return mName.compareTo(uv.mName);
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeString(mName);
			parcel.writeString(mDescription);
			parcel.writeDouble(mRate);
			parcel.writeDouble(mSuccessRate);
		}
	}

    public static class UVDetailData {
        private List<UVComment> mComments;
        private float mAverageRate;
        private List<UVPoll> mPolls;

        public UVDetailData(List<UVComment> comments, float averageRate, List<UVPoll> polls) {
            mComments = comments;
            mAverageRate = averageRate;
            mPolls = polls;
        }

        public List<UVComment> getComments() {
            return mComments;
        }

        public void setComments(List<UVComment> comments) {
            mComments = comments;
        }

        public float getAverageRate() {
            return mAverageRate;
        }

        public void setAverageRate(float averageRate) {
            mAverageRate = averageRate;
        }

        public List<UVPoll> getPolls() {
            return mPolls;
        }

        public void setPolls(List<UVPoll> polls) {
            mPolls = polls;
        }
    }

    public static class UVPoll {
        private float mSuccessRate;
        private int mYear;
        private String mSeason;

        public UVPoll(float successRate, int year, String season) {
            mSuccessRate = successRate;
            mYear = year;
            mSeason = season;
        }

        public float getSuccessRate() {
            return mSuccessRate;
        }

        public void setSuccessRate(float successRate) {
            mSuccessRate = successRate;
        }

        public int getYear() {
            return mYear;
        }

        public void setYear(int year) {
            mYear = year;
        }

        public String getSeason() {
            return mSeason;
        }

        public void setSeason(String season) {
            mSeason = season;
        }
    }

	public static class UVComment implements Comparable<UVComment>, Parcelable {
		public static final Parcelable.Creator<UVComment> CREATOR = new Parcelable.Creator<UVComment>() {
			public UVComment createFromParcel(Parcel in) {
				return new UVComment(in);
			}

			public UVComment[] newArray(int size) {
				return new UVComment[size];
			}
		};
		private User mAuthor;
		private DateTime mDate;
		private String mComment;
		private int mGlobalRate;
		private String mSemester;

		public UVComment(String author, String email, DateTime date, String comment, int globalRate, String semester) {
			mAuthor = new User(author, email);
			mDate = date;
			mComment = comment;
			mGlobalRate = globalRate;
			mSemester = semester;
		}

		public UVComment(String author, String email, String date, String comment, int globalRate, String semester) {
			this(author, email, DateUtils.getDateFromString(date), comment, globalRate, semester);
		}


		protected UVComment(Parcel in) {
			mAuthor = in.readParcelable(User.class.getClassLoader());
			mDate = DateUtils.getDateFromString(in.readString());
			mComment = in.readString();
			mGlobalRate = in.readInt();
			mSemester = in.readString();
		}

		public User getAuthor() {
			return mAuthor;
		}

		public void setAuthor(User author) {
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

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeParcelable(mAuthor, flags);
			parcel.writeString(DateUtils.getFormattedDate(mDate));
			parcel.writeString(mComment);
			parcel.writeInt(mGlobalRate);
			parcel.writeString(mSemester);
		}

		@Override
		public int compareTo(UVComment uvComment) {
			return mDate.compareTo(uvComment.mDate);
		}
	}

	public static class NewsFeedEntry implements Comparable<NewsFeedEntry>, Parcelable {
		public static final Parcelable.Creator<NewsFeedEntry> CREATOR = new Parcelable.Creator<NewsFeedEntry>() {
			public NewsFeedEntry createFromParcel(Parcel in) {
				return new NewsFeedEntry(in);
			}

			public NewsFeedEntry[] newArray(int size) {
				return new NewsFeedEntry[size];
			}
		};
		private User mOwner;
		private DateTime mDate;
		private String mComment;
		private String mAction;

		public NewsFeedEntry(String author, String email, DateTime date, String comment, String action) {
			mOwner = new User(author, email);
			mDate = date;
			mComment = comment;
			mAction = action;
		}

		public NewsFeedEntry(String author, String gravatarHash, String date, String comment, String action) {
			this(author, gravatarHash, DateUtils.getDateFromString(date), comment, action);
		}

		protected NewsFeedEntry(Parcel in) {
			mOwner = in.readParcelable(User.class.getClassLoader());
			mDate = DateUtils.getDateFromString(in.readString());
			mComment = in.readString();
			mAction = in.readString();
		}

		public User getOwner() {
			return mOwner;
		}

		public void setOwner(User owner) {
			mOwner = owner;
		}

		public String getAction() {
			return mAction;
		}

		public void setAction(String action) {
			mAction = action;
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

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeParcelable(mOwner, flags);
			parcel.writeString(DateUtils.getFormattedDate(mDate));
			parcel.writeString(mComment);
			parcel.writeString(mAction);
		}

		@Override
		public int compareTo(NewsFeedEntry newsFeedEntry) {
			return mDate.compareTo(newsFeedEntry.mDate);
		}
	}

	public static class User implements Parcelable {
		public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
			public User createFromParcel(Parcel in) {
				return new User(in);
			}

			public User[] newArray(int size) {
				return new User[size];
			}
		};
		private final String mName;
		private final String mEmail;

		public User(String name, String email) {
			mName = name;
			mEmail = email;
		}

		protected User(Parcel in) {
			mName = in.readString();
			mEmail = in.readString();
		}

		public String getName() {
			return mName;
		}

		public String getEmail() {
			return mEmail;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeString(mName);
			parcel.writeString(mEmail);
		}
	}
}
