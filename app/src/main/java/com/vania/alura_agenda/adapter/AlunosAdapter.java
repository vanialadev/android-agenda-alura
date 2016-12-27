package com.vania.alura_agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vania.alura_agenda.R;
import com.vania.alura_agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by vania on 25/10/16.
 */

public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public int getCount() {//pergunta que a lista vai fazer pro adapter pra dizer uantos item vai pedir
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//quando precisa mostrar alguma coisa
       // TextView textView = new TextView(context);
        Aluno aluno = alunos.get(position);
       // view.setText(aluno.toString());
        LayoutInflater inflater = LayoutInflater.from(context); //inflarer é pegar o xml e transformar em algo completo
        //covertView é as views que sao mostradas no layout e ja estao prontas, qtem em cima e a baixo d listviewm quando as de baixo acaba o android tras as de cim a auomaticamente
        //nao precisa ficar fazendo o inflate toda vida é aautomatico, so precisa a primeira vezz

        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item, parent, false); // opcao diz quem vai ser o pai desse layout o flase é pra nao colocar log opra dentro, so quando a llista chamar
        }

            TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
            campoNome.setText(aluno.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();

        TextView campoEndereco = (TextView) view.findViewById(R.id.item_indereco);
        if (campoEndereco != null){
            campoEndereco.setText(aluno.getEndereco());

        }

        TextView campoSite = (TextView) view.findViewById(R.id.item_site);
        if (campoSite != null){
            campoSite.setText(aluno.getSite());
        }


        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
           // campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            //campoFoto.setTag(caminhoFoto); recuperar o caminho em algum momento
        }

        return view;

    }
}
