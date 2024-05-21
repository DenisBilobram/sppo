#include <iostream>
#include <vector>
#include <set>
#include <algorithm>

using namespace std;

int main()
{
    int n;
    cin >> n;

    vector<vector<int>> graph(n + 1);
    for (int i = 1; i <= n; ++i)
    {
        int keyLocation;
        cin >> keyLocation;
        graph[keyLocation].push_back(i);
    }

    set<set<int>> components;

    for (int i = 1; i <= n; ++i)
    {
        vector<bool> visited(n + 1, false);
        set<int> new_component;
        vector<int> stack;
        stack.push_back(i);
        while (!stack.empty())
        {
            int vertex = stack.back();
            stack.pop_back();
            if (!visited[vertex])
            {
                new_component.insert(vertex);
                visited[vertex] = true;
                for (int connected_vertex : graph[vertex])
                {
                    if (!visited[connected_vertex])
                    {
                        stack.push_back(connected_vertex);
                    }
                }
            }
        }

        bool uniq = true;
        for (auto it = components.begin(); it != components.end();)
        {
            const set<int> &comp = *it;
            if (includes(comp.begin(), comp.end(), new_component.begin(), new_component.end()))
            {
                uniq = false;
                break;
            }
            if (includes(new_component.begin(), new_component.end(), comp.begin(), comp.end()))
            {
                it = components.erase(it);
                components.insert(new_component);
                uniq = false;
            }
            else
            {
                ++it;
            }
        }
        if (uniq)
        {
            components.insert(new_component);
        }
    }

    cout << components.size() << endl;

    return 0;
}