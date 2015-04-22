package com.ziliang.CrimalIntent;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;

/**
 * JSON serializer which can serialize a list of crimes object to json
 * Created by Kurt on 2015/2/19.
 */
public class CriminalIntentJSONSerializer {
    private Context mContext;
    private String mFilename;
    public CriminalIntentJSONSerializer(Context c,String f){
        mContext=c;
        mFilename=f;
    }

    //save a list of crimes to a file
    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,IOException{
        JSONArray array=new JSONArray();
        for(Crime c:crimes){
            array.put(c.toJSON());
        }
        Writer writer=null;
        try{
            OutputStream out=mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
            writer=new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if(writer!=null){
                writer.close();
            }
        }
    }

    //load a list of crimes from file
    public ArrayList<Crime> loadCrimes() throws IOException,JSONException{
        ArrayList<Crime> crimes=new ArrayList<Crime>();
        BufferedReader reader=null;
        try{
            InputStream in=mContext.openFileInput(mFilename);
            reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString=new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null){
                jsonString.append(line);
            }
            JSONArray array=(JSONArray)new JSONTokener(jsonString.toString()).nextValue();
            for(int i=0;i<array.length();i++){
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        }catch(FileNotFoundException e){

        }finally{
            if(reader!=null){
                reader.close();
            }
        }
        return crimes;
    }
}
