#include <iostream>
#include <algorithm>
#include <stack>
#include <vector>
#include <string>
#include <cctype>

using namespace std;

int main() {

    string st;
    cin >> st;
    int n = st.size();

    stack<pair<char, int>> stck;
    vector<pair<int, int>> vec;

    int ind;
    if (islower(st[0])) ind = 1;
    else ind = 0;

    stck.push(make_pair(st[0], ind));
    for (int i = 1; i < n; i++) {
        if (stck.empty()) {
            stck.push(make_pair(st[i], i));
            continue;
        }

        if (isupper(st[i])) {
            if (tolower(st[i]) == stck.top().first) {
                vec.push_back(make_pair(i, stck.top().second));
                stck.pop();
            } else {
                stck.push(make_pair(st[i], i));
            }
        } else {
            ind++;
            if (toupper(st[i]) == stck.top().first) {
                vec.push_back(make_pair(stck.top().second, ind));
                stck.pop();
            } else {
                stck.push(make_pair(st[i], ind));
            }
        }

    }

    if (stck.empty()) {
        sort(vec.begin(), vec.end());

        cout << "Possible\n";
        for (pair<int, int> el : vec) {
            cout << el.second << " ";
        }
    } else {
        cout << "Impossible";
    }

}