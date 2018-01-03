package org.forkjoin.jdbckit.core;

import com.fasterxml.jackson.core.type.TypeReference;
import org.forkjoin.jdbckit.core.identifier.Field;
import org.forkjoin.jdbckit.core.identifier.Key;
import org.forkjoin.jdbckit.core.identifier.Table;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现一系列函数,
 * dao通过这些函数让异步存储逻辑变成存储队列,这样可以避免脏数据保存到内存!
 *
 * @author zuoge85
 */
public abstract class EntityMeta<T extends EntityObject, K extends KeyObject> {
    public abstract String getInsertSql();

    public abstract String getReplaceSql();

    public abstract String getFastInsertPrefixSql();

    public abstract String getFastInsertValueItemsSql();

    public abstract String getUpdateSql();

    public abstract boolean isHasAutoIncrement();

    public abstract void setKey(T t, ResultSet rs) throws SQLException;

    public abstract int setPreparedStatement(T t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException;

    public abstract int setAllPreparedStatement(T t, PreparedStatement ps, int i) throws SQLException;

    public abstract int setPreparedStatementKeys(T t, PreparedStatement ps, int i) throws SQLException;


    public abstract String getKeyUpdatePartialPrefixSql();

    public abstract String getKeyWhereByKeySql();

    public abstract String getKeyDeleteSql();

    public abstract int setKeyPreparedStatement(K k, PreparedStatement ps, int i) throws SQLException;

    public abstract String getSelectByKeySql();

    public abstract String getSelectCountSql();

    public abstract String getFormatSelectSql();

    public abstract String getFormatSelectPrefixSql();

    public abstract String getSelectPrefixSql();

    public abstract String getOrderByIdDescSql();

    public abstract RowMapper<T> getRowMapper();

    public abstract <C extends T> RowMapper<C> getRowMapper(Class<C> cls);

    public abstract Table getDbTableName();

    public abstract Map<Key, UniqueInfo> getUniques();

    public abstract UniqueInfo getUniques(Key uniqueName);

    private Map<String, EntityProperty> propertiesMap = new HashMap<>();
    private Map<Field, EntityProperty> dbNameMap = new HashMap<>();

    protected void initProperty(Field dbName, String propertyName, Class type, TypeReference<?> valueTypeRef) {
        EntityProperty property = new EntityProperty(dbName, propertyName, type, valueTypeRef);
        dbNameMap.put(dbName, property);
        propertiesMap.put(propertyName, property);
    }

    public Collection<EntityProperty> getEntityProperties() {
        return dbNameMap.values();
    }

    public EntityProperty getPropertyByDbName(Field dbName) {
        return dbNameMap.get(dbName);
    }

    public EntityProperty getProperty(String propertyName) {
        return propertiesMap.get(propertyName);
    }
}