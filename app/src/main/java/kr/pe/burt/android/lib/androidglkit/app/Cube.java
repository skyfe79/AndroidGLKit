package kr.pe.burt.android.lib.androidglkit.app;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import kr.pe.burt.android.lib.androidglkit.GLKBufferUtils;
import kr.pe.burt.android.lib.androidglkit.GLKMatrix4f;
import kr.pe.burt.android.lib.androidglkit.GLKProgram;
import kr.pe.burt.android.lib.androidglkit.GLKShaderUtils;
import kr.pe.burt.android.lib.androidglkit.GLKTexture;
import kr.pe.burt.android.lib.androidglkit.GLKVector3f;

public class Cube {
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;
    private GLKProgram shader;

    private int vertexBufferId;
    private int vertexCount;
    private int vertexStride;
    private int indexBufferId;

    // One Second
    private static final float ONE_SEC = 1000.0f; // 1 second

    // Texture name
    private GLKTexture texture = null;

    // ModelView Transformation
    private GLKVector3f position = new GLKVector3f(0f, 0f, 0f);
    private float rotationX  = 0.0f;
    private float rotationY  = 0.0f;
    private float rotationZ  = 0.0f;
    private float scale      = 1.0f;
    private GLKMatrix4f camera  = new GLKMatrix4f();
    private GLKMatrix4f projection = new GLKMatrix4f();

    // number of coordinates per vertex in this array
    private static final int COORDS_PER_VERTEX = 3;
    private static final int COLORS_PER_VERTEX = 4;
    private static final int TEXCOORDS_PER_VERTEX = 2;
    private static final int SIZE_OF_FLOAT = 4;
    private static final int SIZE_OF_SHORT = 2;

    static final float vertices[] = {
            // Front
            1, -1, 1,      1, 0, 0, 1,     1, 0, // 0
            1,  1, 1,      0, 1, 0, 1,     1, 1, // 1
            -1,  1, 1,      0, 0, 1, 1,     0, 1, // 2
            -1, -1, 1,      0, 0, 0, 1,     0, 0, // 3

            // Back
            -1, -1, -1,     0, 0, 1, 1,     1, 0, // 4
            -1,  1, -1,     0, 1, 0, 1,     1, 1, // 5
            1,  1, -1,     1, 0, 0, 1,     0, 1, // 6
            1, -1, -1,     0, 0, 0, 1,     0, 0, // 7

            // Left
            -1, -1,  1,     1, 0, 0, 1,     1, 0, // 8
            -1,  1,  1,     0, 1, 0, 1,     1, 1, // 9
            -1,  1, -1,     0, 0, 1, 1,     0, 1, // 10
            -1, -1, -1,     0, 0, 0, 1,     0, 0, // 11

            // Right
            1, -1, -1,     1, 0, 0, 1,     1, 0, // 12
            1,  1, -1,     0, 1, 0, 1,     1, 1, // 13
            1,  1,  1,     0, 0, 1, 1,     0, 1, // 14
            1, -1,  1,     0, 0, 0, 1,     0, 0, // 15

            // Top
            1, 1,  1,      1, 0, 0, 1,     1, 0, // 16
            1, 1, -1,      0, 1, 0, 1,     1, 1, // 17
            -1, 1, -1,      0, 0, 1, 1,     0, 1, // 18
            -1, 1,  1,      0, 0, 0, 1,     0, 0, // 19

            // Bottom
            1, -1, -1,     1, 0, 0, 1,     1, 0, // 20
            1, -1,  1,     0, 1, 0, 1,     1, 1, // 21
            -1, -1,  1,     0, 0, 1, 1,     0, 1, // 22
            -1, -1, -1,     0, 0, 0, 1,     0, 0, // 23

    };

    static final short indices[] = {

            // Front
            0, 1, 2,
            2, 3, 0,

            // Back
            4, 5, 6,
            6, 7, 4,

            // Left
            8, 9, 10,
            10, 11, 8,

            // Right
            12, 13, 14,
            14, 15, 12,

            // Top
            16, 17, 18,
            18, 19, 16,

            // Bottom
            20, 21, 22,
            22, 23, 20
    };


