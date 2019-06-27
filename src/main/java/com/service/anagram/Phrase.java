package com.service.anagram;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Phrase {

@NotNull
@Size(min=1, message="Phrase should be not null and can't be blank")
String phrase;

public String getPhrase() {
	return phrase;
}

public void setPhrase(String phrase) {
	this.phrase = phrase;
}

	
}
