package ads.vilhena.ifro.edu.br.tarefasjoao;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ads.vilhena.ifro.edu.br.tarefasjoao.DAO.AppDatabase;
import ads.vilhena.ifro.edu.br.tarefasjoao.model.Tarefa;

public class CadastrarTarefaAcivity extends AppCompatActivity {

    private TextInputEditText tilDescricao;
    private TextView txtData;
    private TextView txtHora;
    private Button btnSalvar;
    private Calendar dataHora = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_tarefa_acivity);

        tilDescricao = findViewById(R.id.til_descricao);
        txtData = findViewById(R.id.txt_data);
        txtHora = findViewById(R.id.txt_hora);
        btnSalvar = findViewById(R.id.btn_salvar);

        txtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CadastrarTarefaAcivity.this, d, dataHora.get(Calendar.YEAR),
                        dataHora.get(Calendar.MONTH), dataHora.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(CadastrarTarefaAcivity.this, t, dataHora.get(Calendar.HOUR),
                        dataHora.get(Calendar.MINUTE), true).show();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarDescricao()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setDescricao(tilDescricao.getText().toString().trim());
                    tarefa.setDataHora(dataHora.getTimeInMillis());
                    AppDatabase.getAppDatabase(CadastrarTarefaAcivity.this).tarefaDAO().inserir(tarefa);
                    Intent intent = new Intent();
                    intent.putExtra("resposta", "OK");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    public boolean validarDescricao(){
        if(tilDescricao.getEditableText().toString().trim().equals("")){
            tilDescricao.setError("A descrição da tarefa não pode estar em branco");
            return false;
        }
        tilDescricao.setError(null);
        return true;
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dataHora.set(Calendar.YEAR, year);
            dataHora.set(Calendar.MONTH, month);
            dataHora.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat formatacao = new SimpleDateFormat("dd/MM/yyyy");
            txtData.setText(formatacao.format(dataHora.getTime()));
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dataHora.set(Calendar.HOUR, hourOfDay);
            dataHora.set(Calendar.MINUTE, minute);
            SimpleDateFormat formacao = new SimpleDateFormat("HH:mm");
            txtHora.setText(formacao.format(dataHora.getTime()));
        }
    };


}
