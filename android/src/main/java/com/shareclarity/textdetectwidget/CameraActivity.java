package com.shareclarity.textdetectwidget;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.shareclarity.textdetectwidget.camera.CameraSource;
import com.shareclarity.textdetectwidget.camera.CameraSourcePreview;
import com.shareclarity.textdetectwidget.others.GraphicOverlay;
import com.shareclarity.textdetectwidget.text_detection.TextRecognitionProcessor;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    //region ----- Instance Variables -----

    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;

    private static String TAG = CameraActivity.class.getSimpleName().toString().trim();
    private String[] companies;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        preview = (CameraSourcePreview) findViewById(R.id.camera_source_preview);
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = (GraphicOverlay) findViewById(R.id.graphics_overlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }

        companies = getIntent().getStringArrayExtra("companies");
        createCameraSource();
        startCameraSource();
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
    }

    /** Stops the camera. */
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private void createCameraSource() {

        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
            cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
        }

        cameraSource.setMachineLearningFrameProcessor(new TextRecognitionProcessor(companies));
    }

    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }
}