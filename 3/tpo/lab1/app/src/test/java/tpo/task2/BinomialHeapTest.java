package tpo.task2;

import org.junit.Test;
import static org.junit.Assert.*;

public class BinomialHeapTest {

    // Тест: поиск минимума в пустой куче должен возвращать null
    @Test
    public void testFindMinEmptyHeap() {
        BinomialHeap heap = new BinomialHeap();
        assertNull("При поиске минимума в пустой куче должен возвращаться null", heap.findMin());
    }

    // Тест: извлечение минимума из пустой кучи должно возвращать null
    @Test
    public void testExtractMinEmptyHeap() {
        BinomialHeap heap = new BinomialHeap();
        assertNull("Извлечение минимума из пустой кучи должно возвращать null", heap.extractMin());
    }

    // Тест: вставка одного элемента
    @Test
    public void testSingleInsert() {
        BinomialHeap heap = new BinomialHeap();
        heap.insert(42);
        assertEquals("После вставки единственного элемента, findMin должен возвращать его", Integer.valueOf(42), heap.findMin());
    }

    // Тест: несколько вставок, проверка findMin
    @Test
    public void testMultipleInsertsFindMin() {
        BinomialHeap heap = new BinomialHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        heap.insert(6);
        // Минимум среди [10, 3, 15, 6] равен 3
        assertEquals("Минимальный элемент должен быть равен 3", Integer.valueOf(3), heap.findMin());
    }

    // Тест: извлечение минимума и последовательное удаление всех элементов
    @Test
    public void testExtractMinSequence() {
        BinomialHeap heap = new BinomialHeap();
        int[] input = {20, 5, 15, 10, 2, 8};
        for (int key : input) {
            heap.insert(key);
        }

        int[] expectedOrder = {2, 5, 8, 10, 15, 20};
        for (int expected : expectedOrder) {
            Integer min = heap.findMin();
            assertNotNull("Минимальный элемент не должен быть null", min);
            assertEquals("Минимальный элемент не соответствует ожидаемому", expected, min.intValue());
            Integer extracted = heap.extractMin();
            assertEquals("Извлечённый элемент не соответствует ожидаемому", expected, extracted.intValue());
        }
        // После удаления всех элементов куча должна быть пуста
        assertNull("После извлечения всех элементов findMin должен возвращать null", heap.findMin());
        assertNull("После извлечения всех элементов extractMin должен возвращать null", heap.extractMin());
    }

    // Тест: объединение двух куч
    @Test
    public void testUnion() {
        BinomialHeap heap1 = new BinomialHeap();
        BinomialHeap heap2 = new BinomialHeap();

        // Заполним первую кучу
        heap1.insert(10);
        heap1.insert(4);
        heap1.insert(30);

        // Заполним вторую кучу
        heap2.insert(3);
        heap2.insert(25);
        heap2.insert(7);

        // Объединение куч
        BinomialHeap unionHeap = BinomialHeap.union(heap1, heap2);

        // Ожидаемый минимум: 3
        assertEquals("После объединения минимальный элемент должен быть равен 3", Integer.valueOf(3), unionHeap.findMin());

        // Проверка корректного извлечения всех элементов по возрастанию
        int[] expectedOrder = {3, 4, 7, 10, 25, 30};
        for (int expected : expectedOrder) {
            Integer min = unionHeap.extractMin();
            assertNotNull("Минимальный элемент не должен быть null", min);
            assertEquals("Извлечённый элемент не соответствует ожидаемому", expected, min.intValue());
        }
        assertNull("После извлечения всех элементов findMin должен возвращать null", unionHeap.findMin());
    }

    // Тест: вставка после извлечения минимального элемента
    @Test
    public void testInsertAfterExtractMin() {
        BinomialHeap heap = new BinomialHeap();
        heap.insert(12);
        heap.insert(7);
        heap.insert(20);

        // Извлекаем минимум (7)
        assertEquals("Минимальный элемент должен быть 7", Integer.valueOf(7), heap.extractMin());

        // Вставляем новый элемент, который меньше текущего минимума
        heap.insert(5);
        assertEquals("Минимальный элемент после вставки 5 должен быть равен 5", Integer.valueOf(5), heap.findMin());
    }

    // Тест: объединение пустой кучи с непустой
    @Test
    public void testUnionWithEmptyHeap() {
        BinomialHeap nonEmptyHeap = new BinomialHeap();
        nonEmptyHeap.insert(11);
        nonEmptyHeap.insert(3);

        BinomialHeap emptyHeap = new BinomialHeap();

        BinomialHeap result1 = BinomialHeap.union(nonEmptyHeap, emptyHeap);
        BinomialHeap result2 = BinomialHeap.union(emptyHeap, nonEmptyHeap);

        assertEquals("При объединении непустой кучи с пустой минимальный элемент должен быть равен 3", Integer.valueOf(3), result1.findMin());
        assertEquals("При объединении пустой кучи с непустой минимальный элемент должен быть равен 3", Integer.valueOf(3), result2.findMin());
    }
}

