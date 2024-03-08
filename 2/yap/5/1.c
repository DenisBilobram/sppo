#include <stdio.h>

#define print_var(x) printf(#x " is %d\n", x)
#define b 20

int main() {
    int a = 10;
    print_var(a); // Выведет "a is 10"
    print_var(b); // Выведет "b is 20"
    print_var(42); // Выведет "42 is 42"

    return 0;
}