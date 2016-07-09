package kr.pe.burt.android.lib.androidglkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class GLKAppCompatActivity extends AppCompatActivity {

    protected GLKView glkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutFileResourceId());

        glkView = (GLKView)findViewById(R.id.glkView);
    }

    protected int getLayoutFileResourceId() {
        return R.layout.common_glk_view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        glkView.onResume();
    }

    @Override
    protected void onPause() {
        glkView.onPause();
        super.onPause();
    }
}
