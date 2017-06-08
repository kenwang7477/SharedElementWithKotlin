package com.practice.kenwang.sharedelement

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class RecyclerViewFragment: Fragment() {
    private val imageArray:Array<String> = arrayOf("https://scontent-tpe1-1.xx.fbcdn.net/v/t1.0-9/s851x315/18446939_1001361036664932_4104315194504340321_n.jpg?oh=c0577b85c565d132c17688773010dfa4&oe=599FF716",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t1.0-0/s480x480/18920390_1014811138653255_999094087349073167_n.jpg?oh=b94f780cc8f4108cc7be9552140d93a3&oe=59AEA3FB",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t31.0-8/18880318_1014711481996554_7004240665043030820_o.jpg?oh=72be2ca2ccbb0d95e4290d1dfbcaf90b&oe=59AB064D",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t31.0-8/18836703_1014614058672963_2230303680990487678_o.jpg?oh=5ff1def29b6f6be3d90db286f8f49dbf&oe=59E1EA0B",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t1.0-0/p480x480/18881943_1014240878710281_5387029162445728050_n.jpg?oh=7f7ec18265d3087521a405877a52a423&oe=59DFAEF8",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t1.0-0/p480x480/18920139_10154723582892896_2122304689143520051_n.jpg?oh=41c2cae1599b7a90f1a96992f24523d6&oe=59E3609C",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t1.0-0/s480x480/18892898_1013992588735110_1556048840265042898_n.jpg?oh=6d6475d1a14eb858097da72323a17f88&oe=599FBD97",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t1.0-0/p480x480/18839377_1013609695440066_9161489319081576690_n.jpg?oh=047d3c2cc3f83a663528d68aadfc12e5&oe=59D9056C",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t1.0-0/q85/s480x480/18814053_1013166178817751_6520508330077308596_n.jpg?oh=879159c226d8066e4c1aab0c8f29d851&oe=59ADA377",
            "https://scontent-tpe1-1.xx.fbcdn.net/v/t1.0-0/s480x480/18839182_1013163928817976_6836708595207529204_n.jpg?oh=099ce5d8f2cd2ab22fc7b61adbd82883&oe=59E94D79")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view?.findViewById(R.id.recyclerview) as RecyclerView
        val listItems = ArrayList<ListItem>(10)
        imageArray.forEachIndexed { index, s -> listItems.add(ListItem("title"+index, s)) }
        val adapter = MyAdapter()
        adapter.list = listItems
        adapter.listener = object: MyAdapter.Listener{
            override fun onClickItem(view: View, item: ListItem) {
                activity.supportFragmentManager.beginTransaction()
                        .addSharedElement(view, ViewCompat.getTransitionName(view))
                        .replace(R.id.container_main, SecondFragment.newInstance(ViewCompat.getTransitionName(view), item.imageURL))
                        .addToBackStack(SecondFragment::class.java.simpleName)
                        .commit()
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, (recyclerView.layoutManager as LinearLayoutManager).orientation))
    }

    class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {
        lateinit var list:List<ListItem>
        var listener:Listener? = null

        interface Listener {
            fun onClickItem(view:View, item:ListItem)
        }

        override fun onBindViewHolder(p0: MyViewHolder?, p1: Int) {
            val item = list[p1]
            p0?.update(item)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup?, position: Int): MyViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val viewHolder = MyViewHolder(layoutInflater.inflate(R.layout.item_myviewholder, viewGroup, false))
            viewHolder.image.setOnClickListener{
                listener?.onClickItem(viewHolder.image, list[viewHolder.layoutPosition])
            }
            return viewHolder
        }

        override fun getItemCount(): Int {
            return list.size
        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById(R.id.myviewholder_title) as TextView
        var image = view.findViewById(R.id.myviewholder_image) as ImageView

        fun update(item: ListItem) {
            title.text = item.title
            Glide.with(itemView.context).load(item.imageURL).diskCacheStrategy(DiskCacheStrategy.NONE).into(image)
            ViewCompat.setTransitionName(image, item.imageURL)
        }
    }
}