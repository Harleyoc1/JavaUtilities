package com.harleyoconnor.javautilities.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
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
     * Suppresses default constructor, ensuring non-instantiability.
     */
    private FileUtils() {}

    /** The default resources path used by functions in this class. */
    private static String RESOURCES_PATH = "src/main/resources/";

    /** The default assets path used by functions in this class. */
    private static String ASSETS_PATH = RESOURCES_PATH + "assets/";

    /**
     * Returns the current default {@link #RESOURCES_PATH}.
     *
     * @return The default {@link #RESOURCES_PATH}.
     * @since JavaUtilities 0.1.1
     */
    public static String resourcePath() {
        return RESOURCES_PATH;
    }

    /**
     * Sets a new default {@link #RESOURCES_PATH}.
     *
     * @param resourcesPath The new {@link #RESOURCES_PATH} to set.
     * @since JavaUtilities 0.1.1
     */
    public static void setResourcesPath(final String resourcesPath) {
        RESOURCES_PATH = resourcesPath;
    }

    /**
     * Returns the current default {@link #ASSETS_PATH}.
     *
     * @return The default {@link #ASSETS_PATH}.
     * @since JavaUtilities 0.1.1
     */
    public static String assetsPath() {
        return ASSETS_PATH;
    }

    /**
     * Sets a new default {@link #ASSETS_PATH}.
     *
     * @param assetsPath The new {@link #ASSETS_PATH} to set.
     * @since JavaUtilities 0.1.1
     */
    public static void setAssetsPath(final String assetsPath) {
        ASSETS_PATH = assetsPath;
    }

    /**
     * Creates a {@link File} object at the specified location using the default
     * resource path.
     *
     * @param relativePath Path relative to the default resource path.
     * @return The new file object.
     */
    public static File file(final String relativePath) {
        return file(relativePath, true);
    }

    /**
     * Creates a {@link File} object at the specified location.
     *
     * @param path The file path.
     * @param useDefaultResourcePath Whether or not to use the default resources path.
     * @return The new file object.
     */
    public static File file(final String path, final boolean useDefaultResourcePath) {
        return new File ((useDefaultResourcePath ? ASSETS_PATH : "") + path);
    }

    /**
     * Gets the internal path of a given asset.
     *
     * @param relativePath The path relative to the resources/assets folder.
     * @return The path including the assets path defined at the top of this class.
     * @since JavaUtilities 0.0.5
     */
    public static String internalPath(final String relativePath) {
        return ASSETS_PATH + relativePath;
    }

    /**
     * Gets children files of a directory as {@link List}, or returns
     * {@link Collections#emptyList()} if the specified given is not a directory.
     *
     * @param directory The directory.
     * @return The list of child file objects.
     * @throws SecurityException If a security manager exists and its
     *         {@link SecurityManager#checkRead(String)} method denies read access
     *         to the directory
     */
    public static List<File> filesAsList(final File directory) {
        return !directory.isDirectory() ? Collections.emptyList() :
                Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    }

    /**
     * Gets children files with the file extension given as a {@link List}, or returns
     * {@link Collections#emptyList()} if the specified file is not a directory.
     *
     * @param directory The directory.
     * @param fileExtension The file extension.
     * @return The list of child files objects.
     */
    public static List<File> filesAsList(final File directory,
                                         final String fileExtension) {
        return !directory.isDirectory() ? Collections.emptyList() :
                Arrays.asList(Objects.requireNonNull(directory
                        .listFiles(file -> file.getName().toLowerCase()
                                .endsWith(fileExtension.toLowerCase()))
                ));
    }

}
