package com.example.borisova_animals;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class add_Animal extends AppCompatActivity {

    EditText Title;
    EditText Kingdom;
    EditText Type;
    EditText Class;
    EditText Detachment;
    EditText Family;
    ImageView Image;
    Bitmap bitmap=null, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        Title = findViewById(R.id.et_Title);
        Kingdom = findViewById(R.id.et_Kingdom);
        Type = findViewById(R.id.et_Type);
        Class = findViewById(R.id.et_Class);
        Detachment = findViewById(R.id.et_Detachment);
        Family = findViewById(R.id.et_Family);
        Image = findViewById(R.id.Image);
        b = BitmapFactory.decodeResource(add_Animal.this.getResources(), R.drawable.icon);
        Image.setImageBitmap(b);
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                    Image.setImageURI(uri);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });

    public void AddPhoto(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    public void AddAnimal(View v)
    {
        //try{

            String title = Title.getText().toString();
            String kingdom = Kingdom.getText().toString();
            String type = Type.getText().toString();
            String clas = Class.getText().toString();
            String detachment = Detachment.getText().toString();
            String family = Family.getText().toString();
            EncodeImg EI = new EncodeImg();
            post(title, kingdom, type, clas, detachment, family, EI.Image(bitmap));
            SystemClock.sleep(1000);
            onClickBack(v);

        /*}
        catch (Exception ex)
        {
            Toast.makeText(add_Animal.this, "Косяк!", Toast.LENGTH_SHORT).show();
        }*/
    }
    private void post(String title, String kingdom, String type, String clas, String detachment, String family, String img)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/БорисоваЕА/api/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Animal modal = new Animal(null,title, kingdom, type, clas, detachment, family, img);
        Call<Animal> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                    Toast.makeText(add_Animal.this, "Успешное добавление!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(add_Animal.this, "Косяк!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickBack(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}