package com.example.anative.ui
// AlertDialogUtils.kt

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object AlertDialogDelete {
    fun showDeleteConfirmationDialog(context: Context, onDeleteConfirmed: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Do you want to delete the item?")
        builder.setPositiveButton("Delete") { dialog, which  ->
            onDeleteConfirmed()
        }
        builder.setNegativeButton("Cancel") { dialog, which  ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}
