package com.testreactnativev3;

import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.netacom.base.chat.logger.Logger;
import com.netacom.full.ui.sdk.NetAloSDK;
import com.netacom.lite.entity.socket.Call;
import com.netacom.lite.entity.ui.user.NeUser;
import com.netacom.lite.network.model.response.SettingResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.netacom.lite.config.EndPoint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

public final class NetAloSdkModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;

    @NotNull
    public String getName() {
        return "NetAloSDK";
    }

    @Nullable
    public Map getConstants() {
        return (Map) (new HashMap());
    }

    @ReactMethod
    public final void setUser(@Nullable String userId, @Nullable String token, @Nullable String userName,
            @Nullable String avatar, @Nullable String email, @Nullable String phone, @Nullable Boolean isAdmin) {
        Logger.INSTANCE.e("setNetAloUser=" + userName + ", token=" + token + ", username=" + userName, new Object[0]);
        NeUser neUser = new NeUser();
        long userID;
        if (userId != null) {
            userID = Long.parseLong(userId);
        } else {
            userID = 0L;
        }
        neUser.setId(userID);
        if (token != null) {
            neUser.setToken(token);
        }
        if (userName != null) {
            neUser.setUsername(userName);
        }
        if (avatar != null) {
            neUser.setAvatar(avatar);
        }
        if (phone != null) {
            neUser.setPhone(phone);
        }
        if (email != null) {
            neUser.setEmail(email);
        }
        if (isAdmin != null) {
            neUser.setAdmin(isAdmin);
        }
        NetAloSDK.INSTANCE.setNetAloUser(neUser);
    }

    @ReactMethod
    public final void showListConversations(@Nullable Boolean isHideCreateGroup, @Nullable ReadableArray types) {
        Logger.INSTANCE.e("openChatConversation", new Object[0]);
        Context context = this.reactContext.getApplicationContext();
        NetAloSDK.INSTANCE.openNetAloSDK(context, (Boolean) isHideCreateGroup, (ArrayList) types.toArrayList(),
                (NeUser) null, (Call) null, (Boolean) false);
    }

    @ReactMethod
    public final void openChatWithUser(@Nullable String userId, @Nullable String username, @Nullable String avatar, @Nullable String email, @Nullable String phone) {
        Logger.INSTANCE.e("openChatWithUser=" + userId + ", username=" + username, new Object[0]);
        Context context = this.reactContext.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(context, "reactContext.applicationContext");
        NeUser neUser = new NeUser();
        long userID;
        if (userId != null) {
            userID = Long.parseLong(userId);
        } else {
            userID = 0L;
        }
        neUser.setId(userID);
        if (username != null) {
            neUser.setUsername(username);
        }
        if (avatar != null) {
            neUser.setAvatar(avatar);
        }
        if (phone != null) {
            neUser.setPhone(phone);
        }
        if (email != null) {
            neUser.setEmail(email);
        }
        NetAloSDK.INSTANCE.openNetAloSDK(context, (Boolean) false, (ArrayList) null, (NeUser) neUser, (Call) null,
                (Boolean) false);
    }

    @ReactMethod
    public final void finishSocket() {
        NetAloSDK.INSTANCE.finishSocket();
    }

    @ReactMethod
    public final void logOut() {
        NetAloSDK.INSTANCE.logOut();
    }

    @ReactMethod
    public final void setDomainLoadAvatarNetAloSdk(String domainAvatar) {
        SettingResponse settingResponse = new SettingResponse();
        settingResponse.setCdnEndpointSdk(domainAvatar);
        NetAloSDK.INSTANCE.initSetting((SettingResponse) settingResponse);
    }

    public NetAloSdkModule(@NotNull ReactApplicationContext reactContext) {
        super(reactContext);
        Intrinsics.checkNotNullParameter(reactContext, "reactContext");
        this.reactContext = reactContext;
    }
}