package com.ziliang.CrimalIntent;

import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * to show the original photo
 * Created by Kurt on 3/4/2015.
 */
public class ImageFragment extends DialogFragment{
    private ImageView mImageView;
    public static final String EXTRA_IMAGE_PATH="com.ziliang.criminalintent.image_path";
    public static ImageFragment newInstance(String imagePath){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_IMAGE_PATH,imagePath);
        ImageFragment fragment=new ImageFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE,0);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState){
        mImageView=new ImageView(getActivity());
        String path=(String)getArguments().getSerializable(EXTRA_IMAGE_PATH);
        BitmapDrawable image=PictureUtils.getScaledDrawable(getActivity(),path);
        mImageView.setImageDrawable(image);
        return mImageView;
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        PictureUtils.cleanImageView(mImageView);
    }
}
