#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>

using namespace std;

#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

int main() {

    int n;
    cin >> n;
    vector<pair<int, int>> points(n); // x, y
    vector<pair<double, int>> angles(n-1); // angle, index

    cin >> points[0].first >> points[0].second;
    for (int i = 1; i < n; i++) {
        cin >> points[i].first >> points[i].second;
        angles[i-1].second = i;
        angles[i-1].first = atan2(points[i].second - points[0].second, points[i].first - points[0].first) * (180.0 / M_PI);

    }

    sort(angles.begin(), angles.end());

    
    for (int i = 0; i < n-1; i++) {
        int c = i;
        while (angles[i].first == angles[i+1].first) {
            i += 1;
        }
        if (i != c) {

            vector<pair<double, int>> dist; // dist, index

            for (int j = c; j <= i; ++j) {
                int index = angles[j].second;
                int dx = points[index].first - points[0].first;
                int dy = points[index].second - points[0].second;
                double distance = sqrt(dx*dx + dy*dy);
                dist.push_back(make_pair(distance, index));
            }

            sort(dist.begin(), dist.end());

            for (int j = c; j <= i; ++j) {
                angles[j].second = dist[j - c].second;
            }

        }
    }

    int start = 0;

    for (int i = 0; i < n-1; i++) {

        if (abs(angles[i].first - angles[i+1].first) >= 180) {
            start = i+1;
            break;
        }

    }

    cout << n << "\n1\n";
    for (int i = 0; i < n-1; i++) {
        int index = (start + i) % (n-1);
        cout << angles[index].second + 1 << "\n";
    }

}