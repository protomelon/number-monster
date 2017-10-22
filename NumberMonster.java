import java.util.Scanner;

// Used to generate random ints
import java.util.concurrent.ThreadLocalRandom;  

public class NumberMonster {
    public static void main(String[] args) {
       
        Scanner reader = new Scanner(System.in);
        
        System.out.printf("%nWelcome to Number Monster!%nHow quickly can you solve 12 simple mental math questions?%n%n");

        // Loads and displays a list of high scores
        HighScore highScores = new HighScore("high.score");
        if (highScores.getScoresList() != null) {
            highScores.printHighScores();
        }

        String username;
        System.out.printf("%nEnter your name to play: %n");
        username = reader.nextLine();

        System.out.printf("%nChoose your monster:%n[1] Addition%n[2] Subtraction%n[3] Multiplication%n[4] Division%n[5] All Operations%n%nGame mode: ");
        int mode = getMode();

        int score = 0;
        
        // Get current run time of program to be subtracted from end time to get final time (score)
        long start = System.currentTimeMillis();

        do {            
            // If int mode == 5 (All Operations) then int operation is randomized every loop
            // Otherwise int operation stays the same 
            int operation = (mode == 5) ? (ThreadLocalRandom.current().nextInt(1, 4)) : mode;

            switch (operation) {
                case 1:
                    // case 1: addition
                    int random1 = ThreadLocalRandom.current().nextInt(1, 10);
                    int random2 = ThreadLocalRandom.current().nextInt(1, 10);

                    int sum = random1 + random2;

                    System.out.printf("%n%d + %d = ", random1, random2);
                    int answer = getInt();

                    if (answer == sum) {
                        score++;
                    }
                    break;

                case 2:
                    // case 2: subtraction
                    random1 = ThreadLocalRandom.current().nextInt(1, 10);
                    random2 = ThreadLocalRandom.current().nextInt(1, 10);

                    // To prevent asking subtraction questions with negative differences
                    int order = random1 > random2 ? 1 : 2;
                    int difference = 0;
                    
                    // Subtract smaller int from larger int
                    switch (order) {
                        case 1:
                            difference = random1 - random2;
                            System.out.printf("%n%d - %d = ", random1, random2);
                            break;

                        case 2:
                            difference = random2 - random1;
                            System.out.printf("%n%d - %d = ", random2, random1);
                            break;
                    }

                    answer = getInt();

                    if (answer == difference) {
                        score++;
                    }
                    break;

                case 3:
                    // case 3: multiplication
                    random1 = ThreadLocalRandom.current().nextInt(1, 10);
                    random2 = ThreadLocalRandom.current().nextInt(1, 10);

                    int product;

                    product = random1 * random2;

                    System.out.printf("%n%d x %d = ", random1, random2);
                    answer = getInt();

                    if (answer == product) {
                        score++;
                    }
                    break;

                case 4:
                    // case 4: division
                    random1 = ThreadLocalRandom.current().nextInt(11, 100);
                    random2 = ThreadLocalRandom.current().nextInt(2, 10);

                    // To prevent asking division questions with remainders
                    int modulus = random1 % random2 == 0 ? 1 : 2;
                    int quotient;
                    
                    // Continue assigning new random ints until quotient does not contain remainder
                    switch (modulus) {
                        case 1:
                            System.out.printf("%n%d / %d = ", random1, random2);
                            quotient = random1 / random2;
                            answer = getInt();

                            if (answer == quotient) {
                                score++;
                            } 
                            break;

                        case 2:
                            break;
                    }
                    break;
            }
        // End loop when user has correctly answered 12 questions
        // Incorrect answers do not increment score
        } while (score != 12); 
        
        // Calculate final time
        long end = System.currentTimeMillis();
        long time = end - start;
        long timeSec = time / 1000;
        long minutes;
        long seconds;

        // Log a new high score
        highScores.addScore(username, time);
        // Rewrite the high scores file
        highScores.saveHighScoresFile();

        if (timeSec > 60) {
            // Output score and time
            minutes = timeSec / 60;
            seconds = timeSec - minutes * 60;
            System.out.printf("You win! Your final time is: %d minute%s and %d seconds", minutes, minutes > 1 ? "" : "s", seconds);
        }
        else {
            System.out.printf("%nYou win! Your final time is: %d seconds", timeSec);
        }
    }

    /**
     * Keeps prompting the user for an int until they get their shit together
     * and enter one.
     *
     * @author Dylan Manness
     * @version 01 Date Created: October 10th, 2017
     */
    static int getInt() {
        // Define a new scanner
        Scanner keyIn = new Scanner(System.in);
        int parsed = 0;
        boolean failed;

        // Keep prompting the user forever until they enter a valid value
        do {
            failed = false;
            // Try to get an integer from the user
            try {
                parsed = Integer.parseInt(keyIn.nextLine());
            } catch (Exception e) {
                // Getting an integer failed
                failed = true;
                System.out.printf("Try again: ");
            }

        } while (failed);

        return parsed;
    }
    // Used to validate mode input at start of game
    static int getMode() {
        //Mode is int 1-5
        Scanner readMode = new Scanner(System.in);
        int mode = 0;
        boolean badInt;
        //Loop until a valid game more is entered
        do {
            badInt = false;
            try { //catch (Exception e) catches bad input exceptions
                do {
                        mode = Integer.parseInt(readMode.nextLine());
                } while (mode < 1 || mode > 5);  //while game mode isnt within valid range
            } catch (Exception e) {
                //catch exception if Integer.parseInt fails (ie. user entered something other than an int)
                badInt = true;
                System.out.printf("Game mode: ");
            }
        } while (badInt);
        //Return the mode as an int
        return mode;
    }
}
