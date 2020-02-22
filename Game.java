import java.util.*;

public class Game {

    // deck is a stack because we take cards off the top like in real life
    public static Stack<Card> deck;
    private static ArrayList<Card> playerHand;
    private static ArrayList<Card> botHand;

    public static Scanner console = new Scanner(System.in);
    public static Random random = new Random();

    // for keeping scores
    private static int playerScore;
    private static int botScore;

    public static void main(String[] args) {

        intro();
        setup();

        playerScore = removePairs(playerHand);
        botScore = removePairs(botHand);

        boolean playing = true;

        while(playing) {
            playerTurn();
            botTurn();

            if(deck.isEmpty()) {
                playing = false;
            }
        }

        if(playerScore > botScore) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose!");
        }
        System.out.println("You had " + playerScore + " pairs and the bot had " + botScore + " pairs.");
    }

    public static void intro() {
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

    // looks through the hand and removes any pairs of cards with the same value
    // returns how many pairs were removed
    private static int removePairs(ArrayList<Card> hand) {
        int pairsRemoved = 0;
        for(int i = 0; i < hand.size(); i++) {
            for(int k = 0; k < hand.size(); k++) {
                // only check when the cards aren't the exact same
                if(i != k) {
                    Card card = hand.get(i);
                    Card otherCard = hand.get(k);

                    if(card.getValue() == otherCard.getValue()) {
                        hand.remove(card);
                        hand.remove(otherCard);
                        pairsRemoved++;
                    }
                }
            }
        }
        return pairsRemoved;
    }

    private static void botTurn() {
        int cardIndex = random.nextInt(botHand.size());
        Card card = botHand.get(cardIndex);
        int num = card.getValue();
        boolean gaveAwayCard = turn(botHand, playerHand, num);

        System.out.println("The bot asked for a " + Card.ranks[num - 1]);
        
        if(gaveAwayCard) {
            System.out.println("You gave away a card");
            String msg = "You have " + playerScore + " pair";
            // so it doesn't say "1 pairs"
            if(playerScore != 1) {
                msg += "s";
            }
            System.out.println(msg);
        } else {
            System.out.println("You told the bot to go fish");
        }
        botScore += removePairs(botHand);
    }

    public static void printPlayerHand() {
        String msg = "Your hand contains:\n";

        for(int cardIndex = 0; cardIndex < playerHand.size(); cardIndex++) {
            msg += "\t"+ (cardIndex + 1) + " - " + playerHand.get(cardIndex).toString() + "\n";
        }

        msg += "You have " + playerScore + " pair";

        // so it doesn't say "1 pairs"
        if(playerScore != 1) {
            msg += "s";
        }

        System.out.println(msg);
    }

    private static void playerTurn() {
        printPlayerHand();

        int answer;
        while(true) {
            System.out.print("Which card would you like to ask the bot for? ");
            answer = console.nextInt();

            if(answer <= playerHand.size() && answer > 0) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }

        Card selected = playerHand.get(answer - 1);
        boolean successful = turn(playerHand, botHand, selected.getValue());

        if(successful) {
            System.out.println("The bot gave you a " + Card.ranks[selected.getValue() - 1] + "!");
        } else  {
            System.out.println("The bot tells you to go fish...");
        }
        playerScore += removePairs(playerHand);
    }
}