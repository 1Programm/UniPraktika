package com.uni.dap2;

public class Sort {

	private static final String PROGRAM_CALL = "java Sort (float)zahl";
	private static final float TOLERANCE = 0.1f * 1000;

	public static void main(String[] args) {

		if (args.length != 1) {			//Pr�fen, ob eine falsche Anzahl an �bergabeparametern vorliegt

			if (args.length == 0) {		

				long sortingTime = getSortingTime(50000);
				System.out.println("Laufzeit f�r BubbleSort mit 50000 Elementen: " + sortingTime + " ms!");
				System.exit(0);

			} else {		//Fehlerbehandlung: Zu viele �bergabeparameter

				error("Zu viele �bergabeparameter eingegeben! Bitte " + PROGRAM_CALL + " nutzen!");

			}

		}

		float comparingTime = 0;

		try {

			comparingTime = (Float.parseFloat(args[0])*1000);	//Speichern der vorgegebenen Zeit in Millisekunden

		} catch (NumberFormatException nfe) {		//Fehlerbehandlung: �bergabeparameter keine Zahl

			error("�bergabeparameter muss eine positive Flie�kommazahl sein!");

		}

		if (comparingTime <= 0) {		//Fehlerbehandlung: �bergabeparameter darf nicht kleiner oder gleich Null sein

			error("Bitte nur positive Flie�kommazahlen �bergeben!");

		}

		int arraySize = getFirstTimeExceed(comparingTime);		//Ermittlung der Feldgr��e, bei der das erste Mal die vorgegebene Zeit �berschritten wird

		binaryApproach(arraySize, comparingTime);		//Suche der richtigen Feldgr��e per Bin�rer Suche

	}

	/**
	 *
	 * @param right Erste Feldgr��e, bei der die vorgegebene Zeit �berschritten wird
	 * @param comparingTime Zeit, die vom Nutzer als angestrebte Sortierdauer eingegeben wurde
	 * 
	 * */
	
	public static void binaryApproach(int right, float comparingTime) {

		System.out.println();
		System.out.println("Starting binary search...");
		System.out.println();
		
		int left = right / 2;		//Berechnung der linken Grenze, also dem Wert, die definitiv unter der vorgegebenen Grenze lag
		
		int arraySize = right - (right - left)/2;		//Berechnung des Wertes zwischen dem linken und dem rechten Rand
		
		long sortingTime = getSortingTime(arraySize);		//Ermittlung der Sortierdauer f�r den mittleren Wert
		
		float difference = (sortingTime - comparingTime);		//Berechnung der Differenz, die zwischen der tats�chlichen und der angestrebten Zeit liegt
		
		assert (arraySize >= left && arraySize <= right) : "Fehlerhafte Anordnung der Parameter left, middle, right";

		while ((difference > TOLERANCE || difference * (-1) > TOLERANCE) && arraySize >= left && arraySize <= right) {		//Pr�fen, ob die Attribute left, middle und right richtig gesetzt sind
																														//Pr�fen, ob die ermittelte Zeit im Toleranzbereich liegt

			if (difference > TOLERANCE) {		//Fall 1: Sortierung hat zu lange gedauert

				right = arraySize - 1;					

				arraySize = arraySize - ((arraySize - left) / 2);

			} else {		//Fall 2: Sortierung ging zu schnell

				left = arraySize + 1;

				arraySize = arraySize + ((right - arraySize) / 2);

			}

			sortingTime = getSortingTime(arraySize);		//Ermittlung der neuen Sortierdauer f�r die neue Feldgr��e

			protocol(arraySize, sortingTime);		//Ausgabe der aktuellen Werte
			
			if(checkForFinish(sortingTime, comparingTime)) finish(arraySize, sortingTime, comparingTime);	//Pr�fen, ob aktuelle Werte bereits die Anforderungen erf�llen

			difference = sortingTime - comparingTime;
			
			assert (arraySize >= left && arraySize <= right) : "Fehlerhafte Anordnung der Parameter left, middle, right";
			
			

		}
		
		assert (arraySize >= left && arraySize <= right) : "Fehlerhafte Anordnung der Parameter left, middle, right";

	}

	/**
	 * 
	 * @param arraySize Die Gr��e des zu sortierenden Feldes
	 * @return Die Zeit in Millisekunden, die die Sortierung gebraucht hat
	 * 
	 * **/
	
