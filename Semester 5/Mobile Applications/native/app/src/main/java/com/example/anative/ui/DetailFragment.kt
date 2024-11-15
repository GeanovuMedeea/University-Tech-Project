package com.example.anative.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.anative.viewModel.MainViewModel
import com.example.anative.R
import com.example.anative.domain.Item
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import logd
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var viewModel: MainViewModel
    private lateinit var viewTitleText: TextView
    private lateinit var itemOriginAndYearTextView: TextView
    private lateinit var itemDescriptionTextView: TextView
    private lateinit var itemKeywordsTextView: TextView
    private lateinit var itemYouTubeTextView: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button
    private var itemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("Detail Fragment created")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemId = DetailFragmentArgs.fromBundle(requireArguments()).itemId
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val item = viewModel.getItemById(itemId)
        viewTitleText = view.findViewById(R.id.itemNameTextView)
        itemOriginAndYearTextView = view.findViewById(R.id.itemOriginAndYearTextView)
        itemDescriptionTextView = view.findViewById(R.id.itemDescriptionTextView)
        itemKeywordsTextView = view.findViewById(R.id.itemKeywordsTextView)
        itemYouTubeTextView = view.findViewById(R.id.itemYouTubeTextView)

        var originAndYearText = item!!.origin

        if (item.year > 0) {
            originAndYearText += " ${item.year} "
            originAndYearText += "AD"
        } else {
            originAndYearText += " ${-item.year} "
            originAndYearText += "BC"
        }

        itemOriginAndYearTextView.text = originAndYearText
        if(item.description.length > 255)
            itemDescriptionTextView.text = item.description.substring(255)
        else
            itemDescriptionTextView.text = item.description

        itemKeywordsTextView.text = item.keywords
        itemYouTubeTextView.text = item.youtubeLink

        editButton = view.findViewById(R.id.editButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        item?.let {
            viewTitleText.text = it.name
        }

        editButton.setOnClickListener {
            //val action = .
            //findNavController().navigate(action)
            item?.let{
                val action = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(it.id, it.name, it.origin, it.year, it.keywords, it.description, it.youtubeLink)
                findNavController().navigate(action)
            }
            //findNavController().popBackStack()
        }

        deleteButton.setOnClickListener {
            AlertDialogDelete.showDeleteConfirmationDialog(requireContext()) {
                item?.let {
                    viewModel.deleteItem(it.id)
                    findNavController().popBackStack()
                }
            }
        }
    }
}
