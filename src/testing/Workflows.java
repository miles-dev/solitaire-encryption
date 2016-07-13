package testing;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import core.Solitaire;

public class Workflows 
{
	@Test
	public void createKeyTest() 
	{
		Solitaire s = new Solitaire("TEST");
		s.createOrderedDeck();
		s.createKey();
		//TODO: This is based off of the code already. verify this manually.
		// Expect the key from an ordered Deck with a phrase of length 4 to be: 1, 14, 5, 23
		List<Integer> expectedKey = new ArrayList<Integer>();
		expectedKey.add(1);
		expectedKey.add(14);
		expectedKey.add(5);
		expectedKey.add(23);
		assertArrayEquals("Key matches orderedDeck key", s.getKey().toArray(), expectedKey.toArray());
	}
	
	@Test
	public void createDecksTest() 
	{
		Solitaire s = new Solitaire("TEST");
		s.createOrderedDeck();
		String[] expectedDeck = {"C0","C1","C2","C3","C4","C5","C6","C7","C8","C9","C10","C11","C12",
				  "D0","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11","D12","JA",
				  "H0","H1","H2","H3","H4","H5","H6","H7","H8","H9","H10","H11","H12","JB",
				  "S0","S1","S2","S3","S4","S5","S6","S7","S8","S9","S10","S11","S12"}; 
		assertArrayEquals("Matching Ordered Decks", s.getDeck().toStringArray(), expectedDeck);
		
		//TODO: Add additional testing on createRandomDeck() and createSpecifiedOrderDeck(String[])
	}
	
	@Test
	public void decryptTest() 
	{
		//TODO This is based off of the code already. verify this manually.
		Solitaire s = new Solitaire("USXQ");
		s.createOrderedDeck();
		s.createKey();
		String decrypted = s.decrypt(s.getOfficial());
		assertEquals("Decrypted with ordered deck", decrypted, "TEST");
	}
	
	@Test
	public void encryptTest() 
	{
		//TODO This is based off of the code already. verify this manually.
		Solitaire s = new Solitaire("TEST");
		s.createOrderedDeck();
		s.createKey();
		String encrypted = s.encrypt();
		assertEquals("Encryption with ordered deck", encrypted, "USXQ");
	}
	
	@Test
	public void prepareInputTest() 
	{
		Solitaire s = new Solitaire("ALL UPPER CASE AS EXPECTED");
		assertEquals("prepareFunction on clean input", s.getOfficial(), "ALLUPPERCASEASEXPECTED");
		
		s.prepareInput("all lower case");
		assertEquals("prepareFunction on lower case", s.getOfficial(), "ALLLOWERCASE");
		
		s.prepareInput("with1number");
		assertEquals("prepareFunction on case with a number", s.getOfficial(), "WITHNUMBER");
		
		s.prepareInput("with .'# special characters");
		assertEquals("prepareFunction on case with special characters", s.getOfficial(), "WITHSPECIALCHARACTERS");
		
		s.prepareInput("");
		assertEquals("prepareFunction on empty case", s.getOfficial(), "");
	}

}
