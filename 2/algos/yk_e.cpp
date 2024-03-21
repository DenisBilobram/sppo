#include <iostream>
#include <string>
#include <vector>

using namespace std;

int main()
{
    int n, k;
    cin >> n >> k;

    vector<int> seats(n);

    for (int i = 0; i < n; i++)
    {
        cin >> seats[i];
    }

    int left = 1;
    int right = seats[n - 1] - seats[0];
    int mid;
    int min;

    while (left <= right)
    {

        mid = (left + right) / 2;

        int dist = 0;
        int c = 1;
        for (int i = 0; i < n - 1; i++)
        {

            if (dist == 0)
            {
                dist = seats[i + 1] - seats[i];
            }

            if (dist < mid)
            {
                dist += seats[i + 2] - seats[i + 1];
            }
            else
            {
                dist = 0;
                c += 1;
            }
        }
        if (c >= k)
        {
            left = mid + 1;
            min = mid;
        }
        else
        {
            right = mid - 1;
        }
    }

    cout << min;
}