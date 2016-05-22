package com.pingan.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;

import com.pingan.http.HttpRequest;

public class ReadFile {
	
	private String url = "http://api.t.sina.com.cn/short_url/shorten.json";
	
	public ReadFile() {
		super();
	}
	
	
	/**
	 * batch get short urls from file
	 * read the long urls from the inputFile
	 * write the short urls to the outputFile
	 */
	public void batchGetShortulrs(String inputFile,String outputFile){
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			writer = new BufferedWriter(new FileWriter(outputFile,true));
			
			String line = null;
			int count=1;
			StringBuffer sb = new StringBuffer();
			while((line=reader.readLine())!=null){
				String[] str = line.split("\t");
				sb.append("&url_long="+URLEncoder.encode(str[0]));
				if(count%20==0){
					String result = HttpRequest.sendGet(url, "source="+Config.keys[count%10]+sb.toString());
					if(result!=null){
						String[] shortUrls=HttpRequest.getShortUrls(result);
						for(String tmp:shortUrls){
							writer.write(tmp);
							writer.newLine();
						}
					}
					result = null;
					sb.setLength(0);
				}
				str=null;
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			try {
				writer.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * get short url for single long input
	 * @param longUrl
	 */
	public String getShortulrs(String longUrl){
		String shortUrl = null;
		if(longUrl!=null){
			String result = HttpRequest.sendGet(url, "source="+Config.keys[0]+"&url_long="+longUrl);
			if(result!=null){
				shortUrl = HttpRequest.getShortUrl(result);
			}
		}
		return shortUrl;
	}
			

}
