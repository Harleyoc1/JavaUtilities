package com.harleyoconnor.javautilities.annotation;

import javax.annotation.Nullable;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.ElementType.METHOD;

/**
 * This annotation can be applied to a package or class to indicate that the
 * methods within that element will return nullable by default unless there is:
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
 *         {@link MethodsReturnNonnullByDefault}) applied to a more tightly
 *         nested element.
 *     </li>
 * </ul>
 *
 * <p>This annotation implies the same "nullness" as no annotation. However, it
 * is different than having no annotation, as it is inherited and it can override
 * a {@link MethodsReturnNonnullByDefault} annotation at an outer scope.</p>
 *
 * @author Harley O'Connor
 * @see Nullable
 * @see javax.annotation.ParametersAreNullableByDefault
 * @since JavaUtilities 0.0.9
 */
@Documented
@Nullable
@TypeQualifierDefault(value = METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MethodsReturnNullableByDefault { }
