package RandomTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class randomTest1 {
    public static void main(String[] args) {
        List<String> l = new ArrayList<>();
        l.add("a");
        l.add("b");
        l.add("c");
        Iterator<String> iterator = l.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        l.forEach(e ->{
            System.out.println(e);
        });
        System.out.println("===========");
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
