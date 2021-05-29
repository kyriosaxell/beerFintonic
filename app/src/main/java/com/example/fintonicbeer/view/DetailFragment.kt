package com.example.fintonicbeer.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.fintonicbeer.R
import com.example.fintonicbeer.databinding.FragmentDetailBinding
import com.example.fintonicbeer.model.BeerColorPalette
import com.example.fintonicbeer.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private var beerUuid = 0
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            beerUuid = DetailFragmentArgs.fromBundle(it).beerUuid
        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(beerUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.beerListData.observe(viewLifecycleOwner, { beer ->
            beer?.let { it ->
                dataBinding.beer = beer

                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource).generate { palette ->
                    val intColor = palette?.vibrantSwatch?.rgb ?: 0
                    val myPalette = BeerColorPalette(intColor)
                    dataBinding.palette = myPalette
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })
    }

}