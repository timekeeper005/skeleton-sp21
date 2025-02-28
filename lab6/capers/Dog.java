package capers;

import jdk.jshell.execution.Util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import static capers.Utils.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.HashSet;

/** Represents a dog that can be serialized.
 * @author Tamaki Tiana
*/
public class Dog implements Serializable {

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = CapersRepository.DOGS;

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        File[] files = DOG_FOLDER.listFiles();

        if (files != null) {
            // traverse each file to find the file with name NAME
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().equals(name)){
                        return readObject(file,Dog.class);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(this);
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     * Assume each dog name is unique.
     * If you were to save a dog whose name is not unique,you would actually override the original dog.
     */
    public void saveDog() {
        // Use a Set to track existing dog names
        Set<String> existingDogNames = new HashSet<>();
        File[] files = DOG_FOLDER.listFiles();

        // Check for existing files and collect names
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    existingDogNames.add(file.getName());
                }
            }
        }

        // If the dog name already exists, delete the file
        if (existingDogNames.contains(name)) {
            File existingFile = Utils.join(DOG_FOLDER, name);
            existingFile.delete();
        }

        // Create the new file
        Path currentFilePath = Utils.join(DOG_FOLDER, name).toPath();
        try {
            Files.createFile(currentFilePath); // Will throw an exception if the file already exists
        } catch (IOException e) {
            System.out.println("An I/O exception occurred: " + e.getMessage());
            e.printStackTrace();
            return; // Exit if there's an error creating the file
        }

        // Write the dog object to the file
        writeObject(currentFilePath.toFile(), this);
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            name, breed, age);
    }

}