	private static long getSortingTime(int arraySize) {

		int[] array = fillArray(arraySize);		//Array wird erzeugt und absteigend bef�llt

		long start = System.currentTimeMillis();

		bubbleSort(array);		//Sortierung des Feldes

		long end = System.currentTimeMillis();

		return (end - start);

	}

	/**
	 * 
	 * @param array Array, welches aufsteigend sortiert werden soll
	 * 
	 * **/
	
	public static void bubbleSort(int[] array) {

		for (int i = 0; i < array.length; i++) {

			for (int j = array.length - 1; j > i; j--) {

				if (array[j] < array[j - 1]) {

					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;

				}

			}

		}
	}

	/**
	 * 
	 * @param size Gr��e des Feldes, welches gef�llt werden soll
	 * @return Das bef�llte Feld
	 * 
	 * **/
	
	public static int[] fillArray(int size) {

		int[] array = new int[size];		//Initialisierung eines neuen Fledes in der richtigen Gr��e

		for (int i = 0; i < array.length; i++) {

			array[i] = array.length - i;

		}

		return array;

	}

	/**
	 * 
	 * @param errorMsg Beschreibung des Errors
	 * 
	 * **/
	
	public static void error(String errorMsg) {

		System.err.println("Das Programm wurde aufgrund eines Fehlers beendet! Grund: " + errorMsg);
		System.exit(0);

	}

	/**
	 * 
	 * @param comparingTime Zeit, in der das Feld mit einer gewissen Toleranz sortiert werden soll
	 * 
	 * @return Feldgr��e, bei der die Soll-Zeit das erste Mal �berschritten wurde
	 **/
	
	public static int getFirstTimeExceed(float comparingTime) {

		int arraySize = 1000;		//Start der �berpr�fung bei 1000 Elementen

		int[] array = fillArray(arraySize);		

		long tStart = System.currentTimeMillis();

		bubbleSort(array);

		long tEnd = System.currentTimeMillis();

		long mSecs = tEnd - tStart;

		protocol(arraySize, mSecs);		//Ausgabe der aktuellen Werte
		
		if(checkForFinish(mSecs, comparingTime)) finish(arraySize, mSecs, comparingTime);	//Pr�fen, ob aktuelle Werte bereits die Anforderungen erf�llen		//Pr�fen, ob aktuelle Werte bereits die Anforderungen erf�llen

		while (mSecs < comparingTime) {		//Pr�fen, ob die Sortierdauer die vorgegebene Zeit unterschreitet

			arraySize = arraySize * 2;		//Sortierung hat zu lange gedauert, also wird die Feldgr��e verdoppelt

			array = fillArray(arraySize);	//Array wird vergr��ert und neu bef�llt

			tStart = System.currentTimeMillis();

			bubbleSort(array);

			tEnd = System.currentTimeMillis();

			mSecs = tEnd - tStart;

			protocol(arraySize, mSecs);		//Ausgabe der aktuellen Werte
			
			if(checkForFinish(mSecs, comparingTime)) finish(arraySize, mSecs, comparingTime);	//Pr�fen, ob aktuelle Werte bereits die Anforderungen erf�llen

		}

		return arraySize;

	}
	
	private static boolean checkForFinish(float mSecs, float comparingTime) {

		return (comparingTime - TOLERANCE <= mSecs && mSecs <= comparingTime + TOLERANCE);

	}

	public static void protocol(int arraySize, float time) {

		System.out.println("Feldgroesse: " + arraySize + ", Zeit benoetigt: " + time);

	}

	private static void finish(int arraySize, float sortingTime, float comparingTime) {

		System.out.println("");
		System.out.println("Final values:");
		System.out.println("Feldgr��e: " + arraySize + " Elemente");
		System.out.println("Dauer: " + sortingTime + " ms (" + (float) sortingTime / 1000 + " sek)");
		System.out.println("Vorgegebene Dauer: " + comparingTime + " ms (" + (float) comparingTime / 1000 + " sek)");
		System.out.println("Erlaubte Toleranz: " + TOLERANCE + " ms (" + TOLERANCE / 1000 + " sek)");
		System.out.println("Abweichung vom Soll: " + Math.abs(sortingTime - comparingTime) + " ms ");

		System.exit(0);

	}
}
