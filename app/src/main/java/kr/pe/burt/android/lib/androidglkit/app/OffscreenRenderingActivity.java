package kr.pe.burt.android.lib.androidglkit.app;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import kr.pe.burt.android.lib.androidglkit.GLKPixelBuffer;

public class OffscreenRenderingActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offscreen_rendering);
        imageView = (ImageView)findViewById(R.id.imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GLKPixelBuffer pb = new GLKPixelBuffer(300, 300);
        Bitmap bitmap = pb.getBitmap(new SquareRenderer(this));
        imageView.setImageBitmap(bitmap);
        pb.destroy();
    }
}
