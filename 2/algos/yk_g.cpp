#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <unordered_map>

using namespace std;

int main()
{
    string s;
    cin >> s;

    vector<pair<int, char>> weights;

    unordered_map<char, int> char_count;

    for (int i = 0; i < 26; i++)
    {
        weights.push_back(make_pair(0, 'a' + i));
        cin >> weights[i].first;
    }

    sort(weights.rbegin(), weights.rend());

    for (int i = 0; i < s.size(); i++)
    {
        char_count[s[i]] += 1;
    }

    string border_left = "";
    string border_right = "";
    string center = "";

    for (auto inst : weights)
    {
        if (char_count[inst.second] > 1)
        {
            border_left += inst.second;
            char_count[inst.second] -= 2;
        }

        center += string(char_count[inst.second], inst.second);

    }

    for (int i = border_left.size() - 1; i >= 0; i--)
    {
        border_right += border_left[i];
    }

    string result = border_left + center + border_right;

    cout << result;
}