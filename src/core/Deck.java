package core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deck 
{
	/*TODO A potential refactor here would be to use a LinkedList instead of ArrayList. 
	 * That change would allow for the move and cut methods to be done with pointer logic
	 * rather than rearranging the lists. Given the size of the lists, and that they can
	 * never grow beyond 54, it isn't worth it right now. Could be interesting to experiment
	 * with later on. 
	 */
	// the deck of cards
	private List<Card> cards = new ArrayList<Card>(); 
	private Map<String, Card> valueToCardMap = new HashMap<String, Card>() 
	{{
		for (int i = 0; i < 54; i++)
		{
			if (i < 13)
				put("C" + i, new Card("C", i+1)); // C1-13
			else if (i < 26)
				put("D" + (i - 13), new Card("D", i+1)); // D14-26
			else if (i < 39)
				put("H" + (i - 26), new Card("H", i+1)); // H27-39
			else if (i < 52)
				put("S" + (i - 39), new Card("S", i+1)); // S40-52
			else if (i == 52)
				put("JA", new Card("JA", 53));
			else if (i == 53)
				put("JB", new Card("JB", 53));
		}
	}};

	/**
	 * Adds a Card to the end of the current Deck.
	 * @param card - New Card to be added.
	 */
	private void add(Card card) 
	{
		cards.add(card);		
	}

	/**
	 * @param pos - Position where the Card should be found.
	 * @return the Card at the provided position.
	 */
	public Card get(int pos) 
	{
		return cards.get(pos);
	}

	/**
	 * Removes a Card from a specific position within this Deck.
	 * @param pos - Position of the Card which should be removed.
	 */
	private void remove(int pos) 
	{
		cards.remove(pos);
	}

	/**
	 * Adds a new Card to a specific position within this Deck.
	 * @param pos - Position where the new Card should go.
	 * @param card - New Card to be added.
	 */
	private void add(int pos, Card card) {
		cards.add(pos, card);
	}
	
	/**
	 * Removes all of the Cards from this Deck.
	 */
	private void clear() 
	{
		cards.clear();
	}

	/**
	 * @return the list of Cards for this Deck.
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * Sets the this.cards object to match the order of the cards of the list of Cards being passed in.
	 * @param list - A list of Cards with a the desired ordering of the cards.
	 */
	private void setCards(List<Card> list) {
		this.cards = list;
	}

	/**
	 * @return the number of Cards stored in this.cards.
	 */
	private int size() {
		return cards.size();
	}

	/**
	 * Sets the this.cards object to match the order of the cards of the Deck being passed in.
	 * @param input - A Deck object with a the desired ordering of the cards.
	 */
	private void setCards(Deck input)
	{
		this.setCards(input.getCards());
	}
	
	/**
	 * Given a String representing the order of the cards, fill in this.cards with the appropriate Card objects.
	 * @param specifiedDeck - String of comma separated values showing the order of the cards. 
	 * precondition: specifiedDeck is of the form: [C1,...,JA53,...,D16,...,JB53,...,S50]
	 * 		Each value matches up with a key from the valueToCardMap.
	 * 		Not required, but it is expected that specifiedDeck will originate from the output of running in
	 * 			encrypt mode with a random deck previously.
	 */
	public void createDeck(String specifiedDeck)
	{
		specifiedDeck = specifiedDeck.replaceAll("[^A-Z0-9,]", "");
		String[] splitSpecifiedDeck = specifiedDeck.split(",");
		for (int i = 0; i < splitSpecifiedDeck.length; i++)
		{
			char suit = splitSpecifiedDeck[i].charAt(0);
			if (suit == 'J')
				cards.add(valueToCardMap.get(splitSpecifiedDeck[i].substring(0,2)));
			else
				cards.add(valueToCardMap.get(suit + "" + ((Integer.parseInt(splitSpecifiedDeck[i].substring(1)) - 1) % 13)));
		}
	}
	
	/**
	 * Given a String[] representing the order of the cards, fill in this.cards with the appropriate Card objects.
	 * @param specifiedDeck - String[] with values showing the order of the cards. 
	 */
	public void createDeck(String[] specifiedDeck)
	{
		for (int i = 0; i < specifiedDeck.length; i++)
		{
			cards.add(valueToCardMap.get(specifiedDeck[i]));
		}
	}
	
	/**
	 * Find the joker in question (either 'a' or 'b') and shift it down in the deck the specified number of spaces.
	 * If shifting the joker involves going past the bottom of the deck, then cycle back to the top of the deck and continue.
	 * @param type - either 'a' or 'b' to designate which joker it is.
	 * @param posShift - either 1 or 2 to designate the number of cards the joker needs to be shifted down.
	 */
	public void moveJoker(String type, int posShift)
	{
		int curJokerPos = 0;
		for (; curJokerPos < 54; curJokerPos++)
		{
			if (cards.get(curJokerPos).getSuit().equals(type))
				break;
		}
		Card holdJoker = cards.get(curJokerPos);
		cards.remove(curJokerPos);
		
		// The Joker can never become the first card as you must consider the cards to actually be in a cycle and not a list.
		if (((curJokerPos+posShift) % 54) == 0)
			cards.add(1,holdJoker);
		else
			cards.add((curJokerPos+posShift) % 54, holdJoker);
	}

	/**
	 * Switch the cards before the 1st Joker with those after the 2nd Joker.
	 * The order of the Jokers doesn't matter.
	 * i.e. [set<Cards>1]JA[set<Cards>2]JB[set<Cards>3] becomes [set<Cards>3]JA[set<Cards>2]JB[set<Cards>1] 
	 */
	public void tripleCut()
	{
		// Find the current positions of the Jokers
		int posJA = -1, posJB = -1;
		for (int i = 0; i < cards.size(); i++)
		{
			if (cards.get(i).getVal() == 53)
			{
				if (posJA == -1)
					posJA = i;
				else
					posJB = i;
			}
		}
		
		// Create the new ordering of the cards around the Joker positions
		List<Card> newCards = new ArrayList<Card>();
		newCards.addAll(cards.subList(posJB+1, cards.size()));
		newCards.add(cards.get(posJA));
		newCards.addAll(cards.subList(posJA+1, posJB));
		newCards.add(cards.get(posJB));
		newCards.addAll(cards.subList(0, posJA));
		
		cards = newCards;
	}

	/**
	 * Look at the bottom card, count down from the top of the deck that many cards. 
	 * Cut after the card you counted to. Keep the bottom card the same. 
	 */
	public void countCut()
	{
		Deck newDeck = new Deck();
		
		// Need to have the '- 1' as the cards have a value of 1-53 and the deck is zero indexed.
		int cutPos = cards.get(cards.size() - 1).getVal() - 1;
		
		for (int i = (cutPos + 1) % 54; (i %= 54) != cutPos; i++) 
		{
			// Don't include the last Card in the cut as it has to stay at the bottom of the Deck.
			if (i != cards.size() - 1)
				newDeck.add(cards.get(i % 54));
		}
		// Add the card at the cut and keep the last card at the bottom.
		newDeck.add(cards.get(cutPos));
		newDeck.add(cards.get(cards.size() - 1));
		
		setCards(newDeck);
	}

	/**
	 * Primarily for testing. Returns a String[] of all of the cards in the Deck in current order.
	 * @return String[] of the cards in the current Deck.
	 */
	public String[] toStringArray() {
		String[] currentCards = new String[54];
		Card cur;
		for (int i = 0; i < cards.size(); i++)
		{
			cur = cards.get(i);
			if (cur.getVal() == 53)
			{
				currentCards[i] = cur.getSuit();
			}
			else
			{
				currentCards[i] = cur.getSuit() + ((cur.getVal() -1) % 13);
			}
		}
		return currentCards;
	}
}