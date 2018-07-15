package kr.pe.burt.android.lib.androidglkit.app;

import android.content.Context;
import android.opengl.GLES20;

import kr.pe.burt.android.lib.androidglkit.GLKMatrix4f;
import kr.pe.burt.android.lib.androidglkit.GLKRenderer;
import kr.pe.burt.android.lib.androidglkit.GLKTexture;
import kr.pe.burt.android.lib.androidglkit.GLKVector3f;
import kr.pe.burt.android.lib.androidglkit.GLKView;

public class TextureRenderer implements GLKRenderer {

    private Cube cube;
    private Context context;
    private long lastTimeMillis = 0L;

    public TextureRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void init(GLKView view) {
        cube = new Cube(context);

        GLKTexture texture = GLKTexture.texture(context, R.drawable.mask_128);

        cube.setPosition(new GLKVector3f(0.0f, 0.0f, 0.0f));
        cube.setTexture(texture);

        lastTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void onSizeChanged(GLKView view, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        GLKMatrix4f perspective = new GLKMatrix4f();
        perspective.loadPerspective(85.0f, (float)width / (float)height, 1.0f, -150.0f);

        if(cube != null) {
            cube.setProjection(perspective);
        }
    }

    @Override
    public void update(GLKView view, long dt) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        cube.draw();
        cube.updateWithDelta(dt);
    }
}
