import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class NumberMonster {

    public static void main(String[] args) {
        //create scanner, print welcome message
        Scanner reader = new Scanner(System.in);
        System.out.printf("%nWelcome to Number Monster!%nHow quickly can you solve 12 simple mental math questions?%n");
        
        //prompt user for name
        String username;
        System.out.printf("%nEnter your name to play: %n");
        username = reader.next();
        
        //Set score to 0 and start a timer
        int score = 0;
        long start = System.currentTimeMillis();

        do {
            //generate a random number to decide what kind of math problem the user will solve next
            int operation = ThreadLocalRandom.current().nextInt(1, 4);

            switch (operation) {
                case 1:
                    //Case 1 - addition
                    int random1 = ThreadLocalRandom.current().nextInt(1, 10);
                    int random2 = ThreadLocalRandom.current().nextInt(1, 10);

                    int sum = random1 + random2;

                    System.out.printf("%n%d + %d = ", random1, random2);
                    int answer = Integer.parseInt(reader.next());

                    if (answer == sum) {
                        score++;
                    }
                    break;

                case 2:
                    //Case 2 - Subtraction
                    random1 = ThreadLocalRandom.current().nextInt(1, 10);
                    random2 = ThreadLocalRandom.current().nextInt(1, 10);

                    int order = random1 > random2 ? 1 : 2;
                    int difference = 0;

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

                    answer = Integer.parseInt(reader.next());

                    if (answer == difference) {
                        score++;
                    }
                    break;

                case 3:
                    //Case 3 - multiplication
                    random1 = ThreadLocalRandom.current().nextInt(1, 10);
                    random2 = ThreadLocalRandom.current().nextInt(1, 10);

                    int product;

                    product = random1 * random2;

                    System.out.printf("%n%d x %d = ", random1, random2);
                    answer = Integer.parseInt(reader.next());

                    if (answer == product) {
                        score++;
                    }
                    break;

                case 4:
                    //case 4 - division
                    random1 = ThreadLocalRandom.current().nextInt(11, 100);
                    random2 = ThreadLocalRandom.current().nextInt(2, 10);

                    int modulus = random1 % random2 == 0 ? 1 : 2;
                    int quotient;

                    switch (modulus) {
                        case 1:
                            System.out.printf("%n%d / %d = ", random1, random2);
                            quotient = random1 / random2;
                            answer = Integer.parseInt(reader.next());

                            if (answer == quotient) {
                                score++;
                            } 
                            break;

                        case 2:
                            break;
                    }
                    break;
            }
        } while (score != 12); //once the user has done 12 problems, break the loop
        
        //Calculate ending time
        long end = System.currentTimeMillis();
        long time = end - start;
        long timeSec = time / 1000;
        long minutes;
        long seconds;

        if (timeSec > 60) {
            //Output score and time
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
                // Getting an integer failed.=
                failed = true;
                System.out.printf("Try again: ");
            }

        } while (failed);

        return parsed;
    }
}
