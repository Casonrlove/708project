import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UnoGame {
    private List<String> deck;
    private List<String> playerHand;
    private List<String> computerHand;

    public UnoGame() {
        initializeDeck();
        playerHand = new ArrayList<>();
        computerHand = new ArrayList<>();
        dealCards();
    }

    private void initializeDeck() {
        deck = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= 9; j++) {
                deck.add("" + j); // Adding number cards
                deck.add("" + j);
            }
            // Adding action cards
            deck.add("Skip");
            deck.add("Reverse");
            deck.add("Draw Two");
        }
        Collections.shuffle(deck);
    }

    private void dealCards() {
        for (int i = 0; i < 7; i++) {
            playerHand.add(deck.remove(0));
            computerHand.add(deck.remove(0));
        }
    }

    private void displayHands() {
        System.out.println("Your Hand: " + playerHand);
        System.out.println("Computer's Hand: " + computerHand);
    }

    private boolean isValidMove(String card, String topCard) {
        return card.equals(topCard) || card.startsWith("Draw Two") || card.startsWith("Skip") || card.startsWith("Reverse");
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        String topCard = deck.remove(0);
        System.out.println("Top card: " + topCard);
        displayHands();

        boolean playerTurn = true;
        while (!playerHand.isEmpty() && !computerHand.isEmpty()) {
            List<String> currentPlayerHand = playerTurn ? playerHand : computerHand;
            String currentPlayerName = playerTurn ? "You" : "Computer";

            if (playerTurn) {
                System.out.println("Your turn. Enter the card you want to play or type 'draw' to draw a card: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("draw")) {
                    String drawnCard = deck.remove(0);
                    currentPlayerHand.add(drawnCard);
                    System.out.println("You drew: " + drawnCard);
                } else {
                    if (!isValidMove(input, topCard)) {
                        System.out.println("Invalid move! Try again.");
                        continue;
                    }
                    currentPlayerHand.remove(input);
                    topCard = input;
                }
            } else {
                // Computer's turn (simple AI)
                boolean cardPlayed = false;
                for (String card : currentPlayerHand) {
                    if (isValidMove(card, topCard)) {
                        currentPlayerHand.remove(card);
                        topCard = card;
                        System.out.println("Computer played: " + card);
                        cardPlayed = true;
                        break;
                    }
                }
                if (!cardPlayed) {
                    String drawnCard = deck.remove(0);
                    currentPlayerHand.add(drawnCard);
                    System.out.println("Computer drew a card.");
                }
            }

            System.out.println(currentPlayerName + "'s Hand: " + currentPlayerHand);
            playerTurn = !playerTurn;
        }

        if (playerHand.isEmpty()) {
            System.out.println("Congratulations! You won!");
        } else {
            System.out.println("Computer won. Better luck next time!");
        }
        scanner.close();
    }

    public static void main(String[] args) {
        UnoGame uno = new UnoGame();
        uno.play();
    }
}