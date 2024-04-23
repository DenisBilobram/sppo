#include <iostream>
#include <vector>
#include <stack>

using namespace std;

int main() {
    int n;
    cin >> n;

    vector<int> balls(n);
    for (int i = 0; i < n; i++) {
        cin >> balls[i];
    }


    stack<int> lunka;
    int next_ball = 1;
    for (int i = 0; i < n; i++) {

        while (next_ball <= balls[i]) {
            lunka.push(next_ball);
            next_ball++;
        }

        if (lunka.top() != balls[i]) {
            cout << "Cheater";
            return 0;
        } else {
            lunka.pop();
        }

    }

    cout << "Not a proof" << "\n";
}