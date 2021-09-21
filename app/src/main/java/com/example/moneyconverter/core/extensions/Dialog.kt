package com.example.moneyconverter.core.extensions

import android.content.Context
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createDialog(block: MaterialAlertDialogBuilder.() -> Unit = {}): AlertDialog {
    val builder = MaterialAlertDialogBuilder(this)
    builder.setPositiveButton(android.R.string.ok, null)
    block(builder)
    return builder.create()
}

fun Context.createProgressDialog(): AlertDialog {
    return createDialog{
        val progressBar = ProgressBar(this@createProgressDialog)
        progressBar.setPadding(16,16,16,16)
        setView(progressBar)
        setPositiveButton(null, null)
        setCancelable(false)
    }
}