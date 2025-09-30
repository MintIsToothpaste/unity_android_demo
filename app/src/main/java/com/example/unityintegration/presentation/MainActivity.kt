package com.example.unityintegration.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.unityintegration.databinding.ActivityMainBinding
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Launch Unity when the user taps the start button
        binding.btnStartUnity.setOnClickListener {
            viewModel.launchUnityFeature()
        }

        // Send a simple message to Unity when the user taps the send button
        binding.btnSendToUnity.setOnClickListener {
            viewModel.sendToUnity()
        }

        // Set up SeekBar to control the scale of a Unity object.
        // The progress value (0-100) is passed to the ViewModel, which forwards it to Unity.
        binding.seekScale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.sendScaleToUnity(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // no-op
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // no-op
            }
        })

        // Observe Unity state and update the status text accordingly
        viewModel.unityState.observe(this) { state ->
            binding.txtStatus.text = state.toString()
        }
    }
}
