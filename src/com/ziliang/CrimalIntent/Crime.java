package com.ziliang.CrimalIntent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * single crime object
 * Created by Kurt on 2/9/2015.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Photo mPhoto;
    private String mSuspect;
    private String suspectNumber;
    private boolean mSolved;
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_SUSPECT = "suspect";
    private static final String JSON_SUSPECT_NUMBER = "suspect number";


    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    //set crime title
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    //set the date when the crime happens
    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    //set if the crime has been resolved
    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    //set suspect name
    public void setmSuspect(String suspect) {
        mSuspect = suspect;
    }

    //set suspect phone number
    public void setSuspectNumber(String number) {
        suspectNumber = number;
    }

    //set the photo of crime scene
    public void setmPhoto(Photo p) {
        mPhoto = p;
    }

    //get crime unique id
    public UUID getmId() {
        return mId;
    }

    //get title of the crime
    public String getmTitle() {
        return mTitle;
    }


    //get the date when the crime happens
    public Date getmDate() {
        return mDate;
    }

    //get if the crime has been resolved
    public boolean ismSolved() {
        return mSolved;
    }

    //get suspect name
    public String getmSuspect() {
        return mSuspect;
    }

    //get suspect number
    public String getSuspectNumber() {
        return suspectNumber;
    }

    //get photo of the crime scene
    public Photo getmPhoto() {
        return mPhoto;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    //serialize current crime object to a json object
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_SUSPECT, mSuspect);
        json.put(JSON_SUSPECT_NUMBER, suspectNumber);
        if (mPhoto != null) {
            json.put(JSON_PHOTO, mPhoto.toJSON());
        }
        return json;
    }

    //deserialize a json object to a crime object
    public Crime(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE)) {
            mTitle = json.getString(JSON_TITLE);
        }
        if (json.has(JSON_PHOTO)) {
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
        }
        if (json.has(JSON_SUSPECT)) {
            mSuspect = json.getString(JSON_SUSPECT);
        }
        if (json.has(JSON_SUSPECT_NUMBER)) {
            suspectNumber = json.getString(JSON_SUSPECT_NUMBER);
        }
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
    }

}
