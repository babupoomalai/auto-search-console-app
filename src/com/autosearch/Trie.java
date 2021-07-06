package com.autosearch;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Queue;

/**
 * Design a solution to complete auto search
 * 
 * @author bpoomalai
 *
 */
public class Trie {

	private static Trie instance;

	public static Trie getInstance() {
		if (instance == null) {
			instance = new Trie();
		}
		return instance;
	}

	// Keep the insertion order
	LinkedHashMap<Character, Trie> dictionary = new LinkedHashMap<>();

	private int SEARCH_LIMIT = 3;

	public void addWord(String text) {
		char[] charArray = text.toCharArray();
		Trie trie = getInstance();

		for (Character c : charArray) {
			if (!trie.getDictionary().containsKey(c)) {
				trie.getDictionary().put(c, new Trie());
			}
			trie = trie.getDictionary().get(c);
		}
	}

	public List<String> search(String toSearch) {

		char[] charArray = toSearch.toCharArray();
		LinkedHashMap<Character, Trie> prefixTree = search(charArray, 0, dictionary);
		if (prefixTree == null || prefixTree.get(toSearch.toCharArray()[toSearch.length() - 1]) == null) {
			return null;
		}

		return getResults(toSearch, prefixTree);
	}

	private List<String> getResults(String prefix, LinkedHashMap<Character, Trie> prefixTree) {
		List<String> results = new ArrayList<>();
		Queue<QueryResult> queue = new ArrayDeque<>();

		queue.add(new QueryResult(prefix, prefixTree.get(prefix.toCharArray()[prefix.length() - 1])));

		int index = 0;
		while (!queue.isEmpty() && index < SEARCH_LIMIT + 1) {
			QueryResult query = queue.remove();
			String queryPrefix = query.getPrefix();
			results.add(queryPrefix);
			Trie trie = query.getTrie();

			if (trie != null && trie.getDictionary().size() > 0) {
				for (Character c : trie.getDictionary().keySet()) {
					queue.add(new QueryResult(queryPrefix + c, trie.getDictionary().get(c)));
				}
			}
			index++;
		}
		return results;
	}

	private LinkedHashMap<Character, Trie> search(char[] charArray, int index, LinkedHashMap<Character, Trie> subTrie) {
		if (index == charArray.length - 1) {
			return subTrie;
		}

		if (subTrie.containsKey(charArray[index])) {
			return search(charArray, index + 1, subTrie.get(charArray[index]).getDictionary());
		} else {
			return null;
		}
	}

	public void printAll() {
		List<String> wordList = new ArrayList<>();

		for (Character c : dictionary.keySet()) {
			print(c + "", dictionary.get(c), wordList);
		}

		for (String word : wordList) {
			System.out.println(word);
		}
	}

	private void print(String text, Trie trie, List<String> wordList) {
		if (trie.getDictionary().size() == 0) {
			wordList.add(text.toString());
			return;
		}

		for (Character c : trie.getDictionary().keySet()) {
			print(text + c, trie.getDictionary().get(c), wordList);
		}
	}

	public LinkedHashMap<Character, Trie> getDictionary() {
		return dictionary;
	}

	@Override
	public String toString() {
		return "Trie [dictionary=" + dictionary + "]";
	}

}

class QueryResult {
	private String prefix;
	private Trie trie;

	public QueryResult(String prefix, Trie trie) {
		super();
		this.prefix = prefix;
		this.trie = trie;
	}

	public String getPrefix() {
		return prefix;
	}

	public Trie getTrie() {
		return trie;
	}

}
