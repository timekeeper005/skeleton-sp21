package capers;

import java.io.File;
import java.io.IOException;

/** A repository for Capers 
 * @author Tamaki Tiana
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD,".capers");
    static final File DOGS = Utils.join(CAPERS_FOLDER,"dogs");
    static final File STORY = Utils.join(CAPERS_FOLDER,"story");

    /**
     * Does require filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        if (!CAPERS_FOLDER.exists()) {
            CAPERS_FOLDER.mkdir();
        }
        if (!DOGS.exists()) {
            DOGS.mkdir();
        }
        try {
            if (!STORY.exists()) {
                STORY.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("An I/O exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
       String originalContent = Utils.readContentsAsString(STORY);
       String newContent = originalContent + text + "\n";
       Utils.writeContents(STORY,newContent);
       System.out.println(newContent);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog newDog = new Dog(name,breed,age);
        newDog.saveDog();
        System.out.println(newDog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog currentDog = Dog.fromFile(name);
        if (currentDog != null){
            currentDog.haveBirthday();
            currentDog.saveDog();
        } else {
            System.out.println("No such dog!");
        }
    }
}
