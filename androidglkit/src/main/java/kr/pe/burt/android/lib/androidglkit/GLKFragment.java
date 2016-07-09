package kr.pe.burt.android.lib.androidglkit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by burt on 2016. 7. 9..
 */
public class GLKFragment extends Fragment {

    protected GLKView glkView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutFileResourceId(), container, false);
        glkView = (GLKView)v.findViewById(R.id.glkView);
        return v;
    }

    protected int getLayoutFileResourceId() {
        return R.layout.common_glk_view;
    }

    @Override
    public void onStart() {
        super.onStart();
        glkView.onResume();
    }

    @Override
    public void onStop() {
        glkView.onPause();
        super.onStop();
    }
}
