package com.naveli.mobauacm.comedoruacm;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.ParagraphStyle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Comedores extends AppCompatActivity {

    TextView dia;
    Bundle bundle;
    String[] dias = {
            "LUNES",
            "MARTES",
            "MIÉRCOLES",
            "JUEVES",
            "VIERNES"

    };
    int[] icon = {
            R.mipmap.lun,
            R.mipmap.mar,
            R.mipmap.mie,
            R.mipmap.jue,
            R.mipmap.vie
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_comedores, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.regresar) {
            Intent a = new Intent(Comedores.this, MainActivity.class);
            startActivity(a);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        setContentView(R.layout.activity_comedores);
        dia = (TextView) findViewById(R.id.dia);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(bundle.getString("nombreComedor"));

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
                        alertaMenu();                      
                        dia.setText(dias[0]);
                        break;
                    case 1:
                        alertaMenu();
                        dia.setText(dias[1]);
                        break;
                    case 2:
                         alertaMenu();
                         dia.setText(dias[2]);
                        break;
                    case 3:
                         alertaMenu();
                         dia.setText(dias[3]);
                        break;
                    case 4:
                         alertaMenu();
                         dia.setText(dias[4]);
                        break;

                }
            }
        });
    }
    public void alertaMenu(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setTitle(bundle.getString("nombreComedor"));
        builder.setView(inflater.inflate(R.layout.formato_menu,null))
                .setPositiveButton("OK", null).setView(dia.setText(dias[0]));
        builder.create();
        builder.show();
    }
}
