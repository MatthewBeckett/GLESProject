package com.app.gl.first.glesproject;


import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import android.opengl.GLSurfaceView.Renderer;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by matt on 27/08/17.
 */

public class GLESPRenderer implements Renderer {

    float[] tableVertices = {
            0f, 0f,
            0f,14f,
            9f,14f,
            9f, 0f
    };
    float[] tableVerticesTriangles = {
            // board triangle 1
            0f, 0f,
            9f,14f,
            0f,14f,

            // board triangle 2
            0f, 0f,
            9f, 0f,
            9f,14f,

            // center line
            0f, 7f,
            9f, 7f,

            // mallets
            4.5f,2f,
            4.5f,12f
    };

    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;

    GLESPRenderer(){
        vertexData = ByteBuffer
                .allocateDirect(tableVerticesTriangles.length*BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVerticesTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(1.0f,0.5f,0.5f,0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
