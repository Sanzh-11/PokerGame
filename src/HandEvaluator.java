import enums.Rank;
import enums.Suit;

import java.util.*;
import java.util.stream.IntStream;

public class HandEvaluator {
    private final Map<Rank, Integer> ranks;
    private final Map<Suit, List<Card>> suits;
    private final List<Card> sortedCards;

    public HandEvaluator() {
        this.ranks = new HashMap<>();
        this.suits = new HashMap<>();
        this.sortedCards = new ArrayList<>();
    }

    public int evaluateHand(List<Card> cards) {
        ranks.clear();
        suits.clear();
        sortedCards.clear();

        for (Card card : cards) {
            ranks.put(card.getRank(), ranks.getOrDefault(card.getRank(), 0) + 1);
            suits.computeIfAbsent(card.getSuit(), k -> new ArrayList<>()).add(card);
            sortedCards.add(card);
        }
        sortedCards.sort((a, b) -> b.getRank().getValue() - a.getRank().getValue());
        // System.out.println(sortedCards);

        int handStrength = 0;

        if (isFlush() && isStraight() && sortedCards.get(0).getRank() == Rank.ACE && sortedCards.get(4).getRank() == Rank.TEN) {
            handStrength = 900;
        } else if (isFlush() && isStraight()) {
            handStrength = 800;
        } else if (checkSameKind(4)) {
            handStrength = 700 + highestRank(4);
        } else if (checkFullHouse()) {
            handStrength = 600 + highestRank(3);
        } else if (isFlush()) {
            handStrength = 500 + highestCard();
        } else if (isStraight()) {
            handStrength = 400 + highestCard();
        } else if (checkSameKind(3)) {
            handStrength = 300 + highestRank(3);
        } else if (checkTwoPair()) {
            handStrength = 200 + highestRank(2);
        } else if (checkSameKind(2)) {
            handStrength = 100 + highestRank(2);
        } else {
            handStrength = highestCard();
        }

        return handStrength;
    }

    private int highestCard() {
        return sortedCards.getFirst().getRank().getValue();
    }
    private int highestRank(int highestRank) {
        return ranks.entrySet().stream()
                .filter(entry -> entry.getValue() == highestRank)
                .mapToInt(entry -> entry.getKey().getValue())
                .max()
                .orElse(0);
    }

    private boolean isFlush() {
        return suits.values().stream().anyMatch(list -> list.size() >= 5);
    }

    private boolean isStraight() {
        List<Integer> rankValues = sortedCards.stream()
                .map(card -> card.getRank().getValue())
                .distinct()
                .sorted()
                .toList();

        boolean standardStraight = false;
        for(int i = 0 ; i < rankValues.size()-4 ; i++) {
            if(rankValues.get(i+1) == rankValues.get(i)+1
                    && rankValues.get(i+2) == rankValues.get(i)+2
                    && rankValues.get(i+3) == rankValues.get(i)+3
                    && rankValues.get(i+4) == rankValues.get(i)+4) {
                standardStraight = true;
            }
        }

        boolean aceLowCase = new HashSet<>(rankValues).containsAll(Arrays.asList(14, 2, 3, 4, 5));

        return standardStraight || aceLowCase;
    }

    private boolean checkSameKind(int count) {
        return ranks.values().stream().anyMatch(c -> c == count);
    }

    private boolean checkFullHouse() {
        return ranks.containsValue(3) && ranks.containsValue(2);
    }

    private boolean checkTwoPair() {
        return ranks.values().stream().filter(c -> c == 2).count() == 2;
    }
}
