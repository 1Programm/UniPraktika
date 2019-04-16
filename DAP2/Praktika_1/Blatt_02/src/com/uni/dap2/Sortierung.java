package com.uni.dap2;

public class Sortierung {
	
	private static final String SORT_MERGE = "merge";		//Name for merge sort mode
	private static final String SORT_INSERT = "insert"; 		//Name for insert sort mode
	
	private static final String FILL_RANDOM = "rand";		//Name for random filling mode
	private static final String FILL_UP = "auf";			//Name for upwards filling mode
	private static final String FILL_DOWN = "ab";			//Name for downwards filling mode
	
	private static final String DEFAULT_SORT = SORT_MERGE; 		//Default sort mode
	private static final String DEFAULT_FILL = FILL_RANDOM; 	//Default filling mode
	
	/*
	 * The valid parameter message
	 * Something like: "Please use 'java Sortierung (int)number [insert|merge [auf|ab|rand]]' with number greater zero!"
	 */
	private static final String VALID_PARAMETER_MESSAGE = 
			"Please use 'java Sortierung (int)number [" + SORT_INSERT + "|" + SORT_MERGE + " [" + FILL_UP + "|" + FILL_DOWN + "|" + FILL_RANDOM + "]]' with number greater zero!";

	
	public static void main(String[] args) {

		int[] array = null;	//Array which will contain the sorted numbers

		
		if (args.length > 3 || args.length < 1) {	//Test for invalid parameter length
			error("Wrong number of parameters!");
		}

		try {
			
			int size = Integer.parseInt(args[0]); 	//Parse first parameter to an integer
			
			if (size > 0) {							//Test valid size range
				
				array = new int[size];				//Initialize array with valid size
				
			} else {

				error("Invalid array size! " + VALID_PARAMETER_MESSAGE);

			}

		} catch (NumberFormatException nfe) {		//Catch Exception if first parameter is not a number

			error("First parameter not a number! " + VALID_PARAMETER_MESSAGE);

		}

		String sortingType = "";	//Will store the type of how the array should be sorted
		String fillingType = "";	//Will store the type of how the array will be initialized

		if (args.length == 1) {		//Set default values if they are not specified in the parameters

			sortingType = DEFAULT_SORT;
			fillingType = DEFAULT_FILL;

		}

		else if (args.length == 2) {	//A second parameter is specified

			if (args[1].equals(SORT_MERGE) || args[1].equals(SORT_INSERT)) {								 	//If the second parameter is a valid sort type

				sortingType = args[1];
				fillingType = DEFAULT_FILL;	

			} else if (args[1].equals(FILL_UP) || args[1].equals(FILL_DOWN) || args[1].equals(FILL_RANDOM)) {	//If the second parameter is a valid filling type

				sortingType = DEFAULT_SORT;
				fillingType = args[1];

			} else {	//The second parameter is neither a valid sorting type nor a vaild filling type -> invalid parameter

				error("No valid second parameter! " + VALID_PARAMETER_MESSAGE);

			}

		} else {	//More than two parameters are specified

			if (args[1].equals(SORT_MERGE) || args[1].equals(SORT_INSERT)) { 									//If the second parameter is a valid sort type

				sortingType = args[1];

			} else {

				error("No valid second parameter! " + VALID_PARAMETER_MESSAGE);

			}

			if (args[2].equals(FILL_UP) || args[2].equals(FILL_DOWN) || args[2].equals(FILL_RANDOM)) {			//If the third parameter is a valid filling type

				sortingType = args[2];

			} else {

				error("No valid third parameter! " + VALID_PARAMETER_MESSAGE);

			}

		}

		fillArray(array, fillingType);	//Run method fillArray which will fill the array specified by a valid fillingType
		
		long tStart = 0, tEnd = 0; //Helper variables to store the start, end of System time for the sort
		
		tStart = System.currentTimeMillis(); 
		
		switch (sortingType) {

			case SORT_MERGE:
				mergeSort(array);
				break;
				
			case SORT_INSERT:
				insertionSort(array);
				break;
	
			default:
				error("Invalid sorting type while initializing sorting algorithm!");
				break;

		}

		tEnd = System.currentTimeMillis();
		
		if(isSorted(array)) {
			
			System.out.println("Feld sortiert!");
			
		}else {
			
			System.out.println("Feld NICHT sortiert!");
			
		}
		
		long mSecs = tEnd - tStart; //Calculate the time (in milliseconds) how long the algorithm took to sort the array
		
		System.out.println("Es dauerte " + mSecs + " ms, um das Feld zu sortieren!");
		
		printArray(array); //Print the sorted array
	}

