package com.zgw.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class NetUtil {

	public static final String NETWORKTYPE_INVALID = "网络未连接";// 没有网络
	public static final String NETWORKTYPE_WAP = "WAP"; // wap网络
	public static final String NETWORKTYPE_WIFI = "WIFI"; // wifi网络


	/**
	 * 获取网络连接类型
	 */
	@SuppressWarnings({"deprecation"})
	public static String getNetWorkType(Context context) {
		String mNetWorkType = null;
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return "ConnectivityManager not found";
		}
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			String type = networkInfo.getTypeName();
			if (type.equalsIgnoreCase("WIFI")) {
				mNetWorkType = NETWORKTYPE_WIFI;
			} else if (type.equalsIgnoreCase("MOBILE")) {
				String proxyHost = android.net.Proxy.getDefaultHost();
				if (TextUtils.isEmpty(proxyHost)) {
					mNetWorkType = mobileNetworkType(context);
				} else {
					mNetWorkType = NETWORKTYPE_WAP;
				}
			}
		} else {
			mNetWorkType = NETWORKTYPE_INVALID;
		}
		return mNetWorkType;
	}

	/**
	 * 获取数据连接情况下联网类型
	 */
	private static String mobileNetworkType(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager == null) {
			return "TM==null";
		}
		switch (telephonyManager.getNetworkType()) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:// ~ 50-100 kbps
				return "2G";
			case TelephonyManager.NETWORK_TYPE_CDMA:// ~ 14-64 kbps
				return "2G";
			case TelephonyManager.NETWORK_TYPE_EDGE:// ~ 50-100 kbps
				return "2G";
			case TelephonyManager.NETWORK_TYPE_EVDO_0:// ~ 400-1000 kbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_EVDO_A:// ~ 600-1400 kbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_GPRS:// ~ 100 kbps
				return "2G";
			case TelephonyManager.NETWORK_TYPE_HSDPA:// ~ 2-14 Mbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_HSPA:// ~ 700-1700 kbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_HSUPA: // ~ 1-23 Mbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_UMTS:// ~ 400-7000 kbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_EHRPD:// ~ 1-2 Mbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_EVDO_B: // ~ 5 Mbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_HSPAP:// ~ 10-20 Mbps
				return "3G";
			case TelephonyManager.NETWORK_TYPE_IDEN:// ~25 kbps
				return "2G";
			case TelephonyManager.NETWORK_TYPE_LTE:// ~ 10+ Mbps
				return "4G";
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return "UNKNOWN";
			default:
				return "4G";
		}
	}

	/**
	 * 判断网络是否连接
	 */
	public static Boolean isNetworkConnected(Context context) {
		try {
			ConnectivityManager manager = (ConnectivityManager) context
					.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			if (manager == null) {
				return false;
			}
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			if (networkinfo == null || !networkinfo.isAvailable()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	//判断手机使用是wifi还是mobile

	/**
	 * 判断手机是否采用wifi连接
	 */
	public static boolean isWIFIConnected(Context context) {
		//Context.CONNECTIVITY_SERVICE). 

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public static boolean isMOBILEConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	//获取使用是wifi还是mobile
	public static String getNetworkType(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return networkInfo.getTypeName();
		}
		return "";
	}

	/**
	 * 设置当前apn信息，保存到应用的全局的变量里面
	 *
	 * @param context
	 */
	public static void setApnParame(Context context) {
		//Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

//		Uri uri=Uri.parse("content://telephony/carriers/preferapn"); 
//		
//		ContentResolver contentResolver = context.getContentResolver();
//		Cursor query = contentResolver.query(uri, null, null, null, null);
//		if(query!=null&&query.getCount()==1)
//		{
//			if(query.moveToFirst())
//			{
//				Constants.PROXY_IP=query.getString(query.getColumnIndex("proxy"));
//				Constants.PROXY_PORT=query.getInt(query.getColumnIndex("port"));
//			}
//		}
//		query.close();
	}

	/**
	 * 域名解析
	 */
	public static String parseDomain(String dormain) {
		String domainString = "";
		int len = 0;
		String ipString = "";
		Map<String, Object> map = getDomainIp(dormain);
		String useTime = (String) map.get("useTime");
		InetAddress[] remoteInet = (InetAddress[]) map.get("remoteInet");
		String timeShow = null;
		if (Integer.parseInt(useTime) > 5000) {// 如果大于1000ms，则换用s来显示
			timeShow = " (" + Integer.parseInt(useTime) / 1000 + "s)";
		} else {
			timeShow = " (" + useTime + "ms)";
		}
		if (remoteInet != null) {// 解析正确
			len = remoteInet.length;
			for (int i = 0; i < len; i++) {
//				_remoteIpList.add(remoteInet[i].getHostAddress());
				ipString += remoteInet[i].getHostAddress() + ",";
			}
			ipString = ipString.substring(0, ipString.length() - 1);
			domainString = ipString + timeShow;
		} else {// 解析不到，判断第一次解析耗时，如果大于10s进行第二次解析
			if (Integer.parseInt(useTime) > 10000) {
				map = getDomainIp(dormain);
				useTime = (String) map.get("useTime");
				remoteInet = (InetAddress[]) map.get("remoteInet");
				if (Integer.parseInt(useTime) > 5000) {// 如果大于1000ms，则换用s来显示
					timeShow = " (" + Integer.parseInt(useTime) / 1000 + "s)";
				} else {
					timeShow = " (" + useTime + "ms)";
				}
				if (remoteInet != null) {
					len = remoteInet.length;
					for (int i = 0; i < len; i++) {
//						_remoteIpList.add(remoteInet[i].getHostAddress());
						ipString += remoteInet[i].getHostAddress() + ",";
					}
					ipString = ipString.substring(0, ipString.length() - 1);
					domainString = ipString + timeShow;
				} else {
					domainString = "解析失败" + timeShow;
				}
			} else {
				domainString = "解析失败" + timeShow;
			}
		}
		return domainString;
	}

	public static Map<String, Object> getDomainIp(String _dormain) {
		Map<String, Object> map = new HashMap<String, Object>();
		long start = 0;
		long end = 0;
		String time = null;
		InetAddress[] remoteInet = null;
		try {
			start = System.currentTimeMillis();
			remoteInet = InetAddress.getAllByName(_dormain);
			if (remoteInet != null) {
				end = System.currentTimeMillis();
				time = (end - start) + "";
			}
		} catch (UnknownHostException e) {
			end = System.currentTimeMillis();
			time = (end - start) + "";
			remoteInet = null;
			e.printStackTrace();
		} finally {
			map.put("remoteInet", remoteInet);
			map.put("useTime", time);
		}
		return map;
	}

	/**
	 * 检查网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean checkWirelessNetwork(Context context){
		boolean connectWIFI = false;
		boolean connectGPRS = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo activeNetInfo = connectivityManager
					.getActiveNetworkInfo();
			try {
				if (activeNetInfo != null) {
					if (connectivityManager.getNetworkInfo(
							ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
						connectWIFI = true;
					}
					if (connectivityManager.getNetworkInfo(
							ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {
						connectGPRS = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connectWIFI || connectGPRS;
	}

	/**
	 * 检查网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isMobileNet(Context context){
		boolean connectGPRS = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo activeNetInfo = connectivityManager
					.getActiveNetworkInfo();
			try {
				if (activeNetInfo != null) {
					if (connectivityManager.getNetworkInfo(
							ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {
						connectGPRS = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connectGPRS;
	}

}
