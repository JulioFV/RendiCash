package com.ingjcfv.rendicash11.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseExecutor {
    public static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(4);
}
