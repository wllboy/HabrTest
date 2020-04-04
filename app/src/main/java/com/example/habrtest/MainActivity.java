package com.example.habrtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String testUrl = "https://samples.openweathermap.org/data/2.5/weather?id=2172797&appid=b6907d289e10d714a6e88b30761fae22"; //url, из которого мы будем брать JSON-объект
    RequestQueue mRequestQueue; // очередь запросов
    TextView tempTextView,windTextView; // текстовые поля для отображения данных
    double temp = 0,windSpeed = 0; // переменные для взятия данных из JSON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempTextView = findViewById(R.id.tempTextView);
        windTextView = findViewById(R.id.windTextView);

        mRequestQueue = Volley.newRequestQueue(this);

        getWeather(testUrl);
        setValues();
    }

    private void getWeather(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //получение данных
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject weather = response.getJSONObject("main"),wind = response.getJSONObject("wind"); //получаем JSON-обьекты main и wind
                    temp = weather.getDouble("temp");
                    windSpeed = wind.getDouble("speed");
                    // присваеваем переменным соответствующие значения из API
                    setValues(); // создадим метод setValues для присваивания значений переменным
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // в случае возникновеня ошибки
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    private void setValues() {
        tempTextView.setText("Температура: " + temp);
        windTextView.setText("Скорость ветра: " + windSpeed);
    }
}
