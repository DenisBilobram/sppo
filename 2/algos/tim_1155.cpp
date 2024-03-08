#include <iostream>
using namespace std;

int main() {

    int peaks[8];
    int sum = 0;

    for (int i = 0; i < 8; i++) {
        cin >> peaks[i];
        sum += peaks[i];
    }
    
    if (sum % 2 != 0) {
        cout << "IMPOSSIBLE";
        exit(1);
    }

    
}