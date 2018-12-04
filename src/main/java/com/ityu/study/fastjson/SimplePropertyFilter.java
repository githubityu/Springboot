package com.ityu.study.fastjson;

import com.alibaba.fastjson.serializer.PropertyFilter;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import java.util.Objects;

/**
 * @author lihe
 */
public class SimplePropertyFilter implements PropertyFilter {


    /**
     * 过滤不需要被序列化的属性，主要是应用于Hibernate的代理和管理。
     *
     * @param object 属性所在的对象
     * @param name   属性名
     * @param value  属性值
     * @return 返回false属性将被忽略，true属性将被保留
     */
    @Override
    public boolean apply(Object object, String name, Object value) {
        //hibernate代理对象
        if (value instanceof HibernateProxy) {
            LazyInitializer initializer = ((HibernateProxy) value).getHibernateLazyInitializer();
            return !initializer.isUninitialized();
            //实体关联集合一对多等
        } else if (value instanceof PersistentCollection) {
            PersistentCollection collection = (PersistentCollection) value;
            if (!collection.wasInitialized()) {
                return false;
            }
            Object val = collection.getValue();
            return !Objects.isNull(val);
        }
        return true;
    }

}
