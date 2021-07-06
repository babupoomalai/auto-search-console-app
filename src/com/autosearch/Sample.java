package com.autosearch;
import java.io.FileNotFoundException;
import java.util.List;

public class Sample {

	public static void main(String args[]) {
		Trie trie = Trie.getInstance();
		trie.addWord("ball");
		trie.addWord("balt");
		trie.addWord("balk");
		trie.addWord("balp");
		trie.addWord("balq");
		trie.addWord("balta");
		trie.addWord("baltb");
		trie.addWord("balla");
		trie.addWord("bass");
		trie.addWord("bask");
		trie.addWord("basa");

		List<String> results = trie.search("bal");
		if (results == null) {
			System.out.println("No results found");
			return;
		}
		for (String result : results) {
			System.out.println(result);
		}
	}

	void constructTrie() throws FileNotFoundException {
//		File file = new File("words.txt");
//		FileInputStream fis = new FileInputStream(file);
//		fis.read()

	}

}
