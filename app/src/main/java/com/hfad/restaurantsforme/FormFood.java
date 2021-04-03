package com.hfad.restaurantsforme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormFood extends Activity {

    private static final int GALLERY_REQUEST = 1;
    DatabaseHelper db;
    ImageView imageFood;
    Restaurant restaurant;
    TextView nomRestaurant;
    Button uploadFoodBtn;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_form_food);
        Intent intent = getIntent();
        int restaurantId = intent.getIntExtra("restaurantId",-1);

        db= new DatabaseHelper(getApplicationContext());
        restaurant = db.getRestaurant(restaurantId);
        nomRestaurant = findViewById(R.id.nomRestaurant);
        nomRestaurant.setText(restaurant.getNom());

        // uplaod image
        uploadFoodBtn = findViewById(R.id.uploadFoodBtn);
        uploadFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }

        });


    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);



        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                imageFood = findViewById(R.id.imageFood);
                imageFood.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(FormFood.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(FormFood.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}