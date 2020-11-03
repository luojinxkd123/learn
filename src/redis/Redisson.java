//package org.luojin.redisson.config.controller;
//
//import org.redisson.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//
///**
// * @author:luojin
// * @apiNote:
// * @since: 2020-11-02 10:46
// */
//@RestController
//public class Test {
//    @Autowired
//    private RedissonClient redissonClient;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @GetMapping("test")
//    public String test() {
//        RLock testLock = redissonClient.getLock("test");
//        testLock.lock();//阻塞式等待，拿到锁才执行下面的//有看门狗
//        //testLock.lock(10, TimeUnit.SECONDS);//没有看门狗
//        //问题：
//        //1、如果传递了锁的超时时间，就发送给redis执行脚本，进行占锁，默认超时就是我们指定的时间
//        //2、如果我们未指定锁的超时时间，就是用30秒【LockWatchDogTime看门狗的默认时间】
//        //    只要占锁成功，就会启动一个定时任务【重新给锁设置过期时间，新的过期时间就是看门狗的默认时间】
//        //      每隔 看门狗时间/3，续期一次
//        try {
//            System.out.println("加锁成功" + "\t" + Thread.currentThread().getId());
//            Thread.sleep(30000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            testLock.unlock();
//            System.out.println("释放锁" + "\t" + Thread.currentThread().getId());
//        }
//        return "test";
//    }
//
//    //改数据加加写锁，读数据加读锁
//    //保证一定能读到最新数据，修改期间，写锁是一个排他锁（互斥锁、独享锁）。读锁是一个共享锁
//    //写锁没释放读就必须等待
//    //读+读：并发读相当于无锁，会同时加锁成功
//    //写+读：等待锁释放
//    //写+写：阻塞方式
//    //读+写：有读锁，写也需要等待
//    //只要有写的存在，都必须等待
//    @GetMapping("write")
//    public String write() {
//        RReadWriteLock lock = redissonClient.getReadWriteLock("r-w-lock");
//        RLock rLock = lock.writeLock();
//        try {
//            rLock.lock();
//            Thread.sleep(30000);
//            stringRedisTemplate.opsForValue().set("r-w-value", UUID.randomUUID().toString());
//        } catch (Exception e) {
//
//        } finally {
//            rLock.unlock();
//        }
//        return "写入成功";
//    }
//
//    @GetMapping("read")
//    public String read() {
//        RReadWriteLock lock = redissonClient.getReadWriteLock("r-w-lock");
//        RLock rLock = lock.readLock();
//        String s = "";
//        try {
//            rLock.lock();
//            s = stringRedisTemplate.opsForValue().get("r-w-value");
//            Thread.sleep(30000);
//        } catch (Exception e) {
//
//        } finally {
//            rLock.unlock();
//            return s;
//        }
//    }
//
//    //CountDownLunch：闭锁，计数
//    @GetMapping("lockDoor")
//    public String lockDoor() throws InterruptedException {
//        RCountDownLatch door = redissonClient.getCountDownLatch("door");
//        door.trySetCount(5);//等待五个班走完
//        door.await();//等待闭锁完成
//
//        return "走完了";
//    }
//
//    @GetMapping("go/{id}")
//    public String go(@PathVariable Integer id) {
//        RCountDownLatch door = redissonClient.getCountDownLatch("door");
//        door.countDown();//走一个班，计数减一
//        return id + "走完了";
//    }
//
//    //Semaphore:信号量，当空间满了则进入等待或者占用失败，可以作为分布式限流
//    @GetMapping("put")
//    public String put() {
//        RSemaphore container = redissonClient.getSemaphore("box");
//        //container.acquire();//获取一个信号，占用一个空间
//        boolean b = container.tryAcquire(1);//不阻塞
//        if (b) {
//            return "占用一个空间";
//        } else {
//            return "占用一个空间失败";
//        }
//    }
//
//    @GetMapping("release")
//    public String release() {
//        RSemaphore container = redissonClient.getSemaphore("box");
//        container.release();
//        return "释放一个空间";
//    }
//    //缓存一致性问题-解决方案
//    //1、双写模式2、失效模式
//    //这两种模式都会导致缓存不一致问题，
//    //1、如果时用户维度数据，这种并发并发几率非常校，不用考虑这个问题，缓存数据加上过期时间
//    // ，每隔一段时间触发读的主动更新即可。
//    //2、如果是基础数据，也可以去使用canal订阅binlog的方式。
//    //3、缓存数据+过期时间也足够解决大部署业务对于缓存的要求。
//    //4、通过加锁保证并发读写，写写的时候按照顺序排好队，读读无所谓，所以适合使用读写锁
//    // （业务不关心脏数据，允许临时脏数据可忽略）
//
//
//}
