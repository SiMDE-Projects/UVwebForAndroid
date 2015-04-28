package fr.utc.assos.uvweb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UvDetailComment implements Parcelable {
    @SerializedName("identity")
    private String author;
    private int globalRate;
    private String date;
    private String semester;
    private boolean passed;
    private String comment;

    public UvDetailComment(Parcel in) {
        author = in.readString();
        globalRate = in.readInt();
        date = in.readString();
        semester = in.readString();
        passed = in.readInt() == 1;
        comment = in.readString();
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

    public boolean isPassed() {
        return passed;
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
        dest.writeInt(globalRate);
        dest.writeString(date);
        dest.writeString(semester);
        dest.writeInt(passed ? 1 : 0);
        dest.writeString(comment);
    }

    public static final Parcelable.Creator<UvDetailComment> CREATOR = new Parcelable.Creator<UvDetailComment>() {
        public UvDetailComment createFromParcel(Parcel in) {
            return new UvDetailComment(in);
        }

        public UvDetailComment[] newArray(int size) {
            return new UvDetailComment[size];
        }
    };
}
