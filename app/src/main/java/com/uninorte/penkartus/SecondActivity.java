package com.uninorte.penkartus;

/**
 * Created by karolaya on 31/10/16.
 */

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SecondActivity extends AppCompatActivity {

    public int cant = 1;
    public static final String JSON_URL = "http://10.20.37.24/pedido.php?id=";
    public static final String JSON_URL_CANT = "http://10.20.37.24/confirmarCantidad.php?id=";
    public static final String JSON_URL_COD = "http://10.20.46.48/on";
    public static final String JSON_URL_OFF = "http://10.20.46.48 /off";
    // declare buttons and text inputs
    private Button buttonPin11, buttonPin12, buttonPin13;
    private EditText editTextIPAddress, editTextPortNumber;
    // shared preferences objects used to save the IP address and port so that the user doesn't have to
    // type them next time he/she opens the app.
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String[] pedido = {"Alicates", "Arandelas", "Baterías", "Bisagras", "Brocas", "Cable", "Cerraduras", "Cinta", "Clavos", "Martillos", "Pinzas", "Taladros", "Tornillos", "Tuercas", "Tubos"};
        ListAdapter theAdapter = new MyAdapter(this, pedido);
        ListView listView = (ListView) findViewById(R.id.Enviar_pack);
        listView.setAdapter(theAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mostrar(position);

                String seleccion = "Seleccionaste" + " " +
                        String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(SecondActivity.this, seleccion, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrar(final int position) {
        final Dialog numPick = new Dialog(SecondActivity.this);
        numPick.setTitle("Cantidad que desea:");
        numPick.setContentView(R.layout.number_picker);

        Button bok = (Button) numPick.findViewById(R.id.buttonOk);
        Button bcancel = (Button) numPick.findViewById(R.id.button2);
        Button brecogido = (Button) numPick.findViewById(R.id.button3);

        NumberPicker np = (NumberPicker) numPick.findViewById(R.id.numberPicker);

        np.setMinValue(1);
        np.setMaxValue(20);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                cant = newVal;
                Log.d("Cantidad escogida:", " " + newVal);
            }

        });

        bok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(position);
                sendCantidad(position, cant);
                sendCodigo(position);
            }
        });

        bcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numPick.dismiss(); // dismiss the dialog
            }
        });

        brecogido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOff(position);
            }
        });

        numPick.show();
    }

    private void sendRequest(int position){
        Log.d("Url:", JSON_URL_CANT+""+(position+1));
        StringRequest stringRequest = new StringRequest(JSON_URL+""+(position+1),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SecondActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendCantidad(int position, int newVal){
        Log.d("Url Pro:", JSON_URL_CANT+""+(position+1)+"&newVal="+newVal);
        StringRequest stringRequest = new StringRequest(JSON_URL_CANT+""+(position+1)+"&newVal="+newVal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(SecondActivity.this, "Cantidad Actualizada", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SecondActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendCodigo(int codigo){
        Log.d("Url Pro Cod:", JSON_URL_COD+""+(codigo+1));
        StringRequest stringRequest = new StringRequest(JSON_URL_COD+""+codigo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SecondActivity.this, "Cantidad Actualizada", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SecondActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendOff(int codigo){
        Log.d("Url Pro off:", JSON_URL_OFF+""+(codigo+1));
        StringRequest stringRequest = new StringRequest(JSON_URL_OFF+""+codigo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SecondActivity.this, "Apagado", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SecondActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        JSONObject jsonObject=null;
        try {
            JSONArray users = null;
            String[] ids;
            String[] productos;
            String[] cantidades;
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray("result");

            ids = new String[users.length()];
            productos = new String[users.length()];
            cantidades = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i] = jo.getString("id");
                productos[i] = jo.getString("Pieza");
                cantidades[i] = jo.getString("Cantidad");
            }
            int Cant_Val = Integer.parseInt(cantidades[0]);
            Log.d("Cantidad número: ", ""+Cant_Val);
            if(Cant_Val < 1) {
                Toast.makeText(SecondActivity.this, "Lo sentimos, no hay piezas existentes de este tipo", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names,ParseJSON.emails);
        //listView.setAdapter(cl);
    }


}





