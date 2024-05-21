#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#include <cmath>

using namespace std;

int main() {
    int n;
    cin >> n;

    vector<vector<int>> graph(n, vector<int>(n));

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            cin >> graph[i][j];
        }
    }


    vector<vector<int>> dp_max_fuel = vector(graph);
    
    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || i == k || j == k) continue;
                
                dp_max_fuel[i][j] = min(dp_max_fuel[i][j], max(dp_max_fuel[i][k], dp_max_fuel[k][j]));

            }
        }          
    }
    int m = 0;

    for (int i = 0; i < n; i++) {
        m = max(m, *max_element(dp_max_fuel[i].begin(), dp_max_fuel[i].end()));
    }

    cout << m;
}
