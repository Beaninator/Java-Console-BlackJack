import java.util.Random;

public class BlackJackGame
{
  public static void main(String args[])
  {
    //variables to track statistics//
    int playerWins = 0;
    int dealerWins = 0;
    int ties = 0;

    //Passing in command line arguments//
    int numRounds = Integer.parseInt(args[0]);
    int numDecks = Integer.parseInt(args[1]);
    int lenTrace = Integer.parseInt(args[2]);

    //initializing the necessary variables to play the round//
    String result = "Result :";
    BlackjackCards shoe = new BlackjackCards(10);
    BlackjackCards discardPile = new BlackjackCards(5);
    BlackjackCards playerHand = new BlackjackCards(5);
    BlackjackCards dealerHand = new BlackjackCards(5);

    System.out.println("Starting Blackjack with " + numRounds + " rounds and " + numDecks + " decks in the shoe");

    //initializes the game//
    initGame(shoe, playerHand, dealerHand, numDecks);

    //keeps track of the original shoe size after initialization so that it can be used later to reshuffle//
    int originalShoeSize = shoe.size();

    //initializes the value to track whether the round is still being palyed//
    boolean playing;

    //plays the amount of specified rounds//
    for(int i = 0; i < numRounds; i ++)
    {
      result = "";
      playing = true;

      System.out.println("Round " + (i + 1) + " beginning\nPlayer: " + playerHand.toString() + " : " + playerHand.getValue() + "\nDealer: " + dealerHand.toString() + " : " + dealerHand.getValue());
      //while nobody has won//
      while(playing)
      {
        //If the dealer will "hit"//
        if(dealerHand.getValue() <= 17)
        {
          System.out.println("Dealer hits : " + dealCard(shoe, dealerHand) + " : " + dealerHand.getValue());
          //if the dealer busts//
          if(dealerHand.getValue() > 21)
          {
            result += "Player wins!";
            playing = false;
            System.out.println("Dealer BUSTS " + dealerHand.toString() + " : " + dealerHand.getValue());
            playerWins ++;
            break;
          }
        }
        else
        {
          System.out.println("Dealer STANDS: " + dealerHand.toString() + " : " + dealerHand.getValue());
        }

        //If the player will "hit"//
        if(playerHand.getValue() <= 17)
        {
          System.out.println("Player hits : " + dealCard(shoe, playerHand) + " : " + playerHand.getValue());
          //If the player busts//
          if(playerHand.getValue() > 21)
          {
            dealerWins ++;
            result += "Dealer wins!";
            playing = false;
            System.out.println("Player BUSTS: " + playerHand.toString() + " : " + playerHand.getValue());
            break;
          }
        }
        else
        {
          System.out.println ("Player STANDS: " + playerHand.toString() + ": " + playerHand.getValue());
        }

        //if both players stand//
        if(playerHand.getValue() >= 17 && dealerHand.getValue() >= 17)
        {
          //player won//
          if(playerHand.getValue() > dealerHand.getValue())
          {
            result += "Player wins!";
            playerWins ++;
          }

          //tie//
          else if (playerHand.getValue() == dealerHand.getValue())
          {
            result += "Push!";
            ties ++;
          }

          //dealer won//
          else
          {
            result += "Dealer wins!";
            dealerWins ++;
          }

          //breaks out of this rounds playing loop//
          playing = false;
          break;
        }
      }
      System.out.println(result);

      //discards both player and dealer hands into the discard pile//
      discardHand(playerHand, discardPile);
      discardHand(dealerHand, discardPile);

      //if the discard pile must be shuffled back into the main shoe//
      if(shoe.size() <= (originalShoeSize / 4))
      {
        System.out.println("Reshuffling the shoe in round " + i);
        discardHand(discardPile, shoe);
      }
    }
    System.out.println("After " + numRounds + " rounds, here are the results:\nDealer Wins: " + dealerWins + "\nPlayer Wins: " + playerWins + "\nPushes: " + ties);
  }

  //Properly initializes shoe, playerhand, and dealer hand//
  public static void initGame(BlackjackCards shoe, BlackjackCards player, BlackjackCards dealer, int numDecks)
  {
    for(int i = 0; i < numDecks; i ++)
    {
      createNewDeck(shoe);
    }

    shoe.shuffle();

    dealCard(shoe, player);
    dealCard(shoe, dealer);
    dealCard(shoe, player);
    dealCard(shoe, dealer);
  }

  //Static method which will create a fresh deck of cards//
  public static void createNewDeck(BlackjackCards deck)
  {
    Card.Suits[] suits = Card.Suits.values();
    Card.Ranks[] ranks = Card.Ranks.values();

    for(Card.Suits suit : suits)
    {
      for(Card.Ranks rank : ranks)
      {
        Card card = new Card(suit, rank);
        deck.enqueue(card);
      }
    }
  }

  //Deals card from BlackjackCards shoe to BlackjackCards player//
  public static Card dealCard(BlackjackCards shoe, BlackjackCards player)
  {
    Card dealt = shoe.dequeue();
    player.enqueue(dealt);
    return dealt;
  }

  //used to shuffle hands back into the discard pile and the discard pile back into the shoe//
  public static void discardHand(BlackjackCards hand, BlackjackCards discardPile)
  {
    for(int i = 0; i < hand.size(); i ++)
    {
      discardPile.enqueue(hand.dequeue());
    }
  }
}
