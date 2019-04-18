package com.uni.dap2;

public class BubbleSort {
	
	public static void bubbleSort(int[] array) {
		for(int i=0;i<array.length-1;i++) {
			for(int o=i+1;o<array.length;o++) {
				if(array[i] > array[o]) {
					int tmp = array[o];
					array[o] = array[i];
					array[i] = tmp;
				}
			}
		}
	}
	
	public static void fillArray(int[] array) {
		for(int i=0;i<array.length;i++) {
			array[i] = (array.length - 1) - i;
		}
	}
	
//	
//	public static void printArray(int[] array) {
//		for(int i=0;i<array.length;i++) {
//			System.out.print(array[i] + " ");
//		}
//		System.out.print("\n");
//	}
//	
//	public static void main(String[] args) {
//		int[] array = new int[100];
//		
//		fillArray(array);
//		
//		printArray(array);
//		
//		bubbleSort(array);
//		
//		printArray(array);
//	}

}
