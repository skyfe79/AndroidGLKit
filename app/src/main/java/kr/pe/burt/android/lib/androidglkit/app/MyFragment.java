package kr.pe.burt.android.lib.androidglkit.app;

import kr.pe.burt.android.lib.androidglkit.GLKFragment;

/**
 * Created by burt on 2016. 7. 9..
 */
public class MyFragment extends GLKFragment {
    @Override
    public void onStart() {
        super.onStart();
        glkView.setGLKRenderer(new BlankRenderer());
    }
}
