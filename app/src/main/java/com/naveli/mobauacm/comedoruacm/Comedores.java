package com.naveli.mobauacm.comedoruacm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

public class Comedores extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_comedores);
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
                        alertaMenu("LUNES");                      
                        break;
                    case 1:
                        alertaMenu("MARTES");
                        break;
                    case 2:
                         alertaMenu("MIÉRCOLES");
                        break;
                    case 3:
                         alertaMenu("JUEVES");
                        break;
                    case 4:
                         alertaMenu("VIERNES");
                        break;

                }
            }
        });
    }
    public void alertaMenu(String dia){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dia);
        builder.setMessage("Aqui estara el menú del dia seleccionado que es: " + dia);
        builder.setPositiveButton("OK",null);
        builder.create();
        builder.show();
    }
}
