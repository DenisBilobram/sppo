package tpo.task2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinomialHeapTest {
    private BinomialHeap heap;

    // Инициализация перед каждым тестом
    @BeforeEach
    public void setUp() {
        heap = new BinomialHeap();
    }

    // Тест вставки элементов
    @Test
    public void testInsert() {
        heap.insert(5);
        heap.insert(3);
        heap.insert(7);
        assertEquals(Integer.valueOf(3), heap.findMin());
    }

    // Тест вставки дубликатов
    @Test
    public void testInsertDuplicates() {
        heap.insert(5);
        heap.insert(5);
        assertEquals(Integer.valueOf(5), heap.findMin());
        assertEquals(Integer.valueOf(5), heap.extractMin());
        assertEquals(Integer.valueOf(5), heap.findMin());
    }

    // Тест объединения двух куч
    @Test
    public void testUnion() {
        BinomialHeap h1 = new BinomialHeap();
        h1.insert(1);
        h1.insert(4);
        BinomialHeap h2 = new BinomialHeap();
        h2.insert(2);
        h2.insert(3);
        BinomialHeap unionHeap = BinomialHeap.union(h1, h2);
        assertEquals(Integer.valueOf(1), unionHeap.findMin());
    }

    // Тест объединения с пустой кучей
    @Test
    public void testUnionWithEmpty() {
        BinomialHeap h1 = new BinomialHeap();
        h1.insert(1);
        BinomialHeap h2 = new BinomialHeap();
        BinomialHeap unionHeap = BinomialHeap.union(h1, h2);
        assertEquals(Integer.valueOf(1), unionHeap.findMin());
    }

    // Тест поиска минимума
    @Test
    public void testFindMin() {
        // Пустая куча
        assertNull(heap.findMin());
        // Куча с одним элементом
        heap.insert(10);
        assertEquals(Integer.valueOf(10), heap.findMin());
        // Куча с несколькими элементами
        heap.insert(5);
        assertEquals(Integer.valueOf(5), heap.findMin());
    }

    // Тест извлечения минимума
    @Test
    public void testExtractMin() {
        // Пустая куча
        assertNull(heap.extractMin());
        // Куча с одним элементом
        heap.insert(10);
        assertEquals(Integer.valueOf(10), heap.extractMin());
        assertNull(heap.findMin());
        // Куча с несколькими элементами
        heap.insert(5);
        heap.insert(3);
        heap.insert(7);
        assertEquals(Integer.valueOf(3), heap.extractMin());
        assertEquals(Integer.valueOf(5), heap.findMin());
    }

    // Тест порядка извлечения элементов
    @Test
    public void testExtractMinOrder() {
        heap.insert(4);
        heap.insert(2);
        heap.insert(6);
        heap.insert(1);
        heap.insert(3);
        assertEquals(Integer.valueOf(1), heap.extractMin());
        assertEquals(Integer.valueOf(2), heap.extractMin());
        assertEquals(Integer.valueOf(3), heap.extractMin());
        assertEquals(Integer.valueOf(4), heap.extractMin());
        assertEquals(Integer.valueOf(6), heap.extractMin());
        assertNull(heap.findMin());
    }

    @Test
    public void testUnionDifferentDegrees() {
        BinomialHeap h1 = new BinomialHeap();
        h1.insert(1); // Дерево степени 0
        BinomialHeap h2 = new BinomialHeap();
        h2.insert(2);
        h2.insert(3); // Дерево степени 1 после объединения двух элементов
        BinomialHeap unionHeap = BinomialHeap.union(h1, h2);
        assertEquals(Integer.valueOf(1), unionHeap.findMin());
    }

    @Test
    public void testUnionDifferentDegrees2() {
        BinomialHeap h1 = new BinomialHeap();
        h1.insert(1); // Дерево степени 0
        h1.insert(6); // Дерево степени 0
        h1.insert(5); // Дерево степени 0
        BinomialHeap h2 = new BinomialHeap();
        BinomialHeap unionHeap = BinomialHeap.union(h1, h2);
        assertEquals(Integer.valueOf(1), unionHeap.findMin());
    }

    @Test
    public void testUnionSameDegrees() {
        BinomialHeap h1 = new BinomialHeap();
        h1.insert(4);
        h1.insert(5); // Дерево степени 1
        BinomialHeap h2 = new BinomialHeap();
        h2.insert(2);
        h2.insert(3); // Дерево степени 1
        BinomialHeap unionHeap = BinomialHeap.union(h1, h2);
        assertEquals(Integer.valueOf(2), unionHeap.findMin());
    }

    @Test
    public void testUnionMultipleSameDegrees() {
        BinomialHeap h1 = new BinomialHeap();
        h1.insert(10);
        h1.insert(20);
        h1.insert(30); // Деревья степеней 0 и 1
        BinomialHeap h2 = new BinomialHeap();
        h2.insert(15);
        h2.insert(25); // Дерево степени 1
        BinomialHeap unionHeap = BinomialHeap.union(h1, h2);
        assertEquals(Integer.valueOf(10), unionHeap.findMin());
    }

    @Test
    public void testUnion_SkipBlockScenario() {
        // Создаём первую кучу h1, где будет 2 отдельных корня degree=0 (вставляем 2 раза):
        BinomialHeap h1 = new BinomialHeap();
        h1.insert(10);  // первый узел (degree=0)
        h1.insert(20);  // второй узел (degree=0)
        // В h1 теперь 2 корня, каждый degree=0, не связаны друг с другом.

        BinomialHeap h2 = new BinomialHeap();
        h2.insert(30);
        h2.insert(40);

        BinomialHeap result = BinomialHeap.union(h1, h2);

        assertEquals(10, (int) result.findMin());
    }

}
