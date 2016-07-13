package core;

public final class Card
{
	// Specifies the suit of the card.
	private final String suit;
	
	// Specifies the value of the card (1-53)
	private final int val;

	/**
	 * Create a new Card with the specified suit and val.
	 * @param suit - value of the suit for the new Card.
	 * @param val - value of the val for the new Card.
	 */
	public Card(String suit, int val)
	{
		this.suit = suit;
		this.val = val;
	}

	/**
	 * @return the suit of the Card.
	 */
	public String getSuit()
	{
		return suit;
	}

	/**
	 * @return the value for the Card.
	 */
	public int getVal()
	{
		return val;
	}

	/**
	 * @return the toString() representation of the Card. This will be of the form "<suit><val>"
	 */
	public String toString()
	{
		return suit + val;
	}
}