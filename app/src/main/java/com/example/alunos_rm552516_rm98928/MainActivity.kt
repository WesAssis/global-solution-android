package com.example.alunos_rm552516_rm98928

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.alunos_rm552516_rm98928.R
import com.example.alunos_rm552516_rm98928.viewmodel.EventoAdapter
import com.example.alunos_rm552516_rm98928.viewmodel.EventoViewModel
import com.example.alunos_rm552516_rm98928.viewmodel.EventoViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EventoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configura a Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Eventos Extremos" // Defina o título da activity

        // Inicializa RecyclerView e Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val eventosAdapter = EventoAdapter(emptyList()) { evento ->
            viewModel.removerEvento(evento)
        }
        recyclerView.adapter = eventosAdapter

        // Campos de entrada
        val editLocal = findViewById<EditText>(R.id.editLocal)
        val editTipo = findViewById<EditText>(R.id.editTipo)
        val editImpacto = findViewById<EditText>(R.id.editGrau)
        val editData = findViewById<EditText>(R.id.editData)
        val editPessoas = findViewById<EditText>(R.id.editPessoas)
        val button = findViewById<Button>(R.id.button)

        // Ao clicar no botão de inclusão
        button.setOnClickListener {
            // Validação de campos obrigatórios
            if (editLocal.text.isEmpty() || editTipo.text.isEmpty() || editImpacto.text.isEmpty() ||
                editData.text.isEmpty() || editPessoas.text.isEmpty()) {

                if (editLocal.text.isEmpty()) editLocal.error = "Preencha o local"
                if (editTipo.text.isEmpty()) editTipo.error = "Preencha o tipo"
                if (editImpacto.text.isEmpty()) editImpacto.error = "Preencha o grau"
                if (editData.text.isEmpty()) editData.error = "Preencha a data"
                if (editPessoas.text.isEmpty()) editPessoas.error = "Preencha o número"
                return@setOnClickListener
            }

            // Adicionando o evento no ViewModel
            viewModel.adicionarEvento(
                local = editLocal.text.toString(),
                tipo = editTipo.text.toString(),
                impacto = editImpacto.text.toString(),
                data = editData.text.toString(),
                pessoasAfetadas = editPessoas.text.toString().toInt()
            )

            // Limpa os campos após a adição
            editLocal.text.clear()
            editTipo.text.clear()
            editImpacto.text.clear()
            editData.text.clear()
            editPessoas.text.clear()
        }

        // Configuração do ViewModel
        val viewModelFactory = EventoViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EventoViewModel::class.java)

        // Observando as mudanças na lista de eventos
        viewModel.eventosLiveData.observe(this) { eventos ->
            eventosAdapter.updateEventos(eventos) // Atualizando o RecyclerView com a nova lista
        }
    }
}
