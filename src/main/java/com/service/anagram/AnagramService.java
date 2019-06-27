package com.service.anagram;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class AnagramService {
public boolean validateRequest(String request) {
		return request!=null && isCorrectLength(request) && isAlphaOnly(request);
}

public boolean isCorrectLength(String request) {
	return request.length() < 1024;
}

public boolean isAlphaOnly(String request) {
	String regex = "^[a-zA-Z ]*$";
	System.out.println("Validate the Input String - "+request);
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(request);
	return (matcher.matches());
}

public String anagramWord(String word) {
	System.out.println("Searching anagram for word - "+word);
	String fileName = "C:\\Users\\meets\\Downloads\\wdvg-anagram\\wdvg-anagram\\words_alpha.txt";
	List<String> listOfWords = new ArrayList<String>();
	String anagram = null;
	try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
		listOfWords = br.lines().collect(Collectors.toList());
		for(String dictionaryWord:listOfWords) {
			//System.out.println("Checking for word "+dictionaryWord+" from dictionary");
			if(isAnagram(word, dictionaryWord)) {
				anagram = dictionaryWord;
				break;
			}
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	return anagram;
}

public boolean  isAnagram(String word, String dictionaryWord) {  
    word = word.replaceAll("\\s", "");  
    dictionaryWord = dictionaryWord.replaceAll("\\s", "");  
    if (word.length() != dictionaryWord.length()) {  
        return false;
    } else {  
    	//System.out.println("Checking for word "+dictionaryWord+" from dictionary");
        char[] arrayS1 = word.toLowerCase().toCharArray();  
        char[] arrayS2 = dictionaryWord.toLowerCase().toCharArray();  
        Arrays.sort(arrayS1);  
        Arrays.sort(arrayS2);  
        return Arrays.equals(arrayS1, arrayS2);  
    }  
}
}
