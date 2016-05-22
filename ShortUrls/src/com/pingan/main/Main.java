package com.pingan.main;

import java.util.Scanner;

import com.pingan.util.ReadFile;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		ReadFile rf = new ReadFile();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose the mode:\n Single mode:\t1\n Batch  mode:\t2\n Quit:\t\t3\nPlease Choose:");
		
		int choose = 0;
		while((choose=scanner.nextInt())!=3){
			if(choose==1){
				String lurl = scanner.next();
				if(lurl!=null){
					System.out.println(rf.getShortulrs(lurl));
				}else{
					System.out.println("Something Error!");
				}
			}else if(choose==2){
				String line = scanner.next();
				String str = scanner.next();
				if(line!=null && str!=null){
					rf.batchGetShortulrs(line, str);
					System.out.println("OK!");
				}else{
					System.out.println("Something Error!");
				}
			}else{
				System.out.println("Invalid Input!");
			}
		}
		System.out.println("Bye!");
	}

}
