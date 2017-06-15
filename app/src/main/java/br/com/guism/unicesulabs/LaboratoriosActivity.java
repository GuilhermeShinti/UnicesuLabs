package br.com.guism.unicesulabs;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import br.com.guism.unicesulabs.Adapter.LabAdapter;
import br.com.guism.unicesulabs.Model.Lab;
import br.com.guism.unicesulabs.Model.Pagina;
import br.com.guism.unicesulabs.Model.PaginaLab;
import br.com.guism.unicesulabs.Model.Turno;

public class LaboratoriosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Boolean sp_status = false;
    String turnoParam = "";
    RelativeLayout rl_error;
    Button bt_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);

        rl_error = (RelativeLayout)findViewById(R.id.rl_error);
        bt_update = (Button)findViewById(R.id.bt_update);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });

        Toolbar tlb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tlb);
        tlb.setSubtitle(Turno.getHoje());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        load();
    }

    public  boolean isOnline() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

//    public boolean isOnline() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spinner, menu);


        MenuItem item = menu.findItem(R.id.sp_turno);
        final Spinner sp_turno = (Spinner) MenuItemCompat.getActionView(item);
        sp_turno.getBackground().setColorFilter(getResources().getColor(android.R.color.background_light), PorterDuff.Mode.MULTIPLY);

        Turno.populaSpinner(sp_turno,this);
        Turno.getPeriodo(sp_turno);

        sp_turno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_status == true) {
                    sp_status = false;
                    turnoParam = Turno.getParamTurnos(sp_turno.getSelectedItemPosition());
                    load();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return true;
    }

    public void load() {
        if (isOnline()){
            Lab.array.clear();
            rl_error.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            new pageRequest().execute();
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            rl_error.setVisibility(View.VISIBLE);
        }
    }

    class pageRequest extends AsyncTask<Void,Void,Void> {
        String url;
        String dataAtual;
        PaginaLab pLab;
        String host = "https://app.unicesumar.edu.br";
        String pathLabs = "/presencial/forms/informatica/horario.php?dados=";//yyyy-MM-dd|{Turno:M,T,N}

        @Override
        protected Void doInBackground(Void... params) {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            } };

            // Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try {
                dataAtual = Turno.getDiaAtual();
                url = host+pathLabs+dataAtual+"|"+turnoParam;
                System.out.println("TURNO > "+ Turno.getDiaAtual()+ " PERIODO > "+ turnoParam);
                pLab = new PaginaLab(Jsoup.connect(url).get());
                pLab.getPaginaLabs();

//            doc = Jsoup.connect("https://app.unicesumar.edu.br/presencial/forms/informatica/horario.php?dados=2017-05-23|N").get();
//            System.out.println(doc);
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println("LABORATORIOS: "+Lab.array.size());
            sp_status = true;
            Lab.setList(LaboratoriosActivity.this,recyclerView);
        }
    }
}




