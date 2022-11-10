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

public class UpdateDelete extends AppCompatActivity {

    EditText Title;
    EditText Kingdom;
    EditText Type;
    EditText Class;
    EditText Detachment;
    EditText Family;
    ImageView Image;

    Bundle arg;
    Animal animal;
    Bitmap bitmap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        arg = getIntent().getExtras();
        animal = arg.getParcelable(Animal.class.getSimpleName());
        Title = findViewById(R.id.et_Title);
        Kingdom = findViewById(R.id.et_Kingdom);
        Type = findViewById(R.id.et_Type);
        Class = findViewById(R.id.et_Class);
        Detachment = findViewById(R.id.et_Detachment);
        Family = findViewById(R.id.et_Family);
        Image = findViewById(R.id.Image);
        Title.setText(animal.getTitle());
        Kingdom.setText(animal.getKingdom());
        Type.setText(animal.getType());
        Class.setText(animal.getClas());
        Detachment.setText(animal.getDetachment());
        Family.setText(animal.getFamily());
        DecodeImg decodeImage = new DecodeImg(UpdateDelete.this);
        Bitmap userImage = decodeImage.getUserImage(animal.getImage());
        Image.setImageBitmap(userImage);
        if(!animal.getImage().equals("null")){
            bitmap = userImage;
        }
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

    public void UpdateAnimal(View v)
    {
        try{
        animal.setTitle(Title.getText().toString());
        animal.setKingdom(Kingdom.getText().toString());
        animal.setType(Type.getText().toString());
        animal.setClass(Class.getText().toString());
        animal.setDetachment(Detachment.getText().toString());
        animal.setFamily(Family.getText().toString());
        EncodeImg EI = new EncodeImg();
        animal.setImage(EI.Image(bitmap));
        put(animal, v);
        SystemClock.sleep(1000);
        onClickBack(v);

        }
        catch (Exception ex)
        {
            Toast.makeText(UpdateDelete.this, "Косяк!", Toast.LENGTH_SHORT).show();
        }
    }

    private void put(Animal animal, View v)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/БорисоваЕА/api/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI update = retrofit.create(RetrofitAPI.class);
        Call<Animal> call = update.createPut(animal, animal.getID());
        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                Toast.makeText(UpdateDelete.this, "Успешное изменение!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(UpdateDelete.this, "Косяк!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void delete(Animal animal, View v)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/БорисоваЕА/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<Animal> call = null;
        RetrofitAPI retrofitAPIsDel = retrofit.create(RetrofitAPI.class);
        call = retrofitAPIsDel.createDelete(animal.getID());
        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                Toast.makeText(UpdateDelete.this, "Успешное удаление!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(UpdateDelete.this, "Косяк!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void DeleteAnimal(View v){
        delete(animal,  v);
        onClickBack(v);
    }

    public void onClickBack(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}