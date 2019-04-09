package com.uni.dap2;

public class Sortierung {

	public static void main(String[] args) {

		int size = 0;
		String argument = "";
		int[] array;

		if (args.length == 1 || args.length == 2) {

			try {

				size = Integer.parseInt(args[0]);

			} catch (Exception e) {

				error();

			}
		} else if (args.length == 2) {

			argument = args[1];

			if (!argument.equals("auf") && !argument.equals("ab") && !argument.equals("rand")) {

				error();

			}

		} else {
			error();
		}

		if (size <= 0) {

			error();

		}

		array = new int[size];

	}

	public static void insertionSort(int[] array) {

	}

	public static boolean isSorted(int[] array) {

	}

	private static void fillArray(int[] array, String argument) {

		switch (argument) {

		case "auf":

			for (int i = 0; i < array.length; i++) {

				array[i] = i;

			}

			break;

		case "ab":

			for (int i = 0; i < array.length; i++) {

				array[i] = array.length - i;

			}
			break;
			
		case "rand":
			
			java.util.Random numberGenerator = new java.util.Random();
			
			
			for(int i = 0; i < array.length; i++) {
				
				array[i] = numberGenerator.nextInt();
				
			}
			
			break;
			
		default: error();
				break;

		}

	}

	private static void error() {

		System.out.println("Please use correct types of parameters!");
		System.exit(0);

	}
}
