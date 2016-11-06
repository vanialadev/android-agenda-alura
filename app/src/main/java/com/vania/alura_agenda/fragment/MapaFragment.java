package com.vania.alura_agenda.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vania.alura_agenda.Localizador;
import com.vania.alura_agenda.dao.AlunoDAO;
import com.vania.alura_agenda.modelo.Aluno;

import java.io.IOException;
import java.util.List;

/**
 * Created by vania on 06/11/16.
 */


public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap mapa;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) { //esse método aqui é pro mapa abrir onde eu passar o endereço
        this.mapa = googleMap;  // guardamos o mapa
        LatLng posicaoDaEscola = pegaCoordenadaDoEndereco("Rua Bela Flor 99, Fortaleza");
        if (posicaoDaEscola != null) {
            centralizaEm(posicaoDaEscola);  // usamos um método aqui agora cetralizar passando  o endereço
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()) {
            LatLng coordenada = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if (coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();

        new Localizador(getContext(), MapaFragment.this);
    }

    private LatLng pegaCoordenadaDoEndereco(String endereco) { //aqui pega o endereço e procura ele
        try {
            Geocoder geocoder = new Geocoder(getContext());

            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1); //lista de resultados da busca, pega só uma a primeira
            if (!resultados.isEmpty()){//se tiver resultado pega a lat e a long do endereço
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // esse método é novo
    public void centralizaEm(LatLng coordenada) { // centraliza o meu mapa no endereço que eu vou passar e o zoom no mapa de 17
        if (mapa != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            mapa.moveCamera(update);
        }
    }
}
