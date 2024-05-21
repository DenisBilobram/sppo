#include <iostream>
#include <vector>
#include <map>
#include <set>

using namespace std;

int main()
{
    int n;
    cin >> n;

    map<int, vector<int>> tree;
    map<int, bool> visited;
    int root = -1;

    int id, parent_id;
    for (int i = 0; i < n; i++)
    {
        cin >> id >> parent_id;
        visited[id] = false;
        if (parent_id == -1)
        {
            root = id;
        }
        else
        {
            tree[parent_id].push_back(id);
        }
    }

    map<int, int> time_in;
    map<int, int> time_out;
    vector<pair<int, bool>> stack;

    stack.push_back({root, true});
    int time = 0;

    while (!stack.empty())
    {
        int node = stack.back().first;
        bool entering = stack.back().second;
        stack.pop_back();

        time++;
        if (entering)
        {
            time_in[node] = time;
            stack.push_back({node, false});
            visited[node] = true;
            for (int child : tree[node])
            {
                if (!visited[child])
                {
                    stack.push_back({child, true});
                }
            }
        }
        else
        {
            time_out[node] = time;
        }
    }

    int l;
    cin >> l;
    int a, b;
    string result = "";

    for (int i = 0; i < l; i++)
    {
        cin >> a >> b;
        if (time_in[a] <= time_in[b] && time_out[a] >= time_out[b])
        {
            result += "1\n";
        }
        else if (time_in[b] <= time_in[a] && time_out[b] >= time_out[a])
        {
            result += "2\n";
        }
        else
        {
            result += "0\n";
        }
    }

    cout << result;

    return 0;
}
