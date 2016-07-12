package kr.pe.burt.android.lib.androidglkit;


import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;


/**
 * PixelBuffer for offscreen rendering.
 * @link { https://mkonrad.net/2014/12/08/android-off-screen-rendering-using-egl-pixelbuffers.html }
 * @link { https://github.com/CyberAgent/android-gpuimage/blob/master/library/src/jp/co/cyberagent/android/gpuimage/PixelBuffer.java }
 *
 * Created by burt on 2016. 7. 10..
 */
public class GLKPixelBuffer {

    private int width, height;
    private EGL10 egl;
    private EGLDisplay eglDisplay;
    private EGLConfig[] eglConfigs;
    private EGLConfig  eglConfig;
    private EGLContext eglContext;
    private EGLSurface eglSurface;
    private GL10 gl;

    public GLKPixelBuffer(int width, int height) {
        this.width = width;
        this.height = height;

        if(width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be bigger than zero.");
        }

        setupEGL();
    }

    private void setupEGL() {

        final int[] surfaceAttributes = new int[] {
                EGL10.EGL_WIDTH, width,
                EGL10.EGL_HEIGHT, height,
                EGL10.EGL_NONE
        };

        int[] version = new int[2];

        egl = (EGL10)EGLContext.getEGL();
        eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl.eglInitialize(eglDisplay, version);
        eglConfig = chooseConfig();


        int EGL_CONTEXT_CLIENT_VERSION = 0x3098;
        final int[] contextAttributes = new int[] {
                EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL10.EGL_NONE
        };

        eglContext = egl.eglCreateContext(eglDisplay, eglConfig, EGL10.EGL_NO_CONTEXT, contextAttributes);
        eglSurface = egl.eglCreatePbufferSurface(eglDisplay, eglConfig, surfaceAttributes);
        egl.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext);

        gl = (GL10)eglContext.getGL();
        
    }

    private EGLConfig chooseConfig() {
        int[] attribList = new int[] {
                EGL10.EGL_RENDERABLE_TYPE,  4,   //EGL_OPENGL_ES2_BIT,
                EGL10.EGL_SURFACE_TYPE,     EGL10.EGL_PBUFFER_BIT,
                EGL10.EGL_RED_SIZE,         8,
                EGL10.EGL_GREEN_SIZE,       8,
                EGL10.EGL_BLUE_SIZE,        8,
                EGL10.EGL_ALPHA_SIZE,       8,
                EGL10.EGL_DEPTH_SIZE,       16,
                EGL10.EGL_STENCIL_SIZE,     0,
                EGL10.EGL_NONE
        };

        // No error checking performed, minimum required code to elucidate logic
        // Expand on this logic to be more selective in choosing a configuration
        int[] numConfig = new int[1];
        egl.eglChooseConfig(eglDisplay, attribList, null, 0, numConfig);
        int configSize = numConfig[0];
        eglConfigs = new EGLConfig[configSize];
        egl.eglChooseConfig(eglDisplay, attribList, eglConfigs, configSize, numConfig);

        return eglConfigs[0]; // Best match is probably the first configuration
    }
}
