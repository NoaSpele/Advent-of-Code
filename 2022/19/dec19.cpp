#include <iostream>
#include <vector>
#include <algorithm>
#include <string>
#include <tuple>
#include <map>

using namespace std;

typedef tuple<int, int, int, int, int, int, int, int, int> tup9;
typedef map<tup9, int> map9;

// Compile with "g++ -std=c++20 -o dec19 dec19.cpp"
// un with "./dec19 < input.txt"
int findMaxGeode(int maxT, map9 &seen, vector<int> &costs, int maxOre, int t, int numOr, int numC, int numOb, int numG, int aOr, int aC, int aOb, int aG) {
  if (t >= maxT-1) {
    return aG + numG;
  }
  vector<int> res;

  if (seen.contains(make_tuple(t, numOr, numC, numOb, numG, aOr, aC, aOb, aG))) {
    return seen[make_tuple(t, numOr, numC, numOb, numG, aOr, aC, aOb, aG)];
  }

  // No robot created
  res.push_back(findMaxGeode(maxT, seen, costs, maxOre, t+1, numOr, numC, numOb, numG, aOr + numOr, aC + numC, aOb + numOb, aG + numG));

  // Create geode robot if you can
  if (aOr >= costs[4] && aOb >= costs[5]) {
    res.push_back(findMaxGeode(maxT, seen, costs, maxOre, t+1, numOr, numC, numOb, numG + 1, aOr + numOr - costs[4], aC + numC, aOb + numOb - costs[5], aG + numG));
  }
  // Create obsidian robot if you can
  else if (numOb < costs[5] && aOr >= costs[2] && aC >= costs[3]) {
    res.push_back(findMaxGeode(maxT, seen, costs, maxOre, t+1, numOr, numC, numOb + 1, numG, aOr + numOr - costs[2], aC + numC - costs[3], aOb + numOb, aG + numG));
  }
  // Create ore robot
  else {
    if (numOr < maxOre && aOr >= costs[0]) {
      res.push_back(findMaxGeode(maxT, seen, costs, maxOre, t+1, numOr + 1, numC, numOb, numG, aOr + numOr - costs[0], aC + numC, aOb + numOb, aG + numG));
    }
    // Create clay robot
    if (numC < costs[3] && aOr >= costs[1]) {
      res.push_back(findMaxGeode(maxT, seen, costs, maxOre, t+1, numOr, numC + 1, numOb, numG, aOr + numOr - costs[1], aC + numC, aOb + numOb, aG + numG));
    }
  }

  seen[make_tuple(t, numOr, numC, numOb, numG, aOr, aC, aOb, aG)] = *max_element( res.begin(),  res.end() );

  return *max_element( res.begin(),  res.end() );
}

int main(void) {
  string line, data;
  int ans1 = 0, ans2 = 1;
  int c1, c2, c3a, c3b, c4a, c4b;
  vector< vector<int> > costs;

  while(getline(cin, line)) {
    vector<int> currCosts;
    line = line.substr(line.find(":")+2, line.length());
    data = line.substr(21, line.find(".")-25);
    c1 = stoi(data);
    currCosts.push_back(c1);
    line = line.substr(line.find(".")+24, line.length());
    data = line.substr(0, line.find(".")-4);
    c2 = stoi(data);
    currCosts.push_back(c2);
    line = line.substr(line.find(".")+28, line.length());
    data = line.substr(0, line.find("ore")-1);
    c3a = stoi(data);
    currCosts.push_back(c3a);
    line = line.substr(line.find("ore")+8, line.length());
    data = line.substr(0, line.find(".")-5);
    c3b = stoi(data);
    currCosts.push_back(c3b);
    line = line.substr(line.find(".")+25, line.length());
    data = line.substr(0, line.find("ore")-1);
    c4a = stoi(data);
    currCosts.push_back(c4a);
    line = line.substr(line.find("ore")+8, line.length());
    data = line.substr(0, line.find(".")-9);
    c4b = stoi(data);
    currCosts.push_back(c4b);
    costs.push_back(currCosts);
    // cout << c1 << " " << c2 << " " << c3a << " " << c3b << " " << c4a << " " << c4b << endl;
  }

  int qualityLevel = 0, maxOre = 0, prevQualityLevel = -1;
  cout << "Started to calculate part 1" << endl;
  for (size_t i = 0; i < costs.size(); i++) {
    maxOre = max(max(max(costs[i][0], costs[i][1]), costs[i][2]), costs[i][4]);
    map9 seen;
    qualityLevel = findMaxGeode(24, seen, costs[i], maxOre, 1, 1, 0, 0, 0, 1, 0, 0, 0);
    ans1 += qualityLevel * (i+1);
    seen.clear();
    cout << "Done with blueprint: " << i+1 << endl;
  }
  cout << "Part 1 - " << ans1 << endl;

  cout << "Started to calculate part 2" << endl;
  for (size_t i = 0; i < min(3,(int) costs.size()); i++) {
    maxOre = max(max(max(costs[i][0], costs[i][1]), costs[i][2]), costs[i][4]);
    map9 seen;
    qualityLevel = findMaxGeode(32, seen, costs[i], maxOre, 1, 1, 0, 0, 0, 1, 0, 0, 0);
    ans2 *= qualityLevel;
    seen.clear();
    cout << "Done with blueprint: " << i+1 << endl;
  }
  cout << "Part 2 - " << ans2 << endl;
  return 0;
}