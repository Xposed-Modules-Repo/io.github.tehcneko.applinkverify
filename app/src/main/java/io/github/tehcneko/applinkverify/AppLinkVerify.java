package io.github.tehcneko.applinkverify;

import android.content.Intent;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class AppLinkVerify implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (loadPackageParam.packageName.equals("android")) {
            try {
                XposedHelpers.findAndHookMethod(
                        "com.android.server.pm.verify.domain.DomainVerificationUtils",
                        loadPackageParam.classLoader,
                        "isDomainVerificationIntent",
                        Intent.class,
                        int.class,
                        new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) {
                                param.setResult(false);
                            }
                        }
                );
            } catch (Throwable t) {
                XposedBridge.log(t);
            }
        }
    }


}
