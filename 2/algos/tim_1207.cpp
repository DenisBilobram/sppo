#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>

#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

using namespace std;
int main() {
    int n;
    cin >> n;

    vector<pair<long long, long long>> points(n);
    vector<pair<long double, int>> angles(n); // angle, index

    for (int i = 0; i < n; i++) {
        cin >> points[i].first >> points[i].second;
    }

    pair<int, int> base_one = points[0];
    int base_one_index = 0;
    for (int i = 0; i < n; i++) {
        if (points[i].second < base_one.second || (points[i].second == base_one.second && points[i].first < base_one.first)) {
            base_one = points[i];
            base_one_index = i;
        }
    }

    for (int i = 0; i < n; i++) {
        angles[i].second = i;
        angles[i].first = atan2(points[i].second - base_one.second, points[i].first - base_one.first) * (180.0 / M_PI);

    }

    angles.erase(angles.begin()+base_one_index);

    sort(angles.begin(), angles.end());

    cout << base_one_index + 1 << " " << angles[(n-1)/2].second + 1;

}