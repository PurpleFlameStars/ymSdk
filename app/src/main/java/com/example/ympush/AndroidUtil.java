
package com.example.ympush;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.UUID;


/**
 * 跟andoird系统相关的工具类
 */
public class AndroidUtil {

    private static String TAG = "AndroidUtil";

    private static String uuid;

    /**
     * 获取安卓设备唯一标识（这里读写SharedPerference逻辑别动）
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        if (uuid == null) {
            //ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置
            //它在Android <=2.1 or Android >=2.3的版本是可靠、稳定的，但在2.2的版本并不是100%可靠的
            //在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
            try {
                final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = androidId;
                } else {
                    //权限： 获取DEVICE_ID需要READ_PHONE_STATE权限，但如果我们只为了获取它，没有用到其他的通话功能，那这个权限有点大才小用
                    //bug：在少数的一些手机设备上，该实现有漏洞，会返回垃圾，如:zeros或者asterisks的产品
                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                            .getDeviceId();
                    if (!TextUtils.isEmpty(deviceId)) {
                        uuid = deviceId;
                    } else {
                        uuid = UUID.randomUUID().toString();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                uuid = UUID.randomUUID().toString();
            }
        }
        return uuid;
    }


}
