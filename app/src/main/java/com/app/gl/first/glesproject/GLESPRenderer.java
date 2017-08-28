package com.app.gl.first.glesproject;


import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import app.gl.first.glesproject.android.util.TextResourceReader;

/**
 * Created by matt on 27/08/17.
 */

public class GLESPRenderer implements Renderer {


    float[] tableVerticesTriangles = {
            // board triangle 1
            -0.5f   ,   -0.5f,
             0.5f   ,    0.5f,
            -0.5f   ,    0.5f,

            // board triangle 2
            -0.5f   ,   -0.5f,
             0.5f   ,   -0.5f,
             0.5f   ,    0.5f,

            // center line
            -0.5f   ,    0f,
             0.5f   ,    0f,

            // mallets
             0f     ,   -0.25f,
             0f     ,    0.25f,

            // puck
             0f     ,    0f,

            //border
            -0.55f  ,   -0.55f,
             0.55f  ,    0.55f,
            -0.55f  ,    0.55f,


            -0.55f  ,   -0.55f,
             0.55f  ,   -0.55f,
             0.55f  ,    0.55f,
    };

    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;
    private final Context context;
    private int program;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private final int POSITION_COMPONENT_COUNT = 2;


    GLESPRenderer(Context context){
        this.context = context;
        vertexData = ByteBuffer
                .allocateDirect(tableVerticesTriangles.length*BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVerticesTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        glClearColor(0.0f,0.0f,0.0f,0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context,R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context,R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader,fragmentShader);

        if(LoggerConfig.ON)
            ShaderHelper.validateProgram(program);

        glUseProgram(program);

        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program,A_POSITION);

        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation,POSITION_COMPONENT_COUNT,GL_FLOAT,false,0,vertexData);

        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        glClear(GL_COLOR_BUFFER_BIT);


        glUniform4f(uColorLocation,0.5f,0.5f,0.5f,1.0f);
        glDrawArrays(GL_TRIANGLES,11,6);


        glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        glDrawArrays(GL_TRIANGLES,0,6);

        glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        glDrawArrays(GL_LINES,6,2);

        glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f);
        glDrawArrays(GL_POINTS,8,1);

        glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        glDrawArrays(GL_POINTS,9,1);

        glUniform4f(uColorLocation,0.0f,0.6f,0.0f,1.0f);
        glDrawArrays(GL_POINTS,10,1);


    }
}
