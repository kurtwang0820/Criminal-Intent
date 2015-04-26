package com.ziliang.CrimalIntent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

/**
 * Created by Kurt on 2/9/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }
//    @Override
//    public void onCrimeSelected(Crime crime){
//        if(findViewById(R.id.detailFragmentContainer)==null){
//            Intent i=new Intent(this,CrimePagerActivity.class);
//            i.putExtra(CrimeFragment.EXTRA_CRIME_ID,crime.getmId());
//            startActivity(i);
//        }else{
//            FragmentManager fm=getFragmentManager();
//            FragmentTransaction ft=fm.beginTransaction();
//            Fragment oldDetail=fm.findFragmentById(R.id.detailFragmentContainer);
//            Fragment newDetail=CrimeFragment.newInstance(crime.getmId());
//            if(oldDetail!=null){
//                ft.remove(oldDetail);
//            }
//            ft.add(R.id.detailFragmentContainer,newDetail);
//            ft.commit();
//        }
//    }
//    @Override
//    public void onCrimeUpdated(Crime crime){
//        FragmentManager fm=getFragmentManager();
//        CrimeListFragment listFragment=(CrimeListFragment)fm.findFragmentById(R.id.fragmentContainer);
//        listFragment.updateUI();
//    }
}
