package com.ziliang.CrimalIntent;

import android.app.Fragment;
public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }

}
