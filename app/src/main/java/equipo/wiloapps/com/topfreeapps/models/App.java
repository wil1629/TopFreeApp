package equipo.wiloapps.com.topfreeapps.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WILO on 10/04/2016.
 */
public final class App implements Parcelable{

    private String mCategory;
    private String mName;

    private String mUrlImage;
    private String mNameImage;

    private String mLinkApp;
    private String mSummary;
    private String mRights;


    public App(){}

    public  App(String category, String name, String urlImage, String nameImage, String linkApp, String summary, String rights){
        mCategory = category;
        mName = name;
        mUrlImage = urlImage;
        mNameImage = nameImage;
        mLinkApp = linkApp;
        mSummary = summary;
        mRights = rights;
    }

    public App (Parcel in) {
        mCategory = in.readString ();
        mName = in.readString ();
        mUrlImage = in.readString ();
        mNameImage = in.readString ();
        mLinkApp = in.readString ();
        mSummary = in.readString ();
        mRights = in.readString ();
    }

    public static final Parcelable.Creator<App> CREATOR = new Parcelable.Creator<App>(){

        @Override
        public App createFromParcel (Parcel in) {
            return new App (in);
        }

        @Override
        public App[] newArray (int size) {
            return new App[size];
        }
    };

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest, int flags) {
        dest.writeString (mCategory);
        dest.writeString (mName);
        dest.writeString (mUrlImage);
        dest.writeString (mNameImage);
        dest.writeString (mLinkApp);
        dest.writeString (mSummary);
        dest.writeString (mRights);
    }

    //<editor-fold desc="Getters App">
    public String getCategory () {
        return mCategory;
    }

    public String getName () {
        return mName;
    }

    public String getUrlImage () {
        return mUrlImage;
    }

    public String getNameImage () {
        return mNameImage;
    }

    public String getLinkApp () {
        return mLinkApp;
    }

    public String getSummary () {
        return mSummary;
    }

    public String getRights () {
        return mRights;
    }
    //</editor-fold>


    //<editor-fold desc="Setters Apps, sin uso">
    public void setCategory (String category) {
        mCategory = category;
    }

    public void setName (String name) {
        mName = name;
    }

    public void setUrlImage (String urlImage) {
        mUrlImage = urlImage;
    }

    public void setNameImage (String nameImage) {
        mNameImage = nameImage;
    }

    public void setLinkApp (String linkApp) {
        mLinkApp = linkApp;
    }

    public void setSummary (String summary) {
        mSummary = summary;
    }

    public void setRights (String rights) {
        mRights = rights;
    }
    //</editor-fold>

}
