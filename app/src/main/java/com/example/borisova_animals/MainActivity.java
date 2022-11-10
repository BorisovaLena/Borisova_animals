package com.example.borisova_animals;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Adapter pAdapter;
    private List<Animal> listAnimal = new ArrayList<>();
    Spinner spinner;
    String[] i = {"по возрастанию","по убыванию"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView ivProducts = findViewById(R.id.BD_Animals);
        pAdapter = new Adapter(MainActivity.this, listAnimal);
        ivProducts.setAdapter(pAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, i);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner=findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        new GetAnim().execute();


    }
    class GetAnim extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/БорисоваЕА/api/Animals");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String str = "";
                while ((str = reader.readLine()) != null) {
                    result.append(str);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Animal tempAnimal = new Animal(
                            productJson.getInt("ID"),
                            productJson.getString("Title"),
                            productJson.getString("Kingdom"),
                            productJson.getString("Type"),
                            productJson.getString("Class"),
                            productJson.getString("Detachment"),
                            productJson.getString("Family"),
                            productJson.getString("Image")
                    );
                    listAnimal.add(tempAnimal);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {
            }
        }
    }
    public void onClickADD(View v)
    {
        Intent intent = new Intent(this, add_Animal.class);
        startActivity(intent);
    }
}