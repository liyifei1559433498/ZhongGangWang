package com.zgw.home.tablayout;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;

public class FileUtils {
	/**
	 * 判断SD是否可以
	 * 
	 * @return
	 */
	public static boolean isSdcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 创建根目录
	 * 
	 * @param path
	 *            目录路径
	 */
	public static void createDirFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件路径
	 * @return 创建的文件
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹的路径
	 */
	public static void delFolder(String folderPath) {
		delAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		myFilePath.delete();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            文件的路径
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		if (tempList == null || tempList.length == 0) return;
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	
	public static void delFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.delete();
		}
	}
	

	/**
	 * 获取文件的Uri
	 * 
	 * @param path
	 *            文件的路径
	 * @return
	 */
	public static Uri getUriFromFile(String path) {
		File file = new File(path);
		return Uri.fromFile(file);
	}
	public static boolean exists(String file) {
		return new File(file).exists();
	}
	/**
	 * 换算文件大小
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "未知大小";
		if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			fileSizeString = df.format((double) size / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size / 1073741824) + "G";
		}
		return fileSizeString;
	}

	



	public static boolean isEnoughSpaceForSdCard(int limit) {
		try {
			if (isSdcardExist() && getSDFreeSize() < limit) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	private static long getSDFreeSize(){  
	     File path = Environment.getExternalStorageDirectory();   
	     StatFs sf = new StatFs(path.getPath());   

	     long blockSize = sf.getBlockSize();   

	     long freeBlocks = sf.getAvailableBlocks();  
  
	     return (freeBlocks * blockSize)/1024 /1024; //单位MB  
	   }  
	
	/**
	 * 是否有足够的空间下载文件
	 * @param mContext
	 * @param limit
	 * @param isShowToast
	 * @return
	 */
//	public static boolean isDownFile(Context mContext, int limit, boolean isShowToast) {
//		if ((isSdcardExist() && isEnoughSpace(mContext, limit, isShowToast)) || (getInternalStorageSize() > limit)) {
//			return true;
//		}
//		return false;
//	}
	
	/**
	 * 获取文件列表
	 * @param path
	 * @return
	 */
	public static String[] getImageFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		if (!file.isDirectory()) {
			return null;
		}
		return file.list();
	}
	
	public static String getDownImagePath(Context context, String imagePath) {
		String path = "";
		if (isSdcardExist()) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			path = sdcardDir.getParent() + "/" + sdcardDir.getName() + "/.quanmincai/"+context.getPackageName()+imagePath;
			deleteDownPath();
		} else {
			path = context.getFilesDir().toString()+imagePath;
		}
		return path;
	}

	public static String getDownloadPath(Context context, String directory) {
		String path = "";
		if (isSdcardExist()) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			path = sdcardDir.getParent() + "/" + sdcardDir.getName() + "/.quanmincai/"
					+ context.getPackageName() + "/" + directory;
		} else {
			path = context.getFilesDir().toString() + "/" + directory;
		}
		return path;
	}

	/**
	 * 删除老版本下载目录
	 */
	public static void deleteDownPath() {
		try {
			String path = "";
			if (isSdcardExist()) {
				File sdcardDir = Environment.getExternalStorageDirectory();
				path = sdcardDir.getParent() + "/" + sdcardDir.getName() + "/quanmincai/";
				File file = new File(path);
				if (file.exists()) {
					deleteAllFiles(file);
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹下的所有文件
	 * @param file
	 */
	public static void deleteAllFiles(final File file) {
		try {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						File files[] = file.listFiles();
						if (files != null) {
							for (File f : files) {
								if (f.isDirectory()) {
									deleteAllFiles(f);
									try {
										f.delete();
									} catch (Exception e) {
									}
								} else {
									if (f.exists()) {
										try {
											f.delete();
										} catch (Exception e) {
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
    /**
     * 获取手机内部剩余存储空间 单位MB
     * @return
     */
    public static long getInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return (availableBlocks * blockSize)/1024 /1024; 
    }


	/**
	 * 读取assets文件
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static String getassetsFile(String fileName, Context context){
		try {
			InputStream inputStream = context.getResources().getAssets().open(fileName);
			InputStreamReader inputReader = new InputStreamReader(inputStream);
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String result = "";
			while((line = bufReader.readLine()) != null) {
				result += line;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取可以使用的缓存目录
	 *
	 * @param context
	 * @param uniqueName
	 *            目录名称
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		//如果内部存储大于10M就使用内部存储，否则再看一下SD卡的情况
		if (getAvailSpace(context.getCacheDir().getPath()) >= 10) {
			cachePath = context.getCacheDir().getPath();
		} else {
			cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
					? getExternalCacheDir(context) : context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * 获取某个目录的可用空间
	 *
	 * @param path
	 *
	 * @return 可用空间 （M）
	 */
	public static long getAvailSpace(String path){
		StatFs sf = new StatFs(path);
		long blockSize = sf.getBlockSize();
		long availCount = sf.getAvailableBlocks();
		return  availCount * blockSize / (1024 * 1024);
	}

	/**
	 * 获取程序外部的缓存目录
	 *
	 * @param context
	 * @return
	 */
	public static String getExternalCacheDir(Context context) {
		return Environment.getExternalStorageDirectory().getPath() + "/Android/data/"
				+ context.getPackageName() + "/cache/";
	}


	/**
	 * 根据文件名读取本地文件
	 *
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String readDataFromLocal(Context context, String fileName) {
		String fileData = null;
		String filePath = getDiskCacheDir(context, FileCacheConstants.FILE_CACHE_PATH).getAbsolutePath().concat(File.separator)
				.concat(md5(fileName));
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(512);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(filePath);
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			byte[] data = outputStream.toByteArray();
			outputStream.close();
			inputStream.close();
			fileData = new String(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileData;
	}


	/**
	 * md5加密方法
	 * @param plain
	 * @return
	 */
	public static String md5(String plain) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(plain.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			return "";
		}
	}


	/**
	 * 保存json到本地
	 *
	 */
	public static void saveDataToLocal(Context context, String data, String fileName) {
		if (data == null) {
			return;
		}

		String filePath = getDiskCacheDir(context, FileCacheConstants.FILE_CACHE_PATH).getAbsolutePath().concat(File.separator)
				.concat(md5(fileName));
		File file = new File(filePath);
		File parentFile = file.getParentFile().getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		File sub_parentFile = file.getParentFile();
		if (!sub_parentFile.exists()) {
			sub_parentFile.mkdirs();
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedReader reader = new BufferedReader(new StringReader(data));
			FileOutputStream outStream = new FileOutputStream(filePath);
			String lineContent = null;
			while ((lineContent = reader.readLine()) != null) {
				outStream.write(lineContent.getBytes());
			}
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
