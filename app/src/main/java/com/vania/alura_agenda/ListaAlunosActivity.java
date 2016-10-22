package com.vania.alura_agenda;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vania.alura_agenda.dao.AlunoDAO;
import com.vania.alura_agenda.modelo.Aluno;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);//cria o dao
        List<Aluno> alunos = dao.buscaAlunos(); //busca os lunos e joga a lista d e alunos
        dao.close(); //fecha o dao

//        String[] alunos = {"João", "Marcelo", "Ana", "Zé", "João", "Marcelo", "Ana", "Zé", "João", "Marcelo", "Ana", "Zé", "João", "Marcelo", "Ana", "Zé"};
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos); //ArrayAdappter converte cada aluno em uma textView
        // adapter usa a toString do object pra imprmir aluno, entao tem qye reescrever o to String
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);


        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listaDeAlunos, View itemClicado, int positionItem, long idItem) {
                Aluno aluno = (Aluno) listaDeAlunos.getItemAtPosition(positionItem);
                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome() + " clicado!", Toast.LENGTH_SHORT).show();

                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);
            }
        });


        Button novoAluno = (Button) findViewById(R.id.novo_aluno);

        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }


    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem itemLigacao = menu.add("Ligar");
        itemLigacao.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{android.Manifest.permission.CALL_PHONE}, 123);
                }else {
                    Intent intentLigacao = new Intent(Intent.ACTION_CALL);
                    intentLigacao.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigacao);
                }
                return false;
            }
        });

        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = aluno.getSite();
        if (!site.startsWith("https://")) {
            site = "https://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW) ;
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Toast.makeText(ListaAlunosActivity.this, "Deletar o aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();

                carregaLista();
                return false;
            }
        });

    }
}

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        //toda vidA QUE UMA PERMISSAO É ACEITA OU NEGADA ELA VEM OPRA ESSE MÉTDO, ENTÃO
//        //AQUI EU PODERIA COLOCAR PRA A LIGAÇÃO SER FEITA IMEDIATAMENTE PORQUE SE EU NAO COLOC AAQUI EU TEHO QUE IR DE NOVO NO CONTEXTmENU PRA FAZER
//        // LIGAÇÃO. COMO ISSO É UM METODO PRA TODAS AS PERMISSOES A GNT PRECISA DO requestCode QUE NO MEU CASO É O 123 WNTAO SE POR EXEPLO TIVESEM DUAS
//        if( requestCode == 123){
//            //faz lilgação
//        } else if (requestCode == 456){
//            //faz oura cpoisa
//        }
//    }

//conexao com o banco
//faz uma busca pra trazer s alunos
//popula o array de string
//fecha o banco
//conexao com o baco
//faz uma query
//fecha o banco