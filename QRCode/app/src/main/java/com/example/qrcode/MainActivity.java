package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {

    String tag = "Generate QR Code";
    EditText editText;
    ImageView imageView;
    Button startButton;
    String inputValue;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.qrcode);
        startButton = findViewById(R.id.createButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputValue = editText.getText().toString().trim();
                if (!inputValue.isEmpty()) {
                    WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = windowManager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3/4;
                    qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        imageView.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.e(tag, e.toString());
                    }

                } else {
                    editText.setError("Text is required");
                }
            }
        });
    }
}
