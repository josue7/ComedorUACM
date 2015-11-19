package com.naveli.mobauacm.comedoruacm;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by plaknava on 9/11/15.
 */
public class SetMenu extends AppCompatActivity {

    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new com.naveli.mobauacm.comedoruacm.JSONParser();

    ArrayList<HashMap<String, String>> directorioList;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ARRAY = "arregloPlantel";
    private static final String TAG_PLANTEL = "plantel";
    private static final String TAG_FECHA = "fecha";
    private static final String TAG_DIA = "dia";
    private static final String TAG_HORAMAT = "horamat";
    private static final String TAG_HORAVES = "horaves";
    private static final String TAG_DESAYUNO = "desayuno";
    private static final String TAG_COMIDA = "comida";
    private static final String TAG_AVISO = "aviso";
    private static final String TAG_EAVISO = "eaviso";
    private static final String TAG_ECOMEDOR = "ecomedor";
    private static final String TAG_ECAMBIO = "ecambio";
    // products JSONArray
    JSONArray instituciones = null;
    ListView lista;

    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();

        switch (bundle.getString("plantel").replace("%20"," ")){
            case "Cuautepec":
                setTheme(R.style.ThemeCuautepec);
                break;
            case "Del Valle":
                setTheme(R.style.ThemeDelValle);
                break;
            case "San Lorenzo Tezonco":
                setTheme(R.style.ThemeSLT);
                break;
            case "Centro Histórico":
                setTheme(R.style.ThemeCentro);
                break;
            case "Casa Libertad":
                setTheme(R.style.ThemeCasaLiber);
                break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle(bundle.getString("plantel").replace("%20", " "));
        directorioList = new ArrayList<HashMap<String, String>>();


        // Cargar los productos en el Background Thread
        new LoadAllProducts().execute();
        lista = (ListView) findViewById(R.id.list_dataset);
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SetMenu.this);
            pDialog.setMessage("Cargando información del ComedorUACM. Por favor espere...");
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
            String url_naveli_cidown = "http://comedoruacm.naveli.net/get_datas.php?plantel="+bundle.getString("plantel")+"&dia="+bundle.getString("dia");
            JSONObject json = jParser.makeHttpRequest(url_naveli_cidown, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("Plantel:", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    instituciones = json.getJSONArray(TAG_ARRAY);

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < instituciones.length(); i++) {
                        JSONObject c = instituciones.getJSONObject(i);

                        // Storing each json item in variable
                        String plantel = c.getString(TAG_PLANTEL);
                        String fecha = c.getString(TAG_FECHA);
                        String dia = c.getString(TAG_DIA);
                        String horamat = c.getString(TAG_HORAMAT);
                        String horaves = c.getString(TAG_HORAVES);
                        String desayuno = c.getString(TAG_DESAYUNO);
                        String comida = c.getString(TAG_COMIDA);
                        String aviso = c.getString(TAG_AVISO);
                        String eaviso = c.getString(TAG_EAVISO);
                        String ecomedor = c.getString(TAG_ECOMEDOR);
                        String ecambio = c.getString(TAG_ECAMBIO);
                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value
                        //map.put(TAG_ID, id);
                        map.put(TAG_PLANTEL, plantel);
                        map.put(TAG_FECHA, fecha);
                        map.put(TAG_DIA, dia);
                        map.put(TAG_HORAMAT, horamat);
                        map.put(TAG_HORAVES, horaves);
                        map.put(TAG_DESAYUNO, desayuno);
                        map.put(TAG_COMIDA, comida);
                        map.put(TAG_AVISO, aviso);
                        map.put(TAG_EAVISO, eaviso);
                        map.put(TAG_ECOMEDOR, ecomedor);
                        map.put(TAG_ECAMBIO, ecambio);

                        directorioList.add(map);
                    }
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
                            SetMenu.this,
                            directorioList,
                            R.layout.formato_menu,
                            new String[] {
                                    TAG_DIA,
                                    TAG_FECHA,
                                    TAG_HORAMAT,
                                    TAG_HORAVES,
                                    TAG_DESAYUNO,
                                    TAG_COMIDA,
                            },
                            new int[] {
                                    R.id.dia,
                                    R.id.fecha,
                                    R.id.horarioMat,
                                    R.id.horarioVes,
                                    R.id.desayuno,
                                    R.id.comida,
                            });
                    // updating listview
                    //setListAdapter(adapter);
                    lista.setAdapter(adapter);

                }
            });
        }
    }
}

