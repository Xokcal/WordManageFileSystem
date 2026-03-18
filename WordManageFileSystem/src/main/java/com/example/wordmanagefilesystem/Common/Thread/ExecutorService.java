package com.example.wordmanagefilesystem.Common.Thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component
public class ExecutorService {

    //服务器线程数
    private static final int THREAD_NUM = Runtime.getRuntime().availableProcessors();

    //公共线程池
    public static final java.util.concurrent.ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(THREAD_NUM);

}
