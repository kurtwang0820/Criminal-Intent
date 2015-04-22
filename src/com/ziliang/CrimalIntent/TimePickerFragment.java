package com.ziliang.CrimalIntent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * a dialog fragment which let the user choose the exact time when the crime happens
 * Created by Kurt on 2/11/2015.
 */
public class TimePickerFragment extends DialogFragment {
    private Date mDate;
    public static TimePickerFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable(DateOrTimeFragment.EXTRA_DATE_TIME,date);
        TimePickerFragment fragment=new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate=(Date)getArguments().getSerializable(DateOrTimeFragment.EXTRA_DATE_TIME);
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(mDate);
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_time,null);
        TimePicker timePicker=(TimePicker)v.findViewById(R.id.dialog_date_timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mDate=new GregorianCalendar(year,month,day,hourOfDay,minute).getTime();
                getArguments().putSerializable(DateOrTimeFragment.EXTRA_DATE_TIME,mDate);
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_OK);
            }
        }).create();
    }

    //send time information to target fragment
    private void sendResult(int resultCode){
        if(getTargetFragment()==null){
            return;
        }
        Intent i=new Intent();
        i.putExtra(DateOrTimeFragment.EXTRA_DATE_TIME, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);
    }
}
