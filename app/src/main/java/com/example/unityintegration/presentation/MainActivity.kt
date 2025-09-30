package com.example.unityintegration.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.unityintegration.data.UnityBridge
import com.example.unityintegration.databinding.ActivityMainBinding
import com.unity3d.player.UnityPlayerActivity

/**
 * MainActivity는 단순한 XML 기반 UI를 사용하여 Unity 화면을 실행하고
 * 안드로이드 ↔ Unity 메시지를 전송하는 역할을 합니다.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel의 상태 관찰
        viewModel.unityState.observe(this, Observer { state ->
            binding.textStatus.text = state
        })

        // Unity 실행 버튼 클릭 리스너
        binding.btnStartUnity.setOnClickListener {
            viewModel.launchUnityFeature()
            // UnityPlayerActivity를 통해 Unity 런타임을 시작
            val intent = Intent(this, UnityPlayerActivity::class.java)
            startActivity(intent)
        }

        // Unity로 메시지 보내기 버튼 클릭
        binding.btnSendToUnity.setOnClickListener {
            viewModel.sendToUnity()
        }
    }
}