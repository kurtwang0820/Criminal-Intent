package com.ziliang.CrimalIntent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * photo object
 * Created by Kurt on 3/4/2015.
 */
public class Photo {
    private static final String JSON_FILENAME="filename";
    private String mFilename;
    public Photo(String filename){
        this.mFilename=filename;
    }
    //get photo file name from a json object
    public Photo(JSONObject json) throws JSONException{
        mFilename=json.getString(JSON_FILENAME);
    }
    //serialize the photo file name to json
    public JSONObject toJSON() throws JSONException{
        JSONObject json=new JSONObject();
        json.put(JSON_FILENAME,mFilename);
        return json;
    }
    //get file name
    public String getFilename(){
        return mFilename;
    }
}
