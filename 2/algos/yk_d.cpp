#include <iostream>
#include <string>


using namespace std;


int main() {

    int a, b, c, d, k;
    cin >> a >> b >> c >> d >> k;

    int now = a;
    int last;
    for (int i = 1; i <= k; i++) {
        last = now;
        now = now*b - c;
        if (now <= 0) {
            cout << 0;
            exit(0);
        } else if (now > d) {
            now = d;
        } 

        if (now == last) {
            cout << now;
            exit(0);
        }

    }
    cout << now;

}