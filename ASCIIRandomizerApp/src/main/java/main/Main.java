package main;

import java.io.File;

import util.FileParser;
import custom_exceptions.InvalidFileInputException;
import randomizer_app.RandomizerApp;

public class Main {

     public static void main(String[] args) {
		try {
			FileParser.validateFile(args[0]);
	  		new RandomizerApp(new File(args[0])); 
		} catch (InvalidFileInputException | ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
		}
    }
}

