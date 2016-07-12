package core;
/* Solitaire Ecryption Program
   miles-dev

 Deck will be in order i.e. A-K, CDaHbS; a = Joker A, b = Joker B
 Each card has a value which is used when encrypting/decrypting an input:
 	* C (Clubs): 1-13
 	* D (Diamonds): 14-26
 	* H (Hearts): 27-39
 	* S (Spades): 39-52
 	* Jokers: 53
 Joker A has a num of -1, and Joker B has a num of -2
 Steps for the encryption:
	1. Move Joker A down 1 card.
	2. Move Joker B down 2 cards.
	3. Triple cut: switch the cards before the 1st joker with
		those after the 2nd joker.
	4. Count Cut: look at the bottom card, count down from the
		top of the deck that many cards. Cut after the
		card you counted too. Keep the bottom card the same.
	5. Look at the top card, and count down that many cards from
		the top. If you hit a joker, start with step 1 again.
		Otherwise, that is the first value for the key.
	6. Store that value and restart from step 1 for as many times
		as needed.
*/

public class Execute 
{
	//TODO: Develop a CLI for user interaction.
	public static void main(String[] args)
	{
		String phrase = "AAHELLO THERE HOW ARE YOU..\\12!#$";
		System.out.println("input from user: " + phrase);
		
		Solitaire runner = new Solitaire(phrase);

		// Decide if you want to use an ordered or random deck. This is mostly for testing.
		runner.createOrderedDeck();
		//runner.createRandomDeck();
		
		// Create the key to be used for encryption/decryption.
		runner.createkey();
		
		System.out.println("original  phrase:  " + runner.getOfficial());
		String encrypted = (runner.encrypt());
		
		// return the output in a 5-character spaced String.
		String officialEncrypted = "";
		for (int i = 0; i < encrypted.length(); i++)
		{
			if (i % 5 == 0 && i != 0)
				officialEncrypted += " ";
			officialEncrypted += encrypted.charAt(i);
		}
		System.out.println("encrypted phrase:  " + officialEncrypted);
		System.out.println("encrypted:  " + encrypted);
		
		String decrypted = runner.decrypt(encrypted);
		
		// return the output in a 5-character spaced String.
		System.out.println("decrypted:  " + decrypted);
		String officialDecrypted = "";
		for (int i = 0; i < encrypted.length(); i++)
		{
			if (i % 5 == 0 && i != 0)
				officialDecrypted += " ";
			officialDecrypted += decrypted.charAt(i);
		}
		System.out.println("decrypted phrase:  " + officialDecrypted);	
	}
}