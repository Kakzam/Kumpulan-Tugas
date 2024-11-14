package com.flow.giftforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flow.giftforyou.ui.UtilString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class DetailActivity extends AppCompatActivity {

    TextView tJudul, tDescription, tWa;
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String collection = getIntent().getStringExtra(UtilString.BUCKET);
        String select = getIntent().getStringExtra("SELECT");
        view = findViewById(R.id.activity_detail_gambar);
        tJudul = findViewById(R.id.activity_detail_judul);
        tDescription = findViewById(R.id.activity_detail_description);
        tWa = findViewById(R.id.activity_detail_wa);

        new UtilString().e().collection(collection).document(select).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    byte[] d = Base64.decode(task.getResult().get(UtilString.JPEG).toString(), Base64.DEFAULT);
                    view.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
                    tJudul.setText(task.getResult().get(UtilString.JUDUL).toString());
                    tDescription.setText(task.getResult().get(UtilString.DESKRIPSI).toString());
                    tWa.setText("wa.me//" + task.getResult().get(UtilString.WA).toString());
                    tWa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = "https://api.whatsapp.com/send?phone=" + task.getResult().get(UtilString.WA).toString();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    });
                }
            }
        });

    }
}