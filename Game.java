import java.util.*;

public class Game {

    // deck is a stack because we take cards off the top like in real life
    public static Stack<Card> deck;
    private static ArrayList<Card> playerHand;
    private static ArrayList<Card> botHand;
    public static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        intro();
        setup();
        printPlayerHand();
    }

    public static void intro()
    {
        System.out.println(
            "Welcome to Go Fish!\n"
            + "In case you don't know how to play, here is a small tutorial:\n"
            + "The goal of the game is to complete as many pairs as possible.\n"
            + "You do this by either drawing cards, 'Going Fish', or by asking your opponent for a card that you have already.\n"
            + "When all of the cards have been used up, the player with the most pairs wins.\n"
            + "In our rendition of the game, you will play against a bot we've designed.\n"
            + "Good luck, and have fun!\n"
        );
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

    public static void printPlayerHand() {
        String msg = "Your hand contains:\n";
        for(int cardIndex = 0; cardIndex < playerHand.size(); cardIndex++) {
            msg += "\t"+ (cardIndex + 1) + " - "+playerHand.get(cardIndex).toString()+"\n";
        }
        System.out.println(msg);
    }

    private static void playerTurn() {
        printPlayerHand();
        System.out.print("Which card would you like to ask the bot for? ");
        int answer = console.nextInt();
        Card selected = playerHand.get(answer - 1);
        boolean successful = turn(playerHand, botHand, selected.getValue());
        if(successful) System.out.println("The bot gave you a " + Card.ranks[selected.getValue()] + "!");
        else System.out.println("The bot tells you to go fish...");
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