#include <iostream>
#include <deque>

using namespace std;

int main()
{
    string n;
    getline(cin, n);

    deque<int> first, second;
    int lim = stoi(n);
    for (int i = 0; i < lim; i++)
    {
        string command;
        getline(cin, command);

        if (command[0] == '+')
        {
            int num = stoi(command.substr(2));
            second.push_back(num);
        }
        else if (command[0] == '*')
        {
            int num = stoi(command.substr(2));
            int sz = first.size() + second.size();
            if (sz % 2 == 0)
            {
                first.push_back(num);
            }
            else
            {
                second.push_front(num);
            }
        }
        else if (command[0] == '-')
        {
            if (!first.empty())
            {
                cout << first.front() << "\n";
                first.pop_front();
            }
            else
            {
                cout << second.front() << "\n";
                second.pop_front();
            }
        }

        if (first.size() > second.size() + 1)
        {
            second.push_front(first.back());
            first.pop_back();
        }
        else if (second.size() > first.size())
        {
            first.push_back(second.front());
            second.pop_front();
        }
    }
}