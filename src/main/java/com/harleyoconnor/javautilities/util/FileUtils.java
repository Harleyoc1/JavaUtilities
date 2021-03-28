package com.harleyoconnor.javautilities.util;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Provides various useful {@code static} {@link File}-related functions.
 *
 * @author Harley O'Connor
 * @since JavaUtilities 0.0.2
 */
public final class FileUtils {

    /**
     * The default resources path used by functions in this class.
     */
    public static String RESOURCES_PATH = "src/main/resources/";

    /**
     * The default assets path used by functions in this class.
     */
    public static String ASSETS_PATH = RESOURCES_PATH + "assets/";

    /**
     * Creates a {@link File} object at the specified location using the default resource path.
     *
     * @param relativePath Path relative to the default resource path.
     * @return The new file object.
     */
    public static File getFile (final String relativePath) {
        return getFile(relativePath, true);
    }

    /**
     * Creates a {@link File} object at the specified location.
     *
     * @param path The file path.
     * @param useDefaultResourcePath Whether or not to use the default resources path.
     * @return The new file object.
     */
    public static File getFile (final String path, final boolean useDefaultResourcePath) {
        return new File ((useDefaultResourcePath ? ASSETS_PATH : "") + path);
    }

    /**
     * Gets the internal path of a given asset.
     *
     * @param relativePath The path relative to the resources/assets folder.
     * @return The path including the assets path defined at the top of this class.
     * @since JavaUtilities 0.0.5
     */
    public static String getInternalPath (final String relativePath) {
        return ASSETS_PATH + relativePath;
    }

    /**
     * Gets child files of a directory as list, or returns null if the file given is not a directory.
     *
     * @param directory The directory.
     * @return The list of child file objects.
     */
    @Nullable
    public static List<File> getChildFiles (final File directory) {
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
    public static List<File> getChildFiles (final File directory, final String fileExtension) {
        return !directory.isDirectory() ? null : Arrays.asList(Objects.requireNonNull(directory.listFiles(file -> file.getName().toLowerCase().endsWith(fileExtension.toLowerCase()))));
    }

}
