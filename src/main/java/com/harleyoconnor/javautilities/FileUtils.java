package com.harleyoconnor.javautilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Harley O'Connor
 */
public final class FileUtils {

    /**
     * The default resources path used by functions in this class.
     */
    public static String RESOURCES_PATH = "src/main/resources/assets/";

    /**
     * Creates a file object at the specified location using the default resource path.
     *
     * @param relativePath Path relative to the default resource path.
     * @return The new file object.
     */
    public static File getFile (@Nonnull final String relativePath) {
        return getFile(relativePath, true);
    }

    /**
     * Creates a file object at the specified location.
     *
     * @param path The file path.
     * @param useDefaultResourcePath Whether or not to use the default resources path.
     * @return The new file object.
     */
    public static File getFile (@Nonnull final String path, final boolean useDefaultResourcePath) {
        return new File ((useDefaultResourcePath ? RESOURCES_PATH : "") + path);
    }

    /**
     * Gets child files of a directory as list, or returns null if the file given is not a directory.
     *
     * @param directory The directory.
     * @return The list of child file objects.
     */
    @Nullable
    public static List<File> getChildFiles (@Nonnull final File directory) {
        return !directory.isDirectory() ? null : Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    }

    /**
     * Gets child files with the file extension given as a list, or returns null if the file given is not
     * a directory.
     *
     * @param directory The directory.
     * @param fileExtension The file extension.
     * @return The list of child files objects.
     */
    @Nullable
    public static List<File> getChildFiles (@Nonnull final File directory, @Nonnull final String fileExtension) {
        return !directory.isDirectory() ? null : Arrays.asList(Objects.requireNonNull(directory.listFiles(file -> file.getName().toLowerCase().endsWith(fileExtension.toLowerCase()))));
    }

}
