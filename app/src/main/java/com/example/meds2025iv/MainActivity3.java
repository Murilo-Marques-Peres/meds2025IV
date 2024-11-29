package com.example.meds2025iv;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    Button buttonAdd;
    Button buttonRemove;

    TextView campoNome;
    TextView campoDose;
    TextView campoHoje;
    TextView campoCaixa;
    Spinner medSpinner;

    private void updateSpinner() {
        List<String> updatedMeds = pegarTodosMeds(); // Get the updated list from the database

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.lay_spinner, updatedMeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the dropdown layout

        medSpinner.setAdapter(adapter); // Set the adapter to the spinner
    }


    Conexao conexao = new Conexao(this);

    public String fixingString(String nome) {
        String[] arrayNome = nome.split(" ", 0);
        String nomeJoin = String.join(" ", arrayNome);
        return nomeJoin;
    }

    public List<String> pegarTodosMeds() {
        List<String> meds = new ArrayList<>();
        SQLiteDatabase db = conexao.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome FROM remedios", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                meds.add(name);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return meds;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        buttonAdd = findViewById(R.id.button);
        buttonRemove = findViewById(R.id.button3);
        campoNome = findViewById(R.id.editTextTextPersonName);
        campoDose = findViewById(R.id.editTextTextPersonName2);
        campoHoje = findViewById(R.id.editTextTextPersonName5);
        campoCaixa = findViewById(R.id.novoIdText1);
        medSpinner = findViewById(R.id.spinner_medication);
        List<String> listaMeds = pegarTodosMeds();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.lay_spinner, listaMeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        medSpinner.setAdapter(adapter);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = campoNome.getText().toString();
                nome = fixingString(nome);
                float dose = Float.parseFloat(campoDose.getText().toString());
                float hoje = Float.parseFloat(campoHoje.getText().toString());
                float quantCaixa = Float.parseFloat(campoCaixa.getText().toString());

                adicionarRemedio(nome, dose, hoje, quantCaixa);
                campoNome.setText("");
                campoDose.setText("");
                campoHoje.setText("");
                campoCaixa.setText("");
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeDelete = medSpinner.getSelectedItem().toString();
                excluirRemedio(nomeDelete);
            }
        });

    }

    public void adicionarRemedio(String nome, float quantDia, float quantHoje, float quantCaixa) {
        /// Area Banco de Dados ///
        SQLiteDatabase db = conexao.getWritableDatabase();

        //////////////////////////////////////////////////////////////
        MainActivity2 mainActivity2 = new MainActivity2();
        float diasAtuais = mainActivity2.calcularAtualNovo();
        float numeroDez = quantHoje + diasAtuais * quantDia;
        if (quantCaixa != 0) {
            while (numeroDez > quantCaixa) {
                numeroDez -= quantCaixa;
            }
        }

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("quantdia", quantDia);
        values.put("quanthj", quantHoje);
        values.put("quantcaixa", quantCaixa);
        values.put("quantdez", numeroDez);


        db.insert("remedios", null, values); // Insere o registro
        db.close(); // Fecha o banco de dados

        Toast.makeText(MainActivity3.this, "Ação de Adicionar Realizada", Toast.LENGTH_LONG).show();
        updateSpinner();
    }

    public void excluirRemedio(String nome) {
        new androidx.appcompat.app.AlertDialog.Builder(MainActivity3.this)
                .setTitle("Confirmar Exclusão")
                .setMessage("Você tem certeza que quer deletar \"" + nome + "\"?")
                .setPositiveButton("OK", (dialog, which) -> {
                    SQLiteDatabase db = conexao.getWritableDatabase();

                    int rowsDeleted = db.delete("remedios", "nome = ?", new String[]{nome});
                    if (rowsDeleted > 0) {
                        Toast.makeText(MainActivity3.this, "Medicação excluída com sucesso", Toast.LENGTH_SHORT).show();
                        updateSpinner(); // Refresh the spinner
                    } else {
                        Toast.makeText(MainActivity3.this, "Falha ao excluir a medicação", Toast.LENGTH_SHORT).show();
                    }

                    db.close();
                })
                .setNegativeButton("Cancelar", (dialog, which) ->
                        Toast.makeText(MainActivity3.this, "Ação cancelada", Toast.LENGTH_SHORT).show())
                .show();
    }


}