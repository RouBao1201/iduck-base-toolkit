package com.iduck.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 基础通用构造器
 *
 * @author songYanBin
 * @since 2022/11/23
 */
public class IBuilder<T> {
    /**
     * 存储对象注入方法
     */
    private final Supplier<T> constructor;

    /**
     * 存储属性构造方法
     */
    private final List<Consumer<T>> injectList = new ArrayList<>();

    private IBuilder(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    /**
     * 创建实体类
     *
     * @param constructor 创建构造器
     * @param <T>         实体类枚举类型
     * @return 构造器IBuilder
     */
    public static <T> IBuilder<T> builder(Supplier<T> constructor) {
        return new IBuilder<T>(constructor);
    }

    /**
     * 属性注入赋值
     *
     * @param consumer 属性setter方法
     * @param p1       属性值
     * @param <P1>     属性值
     * @return IBuilder
     */
    public <P1> IBuilder<T> with(DiInjectConsumer<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        injectList.add(c);
        return this;
    }

    /**
     * 执行创建逻辑
     *
     * @return 实体对象
     */
    public T build() {
        T instance = constructor.get();
        this.injectList.forEach(it -> it.accept(instance));
        return instance;
    }

    @FunctionalInterface
    public interface DiInjectConsumer<T, P1> {
        /**
         * 属性注入函数接口
         *
         * @param t  属性注入方法
         * @param p1 属性值
         */
        void accept(T t, P1 p1);
    }
}
