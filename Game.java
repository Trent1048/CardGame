/*
Trent Bultsma, John-Paul Powers
CS 145
February 21 2020
Card Game

Our card game is go fish. The user plays against a robot, asking it for
a card and being given one if the robot has it. The then asks the user for
a card matching a random card from it's hand. This cycle goes back and fourth 
until there are no cards left in the deck or hands and the game is over.

We used a stack to represent our card deck because as the player or bot 
draws from the deck, they take off the top card (like how a stack works)
and it is removed from the collection (like a stack). Card decks are just
like stacks in real life so we decided to use it for the deck.
*/

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

        boolean playing = true;

        while(playing) {
            playerTurn();
            botTurn();

            // when both players run out of cards, the game ends
            if(playerHand.isEmpty() && botHand.isEmpty()) {
                playing = false;
            }
        }

        // prints who won
        if(playerScore > botScore) {
            System.out.println("You win!");
            console.nextLine();
        } else {
            System.out.println("You lose!");
            console.nextLine();
        }
        System.out.println("You had " + playerScore + " pairs and the bot had " + botScore + " pairs.");
    }

    // introduces the game to the user
    public static void intro() {
        System.out.println(
            "Welcome to Go Fish!\n"
            + "In case you don't know how to play, here is a small tutorial:\n"
            + "The goal of the game is to complete as many pairs as possible.\n"
            + "You do this by either drawing cards, 'Going Fish', or by asking your opponent \n" 
            + "\tfor a card of the same rank as a card you already have.\n"
            + "When all of the cards have been used up, the player with the most pairs wins.\n"
            + "In our rendition of the game, you will play against a bot we've designed.\n"
            + "Good luck, and have fun!\n"
            + "(Always press Enter to continue)\n"
        );
        console.nextLine();
    }

    // sets up the hands
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
            goFish(playerHand);
            goFish(botHand);
        }

        // sets the scores initially to how many pairs 
        // got removed after the hands were created
        playerScore = removePairs(playerHand);
        botScore = removePairs(botHand);

        // tells the user if they got a pair in their initial draw
        if(playerScore != 0) {
            String msg = "You found " + playerScore + " pair";
            if(playerScore != 0) {
                msg += "s";
            }
            msg += " on your initial draw";
            System.out.println(msg);
            console.nextLine();
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

    // adds a card to the inputted hand
    private static void goFish(ArrayList<Card> hand) {
        // causes the game to not break when the deck is empty
        if(!deck.isEmpty()) {
            hand.add(deck.pop());
        }
    }

    // Turns:

    // executes a turn for the bot
    private static void botTurn() {
        int botHandSize = botHand.size();

        // draw seven cards when the bot runs out
        if(botHandSize == 0) {
            for(int i = 0; i < 7; i++) {
                goFish(botHand);
            }
        }

        // if the bot has no cards, even after drawing seven, don't do anything
        // because it means there are no cards in the deck and the game is about to end
        if(botHandSize != 0) {
            // picks a random card from the hand and guesses that card
            int cardIndex = random.nextInt(botHand.size());
            Card card = botHand.get(cardIndex);
            int num = card.getValue();
            boolean gaveAwayCard = turn(botHand, playerHand, num);

            System.out.println("The bot asked for a " + Card.ranks[num - 1]);
            console.nextLine();
            
            // tells the user the outcome of the bot's turn
            if(gaveAwayCard) {
                System.out.println("You gave away a card");
                console.nextLine();
                printPlayerHand();
            } else {
                System.out.println("You told the bot to go fish...");
                console.nextLine();
                goFish(botHand);
            }
            botScore += removePairs(botHand);
        } else {
            System.out.println("The bot did nothing");
            console.nextLine();
        }
    }

    // executes a turn for the player
    private static void playerTurn() {
        printPlayerHand();

        // loop to make sure the user inputs a valid number
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

        // prints the result
        if(successful) {
            System.out.println("The bot gave you a " + Card.ranks[selected.getValue() - 1] + "!");
            console.nextLine();
        } else  {
            System.out.println("The bot tells you to go fish...");
            console.nextLine();
            goFish(playerHand);
        }

        printPlayerHand();

        // calculates if the user got a pair
        // if they did, tell them
        int pairsRemoved = removePairs(playerHand);
        if(pairsRemoved != 0) {
            System.out.println("You found a pair");
            console.nextLine();
            playerScore += pairsRemoved;
            printPlayerHand();
        }
    }

    // prints out the player's hand
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
        console.nextLine();
    }
}