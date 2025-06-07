package com.example.alunos_rm552516_rm98928.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.alunos_rm552516_rm98928.model.EventoModel

class EventoViewModel(application: Application) : AndroidViewModel(application) {

    private val _eventos = MutableLiveData<List<EventoModel>>(emptyList())
    val eventosLiveData: LiveData<List<EventoModel>> = _eventos

    fun adicionarEvento(
        local: String,
        tipo: String,
        impacto: String,
        data: String,
        pessoasAfetadas: Int
    ) {
        val novoEvento = EventoModel(
            local = local,
            tipo = tipo,
            impacto = impacto,
            data = data,
            pessoasAfetadas = pessoasAfetadas
        )

        val listaAtual = _eventos.value?.toMutableList() ?: mutableListOf()
        listaAtual.add(novoEvento)
        _eventos.value = listaAtual
    }

    fun removerEvento(evento: EventoModel) {
        val listaAtual = _eventos.value?.toMutableList() ?: mutableListOf()
        listaAtual.remove(evento)
        _eventos.value = listaAtual
    }
}
