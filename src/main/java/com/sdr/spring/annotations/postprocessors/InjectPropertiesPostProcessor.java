package com.sdr.spring.annotations.postprocessors;

import com.sdr.spring.annotations.InjectProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Properties;

/**
 *  Processes {@link InjectProperties} annotation.
 */
public class InjectPropertiesPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), field -> process(bean, field));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }

    private void process(Object bean, Field field) throws IllegalAccessException {
        if (!field.isAnnotationPresent(InjectProperties.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        Type type = field.getType();
        if (type.getTypeName().equals(Properties.class.getTypeName())) {
            String path = field.getDeclaredAnnotation(InjectProperties.class).value();
            field.set(bean, load(path));
        }
    }

    private Properties load(String path) {
        try {
            if (path == null) {
                return null;
            }
            Properties result = new Properties();
            result.load(new FileInputStream(path));
            return result;
        } catch (Exception e) {
            throw new BeanCreationException(e.getMessage(), e);
        }
    }
}
