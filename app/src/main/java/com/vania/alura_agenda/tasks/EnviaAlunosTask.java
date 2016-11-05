package com.vania.alura_agenda.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.vania.alura_agenda.web.WebClient;
import com.vania.alura_agenda.converter.AlunoConverter;
import com.vania.alura_agenda.dao.AlunoDAO;
import com.vania.alura_agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by vania on 03/11/16.
 */

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {


    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        dialog = ProgressDialog.show(context,"Agurde", "Enviando as notas dos alunos", true, true);

    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);


        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {

        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
