package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.StringReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.Element;
import custom_exceptions.InvalidColumnNumberException;
import custom_exceptions.InvalidFileContentException;
import custom_exceptions.InvalidFileInputException;

public class FileParser {

	public FileParser(File inFile, ArrayList<Element> arrayListOfElements) {
		parseFileContent(inFile, arrayListOfElements);
	}

	public static void parseFileContent(File file, ArrayList<Element> arrayListOfElements) {
		BufferedReader bufferReader = null;
		int maxCol = 0;
        try {
            String currentLine = "";
            bufferReader = new BufferedReader(new FileReader(file));
            int row = 0, column = 0;
            while ((currentLine = bufferReader.readLine()) != null) {
            	validateLine(currentLine, row);
            	while (currentLine.contains(" || ")) {
            		arrayListOfElements.add(new Element(currentLine.substring(0,3), currentLine.substring(4,7), row, column));
            		currentLine = currentLine.substring(11);
            		column++;
            	}
            	if (row == 0) {
            		maxCol = column;
            	}
            	validateColumnNumber(maxCol, column);
            	arrayListOfElements.add(new Element(currentLine.substring(0,3), currentLine.substring(4,7), row, column));
            	row++;
            	column = 0;
            }
        } catch (IOException | InvalidFileContentException | InvalidColumnNumberException e) {
            System.out.println(e);
            System.exit(0);
        } finally {
            try {
                if (bufferReader != null) {
                	bufferReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	public static void validateFile(String fileName) throws InvalidFileInputException {
		if (!fileName.substring(fileName.length()-4).equals(".txt")) {
			throw new InvalidFileInputException(" File is not valid.");
		}
		else {
			System.out.println("File format is valid.");
		}

	}

	public static void validateColumnNumber(int maxCol, int column) throws InvalidColumnNumberException {
		if (maxCol != column) {
			throw new InvalidColumnNumberException("Column numbers are not the same.");
		}
	}

	public static void validateLine(String line, int row) throws InvalidFileContentException {
		// Verifies if the line contains an element separator " || "
		while (line.length() > 11 && line.substring(7, 11).equals(" || ") && line.charAt(3) == ':') {
			line = line.substring(11);
		}
		// Verifies if the line has contains a key value separator ' : '
		if (line.length() == 7 && line.charAt(3) == ':') {
			System.out.println("Line " + (row + 1) + " valid");
		}
		// Throws an exception
		else {
			throw new InvalidFileContentException("Line " + (row + 1) +" is not valid");
		}
	}

	public static void updateFile(File file, ArrayList<Element> arrayListOfElements) {
		BufferedWriter bufferWriter = null;
        try {
            String currentLine = "";
            bufferWriter = new BufferedWriter(new FileWriter(file));
            for (Element element : arrayListOfElements) {
				if (element.getColumn() == 0) {
					if (element.getRow() != 0) {
						bufferWriter.write("\n");
					}
				}
				else {
					bufferWriter.write(" || ");
				}
				bufferWriter.write(element.getKey() + ":" + element.getValue());
			}

        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        } finally {
            try {
                if (bufferWriter != null) {
                	bufferWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}