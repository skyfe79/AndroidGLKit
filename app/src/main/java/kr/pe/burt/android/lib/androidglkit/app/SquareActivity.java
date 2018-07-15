package kr.pe.burt.android.lib.androidglkit.app;

import kr.pe.burt.android.lib.androidglkit.GLKAppCompatActivity;

public class SquareActivity extends GLKAppCompatActivity {

    @Override
    protected int getLayoutFileResourceId() {
        return R.layout.activity_square;
    }

    @Override
    protected void onStart() {
        super.onStart();
        glkView.setGLKRenderer(new SquareRenderer(this));
    }
}
