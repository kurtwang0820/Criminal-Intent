package com.ziliang.CrimalIntent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Kurt on 2/9/2015.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Photo mPhoto;
    private boolean mSolved;
    private static final String JSON_ID="id";
    private static final String JSON_TITLE="title";
    private static final String JSON_SOLVED="solved";
    private static final String JSON_DATE="date";
    private static final String JSON_PHOTO="photo";
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public UUID getmId() {

        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Date getmDate() {

        return mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public Crime(){
        mId=UUID.randomUUID();
        mDate=new Date();
    }
    public Crime(JSONObject json)throws JSONException{
        mId=UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE)){
            mTitle=json.getString(JSON_TITLE);
        }
        mSolved=json.getBoolean(JSON_SOLVED);
        mDate=new Date(json.getLong(JSON_DATE));
    }
    @Override
    public String toString(){
        return mTitle;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json=new JSONObject();
        json.put(JSON_ID,mId.toString());
        json.put(JSON_TITLE,mTitle);
        json.put(JSON_SOLVED,mSolved);
        json.put(JSON_DATE,mDate.getTime());
        if(mPhoto!=null){
            json.put(JSON_PHOTO,mPhoto.toJSON());
        }
        return json;
    }
    public Photo getmPhoto(){
        return mPhoto;
    }
    public void setmPhoto(Photo p){
        mPhoto=p;
    }
}
