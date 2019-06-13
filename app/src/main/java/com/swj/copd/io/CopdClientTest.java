//package com.swj.copd.io;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//public class CopdClientTest {
//
//    private static final int POOL_SIZE_SEND = 100;
//
//    /**
//     * @param args
//     * @throws InterruptedException
//     */
//    public static void main(String[] args) throws InterruptedException {
//
//        Executor executor = Executors.newFixedThreadPool(POOL_SIZE_SEND);
//        for (int i = 0; i < POOL_SIZE_SEND; i++) {
//            executor.execute(new ClientTask());
//            Thread.sleep(100);
//        }
//    }
//
//}