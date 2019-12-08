package com.kurocho.speech2textrecorder.ratioFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kurocho.speech2textrecorder.R

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [RatioFragment.OnListFragmentInteractionListener] interface.
 */
class RatioFragment : Fragment() {

    private var ratioList : List<Ratio>? = null

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            ratioList = it.getParcelableArrayList(RATIO_LIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ratio_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                 LinearLayoutManager(context)
                adapter =
                    ratioList?.let {
                        MyRatioRecyclerViewAdapter(
                            it,
                            listener
                        )
                    }
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Ratio?)
    }

    companion object {
        const val RATIO_LIST = "ratio-list"

        fun newInstance(myList : ArrayList<Ratio>): RatioFragment {
            val args = Bundle()
            args.putParcelableArrayList(RATIO_LIST, myList)
            val fragment = RatioFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
