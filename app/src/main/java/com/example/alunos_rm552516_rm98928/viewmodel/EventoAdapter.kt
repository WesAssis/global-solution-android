package com.example.alunos_rm552516_rm98928.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alunos_rm552516_rm98928.R
import com.example.alunos_rm552516_rm98928.model.EventoModel

class EventoAdapter(
    private var eventos: List<EventoModel>,
    private val onDeleteClick: (EventoModel) -> Unit
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    inner class EventoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtLocal: TextView = view.findViewById(R.id.txtLocal)
        val txtTipo: TextView = view.findViewById(R.id.txtTipo)
        val txtImpacto: TextView = view.findViewById(R.id.txtImpacto)
        val txtData: TextView = view.findViewById(R.id.txtData)
        val txtPessoas: TextView = view.findViewById(R.id.txtPessoas)
        val btnExcluir: Button = view.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]
        holder.txtLocal.text = "Local: ${evento.local}"
        holder.txtTipo.text = "Tipo: ${evento.tipo}"
        holder.txtImpacto.text = "Impacto: ${evento.impacto}"
        holder.txtData.text = "Data: ${evento.data}"
        holder.txtPessoas.text = "Pessoas afetadas: ${evento.pessoasAfetadas}"

        holder.btnExcluir.setOnClickListener {
            onDeleteClick(evento)
        }
    }

    override fun getItemCount(): Int = eventos.size

    fun updateEventos(novosEventos: List<EventoModel>) {
        this.eventos = novosEventos
        notifyDataSetChanged()
    }
}
