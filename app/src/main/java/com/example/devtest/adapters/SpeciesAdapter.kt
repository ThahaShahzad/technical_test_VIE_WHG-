package com.example.devtest.adapters

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.devtest.R
import com.example.devtest.databinding.SpeciesRvItemsBinding
import com.example.devtest.models.Specie
import com.example.devtest.ui.fragments.Fragment2
import com.squareup.picasso.Picasso

class SpeciesAdapter() : RecyclerView.Adapter<SpeciesAdapter.SpecieViewHolder>() {

    inner class SpecieViewHolder(val binding: SpeciesRvItemsBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Specie>() {
        override fun areItemsTheSame(oldItem: Specie, newItem: Specie): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Specie, newItem: Specie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var species: List<Specie>?
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    // method for filtering our recyclerview items.
    fun filterList(filteredList: List<Specie>) {
        // below line is to add our filtered
        // list in our course array list.
        species = filteredList
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun getItemCount() = species?.size!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        return SpecieViewHolder(SpeciesRvItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        holder.binding.apply {
            val currentSpecie = species!![position]
            tvName.text = currentSpecie.name
            tvSciname.text = currentSpecie.sciname
            tvEnvironment.text = TextUtils.join(", ",currentSpecie.environment)

            Picasso
                .get()
                .load("https://www.fishbase.org/images/thumbnails/jpg/tn_Abhia_u1.jpg")
                .into(ivThumbnail);
        }
        // onclick to go to 2nd fragment
        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val fragment2 = Fragment2()

                val currentSpecie = species!![position]

                // send id of clickedItem to 2nd frag
                val bundle = Bundle()
                bundle.putString("id", currentSpecie.id.toString())
                fragment2.arguments = bundle;
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragment1, fragment2).addToBackStack(null).commit()

            }
        })
    }
}
