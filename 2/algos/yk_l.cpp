#include <iostream>
#include <queue>

using namespace std;

int main()
{

    int n, k;
    cin >> n >> k;

    deque<int> deq;
    vector<int> nums(n);

    for (int i = 0; i < n; i++)
    {
        cin >> nums[i];
    }

    for (int i = 0; i < n; i++)
    {
        if (!deq.empty() && deq.front() == i - k)
        {
            deq.pop_front();
        }
        while (!deq.empty() && nums[deq.back()] >= nums[i])
        {
            deq.pop_back();
        }
        deq.push_back(i);
        if (i >= k - 1)
        {
            cout << nums[deq.front()] << " ";
        }
    }
}