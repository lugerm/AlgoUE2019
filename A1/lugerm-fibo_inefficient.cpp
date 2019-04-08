#include <iostream>
#include <chrono>

using namespace std;
using namespace std::chrono;
//trying to do an ineffient Fibonacci count -->?

/*  tried to include the flags like this:


int main(int argc, const char* argv[])
{
    int number_input, term_0 = 0, term_1 = 1, next_term = 0;
    auto start = high_resolution_clock::now();

    for(int i= 0; i < argc; ++i){

        if(std::string(argc[i]) == "--help"){
            return (0);   
        }
    else if (std::string(argc[i]) == "--all") {
            return(1);
        }  
    } 

*/

int main() {
    int number_input, term_0 = 0, term_1 = 1, next_term = 0; //def 
    
    auto start = high_resolution_clock::now();   //Startuhrzeit
    
    cout << "Enter any natural and postivie number for the Fibonacci count: \n";
    cin >> number_input;

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
    cout <<"operating time & runtime in milliseconds: "<< duration.count() << endl; //output of the total runtime

    return 0;
}