package com.example.unityintegration.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.unityintegration.databinding.ActivityMainBinding
import com.unity3d.player.UnityPlayer

/**
 * MainActivity는 XML 기반 UI를 사용하여 Unity 런타임을 포함하고 제어하는 역할을 합니다.
 * 두 개의 버튼을 통해 Unity 실행 및 종료를 제어하며,
 * 버튼들은 Unity가 실행 중일 때도 항상 화면에 표시됩니다.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    /**
     * 현재 실행 중인 UnityPlayer 인스턴스. null이면 Unity가 실행되지 않은 상태입니다.
     */
    private var unityPlayer: UnityPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel 상태를 관찰하여 UI를 업데이트합니다.
        viewModel.unityState.observe(this, Observer { state ->
            binding.textStatus.text = state
        })

        // Unity 실행 버튼
        binding.btnStartUnity.setOnClickListener {
            viewModel.launchUnityFeature()
            startUnity()
        }

        // Unity 종료 버튼
        binding.btnExitUnity.setOnClickListener {
            stopUnity()
        }
    }

    /**
     * UnityPlayer를 초기화하고 컨테이너에 추가합니다.
     * 이미 실행 중일 경우 아무 동작을 하지 않습니다.
     */
    private fun startUnity() {
        if (unityPlayer != null) return

        // Unity 런타임을 초기화하고 뷰를 컨테이너에 추가
        unityPlayer = UnityPlayer(this)
        val unityView = unityPlayer!!.view
        binding.unityContainer.addView(
            unityView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        // UnityPlayer 포커스 및 라이프사이클 관련 호출
        unityPlayer!!.requestFocus()
        unityPlayer!!.windowFocusChanged(true)
        unityPlayer!!.resume()
    }

    /**
     * UnityPlayer를 종료하고 컨테이너에서 제거합니다.
     * 실행 중이 아닐 경우 아무 동작을 하지 않습니다.
     */
    private fun stopUnity() {
        unityPlayer?.let { player ->
            viewModel.exitUnityFeature()
            // UnityPlayer.quit()를 호출하여 런타임을 종료
            player.quit()
            // 컨테이너에서 Unity 뷰 제거
            binding.unityContainer.removeAllViews()
            unityPlayer = null
        }
    }

    // Activity 라이프사이클과 UnityPlayer의 라이프사이클을 동기화합니다.
    override fun onDestroy() {
        unityPlayer?.quit()
        super.onDestroy()
    }

    override fun onPause() {
        unityPlayer?.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        unityPlayer?.resume()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        unityPlayer?.windowFocusChanged(hasFocus)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        unityPlayer?.configurationChanged(newConfig)
    }
}
