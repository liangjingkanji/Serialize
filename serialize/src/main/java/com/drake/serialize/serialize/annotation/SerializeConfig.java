package com.drake.serialize.serialize.annotation;

import com.tencent.mmkv.MMKV;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 序列化对象配置信息
 * <p>
 * 当被当前注解修饰的类使用委托属性 [serial]/[serialLazy]/[serialLiveData] 进行序列化读写时, 会使用当前注解中的配置信息
 */
@Target({ElementType.TYPE})
public @interface SerializeConfig {
    /**
     * MMKV 实例的唯一ID
     * The unique ID of the MMKV instance
     * <p>
     * 不同ID的MMKV数据互相隔离, 并且完全使用name作为key, 不会自动添加前缀(默认情况下都会使用全路径类名作为前缀, 例如 com.drake.serialize.UserConfig.name)
     * <p>
     * 指定该属性可以避免包/类名称发生变化后导致读不到旧值
     */
    String mmapID();

    /**
     * MMKV实例的进程模式，默认为 {@link MMKV#SINGLE_PROCESS_MODE}
     */
    int mode() default 1;

    /**
     * The encryption key of the MMKV instance (no more than 16 bytes)
     */
    String cryptKey() default "";
}
