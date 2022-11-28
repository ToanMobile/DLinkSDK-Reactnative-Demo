package com.testreactnativev3;

import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.work.Configuration;

import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.netacom.full.ui.sdk.NetAloSDK;
import com.netacom.full.ui.sdk.NetAloSdkCore;
import com.netacom.lite.entity.ui.theme.NeTheme;
import com.netacom.lite.sdk.AccountKey;
import com.netacom.lite.sdk.AppID;
import com.netacom.lite.sdk.AppKey;
import com.netacom.lite.sdk.SdkConfig;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import io.realm.Realm;
import kotlin.jvm.internal.Intrinsics;

@HiltAndroidApp
public class MainApplication extends Application implements ReactApplication, Configuration.Provider {
    @Inject
    NetAloSdkCore netAloSdkCore;
    private boolean isProduction = false;

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            @SuppressWarnings("UnnecessaryLocalVariable")
            List<ReactPackage> packages = new PackageList(this).getPackages();
            // Packages that cannot be autolinked yet can be added manually here, for
            // example:
            packages.add(new NetAloSdkPackage());
            return packages;
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    /**
     * Loads Flipper in React Native templates. Call this in the onCreate method
     * with something like initializeFlipper(this,
     * getReactNativeHost().getReactInstanceManager());
     *
     * @param context
     * @param reactInstanceManager
     */
    private static void initializeFlipper(Context context, ReactInstanceManager reactInstanceManager) {
        if (BuildConfig.DEBUG) {
            try {
                /*
                 * We use reflection here to pick up the class that initializes Flipper, since
                 * Flipper library is not available in release mode
                 */
                Class<?> aClass = Class.forName("com.awesomeproject.ReactNativeFlipper");
                aClass.getMethod("initializeFlipper", Context.class, ReactInstanceManager.class).invoke(null, context,
                        reactInstanceManager);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    // Init SDK
    private final SdkConfig sdkConfig;
    private final NeTheme sdkTheme;

    @NotNull
    public final NetAloSdkCore getNetAloSdkCore() {
        NetAloSdkCore netAloSdkCore = this.netAloSdkCore;
        if (netAloSdkCore == null) {
            Intrinsics.throwUninitializedPropertyAccessException("netAloSdkCore");
        }

        return netAloSdkCore;
    }

    public final void setNetAloSdkCore(@NotNull NetAloSdkCore netAloSdkCore) {
        this.netAloSdkCore = netAloSdkCore;
    }

    @NotNull
    public Configuration getWorkManagerConfiguration() {
        Configuration.Builder builder = new Configuration.Builder();
        NetAloSdkCore netAloSdkCore = this.netAloSdkCore;
        if (netAloSdkCore == null) {
            Intrinsics.throwUninitializedPropertyAccessException("netAloSdkCore");
        }

        Configuration configuration = builder.setWorkerFactory(netAloSdkCore.getWorkerFactory()).build();
        Intrinsics.checkNotNullExpressionValue(configuration, "Configuration.Builder()\n…ory)\n            .build()");
        return configuration;
    }

    protected void attachBaseContext(@Nullable Context base) {
        super.attachBaseContext(base);
        Realm.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
        // SDK
        NetAloSDK netAloSDK = NetAloSDK.INSTANCE;
        Application application = this;
        NetAloSdkCore netAloSdkCore = this.netAloSdkCore;
        if (netAloSdkCore == null) {
            Intrinsics.throwUninitializedPropertyAccessException("netAloSdkCore");
        }
        netAloSDK.initNetAloSDK(application, netAloSdkCore, this.sdkConfig, this.sdkTheme);
    }

    public MainApplication() {
        int appId;
        String appKey;
        String accountKey;
        if (isProduction) {
            appId = 5;
            appKey = "F9E2CBCC24B2F";
            accountKey = "5";
        } else {
            appId = 11;
            appKey = "V5WIfKdRfNaqapNSRVCsVCjZ39pWidpq";
            accountKey = "11";
        }
        boolean isSyncContact = true;
        boolean hidePhone = true;
        boolean hideCreateGroup = true;
        boolean hideAddInfoInChat = true;
        boolean hideInfoInChat = true;
        boolean hideCallInChat = true;
        boolean hideSearch = true;
        String classMainActivity = MainActivity.class.getName();
        this.sdkConfig = new SdkConfig(appId, appKey, accountKey, classMainActivity, isSyncContact, hidePhone,
                hideCreateGroup, hideAddInfoInChat, hideInfoInChat, hideCallInChat, hideSearch);
        this.sdkTheme = new NeTheme("#00B14F", "#D6F3E2", "#683A00", "#00B14F");
    }
}
