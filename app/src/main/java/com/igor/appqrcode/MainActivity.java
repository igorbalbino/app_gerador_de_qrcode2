package com.igor.appqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    EditText editTextQRCode;
    Button btnQRCode;
    ImageView imageViewQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextQRCode.getText().toString())) {
                    editTextQRCode.setError("*");
                    editTextQRCode.requestFocus();
                } else gerarQRCode(editTextQRCode.getText().toString());
            }
        });

        MobileAds.initialize(this);
    }

    private void initComponents() {
        editTextQRCode = findViewById(R.id.editQRCode);
        btnQRCode = findViewById(R.id.btnGerarQRCode);
        imageViewQRCode = findViewById(R.id.imageQRCode);
    }

    private void gerarQRCode(String QRCodeContent) {
        QRCodeWriter qrCode = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = qrCode.encode(QRCodeContent, BarcodeFormat.QR_CODE, 196, 196);

            int largura = bitMatrix.getWidth();
            int altura = bitMatrix.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(largura, altura, Bitmap.Config.RGB_565);

            for(int x = 0; x < largura; x++) {
                for(int y = 0; y < largura; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            imageViewQRCode.setImageBitmap(bitmap);
        } catch(WriterException e) {
            e.printStackTrace();
        }
    }
}