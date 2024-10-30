package com.example.messageapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText phoneInput;
    private EditText messageInput;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneInput = findViewById(R.id.phoneInput);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneInput.getText().toString().trim();
                String message = messageInput.getText().toString().trim();

                if (!phoneNumber.isEmpty() && !message.isEmpty()) {
                    sendWhatsAppMessage(phoneNumber, message);
                } else {
                    Toast.makeText(MainActivity.this, "Masukkan nomor dan pesan terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendWhatsAppMessage(String phoneNumber, String message) {
        // Menghapus spasi dan memformat nomor telepon
        String formattedNumber = phoneNumber.replaceAll("\\s+", ""); // Menghapus spasi
        if (!formattedNumber.startsWith("+")) {
            formattedNumber = "+62" + formattedNumber.substring(1); // Ganti '0' dengan '+62'
        }

        // Menggunakan URL scheme untuk mengirim pesan
        String url = "https://api.whatsapp.com/send?phone=" + formattedNumber + "&text=" + Uri.encode(message);

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse(url));

        try {
            startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "WhatsApp tidak terpasang atau tidak dapat mengirim pesan.", Toast.LENGTH_SHORT).show();
        }
    }
}