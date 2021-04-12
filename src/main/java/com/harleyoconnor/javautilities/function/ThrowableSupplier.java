package com.harleyoconnor.javautilities.function;

import java.util.function.Supplier;

/**
 * Mirrors the {@link Supplier}, except that {@link #get()} can throw a
 * {@link Throwable} of type {@link T}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is
 * {@link #get()}.
 *
 * @param <R> The type of the result supplied by this supplier.
 * @param <T> The type that this supplier {@code throws}.
 *
 * @author Harley O'Connor
 * @see Supplier
 * @since JavaUtilities 0.0.10
 */
@FunctionalInterface
public interface ThrowableSupplier<R, T extends Throwable> {

    /**
     * Gets a result of type {@link R}, or throws {@link T}.
     *
     * @return A result.
     * @throws T The supplier {@link Throwable}.
     */
    R get() throws T;

    /**
     * Creates a {@link ThrowableSupplier} that acts as a "proxy" to the
     * given {@link Supplier} (calling {@link Supplier#get()} when
     * {@link #get()} is called on it). It can throw a {@link Throwable}
     * of inferred type {@link T}.
     *
     * @param supplier The {@link Supplier} to act as proxy to.
     * @param <R> The type of the result supplied by this supplier.
     * @param <T> The type that this supplier {@code throws}.
     * @return A new {@link ThrowableFunction}.
     */
    static <R, T extends Throwable> ThrowableSupplier<R, T> proxy(final Supplier<R> supplier) {
        return supplier::get;
    }

}
