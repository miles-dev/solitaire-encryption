package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Execute 
{
	/**
	 * Main runner to kick off the CLI prompts.
	 * @param args - Unused at this time.
	 */
	public static void main(String[] args)
	{
		// Get input from the user:
		Scanner reader = new Scanner(System.in);
		List<String> options = new ArrayList<String>();
		String phrase = getUserInput(reader, "Enter phrase: ", options).toLowerCase();
		
		options.clear();
		options.addAll(Arrays.asList("E","D"));
		String method = getUserInput(reader, "Enter e for encryption, enter d for decryption: ", options);
		
		options.clear();
		options.addAll(Arrays.asList("O","R","S"));
		String deckOrder = getUserInput(reader, "Enter o for an ordered deck, enter r for a random deck, enter s to specify your own deck: ", options);
		
		String specifiedDeck = "";
		if (deckOrder.equals("S"))
		{
			options.clear();
			specifiedDeck = getUserInput(reader, "Enter order for your deck (Ex: [C1,C2,...,JA53,...D17,...JB53,S48]: ", options);
		}
			
		System.out.println("phrase:" + phrase);
		System.out.println("Encrypt/Decrypt:" + method);
		System.out.println("Deck Order:" + deckOrder);
		reader.close();
		
		Solitaire runner = new Solitaire(phrase);

		// Decide if you want to use an ordered, random, or a specified deck.
		List<Card> generatedDeck = null;
		if (deckOrder.equals("O"))
			runner.createOrderedDeck();
		else if (deckOrder.equals("R"))
		{
			runner.createRandomDeck();
			generatedDeck = runner.getDeck().getCards();
		}
		else
			runner.createSpecifiedOrderedDeck(specifiedDeck);
			
		
		runner.createKey();
		String output;
		if (method.equals("E"))
			output = (runner.encrypt());
		else
			output = runner.decrypt(runner.getOfficial());
		
		// return the output in a 5-character spaced String.
		String officialOutput = "";
		for (int i = 0; i < output.length(); i++)
		{
			if (i % 5 == 0 && i != 0)
				officialOutput += " ";
			officialOutput += output.charAt(i);
		}
		System.out.println("Output:  " + officialOutput);
		if (deckOrder.equals("R"))
			System.out.println("Generated Deck Order was:\n" + generatedDeck);
	}
	
	/**
	 * Helper to pull out all of the logic for getting input from the user.
	 * @param reader - Scanner to pull input from the user.
	 * @param prompt - Prompt telling the user what input is needed.
	 * @param options - Where applicable, options the user is allowed to provide. 
	 * 			It will loop until one of the options is entered by the user.
	 * @return Input from the user that meets the options requirement if applicable.
	 */
	private static String getUserInput(Scanner reader, String prompt, List<String> options)
	{
		System.out.print(prompt);
		String input = reader.nextLine().toUpperCase();
		
		// If there are no options to check against, accept whatever the user enters.
		while (options.size() > 0 && !options.contains(input))
		{
			System.out.print(prompt);
			input = reader.nextLine().toUpperCase();
		}
		return input;
	}
}