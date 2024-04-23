#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

bool is_better(const string &a, const string &b)
{
    return a + b > b + a;
}

int main()
{
    vector<string> vec;
    string str;

    while (cin >> str)
    {
        vec.push_back(str);
    }

    sort(vec.begin(), vec.end(), is_better);

    for (const string &el : vec)
    {
        cout << el;
    }
}