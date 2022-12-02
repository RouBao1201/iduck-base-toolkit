//package com.iduck.mongo.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
///**
// * MongoDB操作工具类
// *
// * @author SongYanBin
// * @copyright 2022-2099 SongYanBin All Rights Reserved.
// * @since 2022/11/29
// **/
//@Component
//public class IMongoHelper {
//    private static IMongoHelper mongoHelper;
//
//    @PostConstruct
//    public void init() {
//        mongoHelper = this;
//        mongoHelper.mongoTemplate = this.mongoTemplate;
//    }
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    /**
//     * 保存数据对象，集合为数据对象中@Document 注解所配置的collection
//     *
//     * @param obj 数据对象
//     */
//    public static void save(Object obj) {
//        mongoHelper.mongoTemplate.save(obj);
//    }
//
//    /**
//     * 指定集合保存数据对象
//     *
//     * @param obj            数据对象
//     * @param collectionName 集合名
//     */
//    public static void save(Object obj, String collectionName) {
//        mongoHelper.mongoTemplate.save(obj, collectionName);
//    }
//
//    /**
//     * 根据数据对象中的id删除数据，集合为数据对象中@Document 注解所配置的collection
//     *
//     * @param obj 数据对象
//     */
//    public static void remove(Object obj) {
//        mongoHelper.mongoTemplate.remove(obj);
//    }
//
//    /**
//     * 指定集合 根据数据对象中的id删除数据
//     *
//     * @param obj            数据对象
//     * @param collectionName 集合名
//     */
//    public static void remove(Object obj, String collectionName) {
//        mongoHelper.mongoTemplate.remove(obj, collectionName);
//    }
//
//    /**
//     * 根据key，value到指定集合删除数据
//     *
//     * @param key            键
//     * @param value          值
//     * @param collectionName 集合名
//     */
//    public static void removeById(String key, Object value, String collectionName) {
//        Criteria criteria = Criteria.where(key).is(value);
//        criteria.and(key).is(value);
//        Query query = Query.query(criteria);
//        mongoHelper.mongoTemplate.remove(query, collectionName);
//    }
//}
