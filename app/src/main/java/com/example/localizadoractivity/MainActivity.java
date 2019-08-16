package com.example.localizadoractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LocationManager _loc_mang;
    private LocationListener _loc_listn;

    private TextView _latitud;
    private TextView _longitud;
    private TextView _precision;
    private TextView _estado;

    private Button _comenzar;
    private Button _finalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _latitud = (TextView)findViewById(R.id._latitud);
        _longitud = (TextView)findViewById(R.id._longitud);
        _precision = (TextView)findViewById(R.id._precision);
        _estado = (TextView)findViewById(R.id._estado);

        _comenzar = (Button)findViewById(R.id._comenzar);
        _finalizar = (Button)findViewById(R.id._finalizar);

        _finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _loc_mang.removeUpdates(_loc_listn);
            }
        });

        _comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

    }

    private void init()
    {
        _loc_mang = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Location location = _loc_mang.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location != null)
        {
            _latitud.setText("Latitud: " + String.valueOf(location.getLatitude()));
            _longitud.setText("Longitud: " + String.valueOf(location.getLongitude()));
            _precision.setText("Precisión: " + String.valueOf(location.getAccuracy()));
        }
        else
        {
            _latitud.setText("Latitud: (sin_datos)");
            _longitud.setText("Longitud: (sin_datos)");
            _precision.setText("Precisión: (sin_datos)");
        }

        _loc_listn = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location != null)
                {
                    _latitud.setText(("Latitud: " + String.valueOf(location.getLatitude())));
                    _longitud.setText("Longitud: " + String.valueOf(location.getLongitude()));
                    _precision.setText("Precisión: " + String.valueOf(location.getAccuracy()));
                }
                else
                {
                    _latitud.setText("Latitud: (sin_datos)");
                    _longitud.setText("Longitud: (sin_datos)");
                    _precision.setText("Precisión: (sin_datos)");
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {
                _estado.setText("Provider Status: " + status);
            }

            @Override
            public void onProviderEnabled(String provider)
            {
                _estado.setText("Provider ON");
            }

            @Override
            public void onProviderDisabled(String provider)
            {
                _estado.setText("Provider OFF");
            }
        };

        _loc_mang.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, _loc_listn);
    }

}
