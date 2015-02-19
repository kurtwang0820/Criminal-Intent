package com.ziliang.CrimalIntent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Kurt on 2/9/2015.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;
    private CrimeLab(Context appContext){
        mAppContext=appContext;
        mSerializer=new CriminalIntentJSONSerializer(mAppContext,FILENAME);
        try{
            mCrimes=mSerializer.loadCrimes();
        }catch (Exception e){
            mCrimes=new ArrayList<Crime>();
            Log.e(TAG,"Error loading crimes: ",e);
        }
//        for(int i=0;i<100;i++){
//            Crime c=new Crime();
//            c.setmTitle("Crime #"+i);
//            c.setmSolved(i%2==0);
//            mCrimes.add(c);
//        }
    }
    public ArrayList<Crime> getmCrimes(){
        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for(Crime c:mCrimes){
            if(c.getmId().equals(id)){
                return c;
            }
        }
        return null;
    }
    public static CrimeLab get(Context c){
        if(sCrimeLab==null){
            sCrimeLab=new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }
    public void addCrime(Crime c){
        mCrimes.add(c);
    }
    private static final String TAG="CrimeLab";
    private static final String FILENAME="crimes.json";
    private CriminalIntentJSONSerializer mSerializer;
    public boolean saveCrimes(){
        try{
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        }catch(Exception e){
            Log.e(TAG,"Error saving crimes: ",e);
            return false;
        }
    }
    public void deleteCrime(Crime c){
        mCrimes.remove(c);
    }
}
