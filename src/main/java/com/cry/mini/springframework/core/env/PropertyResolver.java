package com.cry.mini.springframework.core.env;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/08 16:40
 */
public interface PropertyResolver<T> {
    boolean containsProperty(String key);

    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    T getProperty(String key, Class targetType);

    T getProperty(String key, Class targetType, T defaultValue);

    Class getPropertyAsClass(String key, Class targetType);

    String getRequiredProperty(String key) throws IllegalStateException;

    T getRequiredProperty(String key, Class targetType) throws IllegalStateException;

    String resolvePlaceholders(String text);

    String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;
}
