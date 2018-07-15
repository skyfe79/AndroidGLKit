package kr.pe.burt.android.lib.androidglkit.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBlankClicked(View sender) {
        Intent intent = new Intent(this, BlankActivity.class);
        startActivity(intent);
    }

    public void onSquareClicked(View sender) {
        Intent intent = new Intent(this, SquareActivity.class);
        startActivity(intent);
    }

    public void onOffscreenRenderingClicked(View sender) {
        Intent intent = new Intent(this, OffscreenRenderingActivity.class);
        startActivity(intent);
    }

    public  void onTextureClicked(View sender) {
        Intent intent = new Intent(this, TextureActivity.class);
        startActivity(intent);
    }


}
