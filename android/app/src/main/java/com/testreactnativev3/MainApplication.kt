package com.testreactnativev3

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import com.asia.sdkcore.entity.ui.theme.NeTheme
import com.asia.sdkcore.sdk.AccountKey
import com.asia.sdkcore.sdk.AppID
import com.asia.sdkcore.sdk.AppKey
import com.asia.sdkcore.sdk.SdkConfig
import com.asia.sdkui.ui.sdk.NetAloSDK
import com.asia.sdkui.ui.sdk.NetAloSdkCore
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import kotlinx.coroutines.ObsoleteCoroutinesApi
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject

@ObsoleteCoroutinesApi
@HiltAndroidApp
class MainApplication : Application(), ReactApplication, Configuration.Provider {
    @Inject
    lateinit var netAloSdkCore: NetAloSdkCore

    private val mReactNativeHost: ReactNativeHost = object : ReactNativeHost(this) {
        override fun getUseDeveloperSupport(): Boolean {
            return BuildConfig.DEBUG
        }

        override fun getPackages(): List<ReactPackage> =
            PackageList(this).packages.apply {
                // Packages that cannot be autolinked yet can be added manually here, for example:
                // packages.add(new MyReactNativePackage());
                add(NetAloSdkPackage())
            }

        override fun getJSMainModuleName(): String {
            return "index"
        }
    }

    override fun getReactNativeHost(): ReactNativeHost {
        return mReactNativeHost
    }

    companion object {
        /**
         * Loads Flipper in React Native templates. Call this in the onCreate method with something like
         * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
         *
         * @param context
         * @param reactInstanceManager
         */
        private fun initializeFlipper(
            context: Context, reactInstanceManager: ReactInstanceManager
        ) {
            if (BuildConfig.DEBUG) {
                try {
                    /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
                    val aClass = Class.forName("com.awesomeproject.ReactNativeFlipper")
                    aClass
                        .getMethod("initializeFlipper", Context::class.java, ReactInstanceManager::class.java)
                        .invoke(null, context, reactInstanceManager)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }
    }

    //Init SDK
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(netAloSdkCore.workerFactory)
            .build()

    private val sdkConfig = SdkConfig(
        appId = AppID.VNDIRECT,
        appKey = AppKey.VNDIRECT_PRO,
        accountKey = AccountKey.VNDIRECT_PRO,
        isSyncContact = false,
        hidePhone = false,
        hideCreateGroup = false,
        hideAddInfoInChat = false,
        hideInfoInChat = false,
        hideSearch = false,
        classMainActivity = MainActivity::class.java.name
    )

    private val sdkTheme = NeTheme(
        mainColor = "#00B14F",
        subColorLight = "#D6F3E2",
        subColorDark = "#683A00",
        toolbarDrawable = "#00B14F"
    )

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Realm.init(this)
    }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this,  /* native exopackage */false)
        initializeFlipper(this, reactNativeHost.reactInstanceManager)
        NetAloSDK.initNetAloSDK(
            context = this,
            netAloSdkCore = netAloSdkCore,
            sdkConfig = sdkConfig,
            neTheme = sdkTheme
        )
    }
}