package fr.utc.assos.uvweb.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UvListItem implements Parcelable {
    private String name;
    private String title;
    private String globalRate;
    private int commentCount;

    public UvListItem(Parcel in) {
        name = in.readString();
        title = in.readString();
        globalRate = in.readString();
        commentCount = in.readInt();
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getGlobalRate() {
        return globalRate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(globalRate);
        dest.writeInt(commentCount);
    }

    public static final Parcelable.Creator<UvListItem> CREATOR = new Parcelable.Creator<UvListItem>() {
        public UvListItem createFromParcel(Parcel in) {
            return new UvListItem(in);
        }

        public UvListItem[] newArray(int size) {
            return new UvListItem[size];
        }
    };
}
