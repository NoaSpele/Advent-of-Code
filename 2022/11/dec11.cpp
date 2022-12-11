#include <iostream>
#include <algorithm>
#include <vector>
#include <functional>
#include <math.h>

using namespace std;

// Run: "make dec11" and "./dec11 <part> < input.txt" where part is 1/2
int main(int argc, char** argv) {
  string line;
  vector< vector< long long > > items;
  vector< vector<string> > operations;
  vector<int> tests;
  vector<int> throwTo;
  vector<int> numInspections;
  int modulo = 1, part = stoi(argv[1]);

  // Read input
  while(getline(cin, line)) {
    vector<long long> initialItems;
    numInspections.push_back(0);
    // Find inital items
    getline(cin, line);
    line = line.substr(line.find(":")+1, line.length());
    while (line.length() > 0) {
      if (line.find(",") != -1) {
        initialItems.push_back(stoi(line.substr(0, line.find(","))));
        line = line.substr(line.find(",")+1, line.length());
      } else {
        initialItems.push_back(stoi(line));
        line = "";
      }
    }
    items.push_back(initialItems);

    // Read Operation
    getline(cin, line);
    line = line.substr(line.find("= ")+2, line.length());
    vector<string> ops;
    ops.push_back(line.substr(0, line.find(" ")));
    line = line.substr(line.find(" ")+1, line.length());
    ops.push_back(line.substr(0, line.find(" ")));
    line = line.substr(line.find(" ")+1, line.length());
    ops.push_back(line);
    operations.push_back(ops);

    // Read test value
    getline(cin, line);
    tests.push_back(stoi(line.substr(line.find(" by ")+4, line.length())));

    // Read which monkey to throw to
    getline(cin, line);
    throwTo.push_back(stoi(line.substr(line.find("monkey ")+7, line.length())));
    getline(cin, line);
    throwTo.push_back(stoi(line.substr(line.find("monkey ")+7, line.length())));

    // Read empty line;
    getline(cin, line);
  }

  for (size_t i = 0; i < tests.size(); i++) {
    modulo = modulo * tests[i];
  }

  int numRounds;
  if (part == 1) {
    numRounds = 20;
  } else {
    numRounds = 10000;
  }
  // Do 20 / 10000 rounds
  for (size_t i = 0; i < numRounds; i++) {
    for (size_t j = 0; j < items.size(); j++) {
      while (items[j].size() > 0) {
        numInspections[j]++;
        long long curr = items[j][0];
        items[j].erase(items[j].begin());
        if (operations[j][1] == "+") {
          if (operations[j][2] == "old") {
            curr = curr + curr;
          } else {
            curr = curr + stoi(operations[j][2]);
          }
        } else {
          if (operations[j][2] == "old") {
            curr = curr * curr;
          } else {
            curr = curr * stoi(operations[j][2]);
          }
        }

        if (part == 1) {
          curr = curr / 3;
        } else {
          curr = curr % modulo;
          if (curr < 0) {
            curr += modulo;
          }
        }

        if (curr%tests[j] == 0) {
          items[throwTo[2*j]].push_back(curr);
        } else {
          items[throwTo[2*j+1]].push_back(curr);
        }
      }
    }
  }

  sort(numInspections.rbegin(), numInspections.rend());
  cout << "Number of inspections: " << endl;
  for(int i = 0; i<numInspections.size(); i++) {
    cout << "Monkey " << i << ": " << numInspections[i] << endl;
  }
  cout << endl;

  cout << "Monkey business: " << (long long) numInspections[0] * numInspections[1] << endl;
  return 0;
}