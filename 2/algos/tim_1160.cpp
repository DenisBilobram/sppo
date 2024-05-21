#include <iostream>
#include <vector>
#include <map>
#include <set>

using namespace std;

int main() {

    int n, m;
    cin >> n >> m;

    map<int, vector<pair<int, int>>> hubs; // hub, hub_to, length_to 

    for (int i = 1; i < n+1; i++) {
        hubs[i] = {};
    }

    int hub, hub_to, length;
    for (int i = 0; i < m; i++) {

        cin >> hub >> hub_to >> length;
        hubs[hub].push_back({hub_to, length});
        hubs[hub_to].push_back({hub, length});
    }

    vector<bool> is_connected(n+1, false);
    
    set<pair<int, pair<int, int>>> pool_of_borders; // length_to, hub, hub_to 
    is_connected[1] = true;
    for (auto border : hubs[1]) {
        pool_of_borders.insert({border.second, {1, border.first}});
    }

    int max_length = 0;
    vector<pair<int, int>> network;
    for (int i = 1; i < n; i++) {

        for (auto it = pool_of_borders.begin(); it != pool_of_borders.end(); ) {
            const auto hub_to = (*it).second.second;
            if (!is_connected[hub_to]) {
                is_connected[hub_to] = true;
                max_length = max(max_length, (*it).first);
                network.push_back({(*it).second.first, (*it).second.second});
                for (auto border : hubs[hub_to]) {
                    if (!is_connected[border.first]) {
                        pool_of_borders.insert({border.second, {hub_to, border.first}});
                    }
                }
            }
            it = pool_of_borders.erase(it);
        }

    }

    cout << max_length << "\n" << network.size() << "\n";
    for (auto el : network) {
        cout << el.first << " " << el.second << "\n";
    }

}