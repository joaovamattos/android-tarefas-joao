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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ads.vilhena.ifro.edu.br.tarefasjoao.DAO.AppDatabase;
import ads.vilhena.ifro.edu.br.tarefasjoao.model.Tarefa;

public class AlterarTarefaActivity extends AppCompatActivity {

    private TextInputEditText tilAlterarDescricao;
    private TextView txtAlterarData;
    private TextView txtAlterarHora;
    private Switch swtRealizado;
    private Button btnAlterar;
    private Calendar dataHora = Calendar.getInstance();
    private Tarefa tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_tarefa);

        tilAlterarDescricao = findViewById(R.id.til_alterar_descricao);
        txtAlterarData = findViewById(R.id.txt_alterar_data);
        txtAlterarHora = findViewById(R.id.txt_alterar_hora);
        swtRealizado = findViewById(R.id.switch_realizado);
        btnAlterar = findViewById(R.id.btn_alterar);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        int id = args.getInt("id_tarefa");
        tarefa = AppDatabase.getAppDatabase(this).tarefaDAO().listarUm(id);

        tilAlterarDescricao.setText(tarefa.getDescricao());
        SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy");
        txtAlterarData.setText(formatarData.format(tarefa.getDataHora()));
        SimpleDateFormat formatarHora = new SimpleDateFormat("HH:mm");
        txtAlterarHora.setText(formatarHora.format(tarefa.getDataHora()));
        swtRealizado.setChecked(tarefa.getRealizado());

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarefa.setDescricao(tilAlterarDescricao.getEditableText().toString().trim());
                tarefa.setDataHora(dataHora.getTimeInMillis());
                tarefa.setRealizado(swtRealizado.isChecked());

                AppDatabase.getAppDatabase(AlterarTarefaActivity.this).tarefaDAO().alterar(tarefa);

                Intent intent = new Intent();
                intent.putExtra("resposta", "OK");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        txtAlterarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AlterarTarefaActivity.this, d, dataHora.get(Calendar.YEAR), dataHora.get(Calendar.MONTH),
                        dataHora.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtAlterarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AlterarTarefaActivity.this, t, dataHora.get(Calendar.HOUR),
                        dataHora.get(Calendar.MINUTE), true).show();
            }
        });

    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dataHora.set(Calendar.YEAR, year);
            dataHora.set(Calendar.MONTH, month);
            dataHora.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat formatacao = new SimpleDateFormat("dd/MM/yyyy");
            txtAlterarData.setText(formatacao.format(dataHora.getTime()));
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dataHora.set(Calendar.HOUR, hourOfDay);
            dataHora.set(Calendar.MINUTE , minute);
            SimpleDateFormat formatacao = new SimpleDateFormat("HH:mm");
            txtAlterarHora.setText(formatacao.format(dataHora.getTime()));
        }
    };
}
