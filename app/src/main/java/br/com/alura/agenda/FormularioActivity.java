package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper formularioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // Retorna a Intent responsavel por iniciar esta Activity
        Intent intent = getIntent();

        // Retorna da Intent o objeto extra aluno, do tipo Aluno, deserializado
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        formularioHelper = new FormularioHelper(this);
        if (null != aluno) {
            formularioHelper.preencherFormulario(aluno);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        Recupera a instância de MenuInflater para criar um
        menu do formulário através do menu menu_formulario
         */
        MenuInflater menuInflater = getMenuInflater();
        // define uma opção no menu recuperado
        menuInflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Método invocado para todas as opções selecionadas do menu.
     * @param item A opção selecionada (ou item) do menu.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verifica o ID do item
        switch (item.getItemId()) {
            case R.id.formulario_menu_ok:
                // Cria uma instância de aluno a partir dos dados do formulário
                Aluno aluno = formularioHelper.getAluno();

                // Cria uma instância de alunoDAO para inserir o aluno
                AlunoDAO alunoDAO = new AlunoDAO(this);

                // Verifica se é um novo aluno, e insere ou altera
                if (null == aluno.getId()) {
                    // Insere o aluno no banco de dados
                    alunoDAO.inserir(aluno);
                } else {
                    // Altera o aluno no banco de dados
                    alunoDAO.alterar(aluno);
                }
                // Fecha a conexão com o banco de dados
                alunoDAO.close();
                /**
                 * Exibe uma mensagem rápida, definindo a Activity Formulario
                 * como a responsável por exibí-la.
                 */
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();

                // Destrói a instância da Activity para que ela não seja reaberta através do botão voltar
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
