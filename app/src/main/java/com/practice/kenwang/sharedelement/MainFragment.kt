package com.practice.kenwang.sharedelement

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainFragment: Fragment() {
    val list: Array<String> = arrayOf("Fragment to Fragment", "RecyclerView to Fragment")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view?.findViewById(R.id.main_recyclerview) as RecyclerView
        val mainAdapter = MainAdapter()
        mainAdapter.list = list
        mainAdapter.listener = object: MainAdapter.MainListener{
            override fun onClickItem(position: Int) {
                when (position) {
                    0 -> {
                        toFragment(FirstFragment())
                    }

                    1 -> {
                        toFragment(RecyclerViewFragment())
                    }
                }
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mainAdapter
    }

    fun toFragment(fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container_main, fragment)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }

    class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {
        lateinit var list: Array<String>
        var listener: MainListener? = null

        interface MainListener {
            fun onClickItem(position: Int)
        }

        override fun onBindViewHolder(viewHolder: MainViewHolder, position: Int) {
            viewHolder.update(list[position])
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup?, p1: Int): MainViewHolder{
            val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.item_mainviewholder, viewGroup, false)
            val viewHolder = MainViewHolder(view)
            view.setOnClickListener{
                listener?.onClickItem(viewHolder.layoutPosition)
            }
            return viewHolder
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    class MainViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.findViewById(R.id.main_title) as TextView

        fun update(text: String) {
            title.text = text
        }
    }
}