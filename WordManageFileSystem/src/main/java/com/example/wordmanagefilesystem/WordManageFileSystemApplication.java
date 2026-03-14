package com.example.wordmanagefilesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EnableScheduling
@SpringBootApplication
@ServletComponentScan
public class WordManageFileSystemApplication<T> {

    public static void main(String[] args) {
        SpringApplication.run(WordManageFileSystemApplication.class, args);
    }

}
