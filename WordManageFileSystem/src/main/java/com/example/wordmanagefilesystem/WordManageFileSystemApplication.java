package com.example.wordmanagefilesystem;

import com.example.wordmanagefilesystem.Pojo.Word;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableScheduling
@SpringBootApplication
@ServletComponentScan
public class WordManageFileSystemApplication<T> {

    public static void main(String[] args) {
        SpringApplication.run(WordManageFileSystemApplication.class, args);
    }

}
