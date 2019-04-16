package com.uni.dap2;

public class Sortierung {

	private static long tStart, tEnd, mSecs;
	private static String sortingType, fillingType;	//sortingType enthält die Sortierungsart, fillingType enthält die Methode, wie das Array gefüllt werden soll

	public static void main(String[] args) {

		int[] array = null;

		
		if (args.length > 3 || args.length < 1) {	//Prüfen, ob eine ungültige Arraygröße eingegeben wurde
			error("Wrong number of parameters!");
		}

		int size = 0;

		try {

			size = Integer.parseInt(args[0]);	
			if (size > 0) {
				array = new int[size];
			} else {

				error("Invalid array size! Please use 'java Sortierung (int)number [insert|merge [auf|ab|rand]]' with number greater zero!");

			}

		} catch (NumberFormatException nfe) {

			error("First parameter not a number! Please use 'java Sortierung (int)number [insert|merge [auf|ab|rand]]'");

		}

		if (args.length == 1) {

			sortingType = "merge";
			fillingType = "rand";

		}

		else if (args.length == 2) {

			if (args[1].equals("merge") || args[1].equals("insert")) {

				sortingType = args[1];
				fillingType = "rand";

			} else if (args[1].equals("auf") || args[1].equals("ab") || args[1].equals("rand")) {

				sortingType = "merge";
				fillingType = args[1];

			} else {

				error("No valid second parameter! Please use 'java Sortierung (int)number [insert|merge [auf|ab|rand]]'");

			}

		} else {

			if (args[1].equals("merge") || args[1].equals("insert")) {

				sortingType = args[1];

			} else {

				error("No valid second parameter! Please use 'java Sortierung (int)number [insert|merge [auf|ab|rand]]'");

			}

			if (args[2].equals("auf") || args[2].equals("ab") || args[2].equals("rand")) {

				sortingType = args[2];

			} else {

				error("No valid third parameter! Please use 'java Sortierung (int)number [insert|merge [auf|ab|rand]]'");

			}

		}

		fillArray(array, fillingType);

		switch (sortingType) {

		case "merge":

			tStart = System.currentTimeMillis();
			mergeSort(array);
			tEnd = System.currentTimeMillis();
			break;

		case "insert":

			tStart = System.currentTimeMillis();
			insertionSort(array);
			tEnd = System.currentTimeMillis();
			break;

		default:
			error("Invalid sorting type while initializing sorting algorithm!");
			break;

		}
		
		if(isSorted(array)) {
			
			System.out.println("Feld sortiert!");
			
		}else {
			
			System.out.println("Feld NICHT sortiert!");
			
		}
		
		mSecs = tEnd - tStart;
		
		System.out.println("Es dauerte " + mSecs + " ms, um das Feld zu sortieren!");
		
		printArray(array);
	}

	public static void insertionSort(int[] array) {

		for (int i = 1; i < array.length; i++) {

			int key = array[i];
			int j = i - 1;

			while (j >= 0 && key < array[j]) {

				array[j + 1] = array[j];

				j--;

			}

			array[j + 1] = key;

		}

	}

	public static boolean isSorted(int[] array) {

		for (int i = 0; i < array.length - 1; i++) {

			if (array[i] > array[i + 1])
				return false;

		}

		return true;

	}

	private static void error(String errorMsg) {

		System.out.println("Error occured with message: " + errorMsg);
		System.out.println("Program was terminated!");

		System.exit(0);

	}

	private static void fillArray(int[] array, String method) {

		switch (method) {

		case "auf":

			for (int i = 0; i < array.length; i++) {

				array[i] = i;

			}

			break;

		case "ab":

			for (int i = 0; i < array.length; i++) {

				array[i] = array.length - 1 - i;

			}

			break;

		case "rand":

			java.util.Random numberGenerator = new java.util.Random();

			for (int i = 0; i < array.length; i++) {

				array[i] = numberGenerator.nextInt();

			}

			break;

		default:

			error("No such filling method: " + "(" + method + ")");

			break;

		}

	}

	private static void printArray(int[] array) {

		if (array.length <= 100) {

			String nums = "";

			for (int i = 0; i < array.length; i++) {

				nums += array[i] + ", ";

			}

			System.out.println(nums.substring(0, nums.length() - 2));

		}

	}

	public static void mergeSort(int[] array) {
		int[] tmpArray = new int[array.length];
		mergeSort(array, tmpArray, 0, array.length - 1);
	}

	private static void mergeSort(int[] array, int[] tmpArray, int left, int right) {

		if (left < right) {

			int middle = (left + right) / 2;

			if (middle == left) {
				merge(array, tmpArray, left, left, right);
			} else {

				mergeSort(array, tmpArray, left, middle);
				mergeSort(array, tmpArray, middle + 1, right);
				merge(array, tmpArray, left, middle, right);
			}

		}

	}

	private static void inPlaceMerge(int[] array, int left, int middleLeft, int right) {

		if (array.length < 1) {
			error("Array with zero elements!");
		}

		if (array.length == 1) {
			return;
		}

		if (left < 0 || left >= array.length || left >= right) {
			error("Invalid argument 'left' (" + left + ")!");
		}
		if (middleLeft < 0 || middleLeft >= array.length || middleLeft < left || middleLeft >= right) {
			error("Invalid argument 'middle' ( " + left + ":" + middleLeft + ":" + right + ")!");
		}
		if (right < 0 || right >= array.length) {
			error("Invalid argument 'right' (" + right + ")!");
		}

		while (left <= middleLeft + 1 && right > middleLeft) {

			if (array[left] <= array[middleLeft + 1]) {

				left++;

			} else {

				int minVal = array[middleLeft + 1];

				for (int i = middleLeft + 1; i >= left + 1; i--) {

					array[i] = array[i - 1];

				}

				array[left] = minVal;

				left++;
				middleLeft++;

			}

		}
	}

	private static void merge(int[] array, int[] tmpArray, int left, int middleLeft, int right) {

		if (array.length < 1) {
			error("Array with zero elements!");
		}

		if (array.length == 1) {
			return;
		}

		if (left < 0 || left >= array.length || left >= right) {
			error("Invalid argument 'left' (" + left + ")!");
		}
		if (middleLeft < 0 || middleLeft >= array.length || middleLeft < left || middleLeft >= right) {
			error("Invalid argument 'middle' ( " + left + ":" + middleLeft + ":" + right + ")!");
		}
		if (right < 0 || right >= array.length) {
			error("Invalid argument 'right' (" + right + ")!");
		}

		int nextIndex = left;
		int rightIndex = middleLeft + 1;
		int leftIndex = left;

		while (leftIndex <= middleLeft && rightIndex <= right) {

			if (array[leftIndex] <= array[rightIndex]) {

				tmpArray[nextIndex] = array[leftIndex];
				leftIndex++;

			} else {

				tmpArray[nextIndex] = array[rightIndex];
				rightIndex++;
			}

			nextIndex++;

		}

		if (rightIndex <= right) {

			for (int i = rightIndex; i <= right; i++) {

				tmpArray[nextIndex] = array[i];
				nextIndex++;

			}

		} else if (leftIndex <= middleLeft) {

			for (int i = leftIndex; i <= middleLeft; i++) {

				tmpArray[nextIndex] = array[i];
				nextIndex++;
			}

		}

		for (int i = left; i <= right; i++) {

			array[i] = tmpArray[i];

		}

	}
}
