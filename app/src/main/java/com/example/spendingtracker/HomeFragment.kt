package com.example.spendingtracker


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.spendingtracker.RealmObjects.CategoryObject
import io.realm.Realm


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView =  view.findViewById<RecyclerView>(R.id.card_container)

        val realmInstance = Realm.getDefaultInstance()

        val obj =  realmInstance.where(CategoryObject().javaClass).findAll()

        recyclerView.layoutManager =  GridLayoutManager(context, 2)

        recyclerView.adapter = CardContainerRecyclerAdapter(context,obj)

        return view
    }

}