    public Cube(Context context) {
        if(setupShader(context)) {
            setupVertexBuffer();
            setupIndexBuffer();
        }
    }

    private boolean setupShader(Context context) {
        // compile & link shader
        shader = new GLKProgram(
                GLKShaderUtils.readShaderFileFromRawResource(context, R.raw.texture_vertex_shader),
                GLKShaderUtils.readShaderFileFromRawResource(context, R.raw.texture_fragment_shader)
        );
        return shader.isCompiled();
    }

    private void setupVertexBuffer() {

        // initialize vertex float buffer for shape coordinates
        vertexBuffer = GLKBufferUtils.newFloatBuffer(vertices.length);

        // add the coordinates to the FloatBuffer
        vertexBuffer.put(vertices);

        // set the buffer to read the first coordinate
        vertexBuffer.position(0);


        //copy vertices from cpu to the gpu
        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        vertexBufferId = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * SIZE_OF_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW);
        vertexStride = (COORDS_PER_VERTEX + COLORS_PER_VERTEX + TEXCOORDS_PER_VERTEX) * SIZE_OF_FLOAT; // 4 bytes per vertex
    }

    private void setupIndexBuffer() {
        // initialize index short buffer for index
        indexBuffer = GLKBufferUtils.newShortBuffer(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);

        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        indexBufferId = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indices.length * SIZE_OF_SHORT, indexBuffer, GLES20.GL_STATIC_DRAW);
    }

    public GLKMatrix4f modelMatrix() {
        GLKMatrix4f mat = new GLKMatrix4f(); // make a new identitiy 4x4 matrix
        mat.translate(position.x, position.y, position.z);
        mat.rotate(rotationX, 1.0f, 0.0f, 0.0f);
        mat.rotate(rotationY, 0.0f, 1.0f, 0.0f);
        mat.rotate(rotationZ, 0.0f, 0.0f, 1.0f);
        mat.scale(scale, scale, scale);
        return mat;
    }

    public void setCamera(GLKMatrix4f mat) {
        camera.load(mat);
    }

    public void setProjection(GLKMatrix4f mat) {
        projection.load(mat);
    }

    public void setTexture(GLKTexture texture) {
        this.texture = texture;
    }

    public void setPosition(GLKVector3f position) {
        this.position = position;
    }

    public void setRotationX(float rotationX) {
        this.rotationX = rotationX;
    }

    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }

    public void setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void updateWithDelta(long dt) {

        GLKMatrix4f camera2 = new GLKMatrix4f();
        camera2.translate(0.0f, 0.0f, -5.0f);
        setCamera(camera2);
        setRotationY((float)( rotationY + Math.PI * dt / (ONE_SEC * 0.1f) ));
        setRotationZ((float)( rotationZ + Math.PI * dt / (ONE_SEC * 0.1f) ));
    }

    public void draw() {
        if( shader.isValid() == false ) return;

        shader.begin();

        if(texture != null) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getTextureName());
            shader.setUniformi("u_Texture", 1);
        }

        camera.multiply(modelMatrix());
        shader.setUniformMatrix("u_ProjectionMatrix", projection);
        shader.setUniformMatrix("u_ModelViewMatrix",  camera);

        shader.enableVertexAttribute("a_Position");
        shader.setVertexAttribute("a_Position", COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

        shader.enableVertexAttribute("a_Color");
        shader.setVertexAttribute("a_Color", COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, COORDS_PER_VERTEX * SIZE_OF_FLOAT);

        shader.enableVertexAttribute("a_TexCoord");
        shader.setVertexAttribute("a_TexCoord", TEXCOORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, (COORDS_PER_VERTEX + COLORS_PER_VERTEX) * SIZE_OF_FLOAT);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES,        // mode
                indices.length,             // count
                GLES20.GL_UNSIGNED_SHORT,   // type
                0);                         // offset

        shader.disableVertexAttribute("a_Position");
        shader.disableVertexAttribute("a_Color");
        shader.disableVertexAttribute("a_TexCoord");


        shader.end();
    }
}