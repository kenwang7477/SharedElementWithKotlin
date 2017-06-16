package com.practice.kenwang.sharedelement

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FirstFragment : Fragment() {
    val imageURL: String = "http://i.imgur.com/mtPxFVH.jpg"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstImage = view?.findViewById(R.id.first_image) as ImageView
        ViewCompat.setTransitionName(firstImage, "image")
        firstImage.setOnClickListener{
            activity.supportFragmentManager.beginTransaction()
                    .addSharedElement(firstImage, ViewCompat.getTransitionName(firstImage))
                    .replace(R.id.container_main, SecondFragment.newInstance(ViewCompat.getTransitionName(firstImage), imageURL))
                    .addToBackStack(SecondFragment::class.java.simpleName)
                    .commit()
        }
        Glide.with(context).load(imageURL).diskCacheStrategy(DiskCacheStrategy.NONE).into(firstImage)
    }

}