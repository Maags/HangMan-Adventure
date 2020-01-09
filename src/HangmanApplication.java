import java.io.IOException;
import java.util.Scanner;

public class HangmanApplication {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to HANGMAN ADVENTURE! \n \nThe rules are simple. A hidden word will be shown in dashes (_ _ _ _). Your job is to guess the word, one character at the time.\n" +
                            "You only have 6 chances. Every incorrect guess will add i body part to your HANGMAN. When its a full person, you lose\n" +
                            "Are you ready?!!");
        System.out.println();

        boolean doYouWantToPlay = true;
        while (doYouWantToPlay) {

            System.out.println("Alright, Lets play");
            HangmanLogic game = new HangmanLogic();
            do {
                System.out.println();
                System.out.println(game.drawHangman());
                System.out.println();
                System.out.println(game.getHiddenWord());
                System.out.println();

                // Get the guess. And change guess to lowercase character
                System.out.println("Enter a character that you think is in the hidden word");
                char guess = (input.next().toLowerCase()).charAt(0);
                System.out.println();

                // Check if the character has been guessed before and ask to re.enter a new character
                if (game.isGuessedAlready(guess)) {
                    System.out.println("Try again. You have guessed this character already");
                    guess = (input.next().toLowerCase()).charAt(0);
                }

                if (game.playGuess(guess)) {
                    System.out.println("Great guess. That character is in in the hidden word");
                }
                else {
                    System.out.println("Unfortunately that character is not in the hidden word");
                }
            }
            while (!game.gameOver());

            //restart game
            System.out.println();
            System.out.println("Do you want to play another game. Enter Y if you do.");
            Character response = (input.next().toUpperCase()).charAt(0);
            doYouWantToPlay = (response == 'Y');
        }

    }
}
