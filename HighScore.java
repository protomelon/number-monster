import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.io.PrintWriter;
/**
 * HighScore - Handles the loading, displaying, updating, and saving of high scores
 * @author DManness
 * @version 01
 * Date Created: 2017-10-12
 * Date Modified: 2017-10-12
 *
 * -- Changelog --
 *
 * -- TODO --
 * Reimplement the scores array as an arrayList for easier adding, sorting, removing, etc...
 */
public class HighScore {
    private File highScoreFile;
    private Score[] scores;

    public HighScore(String hsFilePath) {
        loadHighScores(hsFilePath);
    }


    private void loadHighScores(String hsFilePath) {
        // Define a Scanner to read the file
        Scanner fReader;
        // Try to load the file
        highScoreFile = new File(hsFilePath);

        // Check if the file exists and create it if it does not
        if (highScoreFile.exists()) {
            try {
                // Try to read the file
                fReader = new Scanner(highScoreFile);

                // Count the number of tokens (lines) in the file
                int lines = 0;
                while (fReader.hasNextLine()) {
                    lines++;
                    fReader.nextLine();
                }

                // Reset the Scanner to read it again
                fReader = new Scanner(highScoreFile);

                // Initialize an array to hold all of the scores
                scores = new Score[lines];

                // Read the file into an array of scores
                int i = 0;
                while (fReader.hasNextLine()) {
                    // Split the line into an array separated by ','
                    String[] current = fReader.nextLine().split(",");

                    // Initialize a new score object
                    scores[i] = new Score(current[1].trim(), Long.parseLong(current[0].trim()));

                    //System.out.println(scores[i].getScore() + " | " + scores[i].getPlayer());

                    // Iterate the counter
                    i++;
                }

                // Close the Scanner
                fReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Warning! Could not create a High Score File, no high scores will be displayed");
            }

        } else {
            // The file does not exist, attempt to create a new one.
            System.out.println("High Score File was not found. Making a new one...");
            try {
                // Create a new file
                highScoreFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Warning! Could not create a High Score File, no high scores will be displayed");
            }
        }
    }

    /**
     * Returns an array containing all of the current scores in the high score tables.
     */
    public Score[] getScoresList() {
        return scores;
    }

    /**
     * Prints the currently loaded high scores list.
     */
    public void printHighScores() {
        // Define a list of high scores
        // Sort the array
        Arrays.sort(scores);

        // Print the header for the high scores list
        System.out.printf("------------------------------%n");
        System.out.printf("%9s%s%n", "", "High Scores");
        System.out.printf("------------------------------%n");
        System.out.printf("%-20s %s%n", "Player", "Score");
        System.out.printf("%-20s %s%n", "------", "-----");


        // Print the scores from fastest to slowest.
        for (int i = scores.length - 1 ; i >= 0; i--)
        {
            long timeSec = scores[i].getScore() / 1000;
            long timemins = timeSec / 60;
            timeSec = timeSec % 60;
            long timeMS = (scores[i].getScore() % 1000);
            System.out.printf("%-20s %02d:%02d.%03d%n", scores[i].getPlayer(),timemins, timeSec, timeMS);
        }

    }

    /** Add a new score to the high scores list
     * @param player - the name of the player to store - NOTE - for safety, the character ',' will be replaced by ' '
     * @param points - The score the player acheived
     */
    public void addScore(String player, long points)
    {
        Score score = new Score(player.replace(",", " "), points);

		// Define a default scores variable if scores is null (IE, it failed to load above)
		if(scores != null)
		{
			// Copy the the score variable into a temporary place
			Score[] oldScores = scores;
			
			// Replace the scores with a bigger array
			scores = new Score[scores.length + 1];
					
			// Refill the rest of the array
			for (int i = 0; i < oldScores.length; i++)
			{
				scores[i] = oldScores[i];
			}
			
			// Let the temp array be destroyed
			oldScores = null;
			
		}
		else
		{
			
			// The array was not initialized. Initialize it with a single element 
			scores = new Score[1];
			
		}
			
			// Add the new element to the array at the final position.
			scores[scores.length - 1] = score;



    }

    /**
     * Overwrites the current scores file
     * TODO - implement a checksum to prevent the user from manually editing the score files.
     */
    public void saveHighScoresFile()
    {
        try {

            // Backup the old file
            File backupFile = new File (highScoreFile.toString() + "_old");
            highScoreFile.renameTo(backupFile);


            // Purge the existing high scores files
            highScoreFile.delete();

            // Define a printwriter to export to file
            PrintWriter fOut = new PrintWriter(highScoreFile);

            // Write the entire high scores array to the file
            for (Score s : scores)
            {
                fOut.println(s.getScore()+","+s.getPlayer());
            }

            // Close the file
            fOut.close();
            // Delete the backup file
            backupFile.delete();

        } catch (FileNotFoundException e)
        {
            System.out.println("Warning: Could not write the high scores file. Scores for this round may be lost.");
        }

    }

}

/**
 * Score - holds a single instance of a score object
 * @author DManness
 * @version 01
 * Date Created: 2017-10-12
 * Date Modified: 2017-10-12
 *
 * -- Changelog --
 */
class Score implements Comparable<Score>
{
    // Declare Class variables
    private long score;
    private String player;

    /**
     * @param player - The name of the player who acheived the score
     * @param score - The Score the player received
     */
    public Score(String player, long score)
    {
        // Set the score of this instance
        this.player = player;
        this.score = score;
    }

    /**
     * Compares two score objects to determine whether one is bigger than the other
     * Returns a positive if anotherScore < x, a negative if anotherScore > x, 0 if they are equal
     * @param anotherScore - A score object that you are comparing too.
     * @return - a positive if anotherScore < x, a negative if anotherScore > x, 0 if they are equal
     */
    @Override
    public int compareTo(Score anotherScore)
    {
        return (int) (anotherScore.getScore() - score);
    }


    // Getters and setters
    public String getPlayer()
    {
        return player;
    }

    public long getScore()
    {
        return score;
    }

}