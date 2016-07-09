package kr.pe.burt.android.lib.androidglkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

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


        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AndroidGLKitAttrs);
        String rendererName = array.getString(R.styleable.AndroidGLKitAttrs_glk_renderer);
        if(rendererName == null) {
            return;
        }

        try {

            if(rendererName.startsWith("$")) {
                String packageName = context.getPackageName();
                rendererName = packageName + "." + rendererName.substring(rendererName.lastIndexOf("$")+1, rendererName.length());
            }

            Class cls  = Class.forName(rendererName);
            GLKRenderer renderer = (GLKRenderer)cls.newInstance();
            setRenderer(renderer);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }



    private void init() {
        // use opengl es 2.0
        setEGLContextClientVersion(2);

        // store opengl context
        setPreserveEGLContextOnPause(true);

        // set renderer
        setRenderer(this);
    }


    //----------------------------------------------------------------------------------------------
    // Render Loop
    private long lastTimeMillis = 0L;
    private long elapsedTime = 0L;
    private boolean rendererNeedsToInit = true;
    private GLKRenderer renderer = null;
    private int surfaceWidth = 0, surfaceHeight = 0;
    protected long getElapsedTime() {
        return elapsedTime;
    }

    public void setRenderer(GLKRenderer renderer) {
        rendererNeedsToInit = true;
        this.renderer = renderer;
        this.renderer.init(this);
        if(surfaceWidth > 0 && surfaceHeight > 0) {
            this.renderer.onSizeChanged(this, surfaceWidth, surfaceHeight);
        }

        // set rendererNeedsToInit to false, renderer.update method is called in the onDrawFrame.
        rendererNeedsToInit = false;

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
        } else {
            surfaceWidth = width;
            surfaceHeight = height;
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
