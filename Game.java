import java.util.*;

public class Game {

    // deck is a stack because we take cards off the top like in real life
    public static Stack<Card> deck;
    private static ArrayList<Card> playerHand;
    private static ArrayList<Card> botHand;

    public static void main(String[] args) {
        setup();
    }

    public static void setup() {
        // creates and fills the deck of cards
        deck = new Stack<Card>();

        for(Card.Suit suit : Card.Suit.values()) {
            for(int value = 1; value <= 13; value++) {
                deck.push(new Card(suit, value));
            }
        }

        // shuffles the deck
        Collections.shuffle(deck);

        // gives the player and bot their initial seven cards
        playerHand = new ArrayList<Card>();
        botHand = new ArrayList<Card>();

        for(int cardNum = 0; cardNum < 7; cardNum++) {
            playerHand.add(deck.pop());
            botHand.add(deck.pop());
        }
    }

    // checks if a card with the number cardNum is in 
    // the otherHand and moves it over to hand if it is
    // returns true if the card was found and moved, false if not
    private static boolean turn(ArrayList<Card> hand, ArrayList<Card> otherHand, int cardNum) {
        for(Card card : otherHand) {
            if(card.getValue() == cardNum) {
                otherHand.remove(card);
                hand.add(card);
                return true;
            }
        }
        return false;
    }
}