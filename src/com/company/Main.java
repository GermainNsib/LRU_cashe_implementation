package com.company;


import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

public class LRUCache<K, V> {

    private final int capacity;
    private final DoublyLinkedList<K, V> cacheList;
    private final HashMap<K, Node<K, V>> cacheMap;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheList = new DoublyLinkedList<>();
        this.cacheMap = new HashMap<>();
    }

    public V get(K key) {
        Node<K, V> node = cacheMap.get(key);
        if (node == null) {
            return null;
        }

        moveToHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> existingNode = cacheMap.get(key);
        if (existingNode != null) {
            existingNode.value = value;
            moveToHead(existingNode);
            return;
        }

        Node<K, V> newNode = new Node<>(key, value);
        cacheList.addFirst(newNode);
        cacheMap.put(key, newNode);

        if (cacheList.size() > capacity) {
            removeLeastRecentlyUsed();
        }
    }

    private void moveToHead(Node<K, V> node) {
        cacheList.remove(node);
        cacheList.addFirst(node);
    }

    private void removeLeastRecentlyUsed() {
        Node<K, V> tail = cacheList.removeLast();
        cacheMap.remove(tail.key);
    }

    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class DoublyLinkedList<K, V> {

        private Node<K, V> head;
        private Node<K, V> tail;

        public void addFirst(Node<K, V> node) {
            if (isEmpty()) {
                head = tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
        }

        public void remove(Node<K, V> node) {
            if (node == head) {
                head = head.next;
            } else if (node == tail) {
                tail = tail.prev;
            }

            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }

        public Node<K, V> removeLast() {
            if (isEmpty()) {
                throw new IllegalStateException("List is empty");
            }

            Node<K, V> last = tail;
            remove(last);
            return last;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public int size() {
            int size = 0;
            Node<K, V> current = head;
            while (current != null) {
                size++;
                current = current.next;
            }
            return size;
        }
    }
    }
    }
