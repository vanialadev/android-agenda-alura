package com.vania.alura_agenda;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.vania.alura_agenda.fragment.MapaFragment;

/**
 * Created by vania on 06/11/16.
 */

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final GoogleApiClient client;
    private final MapaFragment mapaFragment;

    public Localizador(Context context, MapaFragment mapaFragment){
        client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        this.mapaFragment = mapaFragment;

        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();
        request.setSmallestDisplacement(50); //deslocamento de 50 metros
        request.setInterval(1000); //depois de um segundi
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//utiilizar o gps

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
       // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(coordenada);
       // mapaFragment.moveCamera(cameraUpdate);
        mapaFragment.centralizaEm(coordenada);


    }
}
