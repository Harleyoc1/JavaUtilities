package com.harleyoconnor.javautilities.util;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.List;

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
    private FileUtils() {
    }

    /**
     * The default resources path used by functions in this class.
     */
    private static String RESOURCES_PATH = "src/main/resources/";

    /**
     * The default assets path used by functions in this class.
     */
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
     * Creates a {@link File} object at the specified location using the default resource path.
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
     * @param path                   The file path.
     * @param useDefaultResourcePath Whether or not to use the default resources path.
     * @return The new file object.
     */
    public static File file(final String path, final boolean useDefaultResourcePath) {
        return new File((useDefaultResourcePath ? ASSETS_PATH : "") + path);
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
     * Returns a {@link List} of {@link File} objects denoting the files in the directory denoted by the specified
     * {@code directory}.
     *
     * <p>If there is an I/O error, the specified {@code directory} is not a
     * directory, or the directory does not contain any files, {@link Collections#emptyList()} will be returned.</p>
     *
     * @param directory The directory, as a {@link File} object.
     * @return Returns a {@link List} of {@link File} objects denoting the files in the directory denoted by the
     * specified {@code directory}, or {@link Collections#emptyList()} if the specified {@code directory} is not a
     * directory, an I/O error occurs, or the directory contains no files.
     * @throws SecurityException If a security manager exists and its {@link SecurityManager#checkRead(String)} method
     *                           denies read access to the directory
     */
    public static List<File> filesAsList(final File directory) {
        final var files = directory.listFiles();
        return files == null ? Collections.emptyList() : List.of(files);
    }

    /**
     * Returns a {@link List} of {@link File} objects denoting the files in the directory denoted by the specified
     * {@code directory}, filtered to those bearing the specified file {@code extension}.
     *
     * <p>If there is an I/O error, the specified {@code directory} is not a
     * directory, or the directory does not contain any files, {@link Collections#emptyList()} will be returned.</p>
     *
     * @param directory The directory, as a {@link File} object.
     * @param extension The file extension to filter to.
     * @return Returns a {@link List} of {@link File} objects denoting the files in the directory denoted by the
     * specified {@code directory}, filtered to those bearing the specified file {@code extension}, or {@link
     * Collections#emptyList()} if the specified {@code directory} is not a directory, an I/O error occurs, or the
     * directory contains no files.
     */
    public static List<File> filesAsList(final File directory,
                                         final String extension) {
        return filesAsList(directory, file ->
                file.getName().toLowerCase().endsWith(extension.toLowerCase()));
    }

    /**
     * Returns a {@link List} of {@link File} objects denoting the files in the directory denoted by the specified
     * {@code directory}, filtered to those that accepted by the specified {@code fileFilter}.
     *
     * <p>If there is an I/O error, the specified {@code directory} is not a
     * directory, or the directory does not contain any files, {@link Collections#emptyList()} will be returned.</p>
     *
     * @param directory  The directory, as a {@link File} object.
     * @param fileFilter The {@link FileFilter}, by which to filter the returned files.
     * @return Returns a {@link List} of {@link File} objects denoting the files in the directory denoted by the
     * specified {@code directory}, filtered to those accepted by the specified {@code fileFilter}, or {@link
     * Collections#emptyList()} if the specified {@code directory} is not a directory, an I/O error occurs, or the
     * directory contains no files.
     * @since JavaUtilities 0.1.1
     */
    public static List<File> filesAsList(final File directory,
                                         final FileFilter fileFilter) {
        final var files = directory.listFiles(fileFilter);
        return files == null ? Collections.emptyList() : List.of(files);
    }

}
