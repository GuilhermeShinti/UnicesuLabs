package br.com.guism.unicesulabs.Model;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.guism.unicesulabs.Adapter.LabAdapter;
import br.com.guism.unicesulabs.LaboratoriosActivity;

/**
 * Created by Guilherme Shinti on 23/05/2017.
 */

public class Lab extends Bloco {

    private String laboratorio;
    private String horarioUm;
    private String horarioDois;
    static LabAdapter adapter;
    public static ArrayList<Lab> array = new ArrayList<>();

    public Lab(String bloco, String laboratorio, String primeiro, String segundo) {
        super(bloco);
        this.bloco = bloco;
        this.laboratorio = laboratorio;
        this.horarioUm = primeiro;
        this.horarioDois = segundo;
    }

    public Lab() {
        super();
    }

    public String getBloco() {
        return bloco;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public String getHorarioUm() {
        return horarioUm;
    }

    public String getHorarioDois() {
        return horarioDois;
    }

    public static ArrayList getArray(){
        return array;
    }

    public static void setList(LaboratoriosActivity context, RecyclerView lv_labs){
        System.out.println("LABORATORIOS: "+array.size());
        adapter = new LabAdapter(array, context);
        adapter.notifyDataSetChanged();
        lv_labs.setAdapter(adapter);
    }
}
