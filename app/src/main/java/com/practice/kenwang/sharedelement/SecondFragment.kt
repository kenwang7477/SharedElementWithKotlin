package com.practice.kenwang.sharedelement

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Exception

class SecondFragment: Fragment() {
    private lateinit var secondImage: ImageView

    companion object Constant {
        val TRANSITION_NAME = "transitionName"
        val IMAGE_URL = "imageURL"

        fun newInstance(transitionName: String, imageURL: String): SecondFragment {
            var fragment = SecondFragment()
            var bundle = Bundle()
            bundle.putString(TRANSITION_NAME, transitionName)
            bundle.putString(IMAGE_URL, imageURL)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        secondImage = view?.findViewById(R.id.second_image) as ImageView
        val transitionName = arguments.get(TRANSITION_NAME) as String
        val imageURL = arguments.get(IMAGE_URL) as String
        ViewCompat.setTransitionName(secondImage, transitionName)
        Glide.with(context).load(imageURL).diskCacheStrategy(DiskCacheStrategy.NONE).listener(object: RequestListener<String, GlideDrawable> {
            override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }
        })
                .into(secondImage)
    }
}