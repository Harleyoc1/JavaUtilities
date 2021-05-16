package com.harleyoconnor.javautilities.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.ElementType.METHOD;

/**
 * This annotation can be applied to a package or class to indicate that the
 * methods within that element will return nonnull by default unless there is:
 *
 * <ul>
 *     <li>
 *         An explicit nullness annotation.
 *     </li>
 *     <li>
 *         The method overrides a method in a superclass (in which case the
 *         annotation of the corresponding method in the superclass applies).
 *     </li>
 *     <li>
 *         There is a default method annotation (like
 *         {@link MethodsReturnNullableByDefault}) applied to a more tightly
 *         nested element.
 *     </li>
 * </ul>
 *
 * @author Harley O'Connor
 * @see Nonnull
 * @see javax.annotation.ParametersAreNonnullByDefault
 * @since JavaUtilities 0.0.9
 */
@Documented
@Nonnull
@TypeQualifierDefault(value = METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MethodsReturnNonnullByDefault { }
