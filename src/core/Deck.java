package core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deck 
{
	private List<Card> cards = new ArrayList<Card>(); // the deck of cards
	private Map<String, Card> valueToCardMap = new HashMap<String, Card>() 
	{{
		for (int i = 0; i < 54; i++)
		{
			if (i < 13)
				put("C" + i, new Card("C", i+1));
			else if (i < 26)
				put("D" + (i - 13), new Card("D", i+1));
			else if (i < 39)
				put("H" + (i - 26), new Card("H", i+1));
			else if (i < 52)
				put("S" + (i - 39), new Card("S", i+1));
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
	private List<Card> getCards() {
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
	 * Given a String[] representing the order of the cards, fill in this.cards with the appropriate Card objects.
	 * @param givenDeck - String[] representing the order of the cards. Each String matches up with a key from the
	 * 	valueToCardMap.
	 */
	public void createDeck(String[] givenDeck)
	{
		for (int i = 0; i < givenDeck.length; i++)
		{
			cards.add(valueToCardMap.get(givenDeck[i]));
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
		for (int findJokerPos = 0; findJokerPos < 54; findJokerPos++)
		{
			if (cards.get(findJokerPos).getSuit().equals(type))
			{
				curJokerPos = findJokerPos;
				break;
			}
		}
		Card holdJoker = cards.get(curJokerPos);
		cards.remove(curJokerPos);
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
		if (cutPos == 0)
			System.out.println("hi");
		
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
}