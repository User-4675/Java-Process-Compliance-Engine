package nl.rug.ap.a1;

import lombok.Getter;

import java.util.Scanner;

/**
 * Handles user configuration for the application.
 * <p>
 * This class allows the user to specify the number of threads to use for
 * processing and whether to display live progress updates. User input is
 * validated to ensure proper configuration values.
 */
@Getter
public class Configuration {

    /** Scanner used to read input from the console. */
    private final Scanner scanner = new Scanner(System.in);

    /** Number of threads configured by the user. */
    private int noOfThreads;

    /** Whether live progress updates should be displayed. */
    private boolean showLiveProgress;

    /**
     * Prompts the user to configure the application.
     * <p>
     * This method sequentially calls {@link #setThreads()} and
     * {@link #setShowLiveProgress()} to collect all required configuration.
     */
    public void getConfiguration(){
        System.out.println("------ Configuration ------- ");
        setThreads();
        setShowLiveProgress();
    }

    /**
     * Reads the number of threads to use from user input.
     * <p>
     * Continuously prompts the user until a valid integer is entered.
     * Sets the {@link #noOfThreads} field with the provided value.
     */
    private void setThreads() {
        while (true) {
            System.out.print("Enter number of threads to use: ");
            if (scanner.hasNextInt()) {
                this.noOfThreads = scanner.nextInt();
                scanner.nextLine(); // consume leftover newline
                break;
            } else {
                System.out.println("Please enter an Integer");
                scanner.next(); // discard invalid input
            }
        }
    }

    /**
     * Prompts the user to select whether live progress should be displayed.
     * <p>
     * Continuously prompts until the user enters "y" or "n".
     * Updates the {@link #showLiveProgress} field accordingly.
     * Note that enabling live progress may impact runtime performance.
     */
    private void setShowLiveProgress(){
        while (true) {
            System.out.print("Do you wish to see live progress ? (This will impact runtime) [y/n]: ");
            String inpt = scanner.nextLine().trim().toLowerCase();
            if (inpt.equals("y") || inpt.equals("n")){
                this.showLiveProgress = inpt.equals("y");
                break;
            } else {
                System.out.println("Please enter y or n");
            }
        }
    }

}
