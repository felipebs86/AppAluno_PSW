package br.com.fbscorp.appaluno_hist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;

import br.com.fbscorp.appaluno_hist.Controller.HistController;
import br.com.fbscorp.appaluno_hist.Model.Historico;
import br.com.fbscorp.appaluno_hist.database.DB;

import android.database.*;

public class ActHistorico extends AppCompatActivity {

    private GridView gvHist;
    private ArrayAdapter<Historico> adpHist;
    private HistController histController;

    private DB banco;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        histController = new HistController();

        //CRIAÇÃO DO BANCO
        try {
            banco = new DB(this);
            conn = banco.getWritableDatabase();

            adpHist = histController.ObterTodosHist(this, conn);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro na criação do banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        //RECUPERA O GRIDVIEW
        gvHist = (GridView)findViewById(R.id.gvHist);

        //VINCULA O LISTVIEW AO ADAPTER CORRESPONDENTE
        gvHist.setAdapter(adpHist);

        //SETA O CLIQUE NO ITEM PARA REALIZAR ALGUMA AÇÃO
        gvHist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
                        Toast.makeText(getApplicationContext(),"Clicável", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
