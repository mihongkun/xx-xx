package org.forkjoin.core.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.Transient;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现一系列函数,
 * dao通过这些函数让异步存储逻辑变成存储队列,这样可以避免脏数据保存到内存!
 *
 * @author zuoge85
 */
public abstract class EntityObject<T extends EntityObject, K extends KeyObject> {
    private static final long serialVersionUID = -8281724195431238638L;

    @JsonIgnore
    @Transient
    public abstract K getKey();

    @JsonIgnore
    @Transient
    public abstract TableInfo<T, K> getTableInfo();


    @JsonIgnore
    transient private AtomicInteger entityVersion = new AtomicInteger();
//	@JsonIgnore private transient volatile  boolean daoIsDelete = false;
//	@JsonIgnore private AtomicBoolean saveSignal = new AtomicBoolean(false);

//	/**
//	 * 尝试进入保存状态,成功则马上继续保存,失败应该停止保存
//	 * @return
//	 */
//	@JsonIgnore @Transient public  boolean daoTrySave(){
//		return saveSignal.compareAndSet(false, true);
//	}

//	/**
//	 * 保存完毕
//	 * @return 返回true,表示需要继续保存
//	 */
//	@JsonIgnore @Transient public  void entitySaveCompare(int version){
//         if(entityVersion.get() == version)
//        {
//            saveSignal.set(false);
//        }
//	}

    @JsonIgnore
    @Transient
    public boolean isEntityChange(int version) {
        return entityVersion.get() != version;
    }

//	@JsonIgnore @Transient protected void daoInitCompare(){
//		saveSignal.set(false);
//	}

    @JsonIgnore
    @Transient
    protected void changeProperty(String name, Object o) {
        entityVersion.incrementAndGet();
    }

    @JsonIgnore
    @Transient
    public int getEntityVersion() {
        return entityVersion.get();
    }

    /**
     * 可选函数
     */
    public abstract void setKey(Object key);

    public abstract Object get(String dbName);

    public abstract boolean set(String dbName, Object obj);

    public abstract T newInstance();


//	@JsonIgnore @Transient public void daoDelete(){
//		daoIsDelete = true;
//	}

//	@JsonIgnore @Transient public boolean isDaoDelete(){
//		return daoIsDelete;
//	}
}