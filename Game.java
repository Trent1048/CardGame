import java.util.*;

public class Game {

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
}