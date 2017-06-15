package br.com.guism.unicesulabs.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.guism.unicesulabs.Model.Lab;
import br.com.guism.unicesulabs.R;
import br.com.guism.unicesulabs.View.LabViewHolder;

/**
 * Created by Guilherme Shinti on 23/05/2017.
 */

public class LabAdapter extends RecyclerView.Adapter  {

    private ArrayList<Lab> labs;
    private Context context;

    public LabAdapter(ArrayList<Lab> labs, Context context){
        this.labs = labs;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lab_list_item, null);
//        return null;

        View view = LayoutInflater.from(context).inflate(R.layout.lab_list_item, parent, false);
        LabViewHolder holder = new LabViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        LabViewHolder holder = (LabViewHolder) viewHolder;
        Lab lab = labs.get(position);
        holder.bloco.setText(lab.getBloco());
        holder.laboratorio.setText(lab.getLaboratorio());
        holder.horarioUm.setText(lab.getHorarioUm());
        holder.horarioDois.setText(lab.getHorarioDois());

    }

    @Override
    public int getItemCount() {
        return labs.size();
    }
}
