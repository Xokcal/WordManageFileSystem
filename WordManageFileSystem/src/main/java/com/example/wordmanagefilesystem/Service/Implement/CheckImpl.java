package com.example.wordmanagefilesystem.Service.Implement;

import com.example.wordmanagefilesystem.Mapper.CheckMapper;
import com.example.wordmanagefilesystem.Pojo.Word;
import com.example.wordmanagefilesystem.Service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class CheckImpl implements CheckService{

    @Autowired
    private CheckMapper checkMapper;

    private static final Random r = new Random();

    //根据前端抽查次数获得随即单词
    public Word[] getRandomWordsByPageSize(Integer pageSize){
        Integer page = getRandomFinallyPage(pageSize);
        Integer pageReal = (page - 1) * pageSize;
        List<Word> words = getRandomPageWord(pageReal, pageSize);
        if (words.size() < pageSize){
            List<Word> words1 = addIfNotEnough(words , pageSize);
            return wordListToArr(words1);
        }
        Word[] wordsArr = wordListToArr(words);
        return fisherYatesAlgorithm(wordsArr);
    }

    //最后一页，不满则补全其他随机页数的单词
    private List<Word> addIfNotEnough(List<Word> words , Integer pageSize){
        //...
        Integer page = getRandomFinallyPage(pageSize);
        Integer pageReal = (page - 1) * pageSize;
        List<Word> ws = getRandomPageWord(pageReal, pageSize);
        Iterator<Word> iterator = ws.iterator();
        while (iterator.hasNext()){
            if (words.size() == pageSize)return words;
            words.add(iterator.next());
        }
        return words;
    }

    private Word[] fisherYatesAlgorithm(Word[] wordsArr){
        for (int i = wordsArr.length - 1; i > 0; i--) {
            int random = r.nextInt(i);
            Word t = wordsArr[random];
            wordsArr[random] = wordsArr[i];
            wordsArr[i] = t;
        }
        return wordsArr;
    }

    private Word[] wordListToArr(List<Word> list){
        Word[] wordArr = new Word[list.size()];
        int index = 0;
        for (Word word : list){
            wordArr[index] = word;
            index++;
        }
        return wordArr;
    }

    private List<Word> getRandomPageWord(Integer page , Integer pageSize){
        return checkMapper.getLimitWord(page, pageSize);
    }

    private Integer getRandomFinallyPage(Integer pageSize) {
        Integer wordCount = checkMapper.getWordCount();
        Integer totalPage = wordCount / pageSize;
        if (wordCount % pageSize != 0) totalPage++;
        return r.nextInt(totalPage) + 1;
    }
}
