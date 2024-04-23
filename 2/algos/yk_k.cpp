#include <vector>
#include <iostream>
#include <map>
#include <algorithm>

using namespace std;

void mergeBlocks(vector<pair<int, int>> &free, int index)
{

    if (index > 0 && free[index - 1].second + 1 == free[index].first)
    {
        free[index - 1].second = free[index].second;
        free.erase(free.begin() + index);
        index--;
    }

    if (index < free.size() - 1 && free[index].second + 1 == free[index + 1].first)
    {
        free[index].second = free[index + 1].second;
        free.erase(free.begin() + index + 1);
    }

    mergeBlocks(free, index);
}

int main()
{

    ios::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    int n, m;
    cin >> n >> m;
    int rej = -1;

    map<int, pair<int, int>> requests;
    vector<pair<int, int>> free;
    free.push_back({1, n});

    int req;
    for (int i = 1; i < m + 1; i++)
    {
        cin >> req;
        if (req > 0)
        {
            bool alloc = false;
            for (int j = 0; j < free.size(); j++)
            {
                if (free[j].second - free[j].first + 1 >= req)
                {
                    int start = free[j].first;
                    int end = free[j].first + req - 1;
                    requests[i] = {start, end};
                    if (free[j].second != end)
                    {
                        free[j].first = end + 1;
                    }
                    else
                    {
                        free.erase(free.begin() + j);
                    }
                    alloc = true;
                    cout << start << "\n";
                    break;
                }
            }

            if (!alloc)
            {
                cout << rej << "\n";
            }
        }
        else
        {
            if (requests.find(abs(req)) == requests.end())
            {
                continue;
            }
            auto bounds = requests[abs(req)];
            int last_i = -1;
            for (int i = 0; i < free.size(); i++)
            {
                if (free[i].first > bounds.second)
                {
                    last_i = i;
                    break;
                }
            }
            if (last_i == -1)
            {
                if (!free.empty() && free.back().second == bounds.first - 1)
                {
                    free.back().second = bounds.second;
                    mergeBlocks(free, free.size() - 1);
                }
                else
                {
                    free.push_back(bounds);
                    last_i = free.size() - 1;
                    mergeBlocks(free, last_i);
                }
            }
            else
            {
                free.insert(free.begin() + last_i, bounds);
                mergeBlocks(free, last_i);
            }
        }
    }
}