import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner tgb = new Scanner(System.in);
        System.out.println("Hur många spelare ska vara med?");
        int playerCount = tgb.nextInt();
        String[] playerNames = new String[playerCount];
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Vad ska spelare " + (i+1) + " heta?");
            playerNames[i] = tgb.next();
        }
        String winningPlayer = StartGame(playerCount, playerNames);
        System.out.println("Spelare " + winningPlayer + " är vinnaren!");
    }

    public static String StartGame(int playerCount, String[] playerNames) {
        Scanner tgb = new Scanner(System.in);
        int[] playerPoints = new int[playerCount];
        int numberOfPlayersAlive = playerCount;
        Arrays.fill(playerPoints, 30);
        while (numberOfPlayersAlive > 1) {
            for (int i = 0; i < playerCount; i++) {
                if (playerPoints[i] > 0) {
                    System.out.println(playerNames[i] + ":s tur. Deras poäng är: " + playerPoints[i]);
                    int Score = Dice();
                    System.out.println(Score);
                    if (Score < 30) {
                        playerPoints[i] -= 30-Score;
                        System.out.println(playerNames[i] + ":s poäng är: " + playerPoints[i]);
                        if (playerPoints[i] <= 0) {
                            numberOfPlayersAlive -= 1;
                            break;
                        }
                    }
                    if (Score == 30) {
                        System.out.println("Du förlorar 0 poäng");
                    }
                    if (Score > 30) {
                        System.out.println("Du förlorar 0 poäng. Vilken spelare vill du attackera? (skriv in namnet på spelaren du vill attackera)");
                        String targetPlayer = tgb.nextLine();
                        int programTargetPlayer = 0;
                        for (int j = 0; j < playerCount; j++) {
                            if ((playerNames[j].toLowerCase()).contains(targetPlayer.toLowerCase())) {
                                programTargetPlayer = j;
                            }
                        }
                        int targetDice = Score - 30;
                        int damage = Attack(targetDice);
                        playerPoints[programTargetPlayer] -= damage;
                        System.out.println("Spelare " + targetPlayer + " har tagit " + damage + " skada och ligger nu på " + playerPoints[programTargetPlayer] + " poäng.");
                        if (playerPoints[programTargetPlayer] <= 0) {
                            numberOfPlayersAlive -= 1;
                            break;
                        }
                    }
                }
            }
        }
        String winningPlayer = null;
        for (int i = 0; i < playerCount; i++) {
            if (playerPoints[i] > 0) {
                winningPlayer = playerNames[i];
            }
        }
        return winningPlayer;
    }

    public static int Dice() {
        Scanner tgb = new Scanner(System.in);
        int[] diceArray = new int[6];
        int[] playerDiceArray = new int[6];

        System.out.println("klicka på enter för att slå tärningarna");
        String temp;
        temp = tgb.nextLine();
        for (int diceNumber = 6; diceNumber > 0; ) {
            for (int i = 0; i < diceNumber; i++) {
                diceArray[i] = (int) ((Math.random() * 6) + 1);
                System.out.println(diceArray[i]);
            }
            System.out.println("Hur många tärningar vill du välja ut? (minst 1)");
            int playerDiceNumber = tgb.nextInt();
            Arrays.sort(diceArray);

            int y = diceNumber-1;
            for (int i = diceNumber-1; i >= 0; i--) {
                playerDiceArray[i] = diceArray[y];
                y--;
            }
            diceNumber -= playerDiceNumber;
        }
        for (int i = 0; i < 6; i++) {
            System.out.println(playerDiceArray[i]);
        }
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += playerDiceArray[i];
        }
        return sum;
    }

    public static int Attack(int targetDice) {
        Scanner tgb = new Scanner(System.in);
        int[] diceArray = new int[6];
        int[] playerDiceArray = new int[6];
        int playerDiceNumber = 0;
        System.out.println("Du måste få tärningar med numret: " + targetDice + ". Tryck enter för att slå tärningarna.");
        String temp;
        temp = tgb.nextLine();
        for (int diceNumber = 6; diceNumber > 0; ) {
            for (int i = 0; i < diceNumber; i++) {
                diceArray[i] = (int) ((Math.random() * 6) + 1);
                System.out.println(diceArray[i]);
            }
            for (int i = 0; i < diceNumber; i++) {
                if (diceArray[i] == targetDice) {
                    playerDiceArray[i] = targetDice;
                    playerDiceNumber += 1;
                }
            }

            diceNumber -= playerDiceNumber;
        }
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += playerDiceArray[i];
        }
        return sum;
    }
}