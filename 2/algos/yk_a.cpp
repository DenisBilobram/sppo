#include <iostream>
#include <vector>
using namespace std;

int main() {
    int n;
    cin >> n;
    vector<int> a(n);
    vector<int> borders;
    
    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }

    int start = 0;
    int end = 0;

    int m = 0;
    int m_c = 0;

    if (n == 1) {
        cout << 1 << " " << 1;
    } else if (n == 2) {
        cout << 1 << " " << 2;
    } else {
        borders.insert(borders.begin(), 1);
        for (int i = 1; i < n-1; i++) {
            if (a[i] == a[i-1] && a[i] == a[i+1]) {
                borders.push_back(i+1);
            }
        }
        borders.insert(borders.end(), n);

        for (int i = 0; i < borders.size()-1; i++) {

            m_c = borders[i+1] - borders[i] + 1;

            if (m_c > m) {
                m = m_c;
                start = borders[i];
                end = borders[i+1];
            }

        }

        cout << start << " " << end;
    }
}