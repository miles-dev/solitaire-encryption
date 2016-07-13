# solitaire-encryption
Repository of side project experimenting with solitaire encryption.
By: miles-dev

This project is intended as an opportunity to experiment with the solitaire encryption algorithm.
An in depth description of this algorithm can be found here: https://en.wikipedia.org/wiki/Solitaire_(cipher).

A default Deck of Cards when in order will be: A-K, CDaHbS; a = JokerA, b = JokerB
 Each card has a value which is used when encrypting/decrypting an input:
 	* C (Clubs): 1-13
 	* D (Diamonds): 14-26
 	* H (Hearts): 27-39
 	* S (Spades): 39-52
 	* Jokers: 53
 Each letter has a value given its position in the alphabet: A-Z = 1-26 

The steps of the algorithm in question are:
	1. Move Joker A down 1 card.
	2. Move Joker B down 2 cards.
	3. Triple cut: switch the cards before the 1st joker with those after the 2nd joker.
	4. Count Cut: look at the bottom card, count down from the top of the deck that many cards. 
		Cut after the card you counted too. Keep the bottom card the same.
	5. Look at the top card, and count down that many cards from the top. If you hit a joker, 
		start with step 1 again. Otherwise, that is the newest value for the Key.
	6. Append that value to the Key and restart from step 1 for as many times as needed.
The end goal will be to have a Key with the same number of values as the length of the input phrase.

Encryption Steps:
	1. Step through the input phrase and the Key in parallel.
	2. Add the value of the Key to the character in question and then mod the sum by 26.
	3. Append the new character to the output string.
	4. Repeat steps 1-3 for the entire length of the input phrase and then return the now encrypted output.
	
Decryption Steps:
	1. Step through the input phrase and the Key in parallel.
	2. Substract the value of the Key from the character in question and then mod the result by 26.
	3. Append the new character to the output string.
	4. Repeat steps 1-3 for the entire length of the input phrase and then return the now decrypted output.