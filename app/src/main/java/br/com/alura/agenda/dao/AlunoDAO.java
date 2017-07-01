package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by barcanjo on 27/06/2017.
 *
 * Classe DAO de Aluno.
 * Extende a classe SQLiteOpenHelper que contém os métodos
 * necessários para abertura da conexão, execução de Scripts SQL
 * e fechamento da conexão.
 */
public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE aluno(");
        sb.append("id INTEGER PRIMARY KEY,");
        sb.append("nome TEXT NOT NULL,");
        sb.append("endereco TEXT,");
        sb.append("telefone TEXT,");
        sb.append("site TEXT,");
        sb.append("nota REAL);");

        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS aluno";
        db.execSQL(sql);
    }

    /**
     * Insere um aluno no banco de dados.
     *
     * @param aluno O aluno que será inserido no banco de dados.
     */
    public void inserir(Aluno aluno) {
        // Pega uma instância do tipo escrita do banco de dados do SQLite
        SQLiteDatabase database = getWritableDatabase();

        // Retorna um ContentValues a partir do aluno
        ContentValues dados = getContentValuesAluno(aluno);

        // Insere na tabela aluno os dados informados na instância de ContentValues
        database.insert("aluno", null, dados);
    }

    /*
    Cria uma instância de ContentValues e associa
    os campos da tabela e os valores do aluno
     */

    /**
     * Cria uma instância de ContentValues e associa
     * os campos da tabela e os valores do aluno.
     *
     * @param aluno O aluno que será utilizado para criar o ContentValues.
     * @return Uma instância de ContentValues criada a partir do parâmetro aluno.
     */
    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        return dados;
    }

    public List<Aluno> getLista() {
        // Cria a query de consulta dos alunos
        String sqlConsulta = "SELECT * FROM aluno";

        // Pega a instância do tipo leitura do banco de dados do SQLite
        SQLiteDatabase database = getReadableDatabase();

        /*
        Cria uma instância de Cursor com os resultados da consulta.
        É utilizado o método rawQuery, da instância do SQLiteDatabase,
        para que a execução do SQL retorne um objeto do tipo Cursor.
        A partir do Cursor é feita uma iteração para obter os dados
        de cada registro da tabela (cada aluno).
         */
        Cursor cursor = database.rawQuery(sqlConsulta, null);

        List<Aluno> lista = new ArrayList<Aluno>();

        // Se ao mover o cursos para próxima posição existir um registro...
        while (cursor.moveToNext()) {
            // Cria um aluno a partir dos dados do Cursor
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            lista.add(aluno);
        }

        // Fecha o Cursor para liberar memória
        cursor.close();

        // Retorna a lista de alunos
        return lista;
    }

    /**
     * Remove um aluno no banco de dados.
     * @param aluno O aluno que será removido
     */
    public void remover(Aluno aluno) {
        // Pega a referência de escrita do banco de dados
        SQLiteDatabase db = getWritableDatabase();

        /*
        Cria um array de Strings com os parâmetros utilizados para
        remover o aluno, neste caso o ID do aluuno
         */
        String[] params = {String.valueOf(aluno.getId())};

        // Remove o aluno utilizando o ID como critério e informa os parâmetros
        db.delete("aluno", "id = ?", params);
    }

    /**
     * Altera um aluno no banco de dados.
     * @param aluno O aluno que será alterado.
     */
    public void alterar(Aluno aluno) {
        // Pega a referência de escrita do banco de dados
        SQLiteDatabase db = getWritableDatabase();

        // Retorna um ContentValues a partir do aluno
        ContentValues dados = getContentValuesAluno(aluno);

        /*
        Cria um array de Strings com o parâmetro do ID utilizado para
        selecionar o aluno que será atualizado
         */
        String[] params = {aluno.getId().toString()};

        // Altera o aluno utilizando o ID como critério e informa os parâmetros
        db.update("aluno", dados, "id = ?", params);
    }
}
