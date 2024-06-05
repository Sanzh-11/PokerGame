import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokerGame {
    final Scanner scanner = new Scanner(System.in);
    public void startGame() {
        boolean continuePlaying = true;
        while (continuePlaying) {
            playRound();
            System.out.print("Желаете ли Вы продолжить игру? (да/нет): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (!input.equals("да")) {
                continuePlaying = false;
            }
        }
        scanner.close();
    }

    private void playRound() {
        final Deck deck = new Deck();
        List<Card> player1Cards = new ArrayList<>();
        List<Card> player2Cards = new ArrayList<>();
        List<Card> tableCards = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            player1Cards.add(deck.drawCard());
            player2Cards.add(deck.drawCard());
        }
        for (int i = 0; i < 5; i++) {
            tableCards.add(deck.drawCard());
        }

        System.out.println("Карты игрока 1: " + player1Cards);
        System.out.println("Карты игрока 2: " + player2Cards);
        System.out.println("Общие карты: " + tableCards);

        player1Cards.addAll(tableCards);
        player2Cards.addAll(tableCards);

        HandEvaluator player1 = new HandEvaluator();
        HandEvaluator player2 = new HandEvaluator();

        int rateOfPlayer1 = player1.evaluateHand(player1Cards);
        int rateOfPlayer2 = player2.evaluateHand(player2Cards);

        if (rateOfPlayer1 > rateOfPlayer2) {
            System.out.println("Первый игрок побеждает" + " " + rateOfPlayer1 + " " + rateOfPlayer2);
        }
        else if (rateOfPlayer1 < rateOfPlayer2) {
            System.out.println("Второй игрок побеждает" + " " + rateOfPlayer1 + " " + rateOfPlayer2);
        }
        else {
            System.out.println("Draw" + " " + rateOfPlayer1 + " " + rateOfPlayer2);
        }
    }
}
