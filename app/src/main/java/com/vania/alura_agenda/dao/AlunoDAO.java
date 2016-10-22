package com.vania.alura_agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.vania.alura_agenda.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vania on 16/07/16.
 */
public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Alunos(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL );";
        sqLiteDatabase.execSQL(sql)

        ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Alunos;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }


    public void inserir(Aluno aluno) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues dados = getContentValuesDoALuno(aluno);

        database.insert("Alunos", null, dados);


    }

    @NonNull
    private ContentValues getContentValuesDoALuno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(sql, null); //o cursor aponto pro ewsultado da busca

        List<Aluno> alunos = new ArrayList<>();
        while (c.moveToNext()) { //move o curso pra proxima linha
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

            alunos.add(aluno);

        }
        c.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase database = getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        database.delete("Alunos","id = ?", params);//a interrogação sao os dados dos parametros

    }

    public void altear(Aluno aluno) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues dados = getContentValuesDoALuno(aluno);

        String[] params = {aluno.getId().toString()};
        database.update("Alunos", dados, "id = ?", params);
    }
}
