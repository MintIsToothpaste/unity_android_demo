package com.example.unityintegration.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unityintegration.data.UnityBridge

/**
 * ViewModel은 Presentation 레이어에서 Unity와 통신하는 메서드를 노출합니다.
 * 상태 데이터를 LiveData로 제공하여 Activity가 UI를 업데이트할 수 있도록 합니다.
 */
class MainViewModel : ViewModel() {
    private val _unityState = MutableLiveData<String>().apply { value = "상태: 대기중" }
    val unityState: LiveData<String> = _unityState

    /**
     * Unity 런타임을 실행할 때 상태를 업데이트합니다.
     */
    fun launchUnityFeature() {
        _unityState.value = "Unity 시작"
    }

    /**
     * 안드로이드에서 Unity로 메시지를 전송하는 메서드입니다.
     */
    fun sendToUnity() {
        // Unity로 문자열 메시지 보내기
        UnityBridge.sendMessageToUnity(
            gameObjectName = "AndroidBridge",
            methodName = "ReceiveFromAndroid",
            message = "안드로이드에서 보낸 메시지"
        )
        _unityState.value = "Unity에 메시지 전송 완료"
    }
}