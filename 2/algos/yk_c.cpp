#include <iostream>
#include <stack>
#include <unordered_map>
#include <map>
#include <queue>
#include <vector>
#include <string>
#include <unordered_set>

using namespace std;

bool isNumber(const std::string& s) {
    for (char c : s) {
        if (!isdigit(c) && c != '-') {
            return false;
        }
    }
    return !s.empty();
}

int main() {

    string st;
    size_t pos;
    string name;
    string val;
    int res;
    int lvl = 0;

    unordered_map<string, vector<pair<int,int>>> variables; // значение уровень
    unordered_map<int, unordered_set<string>> changes;
    vector<string> vec;
    
    while (true) {
        getline(cin, st);
        if (st.empty()) {
            break;
        }
        vec.push_back(st);
    }

    for (string st : vec) {
        

        if (st == "{") {
            lvl++;
        } else if (st == "}") {
            for (string changed : changes[lvl]) {
                variables[changed].pop_back();
                if (variables[changed].empty()) {
                    variables.erase(changed);
                }
            }
            changes[lvl].clear();
            lvl--;
        } else {
            pos = st.find("=");
            name = st.substr(0, pos);
            val = st.substr(pos + 1);

            res = 0;
            if (isNumber(val)) {
                res = stoi(val);
            } else {

                if (variables.count(val)) {
                    res = variables[val].back().first;
                }

                cout << res << "\n";
            }
            if (variables.count(name)) {
                if (variables[name].back().second == lvl) {
                    variables[name].back().first = res;
                } else {
                    variables[name].push_back(make_pair(res, lvl));
                }
            } else {
                variables[name] = vector<pair<int, int>>();
                variables[name].push_back(make_pair(res, lvl));
            }
            changes[lvl].insert(name);
        }
    }
}