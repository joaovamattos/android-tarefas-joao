package ads.vilhena.ifro.edu.br.tarefasjoao.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ads.vilhena.ifro.edu.br.tarefasjoao.model.Tarefa;

@Dao
public interface TarefaDAO {

    @Query("select * from tarefas")
    List<Tarefa> listarTodos();

    @Query("select * from tarefas where id = :id")
    Tarefa listarUm(int id);

    @Insert
    void inserir(Tarefa tarefa);

    @Update
    void alterar(Tarefa tarefa);

    @Delete
    void deletar(Tarefa tarefa);
}
