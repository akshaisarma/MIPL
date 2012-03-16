/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: TableMatrixLoader.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Table Matrix Loader implementing Matrix Loader
 * 				Only supports either integer or double matrices
 */

package edu.columbia.mipl.datastr;

import java.util.ArrayList;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TableMatrixLoader extends MatrixLoader {

	public String getLoaderName() {
		return "Table";
	}

	public PrimitiveMatrix loadMatrix(String file) {
		try {
			FileInputStream tableMatrix = new FileInputStream(file);
			Scanner matrixScan = new Scanner(tableMatrix);
			String line = null;

			/* Skip leading whitespace */
			while (matrixScan.hasNextLine()) {
				line = matrixScan.nextLine();
				if (!line.trim().equals(""))
					break;
			}
			/* If file is empty */
			if (line == null)
				return null;

			/*
			 * Find type of data in matrix file. Double matrices can
			 * have integers
			 */
			Scanner stringScanner = new Scanner(line);
			PrimitiveArray loadedMatrix = null;
			if (stringScanner.hasNextInt())
				loadedMatrix = (PrimitiveIntArray) copyToArray(matrixScan, line, Integer.class);
			else if (stringScanner.hasNextDouble())
				loadedMatrix = (PrimitiveDoubleArray) copyToArray(matrixScan, line, Double.class);
			else
				//Add new types here.

			tableMatrix.close();
			return new PrimitiveMatrix(loadedMatrix);
		}
		catch (FileNotFoundException e) {
			System.out.println("Matrix file not found!");
		}
		catch (NumberFormatException e) {
			System.out.println("Unsupported type in Matrix!");
		}
		catch (IllegalStateException e) {
			System.out.println("Scanner closed while reading!");
		}
		catch (IOException e) {
			System.out.println("Could not close file");
		}
		return null;
	}

	public void saveMatrix(String file, PrimitiveMatrix matrix) {
		saveMatrix(file, matrix.getData());
	}

	void saveMatrix(String file, PrimitiveArray matrix) {
		try {
			FileWriter outputFile = new FileWriter(file);
			BufferedWriter outputWriter = new BufferedWriter(outputFile);

			if (matrix instanceof PrimitiveDoubleArray) {
				PrimitiveDoubleArray pda = (PrimitiveDoubleArray) matrix;
				double data[] = pda.getData();
				/* Saving padded matrix instead of real matrix */
				int rows = pda.getPaddedRow();
				int cols = pda.getPaddedCol();
				for (int i = 0; i < rows; i++)  {
					String oneLine = "";
					for (int j = 0; j < cols; j++)
						oneLine = oneLine + data[i * cols + j] + "\t";
					outputWriter.write(oneLine.trim() + "\n");
				}
			}
			else if (matrix instanceof PrimitiveIntArray) {
				PrimitiveIntArray pia = (PrimitiveIntArray) matrix;
				int data[] = pia.getData();
				/* Saving padded matrix instead of real matrix */
				int rows = pia.getPaddedRow();
				int cols = pia.getPaddedCol();
				for (int i = 0; i < rows; i++)  {
					String oneLine = "";
					for (int j = 0; j < cols; j++)
						oneLine = oneLine + data[i * cols + j] + "\t";
					outputWriter.write(oneLine.trim() + "\n");
				}
			}
			else {
				//Additional PrimitiveArray types here
			}
			outputWriter.close();
		}
		catch (IOException e) {
			System.out.println("IO Exception occurred while saving Matrix");
		}
	}

	private <T> PrimitiveArray copyToArray(Scanner matrixScan,
					String line, Class<T> type)throws NumberFormatException {
		int numberOfCols = 0;
		int numberOfRows = 1;
		String rowValues[] = line.trim().split("\\s+");
		numberOfCols = rowValues.length;
		ArrayList<T> values = new ArrayList<T>();
		if (type == java.lang.Double.class) {
			for (int i = 0; i < rowValues.length; i++)
				values.add((T) new Double(Double.parseDouble(rowValues[i])));
		}
		else if (type == java.lang.Integer.class) {
			for (int i = 0; i < rowValues.length; i++)
				values.add((T) new Integer(Integer.parseInt(rowValues[i])));
		}
		else {
			// Add new types here.
		}

		while (matrixScan.hasNextLine()) {
			line = matrixScan.nextLine();
			if (line.trim().equals(""))
				continue;
			numberOfRows++;
			rowValues = line.trim().split("\\s+");
			if (type == Double.class) {
				for (int i = 0; i < rowValues.length; i++)
					values.add((T) new Double(Double.parseDouble(rowValues[i])));
			}
			else if (type == Integer.class) {
				for (int i = 0; i < rowValues.length; i++)
					values.add((T) new Integer(Integer.parseInt(rowValues[i])));
			}
			else {
				// Add new types here.
			}
		}
		if (type == Double.class) {
			PrimitiveDoubleArray loadedArray = new PrimitiveDoubleArray(numberOfRows, numberOfCols);
			double data[] = loadedArray.getData();
			int paddedCol = loadedArray.getPaddedCol();
			int j = 0;
			for (int i = 0; i < values.size(); i++) {
				/* Skip padded parts */
				while (j % paddedCol > (numberOfCols - 1))
					data[j++] = 0;
				data[j++] = (double) (Double) values.get(i);
			}
			return loadedArray;
		}
		else if (type == Integer.class) {
			PrimitiveIntArray loadedArray = new PrimitiveIntArray(numberOfRows, numberOfCols);
			int data[] = loadedArray.getData();
			int paddedCol = loadedArray.getPaddedCol();
			int j = 0;
			for (int i = 0; i < values.size(); i++) {
				/* Skip padded parts */
				while (j % paddedCol > (numberOfCols - 1))
					data[j++] = 0;
				data[j++] = (int) (Integer) values.get(i);
			}
			return loadedArray;
		}
		else {
			// Add new types here.
		}
		return null;
	}
}
