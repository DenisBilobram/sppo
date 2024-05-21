#include <iostream>
#include <map>
#include <vector>
#include <queue>

using namespace std;

int main()
{

    int n, m;
    cin >> n >> m;

    map<int, vector<int>> graph;

    int first, second;
    for (int i = 0; i < m; i++)
    {
        cin >> first >> second;
        if (first == second)
        {
            cout << "NO";
            return 0;
        }
        if (graph.find(first) != graph.end())
        {
            graph[first].push_back(second);
        }
        else
        {
            graph[first] = {second};
        }
        if (graph.find(second) != graph.end())
        {
            graph[second].push_back(first);
        }
        else
        {
            graph[second] = {first};
        }
    }

    for (int i = 1; i < n + 1; i++)
    {
        vector<int> color(n + 1);
        queue<pair<int, int>> queue;
        vector<int> visited(n + 1);
        queue.push({i, -1});

        while (!queue.empty())
        {
            int vertex = queue.front().first;
            int current_id = -queue.front().second;
            queue.pop();
            visited[vertex] = true;

            for (int conn_vert : graph[vertex])
            {
                if (color[conn_vert] == current_id)
                {
                    cout << "NO";
                    return 0;
                }
            }

            if (color[vertex] == 0)
            {
                color[vertex] = current_id;
            }
            for (int conn_vert : graph[vertex])
            {
                if (!visited[conn_vert])
                {
                    queue.push({conn_vert, current_id});
                }
            }
        }
    }

    cout << "YES";
}