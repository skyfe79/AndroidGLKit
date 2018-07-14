package kr.pe.burt.android.lib.androidglkit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 * GLKTexture.texture()
 * Created by burt on 2016. 7. 9..
 */
public class GLKTexture {

    private int textureName = 0;

    public interface OnTexParameters {
        void onTexParameters(int texture);
    }



    public static GLKTexture texture(Context context, int drawableID) {
        return texture(context, drawableID, true, null);
    }

    public static GLKTexture texture(Context context, int drawableID, OnTexParameters parameters) {
        return texture(context, drawableID, true, parameters);
    }

    public static GLKTexture texture(Context context, int drawableID, boolean isBottomLeftOrigin, OnTexParameters parameters) {
        Bitmap bitmap = null;
        Bitmap flippedBitmap = null;

        try {

            GLKTexture texture;

            bitmap = BitmapFactory.decodeResource(context.getResources(), drawableID);

            if(isBottomLeftOrigin) {
                Matrix flip = new Matrix();
                flip.postScale(1f, -1f);
                flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), flip, false);
                texture = texture(flippedBitmap, parameters);
            } else {
                texture = texture(bitmap, parameters);
            }

            return texture;

        } finally {

            if(bitmap != null) {
                bitmap.recycle();
            }

            if(flippedBitmap != null) {
                flippedBitmap.recycle();
            }
        }
    }

    public static GLKTexture texture(Bitmap bitmap, OnTexParameters parameters) {

        int textureName[] = new int[1];
        GLES20.glGenTextures(1, textureName, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureName[0]);

        if(parameters == null) {
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        } else {
            parameters.onTexParameters(textureName[0]);
        }

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        return new GLKTexture(textureName[0]);
    }

    public static GLKTexture texture(Bitmap bitmap, boolean isBottomLeftOrigin, OnTexParameters parameters) {
        Bitmap flippedBitmap = null;

        if(isBottomLeftOrigin) {
            Matrix flip = new Matrix();
            flip.postScale(1f, -1f);
            flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), flip, false);
            return texture(flippedBitmap, parameters);
        }
        return texture(bitmap, parameters);
    }

    private GLKTexture(int textureName) {
        this.textureName = textureName;
    }

    public int getTextureName() {
        return textureName;
    }
}
