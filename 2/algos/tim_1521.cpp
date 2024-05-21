#include <iostream>
#include <vector>

using namespace std;

int main()
{

    int n, k;
    cin >> n >> k;

    vector<int> soldiers(n);

    for (int i = 0; i < n; i++)
    {
        soldiers[i] = i + 1;
    }

    int to_toilet = k - 1;
    while (!soldiers.empty())
    {

        cout << soldiers[to_toilet] << " ";

        soldiers.erase(soldiers.begin() + to_toilet);

        if (!soldiers.empty())
        {
            to_toilet = (to_toilet - 1 + k) % soldiers.size();
        }
    }
}