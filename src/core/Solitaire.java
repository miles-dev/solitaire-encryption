package core;
import java.util.ArrayList;
import java.util.List;

public class Solitaire
{
	// The deck of cards to be used to calculate the key
	private Deck deck = new Deck();
	
	// Key for encrypting/decrypting
	private List<Integer> key;
	
	// Input provided from the user which is to be encrypted/decrypted
	private String input;
	
	// TODO: Potential place to clean up would be to find another solution than this for the ordered deck workflow
	// input for an ordered deck here:
	private final String[] deckOrder = {"C0","C1","C2","C3","C4","C5","C6","C7","C8","C9","C10","C11","C12",
								  "D0","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11","D12","JA",
								  "H0","H1","H2","H3","H4","H5","H6","H7","H8","H9","H10","H11","H12","JB",
								  "S0","S1","S2","S3","S4","S5","S6","S7","S8","S9","S10","S11","S12"};

	/**
	 * Create a new Solitaire object for the specified input phrase and instantiate the key list with the given length.
	 * @param phrase
	 */
	public Solitaire(String phrase)
	{
		prepareInput(phrase);
		key = new ArrayList<Integer>(this.input.length());
	}
	
	/**
	 * Cleans up the input string by removing white space and non-alphabet characters and setting all characters to upper case.
	 * Also stores the cleaned up input in the global variable for future use within program. 
	 * @param input - String from the user which is to be encrypted/decrypted.
	 */
	public void prepareInput(String input)
	{
		input = input.replaceAll("[^a-zA-Z]", "");
		
		this.input = input.toUpperCase();
	}

	/**
	 * Create a deck for this instance with the Cards in order.
	 */
	public void createOrderedDeck()
	{
		deck.createDeck(deckOrder);
	}
	
	/**
	 * Create a deck for this instance with the Cards in a random order.
	 */
	public void createRandomDeck() {
		List<Integer> used = new ArrayList<Integer>();
		String[] randomDeck = new String[54];
		for (int i = 0; i < randomDeck.length; i++)
		{
			used.add(i);
		}
		for(int pos = 0; pos < randomDeck.length; pos++)
		{
			int i = (int) (Math.random()*used.size());
			int cur = used.get(i);
			randomDeck[pos] = deckOrder[cur];
			used.remove(i);
		}
		deck.createDeck(randomDeck);
	}
	
	/**
	 * Create a new Deck with the cards in the specified order as provided in the passed in String.
	 * @param specifiedDeck - String representing the order wanted for the cards.
	 */
	public void createSpecifiedOrderedDeck(String specifiedDeck)
	{
		deck.createDeck(specifiedDeck);
	}

	/**
	 * Executes the move set for the Solitaire Encryption Algorithm to populate the 'key' object.
	 * At the end of running this array, the key will be completely produced at the necessary length to complete encryption/decryption.
	 */
	public void createKey()
	{
		// Iterate until the key is the same length as the input.
		while (key.size() != input.length())
		{
			deck.moveJoker("JA", 1);
			deck.moveJoker("JB", 2);
			deck.tripleCut();
			deck.countCut();
			countToUpdateKey();
		}
	}

	/**
	 * Look at the top card, and count down that many cards from the top. If you hit a joker, start with step 1 again. 
	 * Otherwise, add that value to the key.
	 * @return:
	 * 		true: The key has been successfully updated with a new value.
	 * 		false: We hit a joker and no update has been pushed to the key. 
	 */
	private void countToUpdateKey()
	{
		// Need to have the '- 1' as the cards have a value of 1-53 and the deck is zero indexed. 
		int count = deck.get(0).getVal() - 1;
		int val = (deck.get(count)).getVal();

		//TODO: Possible improvement to determine if the key must be %26 for the math to work and not turn 'A' into '[' during decryption.
		// If we get back a Joker, val == 53, we don't want to add it to the key and we want to return false.
		if (val != 53)
			key.add(val % 26);
	}

	/**
	 * Calculate and return the encrypted version of the String stored in the variable 'official'.
	 * @return: A String representing the encrypted version of the input using the already created key. 
	 */
	public String encrypt()
	{
		String encrypted = "";
		
		// Need to keep track of where you are in the key to decrypt the current letter.
		int posInKey = 0;
		for (char letter : input.toCharArray())
		{
			// Need "- 'A'" to make the math simpler for the % 26 as we may have to loop past Z and go back to A.
			int newLetter = letter - 'A';
			newLetter = (newLetter + key.get(posInKey++)) % 26;
			encrypted += (char) (newLetter + 'A');
		}
		return encrypted;
	}

	/** 
	 * Calculate and return the decrypted version of the phrase.
	 * @param phrase - The encrypted text String which needs to be decrypted.
	 * @return: A String representing the decrypted version of the input using the already created key.
	 */
	public String decrypt(String phrase)
	{
		String decrypted = "";
		
		// Need to keep track of where you are in the key to decrypt the current letter with the right part of the key.
		int posInKey = 0;
		for (char letter : phrase.toCharArray())
		{
			// Need "- 'A'" to make the math simpler for the % 26 as we may have to loop past Z and go back to A.
			int newLetter = (letter - 'A');
			int shift = key.get(posInKey++);
			
			if (shift > newLetter)
				newLetter = (newLetter - shift) % 26 + 26;
			else
				newLetter = (newLetter - shift) % 26;

			decrypted += (char) (newLetter + 'A');
		}
		return decrypted;
	}

	/**
	 * @return the prepared phrase which will be encrypted/decrypted.
	 */
	public String getOfficial() 
	{
		return input;
	}
	
	/**
	 * @return the current Deck
	 */
	public Deck getDeck() {
		return deck;
	}
}