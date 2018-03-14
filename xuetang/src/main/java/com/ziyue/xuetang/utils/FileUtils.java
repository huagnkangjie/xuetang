package com.ziyue.xuetang.utils;

import java.io.File;
import java.io.FileOutputStream;

import com.ziyue.xuetang.constant.Constants;

public class FileUtils {

	public static String getRealPath(String path) {

		return System.getProperty("user.dir") + path;

	}

	public static void deleteAllFilesOfDir(File path) {

		if (!path.exists())
			return;
		if (path.isFile()) {

			path.delete();
			System.out.println("delete:" + path.toString());
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAllFilesOfDir(files[i]);
		}

		path.delete();
		System.out.println("delete:" + path.toString());
	}

	/**
	 * 删除目录或者文件
	 * 
	 * @param dir
	 *            文件路径或者目录路径
	 */
	public static void deleteFile(String path) {
		File dir = new File(path);
		if (dir != null && dir.isDirectory()) {
			File[] file = dir.listFiles();
			for (File file2 : file) {
				deleteFile(file2.toString());
				file2.delete();
			}

		}

	}

	public static void saveTxtContactFile(String path, String name, String content) {
		File dir = new File(path);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		File fileName = new File(path + File.separator + name);

		try {
			fileName.createNewFile();
			FileOutputStream o = null;
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("UTF-8"));
			o.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
//		String path = LucenePath.getLucenePath(Constants.LUCENE_COMPANY_PATH);
//
//		File file = new File(path);
//		FileUtils.deleteAllFilesOfDir(file);
//
//		System.out.println("the end");

	}
}
