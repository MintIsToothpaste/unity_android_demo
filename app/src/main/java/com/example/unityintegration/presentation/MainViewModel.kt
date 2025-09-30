package com.example.unityintegration.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unityintegration.data.UnityBridge

/**
 * ViewModelÀÁ Presentation 레이어에서 Unity를 통스하는 데이터드를 노출합니다.
 * 상태 데이터를 LiveData로 제공하여 Activity가 UI를 업데이트할 수 있도록 합니다.
 */
class MainViewModel : ViewModel() {
    private val _unityState = MutableLiveData<String>().apply { value = "상태 대기중" }
    val unityState: LiveData<String> = _unityState

    /**
     * Unity 런터임을 실행할 때 상태를 업데이트합니다.
     */
    fun launchUnityFeature() {
        _unityState.value = "Unity 시작"
    }

    /**
     * 안드로이드에서 Unity로 메시지를 전송하는 메서드입니다.
     */
    fun sendToUnity() {
        // Unity로 갟체 메시지 보내기
        UnityBridge.sendMessageToUnity(
            gameObjectName = "AndroidBridge",
            methodName = "ReceiveFromAndroid",
            message = "안드로이드에서 보내는 메시지"
        )
        _unityState.value = "Unity에 메시지 전송 완료"
    }

    /**
     * SeekBar 조작 시 Unity 오브젝트 크기를 변경하는 메서드입니다.
     */
    fun sendScaleToUnity(progress: Int) {
        UnityBridge.sendMessageToUnity(
            gameObjectName = "AndroidBridge",
            methodName = "UpdateScale",
            message = progress.toString()
        )
        _unityState.value = "크기 업데이트: $progress"
    }
}
