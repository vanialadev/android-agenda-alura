package com.vania.alura_agenda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.vania.alura_agenda.DetalhesProvaActivity;
import com.vania.alura_agenda.R;
import com.vania.alura_agenda.modelo.Prova;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vania on 05/11/16.
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPor = Arrays.asList("Sujeito", "obj direto", "obj indireto");
        Prova provaPortgues = new Prova("Português", "10/10/2016", topicosPor);


        List<String> topicosMat = Arrays.asList("Equações de segundo grau", "Trigonometria");
        Prova provaMatematica =  new Prova("Matemática", "28/10/2016", topicosMat);

        List<Prova> provas = Arrays.asList(provaPortgues, provaMatematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou na prova de " + prova, Toast.LENGTH_SHORT).show();

                Intent vaiParaDetalhes = new Intent(getContext(), DetalhesProvaActivity.class);
                vaiParaDetalhes.putExtra("prova", prova);
                startActivity(vaiParaDetalhes);
            }
        });

        return view;
    }
}
