package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listViewAlunos;

    /**
     * Método executado sempre que a Activity é criada.
     * É recomendável chamar o metodo onCreate do pai dessa classe.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        // Recupera a instância da lista de alunos no arquivo xml
        listViewAlunos = (ListView) findViewById(R.id.lista_alunos);

        // Define o evento de clique no item da ListView
        listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Recupera o aluno na posição position da lista
                Aluno aluno = (Aluno) listViewAlunos.getItemAtPosition(position);

                // Inicia a Activity do Formulario
                Intent intentIrParaFormulario = new Intent(getBaseContext(), FormularioActivity.class);
                intentIrParaFormulario.putExtra("aluno", aluno);
                startActivity(intentIrParaFormulario);
                // Fim
            }
        });

        // recupe a instância do botão novo aluno da Activity ListaAlunos
        Button novoAluno = (Button) findViewById(R.id.lista_alunos_novo_aluno);
        // define uma ação para o evento de clique do botão
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cria uma intenção para chamar a Activity Formulario
                Intent intentIrParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentIrParaFormulario);
            }
        });

        // Registra um menu de contexto para ListView de alunos
        registerForContextMenu(listViewAlunos);
    }

    /**
     * Método executado sempre que o usuário retorna para a Activity.
     * É recomendável chamar o metodo onResume do pai dessa classe.
     */
    @Override
    protected void onResume() {
        super.onResume();
        carregarListaAlunos();
    }

    /**
     * Carrega a lista de alunos na ListView de alunos na Activity
     */
    private void carregarListaAlunos() {
        // Cria uma instância de alunoDAO para consultar os alunos
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> listaAlunos = alunoDAO.getLista();

        // Recupera a instância da lista de alunos no arquivo xml
        listViewAlunos = (ListView) findViewById(R.id.lista_alunos);

        // cria um adaptador de String para converter os valores numa View
        ArrayAdapter<Aluno> stringArrayAdapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, listaAlunos);

        // define o adapter de String na lista
        listViewAlunos.setAdapter(stringArrayAdapter);
    }

    /**
     * Método utilizado para a criação do menu de contexto.
     *
     * @param menu O menu de contexto que será configurado.
     * @param v
     * @param menuInfo Informações do menu e seus items.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        // super.onCreateContextMenu(menu, v, menuInfo);
        // Cria um item (MenuItem) para o menu e devolve sua referência
        MenuItem menuDeletar = menu.add("Deletar");
        // Define o evento de clique no item do menu
        menuDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                /*
                Converte o menuInfo em uma instância de AdapterContextMenuInfo
                para obter informações do item
                 */
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                /*
                Obtém a posição do item da ListView de alunos e faz um cast desse item em um Aluno
                 */
                Aluno aluno = (Aluno) listViewAlunos.getItemAtPosition(info.position);

                // Remove o aluno do banco de dados
                AlunoDAO alunoDAO = new AlunoDAO(getBaseContext());
                alunoDAO.remover(aluno);
                alunoDAO.close();
                // Fim

                // Recarrega a lista de alunos atualizada
                carregarListaAlunos();

                return false;
            }
        });
    }
}