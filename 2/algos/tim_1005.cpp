#include <iostream>
#include <vector>

using namespace std;

int main() {
    
    int n;
    cin >> n;
    vector<int> weights(n);
    int total = 0;
    for (int i = 0; i < n; i++) {
        cin >> weights[i];
        total += weights[i]; 
    }

    int min_diff = total;
    for (int bit_mask = 0; bit_mask < (1 << n); bit_mask++) {
        int current = 0;
        for (int i = 0; i < n; i++) {
            if (bit_mask & (1 << i)) {
                current += weights[i];
            }
        }
        int diff = abs(total - current*2);
        min_diff = min(min_diff, diff);
    }
    cout << min_diff;
}