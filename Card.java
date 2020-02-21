public class Card {

    public enum Suit {
        HEART,
        DIAMOND,
        SPADE,
        CLUB
    }

    public static String[] suits = new String[] {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

    public Suit suit;

    public int value;

    public Card(Suit suit, int value)
    {
        if(value >= 1 && value <= 13)
        {
            this.suit = suit;
            this.value = value;
        }
    }
    public String toString()
    {
        return (suits[this.value-1]+" of "+this.suit.toString().toLowerCase());
    }
}