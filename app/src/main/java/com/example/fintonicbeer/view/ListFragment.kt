package com.example.fintonicbeer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintonicbeer.R
import com.example.fintonicbeer.adapters.BeerListAdapter
import com.example.fintonicbeer.model.Beer
import com.example.fintonicbeer.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.ArrayList

class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val beerListAdapter = BeerListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        beerList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = beerListAdapter
        }

        refreshLayout.setOnRefreshListener {
            beerList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.GONE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.beersList.observe(viewLifecycleOwner, { beers ->
            beers?.let {
                beerList.visibility = View.VISIBLE
                beerListAdapter.updateBeerList(beers as ArrayList<Beer>)
            }
        })

        viewModel.beersLoadError.observe(viewLifecycleOwner, { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    beerList.visibility = View.GONE
                }
            }
        })
    }

}