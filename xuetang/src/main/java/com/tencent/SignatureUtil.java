package com.tencent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SignatureUtil {

	public String ecKey = "config/sig/p8_priv.pem";
	
	public String publicKey = "config/sig/public.pem";
	

	private static SignatureUtil instance;
	
	public static SignatureUtil instance(){
		
		if(instance ==null){
			
			return new SignatureUtil ();
			
		}
		return instance;
	}
	
	

	private  String getPrivatePath() {

		return this.getClass().getResource("/").getPath();

	}

	private String readFile(String filePath) {
		try {
			String encoding = "UTF-8";
			StringBuilder strBuffer = new StringBuilder();
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // file is exist
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// encode format
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);

					strBuffer.append(lineTxt + '\n');

				}
				read.close();

				return strBuffer.toString();
			} else {
				System.out.println("can't found file");
			}

		} catch (Exception e) {
			System.out.println("read file content is error");
			e.printStackTrace();
		}

		return null;

	}

	public String getPrivateContent() {

		String path = getPrivatePath()+ ecKey;

		return readFile(path);
	}
	
	public String getPublicContent() {

		String path = getPrivatePath()+ publicKey;

		return readFile(path);
	}
	
	
	

	
	
	
	
	
	

}
