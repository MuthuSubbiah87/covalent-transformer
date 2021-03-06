package com.covalent.spellchecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;
import com.swabunga.spell.event.TeXWordFinder;

/**
 * 
 * @author Muthu Subbiah
 *
 */

public class SpellChker implements SpellCheckListener {

	private SpellChecker spellChecker;
	private List<String> misspelledWords = new ArrayList<String>();;

	private DictionaryMap dictionaryMap;
	private String inputString;
	private String correctedString;
	private Properties customDictonary;
	private Properties ignoreWordsDictionary;
	private int errorWordCount = 0;

	public SpellChker(String filePath, Properties ignoreWords,
			Properties customeDictonary) {
		this.customDictonary = customeDictonary;
		ignoreWordsDictionary = ignoreWords;
		if (filePath != null && !filePath.isEmpty()) {

			dictionaryMap = DictionaryMap.getInstance(filePath);
			spellChecker = new SpellChecker(dictionaryMap.getDictionaryMap());

			spellChecker.addSpellCheckListener(this);
		}
	}

	// Method to Detect Misspelled Words From Given Line
	public List<String> detectMisspelledWords(String text) {

		StringWordTokenizer strTokenizer = new StringWordTokenizer(text,
				new TeXWordFinder());
		spellChecker.checkSpelling(strTokenizer);
		return misspelledWords;
	}

	// Method to do Correction on Misspelled Words for the given Line
	public String doCorrection(String line) {
		inputString = line;
		correctedString = line;
		errorWordCount = 0;

		StringWordTokenizer strTokenizer = new StringWordTokenizer(line,
				new TeXWordFinder());
		spellChecker.checkSpelling(strTokenizer);

		System.out.println("original:" + inputString);
		System.out.println("corrected:" + correctedString);

		return correctedString;
	}

	@Override
	public void spellingError(SpellCheckEvent event) {
		errorWordCount++;
		boolean needTobeReplaced = false;

		List<Word> suggestions = event.getSuggestions();
		String replaementCandidate = event.getInvalidWord();
		System.out.println("MISSPELT WORD(" + errorWordCount + "):"
				+ event.getInvalidWord());
		if (isInIgnoreWordDictonary(event.getInvalidWord())) {
			System.out.println("Ignoring:" + event.getInvalidWord());
		} else if (isInCustomDictonary(event.getInvalidWord())) {
			replaementCandidate = getCustomDictoaryReplaement(event
					.getInvalidWord());
			System.out.println("custum replacemet:" + replaementCandidate);
			needTobeReplaced = true;
		} else {

			if (suggestions.size() > 0) {
				replaementCandidate = suggestions.get(0).toString();
				System.out
						.println("replaementCandidate:" + replaementCandidate);
				needTobeReplaced = true;
			} else {
				System.out.println("\tNo suggestions");
			}
		}
		if (needTobeReplaced)
			correctedString = correctedString.replace(event.getInvalidWord(),
					replaementCandidate);
	}

	private boolean isInIgnoreWordDictonary(String invalidWord) {
		return ignoreWordsDictionary.containsKey(invalidWord);
	}

	private String getCustomDictoaryReplaement(String invalidWord) {
		return customDictonary.getProperty(invalidWord);
	}

	private boolean isInCustomDictonary(String invalidWord) {
		return customDictonary.containsKey(invalidWord);
	}

}