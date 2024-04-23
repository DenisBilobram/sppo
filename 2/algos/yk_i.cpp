#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>
#include <unordered_set>

using namespace std;

int main()
{
    int n, k, p;
    cin >> n >> k >> p;

    vector<queue<int>> next_use(n + 1);
    vector<int> requests(p);

    for (int i = 0; i < p; i++)
    {
        cin >> requests[i];
        next_use[requests[i]].push(i);
    }

    unordered_set<int> floor;
    priority_queue<pair<int, int>> farthest_next;

    int res = 0;

    for (int i = 0; i < p; i++)
    {
        int car = requests[i];

        next_use[car].pop();

        if (floor.find(car) != floor.end())
        {
            if (!next_use[car].empty())
            {
                int next_time = next_use[car].front();
                farthest_next.push({next_time, car});
            }
            else
            {
                farthest_next.push({p, car});
            }
            continue;
        }

        res++;
        if (floor.size() == k)
        {
            auto [next_time, to_remove] = farthest_next.top();
            farthest_next.pop();
            floor.erase(to_remove);
        }

        floor.insert(car);
        if (!next_use[car].empty())
        {
            int next_time = next_use[car].front();
            farthest_next.push({next_time, car});
        }
        else
        {
            farthest_next.push({p, car});
        }
    }

    cout << res;
}
