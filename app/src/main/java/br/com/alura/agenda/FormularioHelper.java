package br.com.alura.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by barcanjo on 27/06/2017.
 *
 * Classe que auxilia na criação de uma instância de Aluno
 * a partir dos dados extraídos dos campos na Activity Formulario.
 */
public class FormularioHelper {

    private RatingBar campoNota;
    private EditText campoSite;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private EditText campoNome;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);

        aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setSite(campoSite.getText().toString());

        return aluno;
    }

    public void preencherFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoNota.setProgress(aluno.getNota().intValue());
        campoSite.setText(aluno.getSite());
        campoTelefone.setText(aluno.getTelefone());

        this.aluno = aluno;
    }
}
