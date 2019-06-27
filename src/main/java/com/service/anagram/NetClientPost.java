package com.service.anagram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


public class NetClientPost {
	static int count = 1;
	public static void main(String[] args) throws JsonProcessingException {

		  Scanner in = new Scanner(System.in);
		  System.out.println("Please Enter a string");
	      String input = in.nextLine();
	      System.out.println("You entered string " + input);	  
		  
	      AnagramService anagramService = new AnagramService();
	      if(anagramService.validateRequest(input)) {
	    	
	    		  Phrase phrase = new Phrase();
	    		  phrase.setPhrase(input);
	    		  ObjectMapper Obj = new ObjectMapper(); 
	    		  String jsonStr = Obj.writeValueAsString(phrase); 
	    		  System.out.println(count(jsonStr,input)); 

	      } else {
	    	  System.out.println("Please correct the Input."
	    	  		+ "Accepts an input string from the user (same constraints as the server input - space delimited, alpha-only words)");
	      }
	  }
	
	public static int count(String jsonStr, String input) {
		System.out.println("jsonString "+jsonStr);
		System.out.println("Input "+input);
		try {

			URL url = new URL("http://localhost:3000/anagram");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
		
			OutputStream os = conn.getOutputStream();
			os.write(jsonStr.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			StringBuilder sb = new StringBuilder();
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
					//System.out.println(output);
					sb.append(output);
			}
		    System.out.println(sb.toString());
			Anagram anagram = new Gson().fromJson(sb.toString(), Anagram.class);
			System.out.println(anagram.getAnagram());
			
			if(anagram.getAnagram()!=null && !anagram.getAnagram().equalsIgnoreCase("") && !anagram.getAnagram().equalsIgnoreCase(input) && count < 10) {
				 System.out.println("Counting.."+count);
	    		  Phrase phrase = new Phrase();
	    		  phrase.setPhrase(anagram.getAnagram());
	    		  ObjectMapper Obj = new ObjectMapper(); 
	    		  String jsonStr1 = Obj.writeValueAsString(phrase); 
	    		  count++;
	    		  count(jsonStr1, input);
			}
			conn.disconnect();
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();
	  }
		return count;
	}
	}

