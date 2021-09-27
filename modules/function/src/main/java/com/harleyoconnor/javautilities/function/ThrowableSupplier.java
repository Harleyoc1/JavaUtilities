package com.harleyoconnor.javautilities.function;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Mirrors the {@link Supplier}, except that {@link #get()} can throw a {@link Throwable} of type {@link T}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is {@link #get()}.
 *
 * @param <R> the type of the result supplied by this supplier
 * @param <T> the type that this supplier {@code throws}
 * @author Harley O'Connor
 * @see Supplier
 * @since JavaUtilities 0.1.0
 */
@FunctionalInterface
public interface ThrowableSupplier<R, T extends Throwable> {

    /**
     * Gets a result of type {@link R}, or throws {@link T}.
     *
     * @return a result
     * @throws T if the supplier throws
     */
    R get() throws T;

    /**
     * Creates a throwable supplier that acts as a "proxy" to the specified {@code supplier} (calling {@link
     * Supplier#get()} when {@link #get()} is called on it). It can throw a {@link Throwable} of inferred type {@link
     * T}.
     *
     * @param supplier the supplier to act as proxy to
     * @param <R>      the type of the result supplied by this supplier
     * @param <T>      the type that this supplier {@code throws}
     * @return a new throwable supplier
     */
    static <R, T extends Throwable> ThrowableSupplier<R, T> proxy(@NotNull final Supplier<R> supplier) {
        Objects.requireNonNull(supplier);
        return supplier::get;
    }

}
