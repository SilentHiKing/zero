package com.hiking.zero.test.data;


import android.os.Parcel;
import android.os.Parcelable;

public class ColorItem implements Parcelable {

    public String name;
    public String hex;
    public int color;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.hex);
        dest.writeInt(this.color);
    }

    public ColorItem() {
    }

    protected ColorItem(Parcel in) {
        this.name = in.readString();
        this.hex = in.readString();
        this.color = in.readInt();
    }

    public static final Parcelable.Creator<ColorItem> CREATOR = new Parcelable.Creator<ColorItem>() {
        @Override
        public ColorItem createFromParcel(Parcel source) {
            return new ColorItem(source);
        }

        @Override
        public ColorItem[] newArray(int size) {
            return new ColorItem[size];
        }
    };
}
