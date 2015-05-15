package fr.utc.assos.uvweb.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Poll implements Parcelable {
    private float successRate;
    private int year;
    private String season;

    public Poll(Parcel in) {
        successRate = in.readFloat();
        year = in.readInt();
        season = in.readString();
    }

    public float getSuccessRate() {
        return successRate;
    }

    public int getYear() {
        return year;
    }

    public String getSeason() {
        return season;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(successRate);
        dest.writeInt(year);
        dest.writeString(season);
    }

    public static final Parcelable.Creator<Poll> CREATOR = new Parcelable.Creator<Poll>() {
        public Poll createFromParcel(Parcel in) {
            return new Poll(in);
        }

        public Poll[] newArray(int size) {
            return new Poll[size];
        }
    };
}
