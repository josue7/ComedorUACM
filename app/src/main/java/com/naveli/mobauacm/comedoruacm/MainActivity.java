package com.naveli.mobauacm.comedoruacm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.parse.Parse;
import com.parse.ParseInstallation;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String FRAGMENT_TAG = "fragment_tag";
    private FragmentManager mFragmentManager;
    String result = "";
    TextView textReturned;


    public static final int[] comedores = {
            R.mipmap.casalibertad,
            R.mipmap.centro,
            R.mipmap.cuautepec,
            R.mipmap.delvalle,
            R.mipmap.sanlorenzo

    };
    public void updateResult(String inputText) {
        result = inputText;
        textReturned.setText(result);

    }
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.drawer_layout), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    public void openSocial(View redSocial, String url) throws Exception
    {
        String link = url;
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }
    public boolean existeInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public void onDataPass(String data) {
        Log.d("LOG", "hello " + data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textReturned = (TextView)findViewById(R.id.fbUser);

        /*Parse.initialize(this, "JGTQI9SubWhcm34J6JUk690fGfnmldVXyTr2wNbW",
                "w9Icxfl0ZdUsErn4BEKIuprLY1D50AM6j3cpxDoN");
        ParseInstallation.getCurrentInstallation().saveInBackground();*/

        mFragmentManager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView titulos = (TextView) findViewById(R.id.titulos_card);
        titulos.setBackgroundColor(Color.argb(65,73,73,73) );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prueba de boton flotante", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            toggleFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleFragment() {
        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(android.R.id.content, new LoginFacebook_CUACM(),FRAGMENT_TAG);
        transaction.commit();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.CL) {

            Intent a = new Intent(MainActivity.this, Comedores.class);
            a.putExtra("nombreComedor","Casa Libertad");
            a.putExtra("imagen",comedores[0]);
            startActivity(a);

        } else if (id == R.id.CH) {

            Intent a = new Intent(MainActivity.this, Comedores.class);
            a.putExtra("nombreComedor","Centro Histórico");
            a.putExtra("imagen",comedores[1]);
            startActivity(a);

        } else if (id == R.id.Cuau) {

            Intent a = new Intent(MainActivity.this, Comedores.class);
            a.putExtra("nombreComedor","Cuautepec");
            a.putExtra("imagen", comedores[2]);
            startActivity(a);

        } else if (id == R.id.DV) {
            Intent a = new Intent(MainActivity.this, Comedores.class);
            a.putExtra("nombreComedor","Del Valle");
            a.putExtra("imagen",comedores[3]);
            startActivity(a);

        } else if (id == R.id.SLT) {
            Intent a = new Intent(MainActivity.this, Comedores.class);
            a.putExtra("nombreComedor","San Lorenzo Tezonco");
            a.putExtra("imagen",comedores[4]);
            startActivity(a);

        } else if (id == R.id.facebookC) {
            String tw = "http://www.facebook.com/comedoruacm";
            View view = (View) findViewById(R.id.facebookC);
            try {
                if(existeInternet()){
                    openSocial(view, tw);
                }else{
                    showSnackBar("No hay internet. Verifica tu conexión");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.facebookMoba) {
            String tw = "http://www.facebook.com/mobauacm";
            View view = (View) findViewById(R.id.facebookMoba);
            try {
                if(existeInternet()){
                    openSocial(view, tw);
                }else{
                    showSnackBar("No hay internet. Verifica tu conexión");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
