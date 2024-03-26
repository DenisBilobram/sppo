#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

int main()
{
    vector<string> vec;
    string str;
    while (true) {
        getline(std::cin, str);
        if (str.empty()) {
            break;
        }
        vec.push_back(str);
    }
    sort(vec.rbegin(), vec.rend());
    for(string el : vec) {
        cout << el;
    }
}