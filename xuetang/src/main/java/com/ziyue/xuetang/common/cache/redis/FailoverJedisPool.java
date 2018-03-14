/** 
 * @Title:TODO  
 * @Desription:TODO
 * @Company:CSN
 * @ClassName:FailSafeJedisPool.java
 * @Author:chembo
 * @CreateDate:2014年2月25日   
 * @UpdateUser:chembo  
 * @Version:0.1 
 *    
 */

package com.ziyue.xuetang.common.cache.redis;


import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


/** 
 * @ClassName: FailSafeJedisPool 
 * @Description: TODO 
 * @author: chembo 
 * @date: 2014年2月25日
 * 
 */
public class FailoverJedisPool extends Pool<JedisFacade> {
    
    public FailoverJedisPool(final  GenericObjectPoolConfig poolConfig,
            final FailoverJedisCluster cluster,final AbandonedConfig abandonedConfig) {
        super(poolConfig, new FailoverJedisFactory(cluster),abandonedConfig);
    }
   
    /**
     * do not cache me. call it directly when you use it. i.e. jedisPool.getJedis().saveOrUpdate("myKey", someSerializeType);
     * @return
     */
    public JedisFacade getJedis() {
        return getResource();
    }
} 