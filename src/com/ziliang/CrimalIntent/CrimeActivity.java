package com.ziliang.CrimalIntent;

import android.app.Fragment;

import java.util.UUID;

//crime activity, used to hold a crime fragment
public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
