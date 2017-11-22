package com.android.sparhim.patients;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItem extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context th = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        final TextView nameView = findViewById(R.id.nm);
        final TextView idView = findViewById(R.id.uid);
        final Button capture = findViewById(R.id.capture);
        photo = findViewById(R.id.photo);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            nameView.setText(bundle.get("name").toString());
            idView.setText(bundle.get("id").toString());
        }
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(imageBitmap);
        }
    }


}
