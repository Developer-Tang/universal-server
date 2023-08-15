package cn.tangshh.universal.core.util;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Spring Operate Util
 *
 * @author Tang
 * @version v1.0
 */
@Slf4j
@Component
@ConditionalOnClass(SpringApplication.class)
public final class SpringUtil implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;
    private static Environment env = null;

    /**
     * Get bean
     *
     * @param name Bean name
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        if (applicationContext == null) {
            throw new RuntimeException("not found applicationContext");
        }
        return (T) applicationContext.getBean(name);
    }

    /**
     * Get bean
     *
     * @param requiredType Bean type
     * @return {@link T}
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (applicationContext == null) {
            throw new RuntimeException("not found applicationContext");
        }
        return applicationContext.getBean(requiredType);
    }

    /**
     * Get env config
     *
     * @param key properties name
     * @return {@link String}
     */
    @Nullable
    public static String getConfig(@NotNull String key) {
        return env.getProperty(key);
    }

    /**
     * Get env config
     *
     * @param key properties name
     * @return {@link String}
     */
    public static String getConfig(@NotNull String key, String defaultVal) {
        return env.getProperty(key, defaultVal);
    }

    /**
     * Is exist env config
     *
     * @param key properties name
     * @return {@link String}
     */
    public static boolean containsConfig(@NotNull String key) {
        return env.containsProperty(key);
    }

    /**
     * Env config if matches
     *
     * @param expressions Expect profiles
     * @return {@link String}
     */
    public static boolean matchesProfiles(@NotNull String... expressions) {
        return env.matchesProfiles(expressions);
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext context) {
        SpringUtil.applicationContext = context;
    }

    public void setEnv(Environment env) {
        SpringUtil.env = env;
    }

    @Override
    public void destroy() {
        applicationContext = null;
    }


}
