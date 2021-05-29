package com.example.fintonicbeer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.fintonicbeer.R
import com.example.fintonicbeer.databinding.ItemBeerBinding
import com.example.fintonicbeer.model.Beer
import com.example.fintonicbeer.view.BeerClickListener
import com.example.fintonicbeer.view.ListFragmentDirections
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_beer.view.*

class BeerListAdapter(private val beerList: ArrayList<Beer>) :
    RecyclerView.Adapter<BeerListAdapter.DataViewHolder>(), BeerClickListener {

    class DataViewHolder(var view: ItemBeerBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemBeerBinding>(inflater, R.layout.item_beer, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val current = beerList[position]
        holder.view.beer = current
        holder.view.listener = this
    }

    fun updateBeerList(newBeerList: java.util.ArrayList<Beer>) {
        beerList.clear()
        beerList.addAll(newBeerList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = beerList.size

    override fun onBeerClicked(v: View) {
        val id = v.Id.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.beerUuid = id
        Navigation.findNavController(v).navigate(action)
    }

}