package com.moxie.client.utils;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.format.Time;
import android.widget.Toast;
import com.moxie.client.commom.GlobalParams;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.TbsReaderView.ReaderCallback;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Locale;

/* compiled from: TbsSdkJava */
public class CommonMethod {
    public static DecimalFormat a = new DecimalFormat("#0.00");
    private static final char[] b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void a(WebView webView) {
        String h = GlobalParams.i().h();
        WebSettings settings = webView.getSettings();
        String userAgentString = settings.getUserAgentString();
        SharedPreferMgr.d("moxie_sdk_useragent", userAgentString);
        settings.setUserAgentString(userAgentString + " MoxieSDKAndroid/" + h);
    }

    public static String a() {
        return SharedPreferMgr.e("moxie_sdk_useragent", "");
    }

    public static String a(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return c(((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getDeviceId());
        } catch (Exception e) {
            return "";
        }
    }

    public static String b(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return c(((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSubscriberId());
        } catch (Exception e) {
            return "";
        }
    }

    public static String c(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return c(((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getLine1Number());
        } catch (Exception e) {
            return "";
        }
    }

    public static String d(Context context) {
        if (context == null) {
            return "";
        }
        try {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getCellLocation();
            if (gsmCellLocation != null) {
                return String.valueOf(gsmCellLocation.getCid()) + "," + String.valueOf(gsmCellLocation.getLac());
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String b() {
        return c(Build.MODEL);
    }

    public static String c() {
        return c(VERSION.RELEASE);
    }

    public static String e(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            return "";
        }
    }

    public static void a(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        try {
            intent.setData(Uri.parse(str));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean f(Context context) {
        return "wifi".equalsIgnoreCase(g(context));
    }

    public static String g(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return "-";
            }
            if (activeNetworkInfo.getType() == 1) {
                return "WIFI";
            }
            if (activeNetworkInfo.getType() == 0) {
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case IX5WebViewClientExtension.FRAME_LOADTYPE_PREREAD /*11*/:
                        return "2G";
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case ReaderCallback.NOTIFY_CANDISPLAY /*12*/:
                    case 14:
                    case IX5WebSettings.DEFAULT_CACHE_CAPACITY /*15*/:
                        return "3G";
                    case 13:
                        return "4G";
                    default:
                        return "?";
                }
//                e.printStackTrace();
            }
            return "?";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String h(Context context) {
        if (context == null) {
            return "";
        }
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            String str = null;
            if (connectionInfo != null) {
                str = connectionInfo.getMacAddress();
            }
            return c(str);
        } catch (Exception e) {
            return "";
        }
    }

    public static String i(Context context) {
        try {
            String bssid;
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager.isWifiEnabled()) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    bssid = connectionInfo.getBSSID();
                    return c(bssid);
                }
            }
            bssid = null;
            return c(bssid);
        } catch (Exception e) {
            return "";
        }
    }

    public static Location j(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            Location lastKnownLocation = locationManager.getLastKnownLocation("gps");
            if (lastKnownLocation == null) {
                return locationManager.getLastKnownLocation("network");
            }
            return lastKnownLocation;
        } catch (Exception e) {
            return null;
        }
    }

    public static void b(Context context, String str) {
        Toast makeText = Toast.makeText(context, str, 0);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    public static Bitmap a(String str) {
        try {
            return BitmapFactory.decodeStream(new ByteArrayInputStream(Base64.decode(str)));
        } catch (Exception e) {
            return null;
        }
    }

    private static String c(String str) {
        return str != null ? str : "";
    }

    public static String b(String str) {
        if (str == null || str.equalsIgnoreCase("")) {
            return "";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes("utf-8"));
            byte[] digest = instance.digest();
            int length = digest.length;
            StringBuilder stringBuilder = new StringBuilder(length * 2);
            for (int i = 0; i < length; i++) {
                stringBuilder.append(b[(digest[i] >> 4) & 15]);
                stringBuilder.append(b[digest[i] & 15]);
            }
            return stringBuilder.toString().substring(0, 32);
        } catch (Exception e) {
            return "";
        }
    }

    public static String a(Time time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(Long.valueOf(time.toMillis(true)));
    }

    public static String d() {
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    Object obj2;
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (inetAddress.isLoopbackAddress()) {
                        obj2 = obj;
                    } else {
                        if (obj == null) {
                            stringBuilder.append('\n');
                        }
                        stringBuilder.append(inetAddress.getHostAddress().toString());
                        obj2 = null;
                    }
                    obj = obj2;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
