package com.zgw.network_api.download_native;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.zgw.base.util.LogUtil;
import com.zgw.network_api.EmptyUtils;

import java.io.File;

public class DownloadManager {
    /**
     *
     * @param context
     * @param url
     * @return downloadId
     */
    public static long downloadFile( Context context,String url) {
        if (EmptyUtils.isEmpty(url)) return -1;
        Uri uri = Uri.parse(url);
        android.app.DownloadManager downloadManager = (android.app.DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(uri);

        File direct = new File(Environment.getExternalStorageDirectory() + File.separator + "物流宝");
        if (!direct.isDirectory() || !direct.exists()) {
            boolean isCreated = direct.mkdirs();
            if (!isCreated) {
                LogUtil.outLog("TAG","Unable to create directory to download file");
                return -1;
            }
        }
        String fileName = new File(url).getName();

        request.setDestinationInExternalPublicDir("物流宝", fileName);
        request.setTitle(fileName);
        request.setDescription("下载中...");
        //request.setDescription(context.getString(R.string.downloading_file));
        request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_MOBILE | android.app.DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        return downloadManager.enqueue(request);




    }

    /**
     *
     * @param context
     * @param downloadId
     * @return 根据downloadId  获取文件路径
     */
    private static String findFileByDownloadId(@NonNull Context context, long downloadId) {
        android.app.DownloadManager dm = (android.app.DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        android.app.DownloadManager.Query query = new android.app.DownloadManager.Query().setFilterById(downloadId);
        Cursor c = dm.query(query);
        if (c != null) {
            if (c.moveToFirst()) {
                return c.getString(c.getColumnIndexOrThrow(android.app.DownloadManager.COLUMN_LOCAL_URI));
            }
            c.close();
        }
        return "";
    }
    /**
     *
     * @param context
     * @param downloadId
     * @return
     *      STATUS_PENDING
     *      STATUS_PAUSED
     *      STATUS_RUNNING
     *      STATUS_SUCCESSFUL
     *      STATUS_FAILED
     */
    private static int getFileDownloadStatusById(@NonNull Context context, long downloadId) {
        android.app.DownloadManager dm = (android.app.DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        android.app.DownloadManager.Query query = new android.app.DownloadManager.Query().setFilterById(downloadId);
        Cursor c = dm.query(query);
        if (c != null) {
            if (c.moveToFirst()) {
                return c.getInt(c.getColumnIndexOrThrow(android.app.DownloadManager.COLUMN_STATUS));
            }
            c.close();
        }
        return android.app.DownloadManager.STATUS_FAILED;

    }
}
