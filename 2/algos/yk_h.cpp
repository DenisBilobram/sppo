#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <unordered_map>

using namespace std;

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> prices(n);

    for (int i = 0; i < n; i++) {
        cin >> prices[i];
    }

    sort(prices.rbegin(), prices.rend());

    int sum = 0;
    for (int el : prices) {
        sum += el;
    }

    for (int i = k-1; i < n; i += k) {
        sum -= prices[i];
    }
    
    cout << sum;
}