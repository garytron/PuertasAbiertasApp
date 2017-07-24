package com.exameple.prueba1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RegistroNotas extends AppCompatActivity {

    private TextView nombre, estado, fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_notas);

        Intent intent = getIntent();

        String alumno = intent.getExtras().getString("al");
        String estado2 = intent.getExtras().getString("estado");
        String fecha2 = intent.getExtras().getString("fecha");

        nombre = (TextView) findViewById(R.id.nombre);
        estado = (TextView) findViewById(R.id.estado);
        fecha = (TextView) findViewById(R.id.fecha);

        nombre.setText(alumno);
        nombre.setTextColor(Color.rgb(2,112,35));
        estado.setText(estado2);
        estado.setTextColor(Color.rgb(255,0,0));
        fecha.setText(fecha2);
        fecha.setTextColor(Color.rgb(112,0,95));

    }
}
