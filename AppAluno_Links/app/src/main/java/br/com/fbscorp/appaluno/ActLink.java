package br.com.fbscorp.appaluno;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;

import br.com.fbscorp.appaluno.Controller.LinksController;
import br.com.fbscorp.appaluno.Model.Link;
import br.com.fbscorp.appaluno.database.DB;

import android.database.*;

public class ActLink extends AppCompatActivity {

    private ListView lst_links;
    private ArrayAdapter<Link> adpLinks;
    private LinksController linksController;

    private DB banco;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_link);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linksController = new LinksController();

        //CRIAÇÃO DO BANCO
        try {
            banco = new DB(this);
            conn = banco.getWritableDatabase();

            adpLinks = linksController.ObterTodosLinks(this,conn);

            //Toast.makeText(getApplicationContext(),"Banco criado com sucesso!", Toast.LENGTH_SHORT).show();

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro na criação do banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        //RECUPERA O LISTVIEW
        lst_links = (ListView)findViewById(R.id.lst_links);

        //VINCULA O LISTVIEW AO ADAPTER CORRESPONDENTE
        lst_links.setAdapter(adpLinks);

        //SETA O CLIQUE NO ITEM PARA ABRIR O LINK NO NAVEGADOR
        lst_links.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
                        navegarInternet(id);
                }

            }
        });
    }
    //METODO QUE REALIZA A ABERTURA DO NAVEGADOR COM O  LINK CORRESPONDENTE
    private void navegarInternet(long id) {
        Link url = adpLinks.getItem((int) id);
        Uri uri = Uri.parse(url.getUrl());
        Intent itNavegar = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(itNavegar);
    }
}
