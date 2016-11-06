package com.vania.alura_agenda;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.vania.alura_agenda.fragment.DetalhesProvaFragment;
import com.vania.alura_agenda.fragment.ListaProvasFragment;
import com.vania.alura_agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_principal, new ListaProvasFragment());

        if (estaNoModeoPaisagem()){
            transaction.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        transaction.commit();
    }

    private boolean estaNoModeoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaActivity(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();

       if (!estaNoModeoPaisagem()){
           FragmentTransaction transaction = manager.beginTransaction();

           DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
           Bundle parametros = new Bundle();
           parametros.putSerializable("prova", prova);
           detalhesProvaFragment.setArguments(parametros);

           transaction.replace(R.id.frame_principal, detalhesProvaFragment);
           transaction.addToBackStack(null);

           transaction.commit();
       }else {
           DetalhesProvaFragment detalhesFragment =
                   (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesFragment.populaCamposCom(prova);
       }
    }
}
