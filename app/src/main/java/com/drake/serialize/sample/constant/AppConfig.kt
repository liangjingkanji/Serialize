package com.drake.serialize.sample.constant

import com.drake.serialize.sample.model.SerializableModel
import com.drake.serialize.serialize.serial
import com.drake.serialize.serialize.serialLazy

/**
 * 常见的保存应用基本数据的单例对象,
 * 在某些场景下比数据库更方便(支持集合/主线程读取)
 * 比SharePreference更快速(内部使用MMKV开源库)
 */
object AppConfig {

    /** 懒读取, 每次写入都会更新内存/磁盘, 但是读取仅第一次会读取磁盘, 后续一直使用内存中, 有效减少ANR */
    var userId: String by serialLazy()

    /** 每次都读写磁盘 */
    var isFirstLaunch: Boolean by serial()

    /** 保存集合 */
    var host: List<String> by serial()

    /** 只要集合元素属于可序列化对象 Serializable/Parcelable 即可保存到本地 */
    var users: List<SerializableModel> by serial()
}