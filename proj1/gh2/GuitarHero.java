package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    // Total number of keys
    final int KEY_NUM = 37;
    // An array of GuitarStrings
    GuitarString[] gsArray;
    // Simulate a real keyboard
    String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    // Constructor
    public GuitarHero(){
        // Create an array of GuitarStrings
        gsArray = new GuitarString[KEY_NUM];
        // Initialize GuitarString array
        for (int i = 0;i < KEY_NUM;i += 1){
            int frequency = (int) Math.round(440 * Math.pow(2.0,(i - 24.0) / 12.0));
            gsArray[i] = new GuitarString(frequency);
        }
    }

    public void play(){
        /* check if the user has typed a key; if so, process it */
        if (StdDraw.hasNextKeyTyped()) {
            // Get the next key
            char key = StdDraw.nextKeyTyped();
            // If the typed key is not in keyboard,just return
            if (!keyboard.contains(Character.toString(key))){
                // comment the print statement
                System.out.println(key+" is not in the keyboard!");
                return;
            }
           // Compute the index of the key
            int index = keyboard.indexOf(key);
            // Pluck the given GuitarString
            gsArray[index].pluck();
        }

        /* compute the superposition of samples */
        double sample = 0;
        for (int i = 0;i < KEY_NUM;i += 1){
            sample += gsArray[i].sample();
        }

        /* play the sample on standard audio */
        StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
        for (int i = 0;i < KEY_NUM;i += 1){
            gsArray[i].tic();
        }
    }

    public static void main(String[] args){
        GuitarHero gh = new GuitarHero();
        while (true){
            gh.play();
        }
    }
}
