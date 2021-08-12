package com.justjump.android_challenge_01_clean.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.justjump.android_challenge_01_clean.BrowserTweets
import com.justjump.android_challenge_01_clean.databinding.DialogLiveSpanSetupBinding

class DialogLiveSpanSetup(private val browserTweets: BrowserTweets) : AppCompatDialogFragment() {

    interface SetupValue{
        fun getValue(value: Int)
    }

    private lateinit var binding: DialogLiveSpanSetupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            binding = DialogLiveSpanSetupBinding.inflate(layoutInflater)

            builder.setView(binding.root)
                // Add action buttons
                .setNegativeButton("Cancel") { _, _ -> }
                // Add action buttons
                .setPositiveButton("Ok"){ _, _ ->
                    browserTweets.getValue(binding.editText.text.toString().toInt())
                }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}