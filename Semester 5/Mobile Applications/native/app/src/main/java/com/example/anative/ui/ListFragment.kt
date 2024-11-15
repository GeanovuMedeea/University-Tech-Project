package com.example.anative.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.anative.R
import com.example.anative.viewModel.ItemAdapter
import com.example.anative.viewModel.MainViewModel
import logd

class ListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        if (!::adapter.isInitialized) {
            adapter = ItemAdapter(emptyList(),
                onItemClick = { item ->
                    val action = ListFragmentDirections.actionListFragmentToDetailFragment(item.id)
                    findNavController().navigate(action)
                },
                onDeleteClick = { item ->
                    AlertDialogDelete.showDeleteConfirmationDialog(requireContext()) {
                        viewModel.deleteItem(item.id)
                    }
                },
                onUpdateClick = { item ->
                    val action = ListFragmentDirections.actionListFragmentToUpdateItemFragment(item.id, item.name, item.origin, item.year, item.keywords, item.description, item.youtubeLink)
                    findNavController().navigate(action)
                })
        }

        logd("List Fragment Created")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { itemList ->
            adapter.updateList(itemList)
        }

        view.findViewById<View>(R.id.addButton).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToAddItemFragment()
            findNavController().navigate(action)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun setViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    fun setAdapter(adapter: ItemAdapter) {
        this.adapter = adapter
    }
}
