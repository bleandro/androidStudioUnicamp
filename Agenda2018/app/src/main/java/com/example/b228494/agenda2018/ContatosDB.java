package com.example.b228494.agenda2018;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ContatosDB extends SQLiteOpenHelper {
    public static final String TAG = "sql";
    public static final String NOME_BANCO = "MeuBancodeDados.db";
    public static final int VERSAO_BANCO = 1;
    public static final String TABLE_NAME = "contatos";
    public static final String COLUNA1 = "nome";
    public static final String COLUNA2 = "telefone";
    public static final String COLUNA3 = "email";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUNA1 + "TEXT," +
                    COLUNA2 + "INT," +
                    COLUNA3 + "TEXT)";

    public ContatosDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        Log.d(TAG, "onCreate: Tabela " + TABLE_NAME + " criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long salvaContato(Contato contato) {
        long id = contato.get_id();

        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put(COLUNA1, contato.getNome());
            valores.put(COLUNA2, contato.getTelefone());
            valores.put(COLUNA3, contato.getEmail());

            if (id != 0) {
                String _id = String.valueOf(id);
                return db.update(TABLE_NAME, valores, "id=?", new String[] {_id});
            } else {
                return db.insert(TABLE_NAME, null, valores);
            }
        }
        catch (Exception e){
            return -1;
        }
        finally {
            db.close();
        }
    }

    public int apagaContato (String nomeContato) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            int count = db.delete(TABLE_NAME, "nome=?", new String[] {nomeContato});
            Log.i(TAG, "apagaContato: deletou " + count + " registros");

            return count;
        }
        finally {
            db.close();
        }
    }

    public ArrayList<Contato> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Contato> lista = new ArrayList<>();

        try {
            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex("_id"));
                    String nome = cursor.getString(cursor.getColumnIndex("nome"));
                    String email = cursor.getString(cursor.getColumnIndex("email"));
                    int telefone = cursor.getInt(cursor.getColumnIndex("telefone"));

                    Contato currentContact = new Contato(id, nome, telefone, email);

                    lista.add(currentContact);
                } while (cursor.moveToNext());
            }
        }
        finally {
            db.close();
        }

        return lista;
    }

    public Contato pesquisaContato(String nomeContato) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            Cursor cursor = db.query(TABLE_NAME, null, "nome=?", new String[] {nomeContato}, null, null, null);

            if (cursor.moveToFirst()) {
                    long id = cursor.getLong(cursor.getColumnIndex("_id"));
                    String nome = cursor.getString(cursor.getColumnIndex("nome"));
                    String email = cursor.getString(cursor.getColumnIndex("email"));
                    int telefone = cursor.getInt(cursor.getColumnIndex("telefone"));

                    return new Contato(id, nome, telefone, email);
            }
        }
        finally {
            db.close();
        }

        return null;
    }
}
