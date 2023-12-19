package com.mrsnow.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-05-02  11:09
 **/
public class QueryWrap<T> extends AbstractWrapper<T, String, QueryWrap<T>> implements Query<QueryWrap<T>, T, String> {
    private boolean skipEmpty = true;
    private SharedString sqlSelect = new SharedString();


    public QueryWrap() {
        this(null);
    }

    public QueryWrap(T entity) {
        super.setEntity(entity);
        super.initNeed();
        //覆盖之前的entity
        if (entity != null) {
            super.setEntity(replace(BeanUtil.toBean(entity, getEntityClass())));
        }
    }
    @Override
    protected QueryWrap<T> instance() {
        return null;
    }

    @Override
    public QueryWrap<T> select(String... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(String.join(StringPool.COMMA, columns));
        }
        return typedThis;
    }

    @Override
    public QueryWrap<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        super.setEntityClass(entityClass);
        this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(getEntityClass()).chooseSelect(predicate));
        return typedThis;
    }
    /**
     * 替换 实体对象中类型为String 类型的参数，并将% 和 _ 符号转义
     *
     * @param source 源对象
     * @return 最新源对象
     */
    public static <T> T replace(Object source) {
        if (source == null) {
            return null;
        }
        Object target = source;

        Class<?> srcClass = source.getClass();
        Field[] fields = ReflectUtil.getFields(srcClass);
        for (Field field : fields) {
            Object classValue = ReflectUtil.getFieldValue(source, field);
            if (classValue == null) {
                continue;
            }
            //final 和 static 字段跳过
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (!(classValue instanceof String)) {
                continue;
            }
            String srcValue = (String) classValue;
            if (srcValue.contains("%") || srcValue.contains("_")) {
                String tarValue = StrHelper.keywordConvert(srcValue);
                ReflectUtil.setFieldValue(target, field, tarValue);
            }
        }
        return (T) target;
    }
}
