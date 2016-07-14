package testing;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
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

		// Expect the key from an ordered Deck with a phrase of length 4 to be: 1, 17, 9, 22
		List<Integer> expectedKey = new ArrayList<Integer>();
		expectedKey.add(1);
		expectedKey.add(17);
		expectedKey.add(9);
		expectedKey.add(22);
		
		Field f = null;
		List<Integer> createdKey = null;
		try {
			f = s.getClass().getDeclaredField("key");
			f.setAccessible(true);
			createdKey = (List<Integer>) f.get(s);
		} catch (Exception e) {
			// Not worrying about various exceptions given this is a Unit Test.
			e.printStackTrace();
		}
		
		
		assertArrayEquals("Key matches orderedDeck key", createdKey.toArray(), expectedKey.toArray());
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
		
		//TODO: Add additional testing on createRandomDeck and createSpecifiedOrderDeck workflows.
	}
	
	@Test
	public void decryptTest() 
	{
		Solitaire s = new Solitaire("UVBP");
		s.createOrderedDeck();
		s.createKey();
		String decrypted = s.decrypt(s.getOfficial());
		assertEquals("Decrypted with ordered deck", decrypted, "TEST");
		
		// Including this test case to catch the edges of the alphabet.
		s = new Solitaire("BSLZVTE");
		s.createOrderedDeck();
		s.createKey();
		decrypted = s.decrypt(s.getOfficial());
		assertEquals("Decrypted with ordered deck, edge letters", decrypted, "ABCDXYZ");
	}
	
	@Test
	public void encryptTest() 
	{
		Solitaire s = new Solitaire("TEST");
		s.createOrderedDeck();
		s.createKey();
		String encrypted = s.encrypt();
		assertEquals("Encryption with ordered deck", encrypted, "UVBP");
		
		// Including this test case to catch the edges of the alphabet.
		s = new Solitaire("ABCDXYZ");
		s.createOrderedDeck();
		s.createKey();
		encrypted = s.encrypt();
		assertEquals("Encryption with ordered deck, edge letters", encrypted, "BSLZVTE");
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
