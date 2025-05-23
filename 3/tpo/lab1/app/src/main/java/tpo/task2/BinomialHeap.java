package tpo.task2;

public class BinomialHeap {
    private Node head; // Голова списка биномиальных деревьев

    // Внутренний класс, описывающий узел биномиального дерева
    private static class Node {
        int key;
        int degree;
        Node parent;
        Node child;
        Node sibling;

        public Node(int key) {
            this.key = key;
            this.degree = 0;
            this.parent = null;
            this.child = null;
            this.sibling = null;
        }
    }

    public BinomialHeap() {
        head = null;
    }

    // Вставка нового элемента в биномиальную кучу
    public void insert(int key) {
        BinomialHeap tempHeap = new BinomialHeap();
        tempHeap.head = new Node(key);
        this.head = union(this, tempHeap).head;
    }

    // Объединение двух биномиальных куч
    public static BinomialHeap union(BinomialHeap h1, BinomialHeap h2) {
        BinomialHeap newHeap = new BinomialHeap();
        newHeap.head = merge(h1.head, h2.head);
        if (newHeap.head == null) {
            return newHeap;
        }

        Node prev = null;
        Node curr = newHeap.head;
        Node next = curr.sibling;

        while (next != null) {
            // Если степень текущего дерева не равна степени следующего,
            // или (следующий и следующий за ним имеют равные степени)
            if (curr.degree != next.degree ||
                    (next.sibling != null)) {
                prev = curr;
                curr = next;
            } else {
                // Если ключ текущего корня меньше или равен ключу следующего,
                // следующий становится поддеревом текущего
                if (curr.key <= next.key) {
                    curr.sibling = next.sibling;
                    linkTrees(curr, next);
                } else {
                    // Иначе, если предыдущий существует, он должен указывать на next,
                    // а текущий становится поддеревом next

                    newHeap.head = next;

                    linkTrees(next, curr);
                    curr = next;
                }
            }
            next = curr.sibling;
        }
        return newHeap;
    }

    // Метод, объединяющий два списка корней отсортированных по степени
    private static Node merge(Node h1, Node h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        Node head;
        Node tail;
        if (h1.degree <= h2.degree) {
            head = h1;
            h1 = h1.sibling;
        } else {
            head = h2;
            h2 = h2.sibling;
        }
        tail = head;

        while (h1 != null && h2 != null) {
            if (h1.degree <= h2.degree) {
                tail.sibling = h1;
                h1 = h1.sibling;
            } else {
                tail.sibling = h2;
                h2 = h2.sibling;
            }
            tail = tail.sibling;
        }
        tail.sibling = (h1 != null) ? h1 : h2;
        return head;
    }

    // Метод связывания двух биномиальных деревьев одного порядка:
    // делаем меньший по ключу корнем, а другой - его поддеревом
    private static void linkTrees(Node minTree, Node other) {
        other.parent = minTree;
        other.sibling = minTree.child;
        minTree.child = other;
        minTree.degree++;
    }

    // Поиск минимального элемента в куче
    public Integer findMin() {
        if (head == null) {
            return null;
        }
        int min = head.key;
        Node curr = head;
        while (curr != null) {
            if (curr.key < min) {
                min = curr.key;
            }
            curr = curr.sibling;
        }
        return min;
    }

    // Извлечение минимального элемента и перестройка кучи
    public Integer extractMin() {
        if (head == null) {
            return null;
        }
        // Находим минимальный узел и его предыдущий узел
        Node minNode = head;
        Node minNodePrev = null;
        Node curr = head;
        Node prev = null;
        while (curr != null) {
            if (curr.key < minNode.key) {
                minNode = curr;
                minNodePrev = prev;
            }
            prev = curr;
            curr = curr.sibling;
        }

        // Удаляем minNode из корневого списка
        if (minNodePrev == null) {
            head = minNode.sibling;
        } else {
            minNodePrev.sibling = minNode.sibling;
        }

        // Переворачиваем список дочерних узлов minNode
        Node child = minNode.child;
        Node newHead = null;
        while (child != null) {
            Node next = child.sibling;
            child.sibling = newHead;
            child.parent = null;
            newHead = child;
            child = next;
        }

        // Объединяем оставшуюся кучу с кучей, состоящей из перевёрнутых детей
        BinomialHeap tempHeap = new BinomialHeap();
        tempHeap.head = newHead;
        BinomialHeap newHeap = union(this, tempHeap);
        this.head = newHeap.head;

        return minNode.key;
    }

}
