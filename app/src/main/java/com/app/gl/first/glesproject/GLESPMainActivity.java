package com.app.gl.first.glesproject;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class GLESPMainActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsES2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if(supportsES2){
            glSurfaceView.setEGLContextClientVersion(2);

            glSurfaceView.setRenderer(new GLESPRenderer(this));
            rendererSet = true;
        }
        else{
            Toast.makeText(this,"Device does not support OpenGL ES 2.0",Toast.LENGTH_LONG).show();
            return;
        }
        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(rendererSet)
            glSurfaceView.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(rendererSet)
            glSurfaceView.onResume();
    }





}
