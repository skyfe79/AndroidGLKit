package kr.pe.burt.android.lib.androidglkit.app;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.pe.burt.android.lib.androidglkit.GLKAppCompatActivity;

public class TextureActivity extends GLKAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        glkView.setGLKRenderer(new TextureRenderer(this));
    }
}
