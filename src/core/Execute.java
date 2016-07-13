package core;

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
		runner.createKey();
		
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