package core;
public class Card
{
	// Specifies the suit of the card.
	private String suit;
	
	// Specifies the value of the card (1-53)
	private int val;

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
	 * Set the suit of the Card to the provided suit.
	 * @param suit - value to be set as this Cards new suit.
	 */
	public void setSuit(String suit) {
		this.suit = suit;
	}

	/**
	 * Set the val of the Card to the provided val.
	 * @param val - value to be set as this Cards new val.
	 */
	public void setVal(int val) {
		this.val = val;
	}
	
	/**
	 * @return the toString() representation of the Card. This will be of the form "<suit><val>"
	 */
	public String toString()
	{
		return suit + val;
	}
}