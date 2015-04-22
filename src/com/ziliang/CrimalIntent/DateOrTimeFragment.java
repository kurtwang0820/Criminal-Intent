package com.ziliang.CrimalIntent;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

/**
 * a dialog fragment which let the user to choose to change date or time
 * Created by Kurt on 2/11/2015.
 */
public class DateOrTimeFragment extends DialogFragment {
    private Date mDate;
    public static final String DATE_TAG="com.ziliang.criminalintent.dateortime";
    public static final String EXTRA_DATE_TIME="com.ziliang.criminalintent.date_time";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    public static DateOrTimeFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable(DATE_TAG,date);
        DateOrTimeFragment fragment=new DateOrTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate=(Date)getArguments().getSerializable(DATE_TAG);
        View v=getActivity().getLayoutInflater().inflate(R.layout.date_or_time,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(v).setTitle("Change date or time");
        builder.setPositiveButton(R.string.change_date, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentManager fm = getActivity().getFragmentManager();
                DatePickerFragment dateDialog = DatePickerFragment.newInstance(mDate);
                dateDialog.setTargetFragment(DateOrTimeFragment.this, REQUEST_DATE);
                dateDialog.show(fm, DIALOG_DATE);
            }
        });
        builder.setNegativeButton(R.string.change_time, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentManager fm = getActivity().getFragmentManager();
                TimePickerFragment timeDialog = TimePickerFragment.newInstance(mDate);
                timeDialog.setTargetFragment(DateOrTimeFragment.this, REQUEST_TIME);
                timeDialog.show(fm, DIALOG_TIME);
            }
        });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_DATE||requestCode==REQUEST_TIME){
            mDate=(Date)data.getSerializableExtra(EXTRA_DATE_TIME);
            sendResult(Activity.RESULT_OK);
        }
    }

    //send result to target fragment
    private void sendResult(int resultCode){
        if(getTargetFragment()==null){
            return;
        }
        Intent i=new Intent();
        i.putExtra(DATE_TAG,mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);
    }
}
