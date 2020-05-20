package com.practice;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessing {

	public static void main(String[] args) {
		System.out.println("NumberGuessing");
		Scanner sc = new Scanner(System.in);
		int tc=sc.nextInt();
		int a=0;
		int b=0;
		int n=0;
		String judge;
		Random rn = new Random();
		int randomNum =0;
		boolean finded = false;
		do {
		a=sc.nextInt();
		b=sc.nextInt();
		while(!finded) {
			n=sc.nextInt();			
			randomNum =  rn.nextInt(b - a + 1) + a;
			System.out.println(randomNum);
			judge = sc.nextLine();
			if(judge.equals("TOO_BIG")) {
				b=randomNum;
			}
			else if(judge.equals("TOO_SMALL")) {
				a=randomNum;
			} else if(judge.equals("CORRECT")) {
				finded = true;
			}else {
				System.out.println("WRONG_ANSWER");
			}
		}
		tc--;
		}while(tc>0);		
		sc.close();
	}

}
