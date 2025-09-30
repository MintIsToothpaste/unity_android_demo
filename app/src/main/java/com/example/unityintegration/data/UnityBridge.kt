package com.example.unityintegration.data

import android.content.Context
import android.widget.Toast
import com.unity3d.player.UnityPlayer

/**
 * UnityBridge는 안드로이드 ↔ Unity 간 메시지 전달을 담당하는 헬퍼 객체입니다.
 * com.unity3d.player.UnityPlayer.UnitySendMessage를 사용하여 Unity 쪽으로
 * 메시지를 보낼 수 있고, Unity에서 호출할 수 있는 정적 메서드도 제공합니다.
 */
object UnityBridge {

    /**
     * Android → Unity 메시지 전송. Unity의 특정 GameObject와 메서드를 호출합니다.
     * @param gameObjectName Unity에서 존재하는 GameObject 이름
     * @param methodName GameObject에 부착된 스크립트의 메서드 이름
     * @param message 전달할 문자열
     */
    fun sendMessageToUnity(gameObjectName: String, methodName: String, message: String) {
        // 메시지는 null이 아닌 문자열이어야 함
        UnityPlayer.UnitySendMessage(gameObjectName, methodName, message)
    }

    /**
     * Unity → Android 호출을 받을 메서드입니다.
     * Unity의 C# 스크립트에서 currentActivity.Call("onMessageFromUnity", message)
     * 형태로 호출하면 이 메서드가 실행됩니다.
     * @param context 현재 Activity 컨텍스트
     * @param message Unity에서 보낸 메시지
     */
    @JvmStatic
    fun onMessageFromUnity(context: Context, message: String) {
        Toast.makeText(context, "Message from Unity: $message", Toast.LENGTH_SHORT).show()
    }
}