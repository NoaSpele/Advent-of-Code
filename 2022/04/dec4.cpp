#include <iostream>
#include <algorithm>

using namespace std;

// Run: "make dec4" and "./dec4 < input.txt"
int main(void) {
  string line, r1, r2;
  int start1, start2, end1, end2, sum1=0, sum2=0;

  while (cin >> line) {
    r1 = line.substr(0,line.find(","));
    r2 = line.substr(line.find(",")+1,line.length());
    start1 = stoi(r1.substr(0,r1.find("-")));
    start2 = stoi(r2.substr(0,r2.find("-")));
    end1 = stoi(r1.substr(r1.find("-")+1,r1.length()));
    end2 = stoi(r2.substr(r2.find("-")+1,r2.length()));

    cout << "r1: " << r1 << ", r2: " << r2 << endl;
    cout << "start1: " << start1 << ", end1: " << end1 << endl;
    cout << "start2: " << start2 << ", end2: " << end2 << endl;

    if ((start1 >= start2 && end1 <= end2)||(start2 >= start1 && end2 <= end1)) {
      cout << "Found match!" << endl;
      sum1++;
    }

    if (
      (start1 >= start2 && start1 <= end2) ||
      (end1 >= start2 && end1 <= end2) ||
      (start2 >= start1 && start2 <= end1) ||
      (end2 >= start1 && end2 <= end1)
    ) {
      sum2++;
    }

    cout << endl;
  }
  cout << "===== Done =====" << endl;
  cout << "Part 1 - Found: " << sum1 << " matches" << endl;
  cout << "Part 2 - Found: " << sum2 << " matches" << endl;
  return 0;
}