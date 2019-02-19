package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/";
    private final String API_KEY = "YjAyNDk2ODEyOTVmNDIyYmI1M2NjMjM3NGM4YjY0MTk";

    private String url_full;

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Bitcoin","onCreate");

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // An item selected
                String currency = adapterView.getItemAtPosition(i).toString();
                Log.d("Bitcoin","SpinSelected: " + currency);

                String full_url = getUrlFull(currency);
                Log.d("Bitcoin","url: " + full_url);
                getBitcoinClient(full_url);

                //letsDoSomeNetworking(full_url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
                Log.d("Bitcoin","SpinSelected: Nothing");
            }
        });

    }

    private String getUrlFull(String currency){
        String url = BASE_URL + "BTC" + currency;
        return url;
    };

    private void getBitcoinClient(String url){
        Log.d("Bitcoin", "getBitcoinClient");

        AsyncHttpClient clientHTTP = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        clientHTTP.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Bitcoin","getBitcoinClient onSuccess");
                Log.d("Bitcoin","json: " + response.toString());
                Double value = getBtcValue(response);
                updateUI(value.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                Log.d("Bitcoin","getBitcoinClient onFailure");
                Log.d("Bitcoin","Status code: " + statusCode);
                Log.e("Bitcoin", e.toString());
            }
        });

    };

    private Double getBtcValue(JSONObject json){
        Double value = 0.0;
        try {
            value = json.getDouble("ask");
            Log.d("Bitcoin","valueS: " + value.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    };

    private void updateUI(String valueS){
        mPriceTextView.setText(valueS);
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // called when response HTTP status is "200 OK"
//                Log.d("Clima", "JSON: " + response.toString());
//                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
//                updateUI(weatherData);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.d("Clima", "Request fail! Status code: " + statusCode);
//                Log.d("Clima", "Fail response: " + response);
//                Log.e("ERROR", e.toString());
//                Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();
//            }
//        });


    }


}
