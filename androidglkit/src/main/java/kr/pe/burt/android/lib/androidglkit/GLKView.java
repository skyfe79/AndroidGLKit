package kr.pe.burt.android.lib.androidglkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by burt on 2016. 6. 15..
 */
public class GLKView extends GLSurfaceView implements GLSurfaceView.Renderer {


    public GLKView(Context context, GLKRenderer renderer) {
        super(context);
        init();
    }

    public GLKView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    private void init() {
        // use opengl es 2.0
        setEGLContextClientVersion(2);

        // store opengl context
        setPreserveEGLContextOnPause(true);

        // set renderer
        setRenderer(this);

        lastTimeMillis = System.currentTimeMillis();
    }


    //----------------------------------------------------------------------------------------------
    // Render Loop
    private long lastTimeMillis = 0L;
    private long elapsedTime = 0L;
    private boolean rendererNeedsToInit = true;
    private GLKRenderer renderer = null;
    protected long getElapsedTime() {
        return elapsedTime;
    }

    public void setGLKRenderer(GLKRenderer renderer) {
        if(this.renderer == null) {
            rendererNeedsToInit = true;
            this.renderer = renderer;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        lastTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        if(renderer != null) {
            renderer.init(this);
            rendererNeedsToInit = false;
        } else {
            rendererNeedsToInit = true;
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        if(renderer != null) {
            renderer.onSizeChanged(this, width, height);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        long currentTimeMillis = System.currentTimeMillis();
        elapsedTime = currentTimeMillis - lastTimeMillis;
        if(renderer != null && rendererNeedsToInit == false) {
            renderer.update(this, elapsedTime);
        }
        lastTimeMillis = currentTimeMillis;
    }
}
