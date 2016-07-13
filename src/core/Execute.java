package core;

import java.util.List;
import java.util.Scanner;

public class Execute 
{
	public static void main(String[] args)
	{
		// Get input from the user:
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter phrase: ");
		String input = reader.nextLine();
		
		String methodRequest = "Enter e for encryption, enter d for decryption:";
		System.out.print(methodRequest);
		String method = reader.next().toLowerCase();
		while (!method.equals("e") && !method.equals("d"))
		{
			System.out.print(methodRequest);
			method = reader.next();
		}
		
		String deckOrderRequest = "Enter o for an ordered deck, enter r for a random deck, enter s to specify your own deck:";
		System.out.print(deckOrderRequest);
		String deckOrder = reader.next().toLowerCase();
		while (!deckOrder.equals("o") && !deckOrder.equals("r") && !deckOrder.equals("s"))
		{
			System.out.print(deckOrderRequest);
			deckOrder = reader.next();
		}
		
		String specifiedDeck = "";
		if (deckOrder.equals("s"))
			while (specifiedDeck.isEmpty())
			{
				System.out.print("Enter order for your deck (Ex: [C1,C2,...,JA53,...D17,...JB53,S48]:");
				specifiedDeck = reader.nextLine();
			}
		
		System.out.println("phrase:" + input);
		System.out.println("Encrypt/Decrypt:" + method);
		System.out.println("Deck Order:" + deckOrder);
		reader.close();
		
		Solitaire runner = new Solitaire(input);

		// Decide if you want to use an ordered, random, or a specified deck.
		List<Card> generatedDeck = null;
		if (deckOrder.equals("o"))
			runner.createOrderedDeck();
		else if (deckOrder.equals("r"))
		{
			runner.createRandomDeck();
			generatedDeck = runner.getDeck().getCards();
		}
		else //TODO: Bug exists somewhere in this workflow. Need to track down.
			runner.createSpecifiedOrderedDeck(specifiedDeck);
		
		runner.createKey();
		String output;
		if (method.equals("e"))
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
		if (deckOrder.equals("r"))
			System.out.println("Generated Deck Order was:\n" + generatedDeck);
	}
}