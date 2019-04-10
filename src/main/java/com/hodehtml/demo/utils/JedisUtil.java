package com.hodehtml.demo.utils;


import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.util.SafeEncoder;

/**
 * @author Mr.hu
 * @version crateTime：2013-10-30 下午5:41:30
 * Class Explain:JedisUtil
 */
@Component
public class JedisUtil {

    private static final String IP = "127.0.0.1"; // ip
    private static final int PORT = 6379;         // 端口
    private static final String AUTH = "";          // 密码(原始默认是没有密码)
    private static int MAX_ACTIVE = 1024;       // 最大连接数
    private static int MAX_IDLE = 200;          // 设置最大空闲数
    private static int MAX_WAIT = 10000;        // 最大连接时间
    private static int TIMEOUT = 10000;         // 超时时间
    private static boolean BORROW = true;         // 在borrow一个事例时是否提前进行validate操作
    private static JedisPool pool = null;
    private static Logger logger = Logger.getLogger(JedisUtil.class);

    /**
     * 初始化线程池
     */
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(BORROW);
        pool = new JedisPool(config, IP, PORT, TIMEOUT);
    }


    /**
     * 获取连接
     */
    public static synchronized Jedis getJedis() {
        try {
            if (pool != null) {
                return pool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.info("连接池连接异常");
            return null;
        }

    }

    /**
     * @param @param  key
     * @param @param  seconds
     * @param @return
     * @return boolean 返回类型
     * @Description:设置失效时间
     */
    public static void disableTime(String key,String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            /*jedis.expire(key, seconds);*/
            jedis.set(key,value,"NX", "EX",seconds);
        } catch (Exception e) {
            logger.debug("设置失效失败.");
        } finally {
            getColse(jedis);
        }
    }


    /**
     * @param @param  key
     * @param @param  obj
     * @param @return
     * @return boolean 返回类型
     * @Description:插入对象
     */
    public static boolean addObject(String key, Object obj) {

        Jedis jedis = null;
        String value = JSONObject.toJSONString(obj);
        try {
            jedis = getJedis();
            String code = jedis.set(key, value);
            if (code.equals("ok")) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("插入数据有异常.");
            return false;
        } finally {
            getColse(jedis);
        }
        return false;
    }


    /**
     * @param @param key
     * @param @param value
     * @return void 返回类型
     * @Description:存储key~value
     */

    public static boolean addValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String code = jedis.set(key, value);
            if (code.equals("ok")) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("插入数据有异常.");
            return false;
        } finally {
            getColse(jedis);
        }
        return false;
    }

    /**
     * @param @param  key
     * @param @return
     * @return boolean 返回类型
     * @Description:删除key
     */
    public static boolean delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long code = jedis.del(key);
            if (code > 1) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("删除key异常.");
            return false;
        } finally {
            getColse(jedis);
        }
        return false;
    }


    public static boolean existsKey(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            boolean code = jedis.exists(key);
            if (code == true) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("查找key异常.");
            return false;
        } finally {
            getColse(jedis);
        }
        return false;
    }

    /**
     * @param @param jedis
     * @return void 返回类型
     * @Description: 关闭连接
     */

    public static void getColse(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
