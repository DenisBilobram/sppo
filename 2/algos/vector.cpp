
#include <iostream>
#include <cassert>

using namespace std;

template <typename T>
class vector
{
private:
    T *data;
    size_t capacity;
    size_t size;

    void expIfNeed() {
        if (size >= capacity) {
            size_t newCapacity = capacity == 0 ? 1 : 2 * capacity;
            T* newData = new T[newCapacity];
            for (size_t i = 0; i < size; ++i) {
                newData[i] = data[i];
            }
            delete[] data;
            data = newData;
            capacity = newCapacity;
        }
    }


public:
    vector() : data(nullptr), capacity(0), size(0) {}

    ~vector()
    {
        delete[] data;
    }

    class Iterator
    {
    private:
        T *ptr;

    public:
        Iterator(T *p) : ptr(p) {}

        Iterator &operator++()
        {
            ptr++;
            return *this;
        }

        T &operator*() const
        {
            return *ptr;
        }

        bool operator!=(const Iterator &other) const
        {
            return ptr != other.ptr;
        }
    };

    Iterator begin()
    {
        return Iterator(data);
    }

    Iterator end()
    {
        return Iterator(data + size);
    }

    void push_back(const T &value)
    {
        expIfNeed();
        data[size++] = value;
    }

    T &pop_back()
    {
        assert(size != 0);

        return data[--size];
    }

    T &operator[](size_t index)
    {
        assert(index < size);
        return data[index];
    }

    void insert(size_t index, const T& value) {
        assert(index <= size);

        expIfNeed();

        for (size_t i = size; i > index; --i) {
            data[i] = data[i - 1];
        }

        data[index] = value;

        ++size;
    }

    size_t getSize() const
    {
        return size;
    }
};

int main()
{

    vector<int> vec;

    for (int i = 0; i < 10; i++)
    {
        vec.push_back(i);
    }

    cout << vec.getSize() << "\n";

    int i = vec.pop_back();

    cout << i << "\n";

    for (vector<int>::Iterator it = vec.begin(); it != vec.end(); ++it)
    {
        cout << *it << " ";
    }

    cout << "\n";

    vec.insert(4, 10);

    for (vector<int>::Iterator it = vec.begin(); it != vec.end(); ++it)
    {
        cout << *it << " ";
    }
}