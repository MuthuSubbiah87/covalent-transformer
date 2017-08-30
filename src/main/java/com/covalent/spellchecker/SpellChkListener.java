package com.covalent.spellchecker;

import java.util.List;

import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;;

/**
 * 
 * @author Muthu Subbiah
 *
 */

public class SpellChkListener implements SpellCheckListener {

	private List<String> misspelledWords;

	public SpellChkListener(List<String> misspelledWords) {
		this.misspelledWords = misspelledWords;
	}

	@Override
	public void spellingError(SpellCheckEvent event) {
		event.ignoreWord(true);
		misspelledWords.add(event.getInvalidWord());
	}

}