package com.example.wordmanagefilesystem.MyList.HandCreateList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("all")
public class XokcalList<T> {
    private ListLinkTable<T> head;
    private int size = 0;

    private static final String INDEX_INVALID = "索引不合法";
    private static final String INDEX_OUT_BOUND_ERROR = "索引超出范围！";

    // 得到大小
    public int size() {
        return this.size;
    }

    // 添加数据
    public void add(T data) {
        ListLinkTable<T> node = new ListLinkTable<>(data, null);
        ListLinkTable<T> h = head;
        if (head == null) {
            head = node;
        } else {
            while (h.getNext() != null) {
                h = h.getNext();
            }
            h.setNext(node);
        }
        size++;
    }

    // 打印集合
    public void print() {
        if(isEmpty()){
            System.out.println("[]");
            return;
        }
        ListLinkTable<T> h = head;
        while (h.getNext() != null) {
            System.out.print(h.getData() + "->");
            h = h.getNext();
        }
        System.out.println(h.getData()+"->null");
    }

    //头插法
    public void addHead(T data){
        ListLinkTable<T> newNode = new ListLinkTable<>(data , head);
        head = newNode;
        size++;
    }

    //倒转链表
    public void reverse(){
        ListLinkTable<T> tail = null;
        ListLinkTable<T> h = head;
        while (h != null) {
            ListLinkTable<T> next = h.getNext();
            h.setNext(tail);
            tail = h;
            h = next;
        }
        head = tail;
    }

    //通过索引获得数据
    public T getIndex(int index){
        if (index < 0 ||index >= size) {
            System.out.println(INDEX_INVALID);
            throw new IndexOutOfBoundsException(INDEX_OUT_BOUND_ERROR);
        }
        int i = 0;
        ListLinkTable<T> h = head;
        while (h != null) {
            if (i == index) {
                return h.getData();
            }
            h = h.getNext();
            i++;
        }
        return null;
    }
    // 添加了isEmpty，contains，remove，toList，addAll等方法
    //判断空
    public boolean isEmpty(){
        return size == 0;
    }

    //是否包含
    public boolean contains(Object o){
        if(o == null){
            return false;
        }
        ListLinkTable<T> h = head;
        while (h != null) {
            if(o.equals(h.getData())){
                return true;
            }
            h = h.getNext();
        }
        return false;
    }

    //通过索引移除
    public T remove(int index){ //2
        if (index < 0 ||index >= size) {
            System.out.println(INDEX_INVALID);
            throw new IndexOutOfBoundsException(INDEX_OUT_BOUND_ERROR);
        }
        int i = 0;
        ListLinkTable<T> h = head;
        ListLinkTable<T> up = null;
        T t = null;
        while(h != null){
            if(i == index){
                if(i == 0){
                    head = h.getNext();
                    size--;
                    return h.getData();
                }
                t = up.getNext().getData();
                up.setNext(up.getNext().getNext());
                size--;
                return t;
            }
            up = h;
            h = h.getNext();
            i++;
        }
        return null;
    }

    //通过指定数据移除
    public boolean  remove(Object o){
        if(o == null){
            return false;
        }
        int i = 0;
        ListLinkTable<T> h = head;
        ListLinkTable<T> up = null;
        while(h != null){
            if(o.equals(h.getData())){
                if(i == 0){
                    head = head.getNext();
                    size--;
                    return true;
                }
                up.setNext(h.getNext());
                size--;
                return true;
            }
            up = h;
            h = h.getNext();
            i++;
        }
        return false;
    }

    //转为官方List
    public List<T> toList(){
        if(isEmpty()){
            return null;
        }
        List<T> list = new ArrayList<>();
        ListLinkTable<T> h = head;
        while(h != null){
            list.add(h.getData());
            h = h.getNext();
        }
        return list;
    }

    //添加其他集合数据
    public boolean addAll(Collection<? extends T> c){
        if(c.isEmpty() || isEmpty()){
            return false;
        }
        ListLinkTable<T> h = head;
        while(h.getNext() != null){
            h = h.getNext();
        }
        for(T t : c){
            ListLinkTable n = new ListLinkTable<>(t , null);
            h.setNext(n);
            h = h.getNext();
            size++;
        }
        return true;
    }

}
