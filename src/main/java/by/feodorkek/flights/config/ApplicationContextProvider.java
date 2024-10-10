package by.feodorkek.flights.config;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Utility {@link Component @Component} for static access to current {@link ApplicationContext}
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    /**
     * Static {@link ApplicationContext} value which set from Spring context in setApplicationContext method
     */
    @Getter
    private static ApplicationContext context;

    /**
     * Setting {@link ApplicationContext} from {@link ApplicationContextAware}
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Gets {@link Bean @Bean} object by {@code Class} from current {@link ApplicationContext}
     *
     * @param beanClass {@code Class} of required {@link Bean @Bean}
     * @param <T>       {@link Bean @Bean} type
     * @return object which was declared like a {@link Bean @Bean}
     * @throws NoSuchBeanDefinitionException   if no bean of the given type was found
     * @throws NoUniqueBeanDefinitionException if more than one bean of the given type was found
     * @throws BeansException                  if the bean could not be created
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

}
