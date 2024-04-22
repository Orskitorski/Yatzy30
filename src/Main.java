import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner tgb = new Scanner(System.in);
        System.out.println("Hur många spelare ska vara med?");
        int playerCount = tgb.nextInt(); //Sätter spelarmängden till indatan från tangentbordet.
        String[] playerNames = new String[playerCount]; //Skapar en array med spelarmängden som storlek
        for (int i = 0; i < playerCount; i++) { //använder indata från tangentbordet för att fylla arrayen med spelarnamn
            System.out.println("Vad ska spelare " + (i+1) + " heta?");
            playerNames[i] = tgb.next();
        }
        String winningPlayer = StartGame(playerCount, playerNames); //kallar på metoden StartGame som returnerar namnet på den vinnande spelaren
        System.out.println("Spelare " + winningPlayer + " är vinnaren!");
    }

    public static String StartGame(int playerCount, String[] playerNames) {
        Scanner tgb = new Scanner(System.in);
        int[] playerPoints = new int[playerCount]; //Skapar en array som ska hålla koll på alla spelares liv
        Arrays.fill(playerPoints, 30); //Fyller spelarlivs-arrayen med 30 på alla platser
        int numberOfPlayersAlive = playerCount; //Skapar en variabel som används för att hålla koll på hur många spelare som är vid liv
        while (numberOfPlayersAlive > 1) { //Loopar denna kod så länge det finns mer än en spelare vid liv
            for (int i = 0; i < playerCount; i++) {
                if (playerPoints[i] > 0) { //Ger spelaren en tur att spela så länge de har över 0 liv
                    System.out.println(playerNames[i] + ":s tur. Deras poäng är: " + playerPoints[i]);
                    int Score = Dice(); //kallar på metoden dice som returnerar spelarens poäng för den rundan
                    System.out.println(" ");
                    System.out.println(Score);
                    if (Score < 30) { //Om spelarens poäng för den rundan är mindre än 30 så förlorar de lika mycket liv som de är under 30 poäng
                        playerPoints[i] -= 30-Score;
                        System.out.println(playerNames[i] + ":s poäng är: " + playerPoints[i]);
                        if (playerPoints[i] <= 0) { //Om spelarens liv går till 0 under deras tur så subtraheras spelarmängden med 1 och så går programmet tillbaka till början av for-loopen
                            numberOfPlayersAlive -= 1;
                            break;
                        }
                    }
                    if (Score == 30) { //Om spelarens poäng är 30 händer inget och det går till nästa spelares tur
                        System.out.println("Du förlorar 0 poäng");
                    }
                    if (Score > 30) { //Om spelarens poäng är över 30 så får spelaren en prompt att attackera en annan genom att skriva in deras namn
                        System.out.println("Du förlorar 0 poäng. Vilken spelare vill du attackera? (skriv in namnet på spelaren du vill attackera)");
                        String targetPlayer = tgb.nextLine();
                        int programTargetPlayer = 0;
                        for (int j = 0; j < playerCount; j++) {
                            if ((playerNames[j].toLowerCase()).contains(targetPlayer.toLowerCase())) { //Kollar vilken position i arrayen som den attackerade spelarens namn har
                                programTargetPlayer = j;
                            }
                        }
                        int targetDice = Score - 30; //Beräknar vilket tärningsnummer som spelaren ska få för att attackera
                        int damage = Attack(targetDice); //Kallar på metoden Attack med hjälp av tärningsnumret och returnerar skadan som den attackerade spelaren ska ta
                        playerPoints[programTargetPlayer] -= damage; //Subtraherar den attackerade spelarens liv med skadan som vi fick av Attack-metoden
                        System.out.println("Spelare " + targetPlayer + " har tagit " + damage + " skada och ligger nu på " + playerPoints[programTargetPlayer] + " poäng.");
                        if (playerPoints[programTargetPlayer] <= 0) { //Om den attackerade spelarens liv går till 0 under denna process så subtraheras spelarmängden med 1 och programmet går tillbaka till början av for-loopen
                            numberOfPlayersAlive -= 1;
                            break;
                        }
                    }
                }
            }
        }
        String winningPlayer = null;
        for (int i = 0; i < playerCount; i++) { //Efter att spelarnumret har gått ner till endast en person vid liv så kollar programmet vilken av spelarnamnen som har mer än 0 liv kvar och sätter denna spelare till vinnaren
            if (playerPoints[i] > 0) {
                winningPlayer = playerNames[i];
            }
        }
        return winningPlayer; //Returnerar den vinnande spelarens namn
    }

    public static int Dice() { //Metoden används för att slå tärningarna
        Scanner tgb = new Scanner(System.in);
        int[] diceArray = new int[6]; //Skapar en array som håller i alla tärningar
        int[] playerDiceArray = new int[6]; //Skapar en array som håller i spelarens utvalda tärningar

        System.out.println("klicka på enter för att slå tärningarna");
        String temp;
        temp = tgb.nextLine(); //Variabel som dröjer programmet tills spelaren trycker på enter eller gör en annan input
        for (int diceNumber = 6; diceNumber > 0; ) { //Slår tärningarna utifrån hur många tärningar spelaren redan har valt ut
            System.out.println("[nr:   1|2|3|4|5|6 ]");
            System.out.println("[------------------]");
            System.out.print("[kast: ");
            for (int i = 0; i < diceNumber; i++) {
                diceArray[i] = (int) ((Math.random() * 6) + 1);
                System.out.print(diceArray[i]);
                System.out.print(",");
            }
            System.out.print("]");
            System.out.println(" ");
            System.out.println("Hur många tärningar vill du välja ut? (minst 1)");
            int playerDiceNumber = tgb.nextInt(); //Tar spelarens input som mängden tärningar de vill välja ut

            System.out.println("Skriv vilken tärning du vill ha följt av enter");
            for (int i = 0; i < playerDiceNumber; i++) { //Låter spelaren välja så många tärningar som de bestämde sig för att välja förut
                int diceNumberChoice = (tgb.nextInt()-1); //Läser in spelarens tärningsval (tärning 1, 2, 3, 4, 5 eller 6
                for (int j = 0; j < diceNumber; j++) {
                    playerDiceArray[j] = diceArray[diceNumberChoice]; //Sätter in spelarens valda tärningar i spelarens tärningsarray
                }
                diceNumber--;
            }
        }

        System.out.print("[");
        for (int i = 0; i < 6; i++) { //Skriver ut spelarens tärningsarray
            System.out.print(playerDiceArray[i]);
            System.out.print(",");
        }
        System.out.print("]");
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += playerDiceArray[i]; //Räknar ut summan av spelarens tärningar
        }
        return sum; //Returnerar summan som används i StartGame-metoden
    }

    public static int Attack(int targetDice) {
        Scanner tgb = new Scanner(System.in);
        int[] diceArray = new int[6]; //Skapar en array som håller i alla tärningar
        int sum = 0;
        System.out.println("Du måste få tärningar med numret: " + targetDice + ". Tryck enter för att slå tärningarna.");
        String temp;
        temp = tgb.nextLine(); //Variabel som dröjer programmet tills spelaren trycker på enter eller gör en annan input
        boolean targetDiceFound = true;

        while (targetDiceFound) { //kör bara koden om targetDiceFound är = true
            for (int diceNumber = 6; diceNumber > 0; ) {
                System.out.println("[nr:   1|2|3|4|5|6 ]");
                System.out.println("[------------------]");
                System.out.print("[kast: ");
                for (int i = 0; i < diceNumber; i++) {
                    diceArray[i] = (int) ((Math.random() * 6) + 1);
                    System.out.print(diceArray[i]);
                    System.out.print(",");
                }
                System.out.print("]");
                System.out.println(" ");
                System.out.println(" ");

                int foundTargetDice = 0; //Skapar variabel som håller koll på om programmet hittar rätt tärningar

                for (int i = 0; i < diceNumber; i++) { //For-loop som kollar om programmet hittar rätt tärning i arrayen
                    if (diceArray[i] == targetDice) {
                        foundTargetDice++;
                        break;
                    }
                }

                if (foundTargetDice == 0) { //If-sats som avbryter while-loopen om programmet inte hittar rätt tärning i arrayen
                    targetDiceFound = false;
                    break;
                }

                for (int i = 0; i < diceNumber; i++) { //For-loop som summerar alla de rätta tärningarna som hittats
                    if (diceArray[i] == targetDice) {
                        sum += targetDice;
                        diceNumber--;
                    }
                }
            }
        }
        return sum; //Returnerar värdet som ska användas som skada som den attackerade spelaren tar
    }
}