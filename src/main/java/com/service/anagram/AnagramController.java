package com.service.anagram;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnagramController {
	
@Autowired
AnagramService anagramService;

@RequestMapping(value = "/anagram", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Anagram> returnAnagram(@Valid @RequestBody Phrase phrase) {
		System.out.println("Input Phrase - "+phrase.getPhrase());
		
		if(anagramService.validateRequest(phrase.getPhrase())) {
			System.out.println("Validated the Request Successfully");
			
			String[] wordArray = phrase.getPhrase().split(" ");
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<wordArray.length; i++) {
				String anagramWord = anagramService.anagramWord(wordArray[i]);
				System.out.println("anagramWord ==> "+anagramWord);
				if(anagramWord!=null) {
					sb.append(anagramWord);
					sb.append(" ");
				}
			}

	    	Anagram anagram = new Anagram();
	    	anagram.setAnagram(sb.toString().trim());
	        return new ResponseEntity<Anagram>(anagram, HttpStatus.OK);
		} else {
			System.out.println("Input Request validation failed");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		

				

    }
}