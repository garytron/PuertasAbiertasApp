package com.exameple.prueba1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtUsu,txtPass ;
    private Button btnEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsu = (EditText) findViewById(R.id.txtusu);
        txtPass = (EditText) findViewById(R.id.txtpass);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Thread tr = new Thread(){
            @Override
            public void run() {
                final String resultado = enviarDatosGET(txtUsu.getText().toString(),txtPass.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = verificaJSON(resultado);
                        //JSONObject json = obtDatosJSON(resultado);
                        String alumno = "",fecha = "", estado ="";

                        if(r > 0)
                        {
                            Intent i = new Intent(getApplicationContext(), RegistroNotas.class);

                            try {
                                // Pulling items from the array
                                 //alumno = json.getString("alumno");
                                JSONArray arr = new JSONArray(resultado);
                                JSONObject jObj = arr.getJSONObject(0);
                                alumno = jObj.getString("alumno");
                                fecha = jObj.getString("fecha");
                                estado = jObj.getString("estado");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            i.putExtra("al",alumno);
                            i.putExtra("fecha",fecha);
                            i.putExtra("estado",estado);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String enviarDatosGET(String usu, String pas)
    {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder result = null;

        try
        {
            url =  new URL("http://puertasabiertas.azurewebsites.net/apiREST.php?usuario="+usu+"&password="+pas);
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            respuesta = conection.getResponseCode();

            result = new StringBuilder();
            if(respuesta==HttpURLConnection.HTTP_OK)
            {
                InputStream in = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while((linea = reader.readLine()) != null)
                    result.append(linea);
            }

        }catch(Exception e){}

        return result.toString();
    }

    public int verificaJSON(String response)
    {
        int res = 0;

        try{
            JSONArray json = new JSONArray(response);

            if(json.length()>0)
                res = 1;

        }catch (Exception e){}

        return res;
    }

    public JSONObject obtDatosJSON(String response)
    {
        JSONObject res = null;

        try{
            JSONObject json = new JSONObject(response);

            if(json.length()>0)
                res = json;

        }catch (Exception e){}

        return res;
    }
}
