package org.nrocn.lib.utils;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
    public static Collection converCollection(Collection<?> collection, Class clazz) {
        clazz = collection.getClass();
        System.out.println(clazz);
        return null;
    }

    /**
     * 从集合数据结构转换成 map数据结构
     *
     * @param collection
     * @param clazz
     * @return
     */

    public static Map converCollectionToMap(Collection<? extends Collection> collection
            , Class<? extends Map> clazz)
            throws IllegalAccessException, InstantiationException {

        return null;
    }
}
