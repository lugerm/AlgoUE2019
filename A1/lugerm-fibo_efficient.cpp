#include <iostream>
#include <chrono>

using namespace std;
using namespace std::chrono;

//snake_case naming is used, Fibonacci-Beispiel


int main(int argc, char *argv[])
{
    int number_input, term_0 = 0, term_1 = 1, next_term = 0;

    auto start = high_resolution_clock::now();

    cout << "Enter any natural and postivie number for the Fibonacci count: \n";
    std::cin >> number_input;

    cout << "Fibonacci Series: ";

    for (int i = 1; i <= number_input; ++i)
    {
        // Prints the first two terms.
        if(i == 1)
        {
            cout << " " << term_0 << " , ";
            continue;
        }
        if(i == 2)
        {
            cout << term_1 << " , ";
            continue;
        }
        next_term = term_0 + term_1;
        term_0 = term_1;
        term_1 = next_term;
        
        cout << next_term << " , ";
    }

    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop - start);
    cout <<"operating time & runtime in milliseconds: "<< duration.count() << endl; 

    return 0;
}



