package kr.pe.burt.android.lib.androidglkit.app;

import kr.pe.burt.android.lib.androidglkit.GLKAppCompatActivity;

public class CustomLayoutActivity extends GLKAppCompatActivity {

    @Override
    protected int getLayoutFileResourceId() {
        return R.layout.activity_custom_layout;
    }

    @Override
    protected void onStart() {
        super.onStart();
        glkView.setGLKRenderer(new SquareRenderer(this));
    }
}
