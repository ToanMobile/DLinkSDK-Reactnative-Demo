package com.testreactnativev3;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactApplicationContext;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public final class NetAloSdkPackage implements ReactPackage {
    @NotNull
    public List createViewManagers(@NotNull ReactApplicationContext reactContext) {
        Intrinsics.checkNotNullParameter(reactContext, "reactContext");
        return CollectionsKt.emptyList();
    }

    @NotNull
    public List createNativeModules(@NotNull ReactApplicationContext reactContext) {
        Intrinsics.checkNotNullParameter(reactContext, "reactContext");
        List modules = (List)(new ArrayList());
        modules.add(new NetAloSdkModule(reactContext));
        return modules;
    }
}

