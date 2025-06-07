package com.example.alunos_rm552516_rm98928

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alunos_rm552516_rm98928.viewmodel.EventoAdapter
import com.example.alunos_rm552516_rm98928.viewmodel.EventoViewModel
import com.example.alunos_rm552516_rm98928.viewmodel.EventoViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var eventoViewModel: EventoViewModel
    private lateinit var eventoAdapter: EventoAdapter

    private lateinit var edtLocal: EditText
    private lateinit var edtTipo: EditText
    private lateinit var edtImpacto: EditText
    private lateinit var edtData: EditText
    private lateinit var edtPessoasAfetadas: EditText
    private lateinit var btnIncluir: Button
    private lateinit var rvEventos: RecyclerView
    private lateinit var btnIdentificacao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialização dos componentes da UI
        edtLocal = findViewById(R.id.edtLocal)
        edtTipo = findViewById(R.id.edtTipo)
        edtImpacto = findViewById(R.id.edtImpacto) // CORREÇÃO: ID do EditText de impacto
        edtData = findViewById(R.id.edtData)
        edtPessoasAfetadas = findViewById(R.id.edtPessoasAfetadas)
        btnIncluir = findViewById(R.id.btnIncluir)
        rvEventos = findViewById(R.id.rvEventos)
        btnIdentificacao = findViewById(R.id.btnIdentificacao)

        // Inicialização do ViewModel
        val factory = EventoViewModelFactory(application)
        eventoViewModel = ViewModelProvider(this, factory).get(EventoViewModel::class.java)

        // Configuração do RecyclerView
        eventoAdapter = EventoAdapter(emptyList()) { evento ->
            eventoViewModel.removerEvento(evento)
        }
        rvEventos.layoutManager = LinearLayoutManager(this)
        rvEventos.adapter = eventoAdapter

        // Observar mudanças na lista de eventos do ViewModel
        eventoViewModel.eventosLiveData.observe(this) { eventos ->
            eventoAdapter.updateEventos(eventos)
        }

        // Listener do botão Incluir
        btnIncluir.setOnClickListener {
            val local = edtLocal.text.toString().trim()
            val tipo = edtTipo.text.toString().trim()
            val impacto = edtImpacto.text.toString().trim()
            val data = edtData.text.toString().trim()
            val pessoasAfetadasStr = edtPessoasAfetadas.text.toString().trim()

            // Validação de campos
            if (local.isEmpty() || tipo.isEmpty() || impacto.isEmpty() || data.isEmpty() || pessoasAfetadasStr.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pessoasAfetadas = pessoasAfetadasStr.toIntOrNull()

            if (pessoasAfetadas == null || pessoasAfetadas <= 0) {
                Toast.makeText(this, "Número de pessoas afetadas deve ser maior que zero!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Adicionar evento ao ViewModel
            eventoViewModel.adicionarEvento(local, tipo, impacto, data, pessoasAfetadas)

            // Limpar campos após adicionar
            edtLocal.text.clear()
            edtTipo.text.clear()
            edtImpacto.text.clear()
            edtData.text.clear()
            edtPessoasAfetadas.text.clear()
        }

        // Listener do botão Identificação
        btnIdentificacao.setOnClickListener {
            val intent = Intent(this, IdentificacaoActivity::class.java)
            startActivity(intent)
        }
    }
}
