import enums.Rank;
import enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.removeFirst();
        }
        return null;
    }
}
