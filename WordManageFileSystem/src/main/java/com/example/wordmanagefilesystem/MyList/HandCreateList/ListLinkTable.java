package com.example.wordmanagefilesystem.MyList.HandCreateList;

@SuppressWarnings("all")
class ListLinkTable<T> {
    private T data;
    private ListLinkTable<T> next;

    public ListLinkTable(T data , ListLinkTable<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public ListLinkTable<T> getNext() {
        return next;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNext(ListLinkTable<T> next) {
        this.next = next;
    }

    public ListLinkTable(){}
}