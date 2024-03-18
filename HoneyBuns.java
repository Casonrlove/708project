import java.util.ArrayList;

import javax.sound.sampled.SourceDataLine;

/*
 *  
 *  Team: 41Honeybuns
 *   
 *  Team Members: Daniel Beltran, Jay Park, Cason Love, Emmanuel Tobias
 *  
 */
public class HoneyBuns {
    
    public static void main(String[] args) {
        //----------CALL CARD FUNCTION----------//
        // leave blank til end //
        playgame();
    }

    static void playgame(){
        /*
            Game Play
            1. Deck is shuffled
            2. Each Player gets 7 cards
            3. A card from the draw pile will be placed on the discard pile, this is now the top card on the discard pile.
            4. User player goes first, will place a card that matches the suit or number of the top card on the discard pile, or may place a joker, or a king wild card. If the player does not have a card that can be placed, then they will draw a card and play that card if it is possible.
                    If a joker is placed,
                    The next player draws 3 cards, this does not skip their turn
                    If a Jack is placed, then the next player will draw 1 card, this does not skip their turn.
                    If a King is placed,
                        This card is a wildcard and can be placed for your turn, the next player can also place any card after since the card does not have a suit or number.
                    If a Queen is placed, Then a trivia question is given, and if answered correctly you may place another card.
                    If an Ace is placed, then you can choose any player to draw a card
                        This is the end of your turn, the next player will go next.. 
            5. If at any point there are no more cards in the draw pile, then the discard pile is shuffled, and all except the top card is inserted back to the draw pile.
            6. Next Player
         */

        /* Initial Array Lists  */ efwsesfs
        ArrayList<String> drawPile = new ArrayList<String>();    // Assumption: The first card in the Draw Pile is the Top card
        ArrayList<String> discardPile = new ArrayList<String>(); // Assumption: The first card in the discard Pile is the Top card
        ArrayList<String> currentPlayer; // This will point to the deck of the current player

        // 4 Players (Each Deck is an Array List )
        ArrayList<String> userPlayer = new ArrayList<String>();
        ArrayList<String> computerPlayer2 = new ArrayList<String>();
        ArrayList<String> computerPlayer3 = new ArrayList<String>();
        ArrayList<String> computerPlayer4 = new ArrayList<String>();

        // 1. Create and Shuffle Deck 
        createShuffledDrawPile(drawPile);
        // printArrayList(drawPile);

        // 2. Distribute Cards
        // TODO: Implement Below
        //distributeCards(drawPile, player1Deck, player2Deck, player3Deck, player4Deck);

        // 3. Create Initial Discard Pile
        createInitialDiscardPile(drawPile,discardPile);
        //printArrayList(drawPile);
        //printArrayList(discardPile);

        currentPlayer = userPlayer; // User Player Goes First 

        // Start The Game 
        while(rule5(/* Might need to update the inputs here */)){ // While game is not Won
            
            // 4. Play Cards
            
            String chosenCard = "";
            if(currentPlayer == userPlayer){ 

                // TODO: PROMPT USER TO CHOOSE A CARD SOMEHOW
                // Show a prompt for user to choose their card or something
                chosenCard = "NA";

            }else { // CurrentPlayer is a computer, therefore we must choose how they play their turn 

                // TODO: HAVE COMPUTER CHOOSE A CARD
                chosenCard = "NA";

            }
            
            

            
            if( discardPile.get(0).charAt(0) == chosenCard.charAt(0) || discardPile.get(0).charAt(1) == chosenCard.charAt(1)){
                /* Card Being Played matches the Suite or Number of top of discard pile */ 
               
                // Rule 7 Applies
                // Play Card by removing card from the player deck , and inserting to the top of dicard pile
                rule7(chosenCard, currentPlayer,discardPile);
                
            }else if(false/* Conditions for Other Rule */){
                
                // etc..

            }

            // PLAY CARDS BELOW 
        


            // 6. Play Next Player TODO: IMPLEMENT BELOW ( )
            // Order could be User 1-> Computer 2-> Comp 3 -> Comp 4  Then loop back 
            // So, if we know currentPlayer , then we know the next player
            nextPlayer();
        }

        

    }   
    //-----------IMPLEMENT CARDS-----------//
    // all game flow implemented here

    // added this//
    //----------IMPLEMENT RULE1&2----------//
    // rules 1 and 2 are implemented here


    //----------IMPLEMENT RULE3&4----------//
    //jay park
    // rules 3 and 4 are implemented here


    //----------IMPLEMENT RULE5&6----------//
    // rules 5 and 6 are implemented here
    static boolean rule5(){ return true;} // <-- To Test Program
    // hola 
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

         System.out.println("TEST"+ (int)(Math.random() * (1) ));

         while(discardDeck.size() > 1){
            int randomInt = (int)(Math.random() * (discardDeck.size()-1) ) + 1 ; // Make sure we don't remove first card of discard pile

            
            String cardRemoved = discardDeck.remove(randomInt);
            drawDeck.add(cardRemoved);

         }


    } // End of rule8()

    /******************************** Game Functions Below **********************************/

    /*
     * createShuffledDrawPile(ArrayList<String> arrayList)
     * 
     * Similar to previous programming assignments, This method works in 3 steps 
     *  1. Create an Array Deck
     *  2. Shuffle Deck
     *  3. Convert Shuffled Array Deck Into  Array List Deck by inserting card by card
     */
    static void createShuffledDrawPile(ArrayList<String> arrayList){
        System.out.println("Create Draw Pile ");

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

    // TODO: SOMEHOME DETEMINE NEXT PLAYER
    static void nextPlayer(){}

    /***************************  Debug Functions  ***************************************/
    static void printArrayList(ArrayList<String> arrayList){
        for(int i = 0; i < arrayList.size();i++){
            System.out.print(arrayList.get(i) + " ");
        }
        System.out.println();
    }

}