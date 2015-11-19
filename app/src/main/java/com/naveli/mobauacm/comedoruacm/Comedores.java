package com.naveli.mobauacm.comedoruacm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Comedores extends AppCompatActivity{

    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new com.naveli.mobauacm.comedoruacm.JSONParser();

    ArrayList<HashMap<String, String>> directorioList;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ARRAY = "arregloPlantel";
    private static final String TAG_AVISO = "aviso";
    private static final String TAG_ECOMEDOR = "ecomedor";
    private static final String TAG_ECAMBIO = "ecambio";

    private static String aviso;
    private static String ecomedor;
    private static String ecambio;
    private static String nomsj = "No hay mensajes.";
    TextView notice;
    // products JSONArray
    JSONArray instituciones = null;
    ListView listaArray;


    Bundle bundle;
    String[] dias = {
            "LUNES",
            "MARTES",
            "MIÉRCOLES",
            "JUEVES",
            "VIERNES"

    };

    int[] icon = {
            android.R.drawable.presence_online,
            android.R.drawable.presence_online,
            android.R.drawable.presence_online,
            android.R.drawable.presence_online,
            android.R.drawable.presence_online
    };

    private void loadImageParallax(int id) {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        // Usando Glide para la carga asíncrona
        Glide.with(this)
                .load(id)
                .centerCrop()
                .into(image);
    }
    ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedores);
        notice = (TextView) findViewById(R.id.avisos);

        //Configuracion de Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(bundle.getString("nombreComedor"));
        //final de Toolbar

        listaArray = (ListView) findViewById(R.id.list_dataset);
        directorioList = new ArrayList<HashMap<String, String>>();

        new LoadAllProducts().execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        loadImageParallax(bundle.getInt("imagen"));

        final ListView lista = (ListView) findViewById(R.id.list);
        adapter = new ListViewAdapter(this, dias, icon);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {

                switch (position) {
                    case 0:
                        executeActivity(0);
                        break;
                    case 1:
                        executeActivity(1);
                        break;
                    case 2:
                        executeActivity(2);
                        break;
                    case 3:
                        executeActivity(3);
                        break;
                    case 4:
                        executeActivity(4);
                        break;

                }
            }
        });
    }
    private void executeActivity(int dia){
        Intent a = new Intent(Comedores.this, SetMenu.class);
        a.putExtra("plantel", bundle.getString("nombreComedor").replace(" ", "%20"));
        a.putExtra("dia", dias[dia]);
        startActivity(a);
    }
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Comedores.this);
            pDialog.setMessage("Verificando si tienes nuevos mensajes por parte de ComedorUACM. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todas las instituciones
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List params = new ArrayList();
            // getting JSON string from URL
            Bundle bundle = getIntent().getExtras();
            String url_naveli_cidown = "http://comedoruacm.naveli.net/set_notices.php?plantel="+bundle.getString("nombreComedor").replace(" ","%20");
            JSONObject json = jParser.makeHttpRequest(url_naveli_cidown, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("Plantel:", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    instituciones = json.getJSONArray(TAG_ARRAY);


                    for (int i = 0; i < instituciones.length(); i++) {
                        JSONObject c = instituciones.getJSONObject(i);

                        aviso = c.getString(TAG_AVISO);
                        ecomedor = c.getString(TAG_ECOMEDOR);

                        HashMap map = new HashMap();
                            map.put(TAG_AVISO, aviso);
                        directorioList.add(map);
                    }
                }else{
                        HashMap map = new HashMap();
                            map.put(TAG_AVISO, nomsj);
                        directorioList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            Comedores.this,
                            directorioList,
                            R.layout.activity_load_notice,
                            new String[] {
                                    TAG_AVISO,
                            },
                            new int[] {
                                    R.id.avisos,
                            });
                    // updating listview
                    //setListAdapter(adapter);
                    listaArray.setAdapter(adapter);

                }
            });
        }
    }




    /*

    public void alerta(String dia){

                final Dialog dialog = new Dialog(this);
                Intent a = new Intent(Comedores.this, SetMenu.class);
                a.putExtra("plantel",bundle.getString("nombreComedor"));
                a.putExtra("dia",dia);
                startActivity(a);
                dialog.setContentView(R.layout.formato_menu);
                dialog.setTitle(bundle.getString("nombreComedor"));
     
                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.dia);
                text.setText(dia);
                dialog.show();

    }*/

}
