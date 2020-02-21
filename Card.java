public class Card {

    public enum Suit {
        HEART,
        DIAMOND,
        SPADE,
        CLUB
    }

    public static String[] ranks = new String[] {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

    private Suit suit;

    private int value;

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public Card(Suit suit, int value) {
        if(value >= 1 && value <= 13) {
            this.suit = suit;
            this.value = value;
        }
    }
    public String toString() {
        String raw = this.suit.toString();
        String firstChar = raw.substring(0, 1);
        String theRest = raw.substring(1, raw.length() - 1).toLowerCase();
        return (ranks[this.value - 1] + " of " + firstChar + theRest + "s");
    }
}