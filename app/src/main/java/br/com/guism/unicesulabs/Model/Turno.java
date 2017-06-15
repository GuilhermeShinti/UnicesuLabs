package br.com.guism.unicesulabs.Model;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.guism.unicesulabs.R;

/**
 * Created by Guilherme Shinti on 23/05/2017.
 */

public class Turno {
    private String dataHoje;
    public static String[] PARAM_TURNO = {"M","T","N"};
    private static String[] turnos = {"Manhã", "Tarde", "Noite"};

    public static String getParamTurnos(Integer num){
        String param = "";
        try {
            param = URLEncoder.encode(PARAM_TURNO[num],"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return param;
    }
    public static String getHoje() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(c.getTime());
    }

    public static String getDiaAtual(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Current date => " + sdf);
        try {
            return URLEncoder.encode(sdf.format(c.getTime()), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getPeriodo(Spinner sp_turno) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("Current time => " + sdf);

        try {
            Date Tarde = sdf.parse("12:00:00");
            Date Noite = sdf.parse("18:00:00");
            String hAtual = sdf.format(c.getTime());
            Date hora = sdf.parse(hAtual);

            if (hora.before(Tarde)) {
                Log.d("TURNO", "MANHA");
                sp_turno.setSelection(0);
            }
            if (hora.after(Tarde) && hora.before(Noite)) {
                Log.d("TURNO", "TARDE");
                sp_turno.setSelection(1);
            }
            if (hora.after(Noite)) {
                Log.d("TURNO", "NOITE");
                sp_turno.setSelection(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populaSpinner(Spinner sp_turno, Context context) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                R.layout.spinner_item, Turno.turnos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_turno.setAdapter(dataAdapter);

    }
}
