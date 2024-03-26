    import java.util.ArrayList;
    import java.util.Scanner;

    import javax.sound.sampled.SourceDataLine;

    /*
    *  Team: 41Honeybuns
    *  Team Members: Daniel Beltran, Jay Park, Cason Love, Emmanuel Tobias
    */
    public class HoneyBuns {
    
        public static void main(String[] args) {
            Scanner rep = new Scanner(System.in);
            
            Boolean replay = true;
            while (replay == true) {
                playgame();
                System.out.println("\nWould you like to play again? Type in Yes or No, please.");
                String replayText = rep.next();
                replayText = replayText.toUpperCase();

                while (!replayText.equals("YES") ) {
                    if (replayText.equals("NO")) {
                        System.exit(0);
                    }

                    System.out.println("Please enter a valid message. Type in Yes or No.");
                    replayText = rep.next();
                    replayText = replayText.toUpperCase();
                }

            } 
        }
    
        static void playgame(){

            /* Initial Array Lists  */
            ArrayList<String> drawPile = new ArrayList<String>();    // Assumption: The first card in the Draw Pile is the Top card
            ArrayList<String> discardPile = new ArrayList<String>(); // Assumption: The first card in the discard Pile is the Top card
            ArrayList<String> currentPlayer; // This will point to the deck of the current player


            // 4 Players (Each Deck is an Array List )
            ArrayList<String> userPlayer = new ArrayList<String>();
            ArrayList<String> computerPlayer2 = new ArrayList<String>();
            ArrayList<String> computerPlayer3 = new ArrayList<String>();
            ArrayList<String> computerPlayer4 = new ArrayList<String>();


            Scanner scnr = new Scanner (System.in); //create scanner


            System.out.print("Enter your username: ");


            String playerName = scnr.next();


            System.out.println("\nHi " + playerName + "! The game will now start. The rules will be shown below, read them carefully.\n");


            System.out.println("1. Each player receives 7 cards at the beginning of the game.\n");
            System.out.println("2. Player 1 goes first. They can place a card that matches the suit or number of the top card on the discard pile. Jokers can be played regardless of the top card.\n   If you cannot play a card, then draw one by typing in '0', and it will move to the next player.\n");
            System.out.println("3. If a Joker is placed, then the current player can choose the top card to be whatever they wish, and the next player will be forced to draw 3 cards.\n");
            System.out.println("4. If a Jack is placed, then the next player will be forced to draw 1 card.\n");
            System.out.println("5. If a King is placed, the next player's turn will be skipped.\n");
            System.out.println("6. If a Queen is placed, Then a trivia question is given, and if answered correctly you may place another card. This 'another card' you place if you answer the question correctly does not trigger any special effects   it might originally have. For example, if you place down a queen, you answer the trivia correctly, and place down a Joker, then this Joker will not trigger its effects.\n");
            System.out.println("7. If an Ace is placed, then you can force any player to draw a card.\n");
            System.out.println("8. Whoever gets rid of all the cards in their hand first will win. Don't pick a card that's out of the range of your hand, although there are surprises in store if you do >:)\n");


            // 1. Create and Shuffle Deck
            createShuffledDrawPile(drawPile);
            // printArrayList(drawPile);


            // 2. Distribute Cards
            distributeCards(drawPile, discardPile, userPlayer, computerPlayer2, computerPlayer3, computerPlayer4);


            // 3. Create Initial Discard Pile
            createInitialDiscardPile(drawPile,discardPile);


            while (drawPile.get(0) == "J1" || drawPile.get(0) == "J2") {
                createInitialDiscardPile(drawPile, discardPile);
            }
            // end of creating initial discard pile


            currentPlayer = userPlayer; // User Player Goes First


            int player = 1; // initializes player variable
            int drawSomeCards = 0; //initializes the drawSomeCards variable shows how many cards the currentplayer will draw.
            int turnCount = 1;

            // Start The Game
            while(rule5(currentPlayer)==false){ // While game is not Won
        
                if (player == 5) { //if the fourth player played last turn, it goes back to the first player.
                    player = 1;
                }


                switch (player) { //switch cases to figure out who is currently playing.
                    case 1:
                        System.out.println("Your turn, " + playerName + "!\n");
                        currentPlayer = userPlayer;
                    break;
            
                    case 2:
                        System.out.println("Computer Player 2 has played.");
                        currentPlayer = computerPlayer2;
                    break;
    
                    case 3:
                        System.out.println("Computer Player 3 has played.");
                        currentPlayer = computerPlayer3;
                    break;
    
                    case 4:
                        System.out.println("Computer Player 4 has played.\n");
                        currentPlayer = computerPlayer4;
                    break;
                }


                for (int index = 0; index < drawSomeCards; index++) {
                    currentPlayer.add(drawCard(drawPile,discardPile));
                }
                drawSomeCards = 0;
            
                // 4. Play Cards
                /******************************** Game Play Below **********************************/
                /***********************************************************************************/
                String chosenCard = "";
                boolean drawCard = false; //boolean to set true if drawing a card so that we can see if we can skip rest of game logic for the iteration
                //------------------------------USER GAMEPLAY------------------------------//
                if(currentPlayer == userPlayer){


                    //----------PRINT USER'S HAND, TOP OF DISCARD PILE----------//
                    System.out.println("Top Of Discard Pile: " + discardPile.get(0) + "\n");


                    System.out.println("Your Cards: (Turn " + turnCount + ")");
                    turnCount++;


                    for (int i = 0; i < userPlayer.size(); i++) {
                        if (i <= 8 ) {
                            System.out.print((i+1) + "  ");
                        }
                        else {
                            System.out.print((i+1) + " ");
                        }
                    }
                    System.out.println();

                    printArrayList(userPlayer);


                    //----------PROMPT USER----------//
                    if (userPlayer.size() == 1) {
                        System.out.println("You only have one card left in your hand!!!! Play it!");
                    }
                    else {
                        System.out.print("\nPick a card (Enter a # ranging from 1 to " + userPlayer.size() + " or 0 to draw): ");
                    }


                    int chosenCardNum = scnr.nextInt();
                    System.out.println();
                
                    while (chosenCardNum > userPlayer.size() || chosenCardNum < 0) {


                        if (chosenCardNum > userPlayer.size()) {
                            System.out.println("I don't know what to tell ya, you picked card #" + chosenCardNum + " when you only have " + userPlayer.size() + " cards in your hand. You may want to refresh up on your counting skills. Pick again please.");
                            chosenCardNum = scnr.nextInt();
                        }

                        if (chosenCardNum < 0) {
                            System.out.println("Holy Guacamole, you picked a negative card number! I hope you do not go into accounting in the future. Pick again please.");
                            chosenCardNum = scnr.nextInt();
                        }


                    }


                    //----------CHECK TO SEE IF DRAW CARD SELECTED----------//
                    if (chosenCardNum == 0)
                    {
                        currentPlayer.add(drawCard(drawPile,discardPile));
                        drawCard = true;
                        System.out.print("\nYou have drawn a card!\n");
                    }
                    //----------USER DOESN'T SELECT TO DRAW A CARD----------//
                    else
                    {
                        chosenCard = userPlayer.get(chosenCardNum-1);


                    
                        //----------CHECK IF CARD CAN BE DISCARDED----------//
                        boolean discard = false;
                        if( discardPile.get(0).charAt(0) == currentPlayer.get(chosenCardNum-1).charAt(0) || discardPile.get(0).charAt(1) == currentPlayer.get(chosenCardNum-1).charAt(1)
                            || currentPlayer.get(chosenCardNum-1).charAt(1) == 'K' || currentPlayer.get(chosenCardNum-1).charAt(0) == 'J')
                        {
                            discardCard(chosenCard, discardPile);
                            userPlayer.remove(chosenCard);
                            discard = true;
                        }
                        //----------DRAW A CARD----------//
                        if(!discard)
                        {
                            currentPlayer.add(drawCard(drawPile,discardPile));
                        }


                        //----------RULE1----------//
                        //rule 1. If a Joker is placed, then the current player can choose the top card to be whatever they wish, and the next player will be forced to draw 3 cards.

                        if ((chosenCard.charAt(0) == 'J' && chosenCard.charAt(1) == '1') || (chosenCard.charAt(0) == 'J' && chosenCard.charAt(1) == '2')){
                            drawSomeCards = 3;
                            System.out.println("You can choose the card to be whatever you wish except a Joker - examples: H7, CK, S2, D9. Type it below.");
                            String rule1wildcard = scnr.next();

                            userPlayer.remove(chosenCard);
                            discardPile.set(0, rule1wildcard);
                            System.out.println();
                        }
                
                        //----------RULE2----------//
                        //rule 2. If you put down a Jack, the next player will draw 1 card.
                        if(discardPile.get(0).charAt(0) == chosenCard.charAt(0) || discardPile.get(0).charAt(1) == chosenCard.charAt(1)) {
                            if (chosenCard.charAt(1) == 'J') {
                                drawSomeCards = 1;


                                discardCard(chosenCard, discardPile);
                                userPlayer.remove(chosenCard);
                            }
                        
                        // // Play Card by removing card from the player deck , and inserting to the top of dicard pile
                        // rule7(chosenCard, currentPlayer,discardPile); //screwing up the discardPile
                        }
                    
                        //----------RULE3----------//
                        if(discard && (chosenCard.charAt(1) == 'Q')) //)currentPlayer.get(chosenCardNum-1).charAt(1)
                        {
                            if (rule3())
                            {
                                //----------PRINT USER'S HAND, TOP OF DISCARD PILE----------//
                                System.out.println("You answered correctly! You get to discard an extra card!");
                                System.out.println("Your Cards: ");
                                printArrayList(userPlayer);
                                //----------PROMPT USER----------//
                                System.out.print("Pick a card (Enter a # ranging from 1 to " + userPlayer.size() + "): \n");
                                int rule3num = scnr.nextInt();
                                chosenCard = userPlayer.get(rule3num-1);
                                discardCard(chosenCard, discardPile);
                                userPlayer.remove(chosenCard);
                            }
                        }

                        //----------RULE4----------//
                        //rule 4. If a King is placed, the next player's turn is skipped.
                        if(discardPile.get(0).charAt(0) == chosenCard.charAt(0) || discardPile.get(0).charAt(1) == chosenCard.charAt(1)) {

                            if (chosenCard.charAt(1) == 'K') {
                                player++;
                                discardCard(chosenCard, discardPile);
                                userPlayer.remove(chosenCard);
                            }
                        }
                        //Rule 6 - If an ace is played, choose someone to draw a card//
                        if (discard && chosenCard.charAt(1)=='A') {
                            rule6(userPlayer, computerPlayer2, computerPlayer3, computerPlayer4, drawPile, discardPile, currentPlayer);
                            
                        }

                    }
                


                //------------------------------NPC GAMEPLAY------------------------------//
                } else {


                    boolean discard = false; //set true if a card was discarded
                    for (int i = 0; i < currentPlayer.size(); i++)
                    {
                        //----------CHECK IF CARD CAN BE DISCARDED----------//
                        if( discardPile.get(0).charAt(0) == currentPlayer.get(i).charAt(0) || discardPile.get(0).charAt(1) == currentPlayer.get(i).charAt(1)
                            || currentPlayer.get(i).charAt(1) == 'K')
                        {
                            chosenCard = currentPlayer.get(i);
                            discardCard(chosenCard, discardPile);
                            currentPlayer.remove(currentPlayer.get(i));
                            discard = true;
                            break;
                        }
                    }
                    //----------DRAW A CARD----------//
                    if(!discard)
                    {
                        currentPlayer.add(drawCard(drawPile,discardPile));
                    }
                
                }
                /******************************** Game Play Above **********************************/
                /***********************************************************************************/
        
            player++;


            chosenCard = "NA";


            }
        
            if (player == 2) {
                System.out.println("Congratulations, you won " + playerName + "! Thanks for playing!");
            }
            else {
            System.out.println("The game has been won by computer player " + (player - 1) + ", thanks for playing! Better luck next time... o7");
            }
        } // End of PlayGame()


        //----------IMPLEMENT RULE3&4----------//
        // rules 3 and 4 are implemented here
        static boolean rule3()
        {
            System.out.println("\n\n**********TRIVIA TIME**********");
            String output_message = "Which of the following options is a line from Harry Potter?\n\n" +
                                    "1.Happiness can be found, even in the darkest of times, if one only remembers to turn on the light.\n"+
                                    "2.It takes a great deal of bravery to stand up to our enemies, but just as much to stand up to our friends.\n"+
                                    "3.Do not pity the dead, Harry. Pity the living, and above all, those who live without love.\n"+
                                    "4.Wit beyond measure is man's greatest treasure.\n";
            System.out.println(output_message);
            Scanner scnr2 = new Scanner(System.in);
            int number_selected = scnr2.nextInt();
            if (number_selected == 2)
            {
                return true;
            }
            else
            {
                return false;
            }
        }


    //----------IMPLEMENT RULE5&6----------// Emmanuel Tobias
    // rules 5 and 6 are implemented here
    // rule 5 - whoever runs out of cards first wins
    static boolean rule5(ArrayList<String> UserHand){
        if (UserHand.size() == 0) {
            return true;
        } else return false;
    } // <-- To Test Program
    //rule 6 - person of your choice draws card on a played ace
    // asks for int input between 1 and 4
    // 1 is human, 2,3,4 are computers
    // adds card from top of deck to desired player's hand
    static void rule6(ArrayList<String> userPlayer, ArrayList<String> computerPlayer2, ArrayList<String> computerPlayer3, ArrayList<String> computerPlayer4, ArrayList<String> drawPile, ArrayList<String> discardPile, ArrayList<String> currentPlayer) {
        if (currentPlayer != userPlayer) {
            userPlayer.add(drawCard(drawPile, discardPile));
            return;
        }
        Scanner scnr = new Scanner(System.in);
        System.out.print("You played an ace! Enter a # 1-4 to choose who you want to draw: ");
        switch (scnr.nextInt()) {
            case 1:
                System.out.println("Player 1 draws a card. ");
                userPlayer.add(drawCard(drawPile, discardPile));
                // System.out.println("Player 1 hand");
                // for (int i = 0; i < userPlayer.size(); i++) {
                //     System.out.print(userPlayer.get(i) + " ");
                // }
                break;
            case 2:
                System.out.println("Player 2 draws a card. ");
                computerPlayer2.add(drawCard(drawPile, discardPile));
                // System.out.println("Player 2 hand");
                // for (int i = 0; i < computerPlayer2.size(); i++) {
                //     System.out.print(computerPlayer2.get(i) + " ");
                // }
                break;
            case 3:
                System.out.println("Player 3 draws a card. ");
                computerPlayer3.add(drawCard(drawPile, discardPile));
                // System.out.println("Player 3 hand");
                // for (int i = 0; i < computerPlayer3.size(); i++) {
                //     System.out.print(computerPlayer3.get(i) + " ");
                // }
                break;
            case 4:
                System.out.println("Player 4 draws a card. ");
                computerPlayer4.add(drawCard(drawPile, discardPile));
                // System.out.println("Player 4 hand");
                // for (int i = 0; i < computerPlayer4.size(); i++) {
                //     System.out.print(computerPlayer4.get(i) + " ");
                // }
                break;
            default:
                break;
        }
    }
        //----------IMPLEMENT RULE7&8----------//
        // rules 7 and 8 are implemented here
    
        /*
        *  Rule 7 - Daniel B.
        * A person can play a card with the same number or same suit that matches the top of the discard pile.
        */


        static void rule7 (String cardPlayed,ArrayList<String> playerDeck, ArrayList<String> discardDeck){
        
            /* Playing Card  */
            playerDeck.remove(cardPlayed);
            discardDeck.add( 0,cardPlayed);




        }


        /**
         *  Rule 8 - Daniel B.
         *  When cards run out, all the cards in the discard pile (except for the top card)
         *  are shuffled and added to the end of draw the deck
         * @param drawDeck
         * @param discardDeck
         */
        static void rule8 (ArrayList<String> drawDeck, ArrayList<String> discardDeck){
            /*
            * This rule works in 3 steps
            * 1. Generate a random number between 0 - sizeOfDrawDeck
            * 2. Remove the card at that index from the discardDeck
            * 3. Add that card to end of drawDeck
            */


            while(discardDeck.size() > 1){
                int randomInt = (int)(Math.random() * (discardDeck.size()-1) ) + 1 ; // Make sure we don't remove first card of discard pile


            
                String cardRemoved = discardDeck.remove(randomInt);
                drawDeck.add(cardRemoved);


            }




        } // End of rule8()


        /******************************** Game Functions Below **********************************/


        //distributes 7 cards to each player, taking out the cards from the main pile/draw pile.


        static void distributeCards(ArrayList<String> drawingPile, ArrayList<String> discardPile,  ArrayList<String> player1Deck, ArrayList<String> player2Deck, ArrayList<String> player3Deck, ArrayList<String> player4Deck) {
            for (int i = 0; i < 7; i++) { //distributes 7 cards to player 1
                player1Deck.add(drawCard(drawingPile,discardPile));
            }


            for (int i = 0; i < 7; i++) { //distributes 7 cards to player 2
                player2Deck.add(drawCard(drawingPile,discardPile));
            }


            for (int i = 0; i < 7; i++) { //distributes 7 cards to player 3
                player3Deck.add(drawCard(drawingPile,discardPile));
            }


            for (int i = 0; i < 7; i++) { //distributes 7 cards to player 4
                player4Deck.add(drawCard(drawingPile,discardPile));
            }
        }




        /*
        * createShuffledDrawPile(ArrayList<String> arrayList)
        *
        * Similar to previous programming assignments, This method works in 3 steps
        *  1. Create an Array Deck
        *  2. Shuffle Deck
        *  3. Convert Shuffled Array Deck Into  Array List Deck by inserting card by card
        */
        static void createShuffledDrawPile(ArrayList<String> arrayList){


            /***************************  1. Create Array Deck ***************************************/


            String  cardsPerSuite = "A23456789TJQK"; // Represent Ace as A, numeric cards 2-9 themselves. Use T for ten, J for Jack, Q for Queen, and K for King
            String  suits =  "HSCD";                 // Use H for Hearts, S for Spades, C for Clubs, D for Diamonds
        
            String [] deck = new String[54];         // 54 Card Deck


            // Creating 54 Card Deck
            int deckIndex = 0;
            for(int suitIndex = 0; suitIndex < suits.length(); suitIndex++ ){


                for(int cardIndex = 0; cardIndex < cardsPerSuite.length(); cardIndex++){


                    deck[deckIndex] = String.valueOf(suits.charAt(suitIndex)) + String.valueOf(cardsPerSuite.charAt(cardIndex));
                    deckIndex++;
                }
            }


            // Add Joker Cards
            deck[52] = "J1";
            deck[53] = "J2";


            /***************************  2. Shuffle Array Deck ***************************************/


            // Shuffling works by looping through each card in the deck, and swapping positions with a random card in the deck


            for(int cardIndex = 0; cardIndex < deck.length; cardIndex++){


                // Generate a random position between 0 and deck length
                int randomInt = (int)(Math.random() * deck.length );


                // Swap Positions
                String tempCard = deck[cardIndex];
                deck[cardIndex] = deck[randomInt];
                deck[randomInt] = tempCard;


            }


            /***************************  3. Insert into Array List ***************************************/
            for(int cardIndex = 0; cardIndex < deck.length; cardIndex++){
                arrayList.add(deck[cardIndex]);
            }


        } // End of createShuffledDrawPile()




        /**
         * Draws a Card, and inserts into Discard Deck
         *
         * @param drawDeck
         * @param discardDeck
         */
        static void createInitialDiscardPile(ArrayList<String> drawDeck, ArrayList<String> discardDeck){


            String card = drawCard(drawDeck,discardDeck);
            discardCard(card,discardDeck);


        } // End of createInitialDiscardPile()


        /**
         *  Gets a card from the top of the deck, and removes it.
         *  If draw pile is empty, will call Rule 8. If both decks are empry, returns "Empty"
         * @param drawDeck
         * @param discardDeck (Required for Rule 8)
         * @return  Card, or "Empty"
         */
        static String drawCard(ArrayList<String> drawDeck, ArrayList<String> discardDeck){


            if(drawDeck.size() == 0){
                rule8(drawDeck,discardDeck);
            }
        
            if(drawDeck.size() == 0){ // If still empty
                return "EMPTY";
            }


            String popCard = drawDeck.get(0);
            drawDeck.remove(0);


            return popCard;


        } // End of drawCard()


        /**
         * Inserts discarted card into top of discard pile
         * @param discardDeck
         */
        static void discardCard(String discardCard, ArrayList<String> discardDeck){
            discardDeck.add(0,discardCard);
        } // End of discardCard()


        /**
         * Updates Current Player to the next player
         * @param currDeck
         * @param player1Deck
         * @param player2Deck
         * @param player3Deck
         * @param player4Deck
         */
        static void nextPlayer(ArrayList<String> currDeck,ArrayList<String> player1Deck,ArrayList<String> player2Deck,ArrayList<String> player3Deck,ArrayList<String> player4Deck){
            if(currDeck == player1Deck){
                currDeck = player2Deck;
            }else if(currDeck == player2Deck){
                currDeck = player3Deck;
            }else if(currDeck == player3Deck){
                currDeck = player4Deck;
            }else if(currDeck == player4Deck){
                currDeck = player1Deck;
            }
        }
    /***************************  Debug Functions  ***************************************/
        static void printArrayList(ArrayList<String> arrayList){
            for(int i = 0; i < arrayList.size();i++){
                System.out.print(arrayList.get(i) + " ");
            }
            System.out.println();
        }


    }
