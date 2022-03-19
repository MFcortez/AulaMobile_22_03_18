package com.example.mobile2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GPSbusca implements LocationListener
{
    Context context;

    //Define o valor do contexto de GPSbusca
    public GPSbusca(Context c)
    {
        context = c;
    }

    public Location getLocation()
    {
        //Checa se o GPS está autorizado a ser utilizado pelo app
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //Avisa ao usuario que o app nao esta permitido a utilizar o gps
            Toast.makeText(context, "GPS sem permissao", Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //Verifica se o GPS esta ativado e retorna a localizacao
        if (isGPSEnabled)
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }
        else
        {
        //Avisa ao usuario que o GPS nao esta ativado
        Toast.makeText(context, "Por favor, habitar o GPS!", Toast.LENGTH_LONG).show();
        }
        //Retorna o valor nulo caso o GPS nao esteja ativado
        return null;
    }

//Necessario para a utilizacao do locationlistener, basicamente atualiza as infos de localizacao do usuario
    @Override
    public void onLocationChanged(@NonNull Location location)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider)
    {
    }
}