	/**
	 * The method to sort an array by the insertion sort algorithm
	 * 
	 * @param array The array to be sorted
	 * 
	 */
	private static void insertionSort(int[] array) {

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

	/**
	 * A method to test if an array is sorted in an ascending order
	 * 
	 * @param array The array that should be tested
	 * 
	 * @return True if the Array is sorted
	 */
	private static boolean isSorted(int[] array) {

		for (int i = 0; i < array.length - 1; i++) {

			if (array[i] > array[i + 1])
				return false;

		}

		return true;

	}

	/**
	 * A method to print an error message and exit the program
	 * 
	 * @param errorMsg The Error Message to be printed
	 */
	private static void error(String errorMsg) {

		System.out.println("Error occured with message: " + errorMsg);
		System.out.println("Program was terminated!");

		System.exit(0);

	}

	/**
	 * Method to fill an array specified by the method argument
	 * 
	 * @param array The array wich should be filled
	 * @param method Should equal either {@code FILL_UP, FILL_DOWN, FILL_RANDOM}
	 */
	private static void fillArray(int[] array, String method) {

		switch (method) {

		case FILL_UP:		//Fills the array with values which equal their indicies

			for (int i = 0; i < array.length; i++) {

				array[i] = i;

			}

			break;

		case FILL_DOWN: 	//Fills the array with values starting from the length of the array minus 1 to 0 (array.length-1, 0) 

			for (int i = 0; i < array.length; i++) {

				array[i] = array.length - 1 - i;

			}

			break;

		case FILL_RANDOM: 	//Fills the array with random integer values

			java.util.Random numberGenerator = new java.util.Random();

			for (int i = 0; i < array.length; i++) {

				array[i] = numberGenerator.nextInt();

			}

			break;

		default:			//Wrong method argument

			error("No such filling method: " + "(" + method + ")");

			break;

		}

	}

	/**
	 * Prints an array if its length is smaller or equal to 100
	 * 
	 * @param array The array that should be printed
	 * 
	 */
	private static void printArray(int[] array) {

		if (array.length <= 100) {

			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i]);

				if(i < array.length-1) {
					System.out.print(", ");
				}
			}
			
			System.out.println();
		}

	}

	/**
	 * Sorts an array with the merge sort algorithm
	 * It is used to start the recursive merge sort method
	 * 
	 * @param array The array which should be sorted
	 */
	public static void mergeSort(int[] array) {
		int[] tmpArray = new int[array.length];
		mergeSort(array, tmpArray, 0, array.length - 1);
	}

	/**
	 * Sorts an array with the merge sort algorithm
	 * It will be used recursively 
	 * 
	 * @param array The Array to be sorted
	 * @param tmpArray An helper array to temporarily store the sorted values
	 * @param left The left border in which the array should be sorted
	 * @param right The right border in which the array should be sorted
	 */
	private static void mergeSort(int[] array, int[] tmpArray, int left, int right) {

		if (left < right) { 									//Check for valid borders

			int middle = (left + right) / 2; 					//Get a mid position

			if (middle == left) {								//Check if left border is mid position (The border should have a length of two)
				merge(array, tmpArray, left, left, right);  	//Sorts the last two values
				
			} else {

				mergeSort(array, tmpArray, left, middle);		//Recursive call to sort the array from left to the mid
				mergeSort(array, tmpArray, middle + 1, right);  //Recursive call to sort the array from (mid+1) to the right
				
				merge(array, tmpArray, left, middle, right);	//Sorts the array (Values from left to mid should be sorted and values from mid+1 to right should be sorted)
			}

		}

	}

	/**
	 * The algorithm to merge two sorted areas in an array LeftArea(left - middleLeft) RightArea(middleLeft+1 - right)
	 *  
	 * @param array The array to be sorted
	 * @param tmpArray A helper array to store temp sorted values
	 * @param left The Start of the left area
	 * @param middleLeft The end of the left area
	 * @param right The end of the right area
	 */
	private static void merge(int[] array, int[] tmpArray, int left, int middleLeft, int right) {

		if (array.length < 1) {
			error("Array with zero elements!");
		}

		if (array.length == 1) { //Already sorted array with length of 1
			return;
		}

		/*
		 * Test for invalid Parameters
		 */
		if (left < 0 || left >= array.length || left >= right) {
			error("Invalid argument 'left' (" + left + ")!");
		}
		if (middleLeft < 0 || middleLeft >= array.length || middleLeft < left || middleLeft >= right) {
			error("Invalid argument 'middle' ( " + left + ":" + middleLeft + ":" + right + ")!");
		}
		if (right < 0 || right >= array.length) {
			error("Invalid argument 'right' (" + right + ")!");
		}

		int nextIndex = left;								//Initializes a pointer nextIndex which points to the next free index in tmpArray
		int rightIndex = middleLeft + 1;					//Initializes a pointer rightIndex which always points to the next value of the right half which was not merged yet
		int leftIndex = left;								//Initializes a pointer leftIndex which always points to the next value of the left half which was not merged yet

		while (leftIndex <= middleLeft && rightIndex <= right) { 	//Check if the left and the right part still have values which need to be merged

			if (array[leftIndex] <= array[rightIndex]) {			//Value of the left part is smaller than or equal to the value of the right part
				
				tmpArray[nextIndex] = array[leftIndex];		//Saves the smallest value of the left part which is not already merged in the right position in tmpArray		
				leftIndex++;

			} else {

				tmpArray[nextIndex] = array[rightIndex];		////Saves the smallest value of the right part which is not already merged in the right position in tmpArray	
				rightIndex++;
			}

			nextIndex++;

		}
		
		/*
		 * As the while-loop above only terminates if either the left of the right part is empty, one of them is definitely empty at this point 
		 */

		if (rightIndex <= right) {			//Checks if there are still values left in the left part which still need to be merged

			for (int i = rightIndex; i <= right; i++) {		//Puts all the remaining values of the left part in their corresponding order into the tmpArray

				tmpArray[nextIndex] = array[i];
				nextIndex++;

			}

		} else if (leftIndex <= middleLeft) {		//Checks if there are still values left in the right part which still need to be merged

			for (int i = leftIndex; i <= middleLeft; i++) {		//Puts all the remaining values of the right part in their corresponding order into the tmpArray

				tmpArray[nextIndex] = array[i];
				nextIndex++;
			}

		}

		for (int i = left; i <= right; i++) {		//Rewrites all the values from tmpArray back into the main array to replace the unmerged values in the main array with the merged values

			array[i] = tmpArray[i];		

		}

	}
}
