package com.iesvirreymorcillo.recyclerviewmontes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iesvirreymorcillo.recyclerviewmontes.Montes
import com.iesvirreymorcillo.recyclerviewmontes.databinding.ItemMontesBinding

class MontesViewHolder(private val binding: ItemMontesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        monteModel: Montes,
        onClickListener: (Montes) -> Unit,
        onClickDelete: (Int) -> Unit
    ) {
        binding.tvNombreMonte.text = monteModel.nombre
        Glide.with(binding.ivMontes.context).load(monteModel.foto).into(binding.ivMontes)
        binding.tvAltura.text = "Altura: ${monteModel.altura}"
        binding.tvContinente.text = "Continente: ${monteModel.continente}"


        itemView.setOnClickListener { onClickListener(monteModel) }
        binding.btnBorrar.setOnClickListener { onClickDelete(adapterPosition) }
    }
}
