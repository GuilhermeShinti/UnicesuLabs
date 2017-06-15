package br.com.guism.unicesulabs.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.guism.unicesulabs.R;

/**
 * Created by Guilherme Shinti on 23/05/2017.
 */

public class LabViewHolder extends RecyclerView.ViewHolder{

    public TextView bloco;
    public TextView laboratorio;
    public TextView horarioUm;
    public TextView horarioDois;


    public LabViewHolder(View itemView) {
        super(itemView);
        laboratorio = (TextView) itemView.findViewById(R.id.tv_laboratorio);
        bloco = (TextView) itemView.findViewById(R.id.tv_bloco);
        horarioUm = (TextView) itemView.findViewById(R.id.tv_horarioUm);
        horarioDois = (TextView) itemView.findViewById(R.id.tv_horarioDois);
    }
}
