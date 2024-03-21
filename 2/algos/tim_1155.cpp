#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
#include <map>

using namespace std;

int main()
{

    string result = "";
    map<int, vector<int>> neighbours = {
        {0, {1, 3, 4}},
        {1, {0, 2, 5}},
        {2, {1, 3, 6}},
        {3, {0, 2, 7}},
        {4, {0, 5, 7}},
        {5, {1, 4, 6}},
        {6, {2, 5, 7}},
        {7, {3, 4, 6}}};
    vector<pair<int, int>> borders = {{0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 5}, {5, 6}, {6, 7}, {7, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};
    vector<pair<int, int>> diagonals = {{0, 6}, {1, 7}, {2, 4}, {3, 5}};
    vector<string> LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H"};

    int peaks[8];
    int sum = 0;

    for (int i = 0; i < 8; i++)
    {
        cin >> peaks[i];
        sum += peaks[i];
    }

    if (sum % 2 != 0)
    {
        cout << "IMPOSSIBLE";
        exit(0);
    }

    for (auto border : borders)
    {
        while (peaks[border.first] != 0 && peaks[border.second] != 0)
        {
            peaks[border.first]--;
            peaks[border.second]--;
            result += LETTERS[border.first] + LETTERS[border.second] + "-\n";
        }
    }
    for (auto diag : diagonals)
    {
        pair<int, int> tmp_pair;
        for (auto border : borders)
        {
            if (find(neighbours[diag.first].begin(), neighbours[diag.first].end(), border.first) != neighbours[diag.first].end() &&
                find(neighbours[diag.second].begin(), neighbours[diag.second].end(), border.second) != neighbours[diag.second].end())
            {
                tmp_pair = {border.first, border.second};
                break;
            }
            else if (find(neighbours[diag.first].begin(), neighbours[diag.first].end(), border.second) != neighbours[diag.first].end() &&
                     find(neighbours[diag.second].begin(), neighbours[diag.second].end(), border.first) != neighbours[diag.second].end())
            {
                tmp_pair = {border.second, border.first};
                break;
            }
        }
        while (peaks[diag.first] != 0 && peaks[diag.second] != 0)
        {

            result += LETTERS[tmp_pair.first] + LETTERS[tmp_pair.second] + "+\n";
            result += LETTERS[diag.first] + LETTERS[tmp_pair.first] + "-\n";
            result += LETTERS[diag.second] + LETTERS[tmp_pair.second] + "-\n";
            peaks[diag.first]--;
            peaks[diag.second]--;
        }
    }
    for (int i = 0; i < 8; i++)
    {
        if (peaks[i] != 0)
        {
            cout << "IMPOSSIBLE";
            exit(0);
        }
    }
    cout << result;
}