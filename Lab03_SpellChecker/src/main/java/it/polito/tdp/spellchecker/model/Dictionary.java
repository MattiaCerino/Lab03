package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {
	
	List<String> dizionario = new LinkedList<>();
	
	public void loadDictionary(String language) {
		if(language.compareTo("Italian") == 0) {
			try {
				FileReader fr = new FileReader("C:\\Users\\Mattia\\Desktop\\eclipse-workspace\\00_Esercitazioni\\Lab03\\Lab03_SpellChecker\\src\\main\\resources\\Italian.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while((word = br.readLine()) != null) {
					dizionario.add(word);
				}
				br.close();
			} catch (IOException e) {
				System.out.println("Errore nella lettura del file");
			}
		}
		else {
			try {
				FileReader fr = new FileReader("C:\\Users\\Mattia\\Desktop\\eclipse-workspace\\00_Esercitazioni\\Lab03\\Lab03_SpellChecker\\src\\main\\resources\\English.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while((word = br.readLine()) != null) {
					dizionario.add(word);
				}
				br.close();
			} catch (IOException e) {
				System.out.println("Errore nella lettura del file");
			}
		}
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		List<RichWord> richWord = new LinkedList<>();
		for(String s : inputTextList) {
			if(dizionario.contains(s)) {
				RichWord rw = new RichWord(s, true);
				richWord.add(rw);
			}
			else {
				RichWord rw = new RichWord(s, false);
				richWord.add(rw);
			}
		}
		return richWord;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		List<RichWord> richWord = new LinkedList<>();
		for(String s : inputTextList) {
			boolean trovato = false;
			for(String ss : dizionario) {
				if(ss.compareTo(s) == 0) {
					trovato = true;
				}
			}
			if (trovato == false) {
				RichWord rw = new RichWord(s, false);
				richWord.add(rw);
			}
			else {
				RichWord rw = new RichWord(s, true);
				richWord.add(rw);
			}
		}
		return richWord;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		List<RichWord> richWord = new LinkedList<>();
		for(String s : inputTextList) {
			if(binarySearch(s) == true) {
				RichWord rw = new RichWord(s, true);
				richWord.add(rw);
			}
			else {
				RichWord rw = new RichWord(s, false);
				richWord.add(rw);
			}
		}
		return richWord;
	}

	private boolean binarySearch(String stemp) {
		int inizio = 0;
		int fine = dizionario.size();

		while (inizio != fine) {
			int medio = inizio + (fine - inizio) / 2;
			if (stemp.compareToIgnoreCase(dizionario.get(medio)) == 0) {
				return true;
			} else if (stemp.compareToIgnoreCase(dizionario.get(medio)) > 0) {
				inizio = medio + 1;
			} else {
				fine = medio;
			}
		}

		return false;
	}
}
