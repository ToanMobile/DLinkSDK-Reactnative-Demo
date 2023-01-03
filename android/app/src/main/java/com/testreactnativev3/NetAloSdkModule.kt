package com.testreactnativev3

import com.asia.sdkbase.logger.Logger
import com.asia.sdkcore.entity.ui.user.NeUser
import com.asia.sdkui.ui.sdk.NetAloSDK
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.google.firebase.messaging.RemoteMessage

class NetAloSdkModule internal constructor(private var reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "NetAloSDK"
    }

    override fun getConstants(): Map<String, Any> {
        return HashMap()
    }

    @ReactMethod
    fun setUser(
        userId: String?,
        token: String?,
        name: String?,
        avatar: String?,
        email: String?,
        phone: String?
    ) {
        Logger.e("setNetAloUser=$userId, token=$token")
        NetAloSDK.setNetAloUser(
            context = reactContext.applicationContext,
            NeUser(
                id = userId?.toLong() ?: 0L,
                token = token ?: "",
                username = name,
                avatar = avatar ?: "",
                phone = phone ?: "",
                email = email ?: "",
                isAdmin = true
            )
        )
    }

    @ReactMethod
    fun showListConversations() {
        Logger.e("openChatConversation")
        NetAloSDK.openNetAloSDK(reactContext.applicationContext)
    }

    @ReactMethod
    fun openChatWithUser(userId: String?, token: String?) {
        Logger.e("openChatWithUser=$userId, token=$token")
        NetAloSDK.openNetAloSDK(
            context = reactContext.applicationContext,
            neUserChat = NeUser(id = userId?.toLong() ?: 0L, token = token ?: "")
        )
    }

    @ReactMethod
    fun initFirebase(remoteMessage: RemoteMessage) {
        NetAloSDK.initFirebase(
            context = reactContext.applicationContext,
            remoteMessage = remoteMessage
        )
    }
}