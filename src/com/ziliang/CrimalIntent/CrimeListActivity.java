package com.ziliang.CrimalIntent;

import android.app.Fragment;

/**
 * Created by Kurt on 2/9/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
