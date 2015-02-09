package com.ziliang.CrimalIntent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Kurt on 2/9/2015.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

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
    @Override
    public String toString(){
        return mTitle;
    }
}
