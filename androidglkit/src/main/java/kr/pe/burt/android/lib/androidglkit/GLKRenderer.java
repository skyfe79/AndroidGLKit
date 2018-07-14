package kr.pe.burt.android.lib.androidglkit;


/**
 * Created by burt on 2016. 6. 15..
 */
public interface GLKRenderer  {
    void init(GLKView view);
    void onSizeChanged(GLKView view, int width, int height);
    void update(GLKView view, long dt);
}
