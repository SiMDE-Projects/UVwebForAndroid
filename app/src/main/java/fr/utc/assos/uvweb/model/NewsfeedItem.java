package fr.utc.assos.uvweb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NewsfeedItem implements Parcelable {
    @SerializedName("identity")
    private String author;
    private String date;
    @SerializedName("name")
    private String uvName;
    private int globalRate;
    private String comment;

    public NewsfeedItem(Parcel in) {
        author = in.readString();
        date = in.readString();
        uvName = in.readString();
        globalRate = in.readInt();
        comment = in.readString();
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUvName() {
        return uvName;
    }

    public int getGlobalRate() {
        return globalRate;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(uvName);
        dest.writeInt(globalRate);
        dest.writeString(comment);
    }

    public static final Parcelable.Creator<NewsfeedItem> CREATOR = new Parcelable.Creator<NewsfeedItem>() {
        public NewsfeedItem createFromParcel(Parcel in) {
            return new NewsfeedItem(in);
        }

        public NewsfeedItem[] newArray(int size) {
            return new NewsfeedItem[size];
        }
    };
}
