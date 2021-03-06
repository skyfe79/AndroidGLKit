package kr.pe.burt.android.lib.androidglkit.app;

import android.content.Context;
import android.opengl.GLES20;

import kr.pe.burt.android.lib.androidglkit.GLKRenderer;
import kr.pe.burt.android.lib.androidglkit.GLKView;

/**
 * Created by burt on 2016. 7. 14..
 */
public class SquareRenderer implements GLKRenderer {

    private Square square;
    private Context context;

    public SquareRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void init(GLKView view) {
        square = new Square(context);
    }

    @Override
    public void onSizeChanged(GLKView view, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void update(GLKView view, long dt) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        square.draw();
    }

}
