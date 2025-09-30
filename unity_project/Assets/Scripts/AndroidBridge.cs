using UnityEngine;
using UnityEngine.Android;

/// <summary>
/// AndroidBridge 클래스는 Unity에서 안드로이드 네이티브 코드로 메시지를 보내고
/// 안드로이드에서 Unity로 들어오는 메시지를 받을 수 있는 메서드를 제공합니다.
/// </summary>
public class AndroidBridge : MonoBehaviour
{
    /// <summary>
    /// 안드로이드에서 Unity로 메시지가 도착했을 때 호출되는 메서드.
    /// UnityPlayer.UnitySendMessage를 통해 GameObject 이름과 메서드 이름이
    /// 정확히 일치해야 호출됩니다.
    /// </summary>
    /// <param name="message">안드로이드에서 전달된 문자열</param>
    public void ReceiveFromAndroid(string message)
    {
        Debug.Log("Android로부터 받은 메시지: " + message);
        // 필요하면 UI 업데이트나 게임 로직 처리를 수행합니다.
    }

    /// <summary>
    /// Unity에서 안드로이드 호스트로 메시지를 전송하는 예시 메서드.
    /// 버튼 클릭 등으로 호출할 수 있습니다.
    /// </summary>
    public void SendToAndroid()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        try
        {
            // UnityPlayer의 현재 Activity를 얻어옵니다.
            AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
            // onMessageFromUnity(Context context, String message)를 호출합니다.
            currentActivity.Call("onMessageFromUnity", "Unity에서 보낸 메시지");
        }
        catch (AndroidJavaException e)
        {
            Debug.LogError("Failed to send message to Android: " + e.Message);
        }
#endif
    }
}