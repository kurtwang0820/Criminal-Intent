package com.ziliang.CrimalIntent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * a crime pool which keeps  all the crimes in record
 * Created by Kurt on 2/9/2015.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private static final String FILENAME = "crimes.json";
    private CriminalIntentJSONSerializer mSerializer;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;
    //private static final String TAG = "CrimeLab";

    //try to load the crime list first, if it doesn't exist, we will create a empty crime list
    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
//            Log.e(TAG, "Error loading crimes: ", e);
        }
        //for test use
//        for(int i=0;i<100;i++){
//            Crime c=new Crime();
//            c.setmTitle("Crime #"+i);
//            c.setmSolved(i%2==0);
//            mCrimes.add(c);
//        }
    }

    //singleton design pattern
    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    //get list of crimes
    public ArrayList<Crime> getmCrimes() {
        return mCrimes;
    }

    //get a crime from the list by its unique ID
    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getmId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    //add a crime to the list
    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    //serialize all the crimes and save them
    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
//            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
//            Log.e(TAG,"Error saving crimes: ",e);
            return false;
        }
    }

    //delete a crime from the list
    public void deleteCrime(Crime c) {
        mCrimes.remove(c);
    }
}
