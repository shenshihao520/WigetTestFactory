package com.example.shenshihao520.wigettestfactory.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CommonUtils {
	// 拷贝数据库的ProgressDialog
	static ProgressDialog copyDialog;
	// 更新数据库的ProgressDialog
	static ProgressDialog progressdialog;
	// 存放子文件的文件名
	static String[] files = null;
	static int[] sortFile;
	// 从数据库获取现在数据库的版本号
	static int databaseVersion = -1;

	public static List cursorToList(Cursor cursor) {
		List deviceList = new ArrayList();

		String[] names = cursor.getColumnNames();
		while (cursor.moveToNext()) {
			if (names != null && names.length > 0) {
				Map map = new HashMap();
				for (int i = 0; i < names.length; i++) {
					map.put(names[i], cursor.getString(cursor.getColumnIndex(names[i])));
				}
				deviceList.add(map);
			}
		}

		return deviceList;
	}

	public static Map cursorToMap(Cursor cursor) {
		Map map = null;

		String[] names = cursor.getColumnNames();
		if (cursor.getCount() > 0) {
			cursor.moveToNext();
			if (names != null && names.length > 0) {
				map = new HashMap();
				for (int i = 0; i < names.length; i++) {
					map.put(names[i], cursor.getString(cursor.getColumnIndex(names[i])));
				}
			}
		}

		return map;
	}

	public static void copyCompressFile(String path, String sourcefileName, Context mContext) throws IOException {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String outFileName = path + sourcefileName + ".db";
		File file = new File(outFileName);
		if (file.exists()) {
			return;
		}
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(outFileName);
		// 压缩文件
		myInput = mContext.getAssets().open(sourcefileName + ".db");
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
		// 更新数据库
		// updateDataBase(mContext);
	}

	public static void copyZipFile(String path, String sourcefileName, Context mContext) throws IOException {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String outFileName = path + sourcefileName;
		// File file = new File(outFileName);
		// if (file.exists()) {
		// return;
		// }
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(outFileName);
		// 压缩文件
		myInput = mContext.getAssets().open(sourcefileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	// 把一个数组分割成字数组 ary为分割用的数组 ，subsize为字数组的大小
	public static Object[] splitAry(String[] ary, int subSize) {
		int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;
		List<List<String>> subAryList = new ArrayList<List<String>>();
		for (int i = 0; i < count; i++) {
			int index = i * subSize;
			List<String> list = new ArrayList<String>();
			int j = 0;
			while (j < subSize && index < ary.length) {
				list.add(ary[index++]);
				j++;
			}
			subAryList.add(list);
		}
		Object[] subAry = new Object[subAryList.size()];
		for (int i = 0; i < subAryList.size(); i++) {
			List<String> subList = subAryList.get(i);
			String[] subAryItem = new String[subList.size()];
			for (int j = 0; j < subList.size(); j++) {
				subAryItem[j] = subList.get(j);
			}
			subAry[i] = subAryItem;
		}
		return subAry;
	}

	public static void copyimagefolderFile(String path, String sourcefileName, Context mContext) throws IOException {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String outFileName = path + sourcefileName + ".zip";
		// File file = new File(outFileName);
		// 改为无论如何都拷贝
		// if (file.exists()) {
		// return;
		// }
		File myFilePath = new File(outFileName);
		if (!myFilePath.exists()) {
			myFilePath.createNewFile();
		}
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(outFileName);
		// 压缩文件
		myInput = mContext.getAssets().open(sourcefileName + ".zip");
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}



	/**
	 * unzip
	 *
	 * @param zipFile
	 * @param folderPath
	 * @return
	 */
	public static String Unzip(String zipFile, String folderPath) {
		int BUFFER = 1024; // 1k
		String strEntry = null;

		File sourceFile = new File(zipFile);

		if (!sourceFile.exists()) {
			return null;
		}
		try {
			BufferedOutputStream dest = null; // 缓冲输出流
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry; //
			while ((entry = zis.getNextEntry()) != null) {

				try {

					int count;
					byte data[] = new byte[BUFFER];
					strEntry = entry.getName();

					File entryFile = new File(folderPath + strEntry);
					File entryDir = new File(entryFile.getParent());

					// 如果是个目录
					if (entry.isDirectory()) {

						strEntry = strEntry.substring(0, strEntry.length() - 1);
						File folder = new File(folderPath + File.separator + strEntry);
						folder.mkdirs();
						continue;
					} else {

						FileOutputStream fos = new FileOutputStream(entryFile);
						dest = new BufferedOutputStream(fos, BUFFER);
						while ((count = zis.read(data, 0, BUFFER)) != -1) {
							dest.write(data, 0, count);
						}
						dest.flush();
						dest.close();
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			zis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		sourceFile.delete();
		return strEntry;
	}

	/**
	 * @param context
	 *            : activity的上下文
	 * @param fileName
	 *            ： property文件的名称（全称哦，包括后缀）

	 * @return : 该节点对应的值
	 */
	public static String readPropertyFile(Context context, String fileName, String nodeName) {

		Properties readprops = new Properties();
		InputStream is = null;
		String nodeValue = null;

		try {
			is = context.getAssets().open(fileName);
			readprops.load(is);
			nodeValue = readprops.getProperty(nodeName);
			return nodeValue;

		} catch (Exception ex) {

			ex.printStackTrace();
			System.out.println("抛异常=====================" + "/n" + "property文件==============" + fileName + "/n" + "节点名称为==============" + nodeName);
			return nodeValue;
		}

	}

	/**
	 *            : activity的上下文
	 * @param fileName
	 *            ： property文件的名称（全称哦，包括后缀）
	 *            ： 要读取的文件中节点名称
	 * @return : 该节点对应的值
	 */
	public static String readPropertyFile(String fileName, String nodeName) {

		Properties readprops = new Properties();
		InputStream is = null;
		String nodeValue = null;

		try {
			is = CommonUtils.class.getResourceAsStream("/assets/" + fileName);
			readprops.load(is);
			nodeValue = readprops.getProperty(nodeName);

			return decodeStr(parseHexStr2Byte(nodeValue));

		} catch (Exception ex) {

			ex.printStackTrace();
			System.out.println("抛异常=====================" + "/n" + "property文件==============" + fileName + "/n" + "节点名称为==============" + nodeName);
			return nodeValue;
		}

	}

	/**
	 * 检测网络环境
	 *
	 * @param mContext
	 * @return
	 */
	public static boolean detect(Context mContext) {

		ConnectivityManager manager = (ConnectivityManager) mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		if (manager == null) {
			return false;
		}

		NetworkInfo networkinfo = manager.getActiveNetworkInfo();

		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}

		return true;
	}

	// 把log日志打印到一个txt文件里
	/**
	 * @param log
	 *            : 打印的信息
	 * @param txtFileName
	 *            ：打印出来的txt文件的全名（包含后缀名哦）
	 */
	public static void printStringToTXT(String log, String txtFileName) {
		File logFile = new File(Environment.getExternalStorageDirectory() + "/" + txtFileName);
		// File logFile = new File(Environment.getExternalStorageDirectory() +
		// "/ACSSLife/log" + "/" + txtFileName);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// 文件存在，我这里是想先把以前存在的数据先删除然后在重新建
			logFile.deleteOnExit();
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			FileWriter fw;
			fw = new FileWriter(logFile);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.print(log);

			pw.close();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param strContainPoint
	 *            带有小数点的数值型string
	 * @return 去掉小数点之后的数值型string
	 */

	public static String removePointOfNumericString(String strContainPoint) {
		if (!"".equals(strContainPoint)) {
			return String.valueOf((int) Double.parseDouble(strContainPoint));
		} else {
			return strContainPoint;
		}
	}




	// 获取系统时间 作为文件名
	public static String getDateAndTime() {
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH);
		int day = ca.get(Calendar.DATE);
		int minute = ca.get(Calendar.MINUTE);
		int hour = ca.get(Calendar.HOUR);
		int second = ca.get(Calendar.SECOND);
		String date = "" + year + (month + 1) + day + hour + minute + second;
		return date;
	}

	private static String getIpbyproperties(Context mContext) {
		Properties readprops = new Properties();
		InputStream input = null;
		String hostAddr = null;

		try {
			input = mContext.getAssets().open("ipaddress.properties");
			readprops.load(input);
			// System.out.println(readprops.getProperty("smtp_server"));
			hostAddr = readprops.getProperty("judgeappversion");
			return decodeStr(parseHexStr2Byte(hostAddr));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("抛异常");
			Map<String, Object> reMap = new HashMap<String, Object>();
			ex.printStackTrace();
			return hostAddr;
		}

	}

	// 读取properties文件
	public static String getIpByProperties(Context context, String prop, String file) {
		Properties readprops = new Properties();
		InputStream input = null;
		String hostAddr = null;
		try {
			if ("ip".equals(file)) {
				input = context.getAssets().open("parameter.properties");
			} else if ("prodoc".equals(file)) {
				input = context.getAssets().open("profitdoc.properties");
			}

			readprops.load(input);
			// System.out.println(readprops.getProperty("smtp_server"));
			hostAddr = readprops.getProperty(prop);
			return decodeStr(parseHexStr2Byte(hostAddr));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("抛异常");
			Map<String, Object> reMap = new HashMap<String, Object>();
			ex.printStackTrace();
			return hostAddr;
		}

	}

	// 写入log文件
	public static void printStringToTXTUpdate(String patn, String log) {
		File logFile = new File(patn);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
				FileWriter fw;
				fw = new FileWriter(logFile);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				pw.print(log);
				pw.close();
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				logFile.createNewFile();
				FileWriter fw;
				fw = new FileWriter(logFile, true);
				fw.write(log);
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 递归删除文件夹及文件夹下的文件
	public static void RecursionDeleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}



	/**
	 * 字符串加密方法
	 *
	 * @return void
	 * @throws Exception
	 */
	public byte[] encodeStr(String str, int keySize) throws Exception {

		// 获取KeyGenerator对象
		KeyGenerator kgen = KeyGenerator.getInstance("AES");

		// 设置加密密匙位数，目前支持128、192、256位
		kgen.init(keySize);

		// 获取密匙对象
		SecretKey skey = kgen.generateKey();

		// 获取随机密匙
		byte[] raw = skey.getEncoded();

		// 初始化SecretKeySpec对象
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		// 初始化Cipher对象
		Cipher cipher = Cipher.getInstance("AES");

		// 用指定密匙对象初始化加密Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		// 加密字符串
		return cipher.doFinal(str.getBytes("utf-8"));
	}

	/**
	 * 字符串解密方法
	 *
	 * @return void
	 * @throws Exception
	 */
	public static String decodeStr(byte[] date) throws Exception {

		if (null == date || date.length % 16 != 0) {
			System.out.println("字符串解密参数校验失败");
			return null;
		}

		// 初始化SecretKeySpec对象
		SecretKeySpec skeySpec = new SecretKeySpec("nW^c$XHeH$9uT!Go".getBytes(), "AES");

		// 初始化Cipher对象
		Cipher cipher = Cipher.getInstance("AES");

		// 用指定密匙对象初始化加密Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		// 解密字符串
		byte[] decData = cipher.doFinal(date);

		return new String(decData, "utf-8");
	}

	/**
	 * 将二进制转换成16进制
	 *
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}



	/**
	 * 将16进制转换为二进制
	 *
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 从输入流中读取内容 返回字符串
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String readLine(PushbackInputStream in) throws IOException {
		char buf[] = new char[128];
		int room = buf.length;
		int offset = 0;
		int c;
		loop: while (true) {
			switch (c = in.read()) {
			case -1:
			case '\n':
				break loop;
			case '\r':
				int c2 = in.read();
				if ((c2 != '\n') && (c2 != -1))
					in.unread(c2);
				break loop;
			default:
				if (--room < 0) {
					char[] lineBuffer = buf;
					buf = new char[offset + 128];
					room = buf.length - offset - 1;
					System.arraycopy(lineBuffer, 0, buf, 0, offset);

				}
				buf[offset++] = (char) c;
				break;
			}
		}
		if ((c == -1) && (offset == 0))
			return null;
		return String.copyValueOf(buf, 0, offset);
	}

	/**
	 * 将一个文件夹进行压缩
	 *
	 * @param zipFileName
	 * @param inputFile
	 * @throws Exception
	 */
	public static void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		// zip(out, inputFile, "");
		zipFiles(out, zipFileName, inputFile.getAbsolutePath(), true);
		out.close();
	}

	private static void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {

			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] buf = new byte[1024];
			int len;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
		}

	}

	public static void zipFiles(ZipOutputStream out, String path, String sourcePath, boolean haveHideFile) {
		File sourceFile = new File(sourcePath);

		if (!"".equals(path) && !path.endsWith("/")) {
			path += "/";
		}

		try {
			// 如果源文件为隐藏文件 并且 无需包含隐藏文件 则直接返回
			if (sourceFile.isHidden() && !haveHideFile)
				return;

			if (sourceFile.isDirectory()) {
				File[] files = sourceFile.listFiles();
				String _fileName = sourceFile.getName();

				for (int i = 0; i < files.length; i++) {
					zipFiles(out, path + _fileName, files[i].getPath(), haveHideFile);
				}
				out.closeEntry();
			} else {
				FileInputStream in = new FileInputStream(sourcePath);
				out.putNextEntry(new ZipEntry( sourceFile.getName()));
				byte[] buf = new byte[1024];
				int len;

				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取SD path
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		}

		return null;
	}

	/**
	 * 复制单个文件
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			File newFile=new File(newPath);
			if (oldfile.exists()) { // 文件存在时
				if(newFile.exists()){
					newFile.delete();
				}
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件
	 *
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 移动文件到指定目录
	 *
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);

	}

	/**
	 * 提示框
	 */
	static AlertDialog  illegalArgumentDialog = null;
	public static void showIllegalArgumentDialog(String message,Context context) {
		if(illegalArgumentDialog == null) {
			illegalArgumentDialog = new AlertDialog.Builder(context)
					.setPositiveButton("确定", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							illegalArgumentDialog.dismiss();
						}
					})
					.setTitle("提示")
					.create();
		}
		illegalArgumentDialog.dismiss();
		illegalArgumentDialog.setMessage(message);
		illegalArgumentDialog.show();
	}
	/**
	 * 将指定图片转换成指定大小 一般用于缩小
	 *	id 的方式

	 * @return
	 */
	public static Bitmap convertToBitmap(Resources res, int id, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeResource(res,id, opts);
		opts.inSampleSize = calculateInSampleSize(opts, w, h); // 调用上面定义的方法计算inSampleSize值

		opts.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeResource(res,id, opts);

		return bitmap;
	}


	/**
	 * 将指定图片转换成指定大小 一般用于缩小
	 *
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(path, opts);
		opts.inSampleSize = calculateInSampleSize(opts, w, h); // 调用上面定义的方法计算inSampleSize值

		opts.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);

		return bitmap;
	}

	/**
	 * @param options   参数
	 * @param reqWidth  目标的宽度
	 * @param reqHeight 目标的高度
	 * @return
	 * @description 计算图片的压缩比率
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}
	/**
	 * 读取图片属性：旋转的角度
	 *
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	/*
   * 旋转图片
   * @param angle
   * @param bitmap
   * @return Bitmap
   */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		//旋转图片 动作
		Matrix matrix = new Matrix();

		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}


	/**
	 * 规则3：必须同时包含大小写字母及数字
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
	public static boolean isContainAll(String str) {
		boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
		boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
		boolean isUpperCase = false;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
				isDigit = true;
			} else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
				isLowerCase = true;
			} else if (Character.isUpperCase(str.charAt(i))) {
				isUpperCase = true;
			}
		}
		String regex = "^[a-zA-Z0-9]+$";
		boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
		return isRight;
	}
	/**
	 * 用来判断服务是否运行.
	 *
	 * @param mContext
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {

		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);

		if (!(serviceList.size() > 0)) {
			return false;
		}
		Log.e("OnlineService：",className);
		for (int i = 0; i < serviceList.size(); i++) {
			Log.e("serviceName：",serviceList.get(i).service.getClassName());
			if (serviceList.get(i).service.getClassName().contains(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 截屏
	 * @param dView dView要截取的View
	 * @param path  保存图片的路径
	 */
	public static void screenshot(View dView,String path) {
		// 获取屏幕
		dView.setDrawingCacheEnabled(true);
		dView.buildDrawingCache();
		Bitmap bmp = dView.getDrawingCache();
		if (bmp != null) {
			try {
				Log.i("666666",path);

				// 图片文件路径
				String filePath = path;

				File file = new File(filePath);
				FileOutputStream os = new FileOutputStream(file);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
				os.flush();
				os.close();
			} catch (Exception e) {
			}
		}
	}
}