package com.example.spendingtracker


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


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
        val titles = arrayOf("Purchase","Withdrawal","Transfer","Bills","Transport","Entertainment")

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView =  view.findViewById<RecyclerView>(R.id.card_container)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        recyclerView.adapter = CardContainerRecyclerAdapter(context,titles)
        // Inflate the layout for this fragment
        return view
    }


}
