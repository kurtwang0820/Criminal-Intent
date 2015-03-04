package com.ziliang.CrimalIntent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Kurt on 2/9/2015.
 */
public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSlovedCheckBox;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    public static final String EXTRA_CRIME_ID = "com.ziliang.criminalintent.crime_id";
//    public static final String DIALOG_DATE = "date";
//    public static final String DIALOG_TIME = "time";
//    public static final int REQUEST_DATE = 0;
//    public static final int REQUEST_TIME = 1;
    private static final int REQUEST_DATE_TIME = 2;
    private static final int REQUEST_PHOTO=1;
    private static final String DIALOG_IMAGE="image";
    private static final String TAG="CrimeFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        setHasOptionsMenu(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity())!=null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(mCrime);
                if(NavUtils.getParentActivityName(getActivity())!=null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        if(NavUtils.getParentActivityName(getActivity())!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
//                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getmDate());
//                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
//                dialog.show(fm, DIALOG_DATE);

//                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getmDate());
//                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
//                dialog.show(fm, DIALOG_TIME);
                DateOrTimeFragment dialog = DateOrTimeFragment.newInstance(mCrime.getmDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE_TIME);
                dialog.show(fm, null);
            }
        });
        mSlovedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSlovedCheckBox.setChecked(mCrime.ismSolved());
        mSlovedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setmSolved(isChecked);
            }
        });
        mPhotoButton=(ImageButton)v.findViewById(R.id.crime_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),CrimeCameraActivity.class);
                startActivityForResult(i,REQUEST_PHOTO);
            }
        });
        PackageManager pm=getActivity().getPackageManager();
        boolean hasCamera=pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)||pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)|| Camera.getNumberOfCameras()>0;
        if(!hasCamera){
            mPhotoButton.setEnabled(false);
        }
        mPhotoView=(ImageView)v.findViewById(R.id.crime_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p=mCrime.getmPhoto();
                if(p==null){
                    return;
                }
                FragmentManager fm=getActivity().getFragmentManager();
                String path=getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.newInstance(path).show(fm,DIALOG_IMAGE);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE_TIME) {
            Date date = (Date) data.getSerializableExtra(DateOrTimeFragment.DATE_TAG);
            mCrime.setmDate(date);
            updateDate();
        }else if(requestCode==REQUEST_PHOTO){
            String filename=data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if(filename!=null){
                Log.i(TAG, "filename: " + filename);
                Photo p=new Photo(filename);
                mCrime.setmPhoto(p);
                Log.i(TAG,"crime: "+mCrime.getmTitle()+" has a photo");
                showPhoto();
            }
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);
    }
    @Override
    public void onStart(){
        super.onStart();
        showPhoto();
    }
    @Override
    public void onPause(){
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }
    public void updateDate() {
        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(mCrime.getmDate());
        mDateButton.setText(dateString);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_crime_delete,menu);
    }
    private void showPhoto(){
        Photo p=mCrime.getmPhoto();
        BitmapDrawable b=null;
        if(p!=null){
            String path=getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
            b=PictureUtils.getScaledDrawable(getActivity(),path);
        }
        mPhotoView.setImageDrawable(b);
    }
}
