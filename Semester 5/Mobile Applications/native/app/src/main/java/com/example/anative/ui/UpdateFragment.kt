package com.example.anative.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.anative.viewModel.MainViewModel
import com.example.anative.R
import com.example.anative.domain.Item
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.io.InputStream
import java.nio.charset.Charset
import logd
import MyException

class UpdateFragment : Fragment(R.layout.fragment_update_item) {

    private lateinit var viewModel: MainViewModel
    private lateinit var nameEditText: EditText
    private lateinit var originEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var keywordsEditText: EditText
    private lateinit var linkEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var cancelUpdateButton: Button
    private lateinit var uploadButton: Button
    private lateinit var clearNameButton: Button
    private lateinit var clearOriginButton: Button
    private lateinit var clearYearButton: Button
    private lateinit var clearKeywordsButton: Button
    private lateinit var clearLinkButton: Button
    private lateinit var clearFileButton: Button
    private lateinit var uploadedFileNameTextView: TextView
    private var description: String = ""
    private val fileRequestCode = 1001
    private var currentItemName: String? = null
    private var currentItemId: Int = -1
    private var currentItemOrigin: String? = null
    private var currentItemYear: Int? = null
    private var currentItemKeywords: String? = null
    private var currentItemDescription: String? = null
    private var currentItemLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("Update Fragment created")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentItemId = UpdateFragmentArgs.fromBundle(requireArguments()).itemId
        currentItemName = UpdateFragmentArgs.fromBundle(requireArguments()).itemName
        currentItemOrigin = UpdateFragmentArgs.fromBundle(requireArguments()).itemOrigin
        currentItemYear = UpdateFragmentArgs.fromBundle(requireArguments()).itemYear
        currentItemKeywords = UpdateFragmentArgs.fromBundle(requireArguments()).itemKeywords
        currentItemDescription = UpdateFragmentArgs.fromBundle(requireArguments()).itemDescription
        currentItemLink = UpdateFragmentArgs.fromBundle(requireArguments()).itemLink

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        nameEditText = view.findViewById(R.id.itemNameEditText)
        originEditText = view.findViewById(R.id.itemOriginEditText)
        yearEditText = view.findViewById(R.id.itemYearEditText)
        keywordsEditText = view.findViewById(R.id.itemKeywordsEditText)
        linkEditText = view.findViewById(R.id.itemLinkEditText)
        nameEditText.setText(currentItemName)
        originEditText.setText(currentItemOrigin)
        if(currentItemYear!! < 0)
            yearEditText.setText((-currentItemYear!!).toString()+" BC")
        else
            yearEditText.setText(currentItemYear.toString()+" AD")
        keywordsEditText.setText(currentItemKeywords)
        linkEditText.setText(currentItemLink)
        uploadedFileNameTextView = view.findViewById(R.id.uploadedFileName)
        updateButton = view.findViewById(R.id.updateButton)
        cancelUpdateButton = view.findViewById(R.id.cancelUpdateButton)
        uploadButton = view.findViewById(R.id.uploadButton)
        clearNameButton = view.findViewById(R.id.clearItemNameButton)
        clearOriginButton = view.findViewById(R.id.clearItemOriginButton)
        clearYearButton = view.findViewById(R.id.clearItemYearButton)
        clearKeywordsButton = view.findViewById(R.id.clearItemKeywordsButton)
        clearLinkButton = view.findViewById(R.id.clearItemLinkButton)
        clearFileButton = view.findViewById(R.id.clearUploadedFile)

        uploadButton.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), fileRequestCode)
        }

        updateButton.setOnClickListener {
            try {
                val itemName = nameEditText.text.toString()
                val id = currentItemId
                val origin = originEditText.text.toString()
                val yearString = yearEditText.text.toString()
                val keywords = keywordsEditText.text.toString()
                if(description.isEmpty())
                    description = currentItemDescription.toString()
                val link = linkEditText.text.toString()

                if(itemName.isEmpty())
                    throw MyException(message ="Name cannot be empty")

                if(origin.isEmpty())
                    throw MyException(message = "Origin cannot be empty")

                if(yearString.isEmpty())
                    throw MyException(message = "Year cannot be empty")

                if(keywords.isEmpty())
                    throw MyException(message = "keywords cannot be empty")

                if(description.isEmpty())
                    throw MyException(message = "Description cannot be empty")

                if(!"^\\d+\\s+(AD|BC)$".toRegex(RegexOption.IGNORE_CASE).matches(yearString))
                    throw MyException(message = "Year doesn't match format");

                var year=0
                if("^\\d+\\s+(AD)$".toRegex(RegexOption.IGNORE_CASE).matches(yearString)) {
                    val matchResult = "^\\d+\\s+(AD)$".toRegex(RegexOption.IGNORE_CASE).find(yearString)
                    val stringYear = matchResult?.groups?.get(0)?.value.toString()
                    val temp = stringYear.substring(0,stringYear.length-3)
                    year = temp.toInt()
                }
                else if("^\\d+\\s+(BC)$".toRegex(RegexOption.IGNORE_CASE).matches(yearString))
                {
                    val matchResult = "^\\d+\\s+(BC)$".toRegex(RegexOption.IGNORE_CASE).find(yearString)
                    val stringYear = matchResult?.groups?.get(0)?.value.toString()
                    val temp = stringYear.substring(0,stringYear.length-3)
                    year = temp.toInt()
                }
                else
                    throw MyException(message = "Year format incorrect.");

                if(!"^[A-Za-z,\\s]+$".toRegex().matches(keywords) && keywords.isNotEmpty())
                    throw MyException(message = "Keywords must be separated by commas");

                if(!link.contains("https://www.youtube.com") && link.isNotEmpty())
                    throw MyException(message = "Youtube link is invalid");

                val newItem = Item(name = itemName, id = id, description = description, year = year, origin = origin, keywords = keywords, youtubeLink = link)
                viewModel.updateItem(newItem)
                findNavController().popBackStack()
            } catch (e: MyException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }

        clearNameButton.setOnClickListener {
            nameEditText.text.clear()
        }

        clearOriginButton.setOnClickListener {
            originEditText.text.clear()

        }

        clearYearButton.setOnClickListener {
            yearEditText.text.clear()
        }

        clearKeywordsButton.setOnClickListener {
            keywordsEditText.text.clear()
        }

        clearLinkButton.setOnClickListener {
            linkEditText.text.clear()
        }

        clearFileButton.setOnClickListener {
            description = ""
            uploadedFileNameTextView.text = "No file selected"
        }

        cancelUpdateButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == fileRequestCode && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            uri?.let {
                val contentResolver = requireContext().contentResolver
                val inputStream: InputStream? = contentResolver.openInputStream(it)
                inputStream?.let { stream ->
                    val fileContent = stream.readBytes().toString(Charset.defaultCharset())
                    if(fileContent.isNotEmpty())
                        description = fileContent
                    uploadedFileNameTextView.text = "File uploaded: ${it.path.toString()}"
                }
                inputStream?.close()
            }
        }
    }
}
