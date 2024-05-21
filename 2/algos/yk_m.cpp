#include <iostream>
#include <queue>
#include <vector>
#include <map>
#include <cmath>
#include <string>

using namespace std;

int main()
{

    int n, m, x_start, y_start, x_finish, y_finish;

    cin >> n >> m >> x_start >> y_start >> x_finish >> y_finish;

    vector<vector<float>> graph(n, vector<float>(m));
    vector<vector<float>> dist(n, vector<float>(m, INFINITY));
    vector<vector<float>> visited(n, vector<float>(m, false));
    vector<vector<pair<int, int>>> path(n, vector<pair<int, int>>(m, {-1, -1}));

    map<char, float> field_types = {{'.', 1}, {'W', 2}, {'#', INFINITY}};

    string str;
    for (int i = 0; i < n; i++)
    {
        cin >> str;
        for (int j = 0; j < m; j++)
        {
            graph[i][j] = field_types.at(str[j]);
        }
    }

    priority_queue<pair<float, pair<int, int>>, vector<pair<float, pair<int, int>>>, greater<pair<float, pair<int, int>>>> vertex_to_take;
    dist[x_start - 1][y_start - 1] = 0;

    vertex_to_take.push({dist[x_start - 1][y_start - 1], {x_start - 1, y_start - 1}});

    while (!vertex_to_take.empty())
    {
        pair<int, int> vertex = vertex_to_take.top().second;
        vertex_to_take.pop();
        visited[vertex.first][vertex.second] = true;

        vector<pair<int, int>> connected;
        if (vertex.first > 0)
        {
            connected.push_back({vertex.first - 1, vertex.second});
        }
        if (vertex.first < n - 1)
        {
            connected.push_back({vertex.first + 1, vertex.second});
        }
        if (vertex.second > 0)
        {
            connected.push_back({vertex.first, vertex.second - 1});
        }
        if (vertex.second < m - 1)
        {
            connected.push_back({vertex.first, vertex.second + 1});
        }

        float new_dist;
        for (auto connected_vert : connected)
        {
            new_dist = dist[vertex.first][vertex.second] + graph[connected_vert.first][connected_vert.second];
            if (!visited[connected_vert.first][connected_vert.second] &&
                dist[connected_vert.first][connected_vert.second] > new_dist)
            {
                dist[connected_vert.first][connected_vert.second] = new_dist;
                vertex_to_take.push({new_dist, {connected_vert.first, connected_vert.second}});
                path[connected_vert.first][connected_vert.second] = vertex;
            }
        }
    }

    map<pair<int, int>, string> dirs = {{{1, 0}, "S"}, {{-1, 0}, "N"}, {{0, 1}, "E"}, {{0, -1}, "W"}};
    string str_path = "";
    auto vertex = make_pair(x_finish - 1, y_finish - 1);
    while (vertex != make_pair(x_start - 1, y_start - 1))
    {

        auto vertex_from = path[vertex.first][vertex.second];
        pair<int, int> dir = {vertex.first - vertex_from.first, vertex.second - vertex_from.second};
        if (vertex_from == make_pair(-1, -1))
        {
            cout << "-1";
            return 0;
        }

        str_path = dirs[dir] + str_path;

        vertex = vertex_from;
    }
    cout << dist[x_finish - 1][y_finish - 1] << "\n";
    cout << str_path;
}