package fr.utc.assos.uvweb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {
    @SerializedName("identity")
    private String author;
    private int globalRate;
    private String date;
    private String semester;
    private String passed;
    private String comment;
    @SerializedName("name")
    private String uvName;

    public Comment(Parcel in) {
        author = in.readString();
        globalRate = in.readInt();
        date = in.readString();
        semester = in.readString();
        passed = in.readString();
        comment = in.readString();
        uvName = in.readString();
    }


    public String getAuthor() {
        return author;
    }

    public int getGlobalRate() {
        return globalRate;
    }

    public String getDate() {
        return date;
    }

    public String getSemester() {
        return semester;
    }

    public String getPassed() {
        return passed;
    }

    public String getComment() {
        return comment;
    }

    public String getUvName() {
        return uvName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeInt(globalRate);
        dest.writeString(date);
        dest.writeString(semester);
        dest.writeString(passed);
        dest.writeString(comment);
        dest.writeString(uvName);
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
