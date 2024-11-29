package com.example.meds2025iv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper{
    private static final String name = "bancoMeds";
    private static final int version = 1;

    public Conexao(Context context){
        super(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE remedios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "quantdia REAL, " +
                "quanthj REAL, " +
                "quantcaixa REAL, " +
                "quantdez REAL)";
        db.execSQL(createTable); // Executa a criação da tabela
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}

