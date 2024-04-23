#include <vector>
#include <iostream>
#include <map>
#include <algorithm>

using namespace std;

void mergeBlocks(vector<pair<int, int>>& free, int index) {
    if (index > 0 && free[index-1].second + 1 >= free[index].first) {
        free[index-1].second = max(free[index-1].second, free[index].second);
        free.erase(free.begin() + index);
        index--;
    }
    
    if (index < free.size() - 1 && free[index].second + 1 >= free[index+1].first) {
        free[index].second = max(free[index].second, free[index+1].second);
        free.erase(free.begin() + index + 1);
    }
}

int main() {
    int n, m;
    cin >> n >> m;
    int rej = -1;
    
    map<int, pair<int, int>> requests;
    vector<pair<int, int>> free;
    free.push_back({1, n});

    int req;
    for (int i = 1; i <= m; i++) {
        cin >> req;
        if (req > 0) {
            bool alloc = false; 
            for (int j = 0; j < free.size(); j++) {
                if (free[j].second - free[j].first + 1 >= req) {
                    int start = free[j].first;
                    int end = start + req - 1;
                    requests[i] = {start, end};
                    if (free[j].second == end) {
                        free.erase(free.begin() + j);
                    } else {
                        free[j].first = end + 1;
                    }
                    alloc = true;
                    cout << start << "\n";
                    break;
                }
            }
            if (!alloc) {
                cout << rej << "\n";
            }
        } else {
            int t = abs(req);
            if (requests.find(t) == requests.end()) {
                continue;
            }
            auto bounds = requests[t];
            auto it = lower_bound(free.begin(), free.end(), make_pair(bounds.first, 0),
                                  [](const pair<int, int>& a, const pair<int, int>& b) {
                                      return a.first < b.first;
                                  });
            if (it != free.begin() && (it - 1)->second + 1 == bounds.first) {
                --it;
            }
            it = free.insert(it, bounds);
            mergeBlocks(free, it - free.begin());
        }
    }
}