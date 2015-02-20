package com.ziliang.CrimalIntent;

import android.app.Fragment;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kurt on 2/20/2015.
 */
public class CrimeCameraFragment extends Fragment {
    private static final String TAG="CrimeCameraFragment";
    private Camera mCamera;
    private SurfaceView mSurfaceView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_crime_camera,container,false);
        Button takePcitureButton=(Button)v.findViewById(R.id.crime_camera_takePictureButton);
        takePcitureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mSurfaceView=(SurfaceView)v.findViewById(R.id.crime_camera_surfaceView);
        SurfaceHolder holder=mSurfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(mCamera!=null){
                        mCamera.setPreviewDisplay(holder);
                    }
                }catch (IOException e){
                    Log.e(TAG, "Error setting up preview display", e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if(mCamera==null){
                    return;
                }
                Camera.Parameters parameters=mCamera.getParameters();
                Camera.Size s=getBestSupportedSize(parameters.getSupportedPreviewSizes(),width,height);
                parameters.setPreviewSize(s.width,s.height);
                mCamera.setParameters(parameters);
                try{
                    mCamera.startPreview();
                }catch (Exception e){
                    Log.e(TAG,"Could not start preview",e);
                    mCamera.release();
                    mCamera=null;
                }

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(mCamera!=null){
                    mCamera.stopPreview();
                }
            }
        });
        return v;
    }
    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes,int width,int height){
        Camera.Size bestSize=sizes.get(0);
        int largestArea=bestSize.width*bestSize.height;
        for(Camera.Size s:sizes){
            int area=s.width*s.height;
            if(area>largestArea){
                largestArea=area;
                bestSize=s;
            }
        }
        return bestSize;
    }
    @Override
    public void onResume(){
        super.onResume();
        mCamera=Camera.open(0);
    }
    @Override
    public void onPause(){
        super.onPause();
        if(mCamera!=null){
            mCamera.release();
            mCamera=null;
        }
    }
}
