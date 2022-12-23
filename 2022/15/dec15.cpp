#include <iostream>
#include <algorithm>
#include <vector>
#include <set>
#include <map>

using namespace std;

vector< vector<int> > mergeRanges(vector< vector<int> > &ranges) {
  vector< vector<int> > maxRanges;
  while (ranges.size() > 0) {
    vector<int> curr = ranges[0];
    ranges.erase(ranges.begin());
    bool added = false;
    for (int k = ranges.size()-1; k >= 0; k--) {
      // a in b
      if (curr[0] >= ranges[k][0] && curr[1] <= ranges[k][1]) {
        added = true;
        break;
      }
      // b in a
      else if (curr[0] <= ranges[k][0] && curr[1] >= ranges[k][1]) {
        ranges.erase(ranges.begin() + k);
      }
      // end in a
      else if (curr[0] <= ranges[k][1] && curr[1] >= ranges[k][1]) {
        curr[0] = ranges[k][0];
        ranges.erase(ranges.begin() + k);
        ranges.push_back(curr);
        added = true;
        break;
      }
      // start in a
      else if (curr[0] <= ranges[k][0] && curr[1] >= ranges[k][0]) {
        curr[1] = ranges[k][1];
        ranges.erase(ranges.begin() + k);
        ranges.push_back(curr);
        added = true;
        break;
      }
      // Can combine ranges 0-3 4-20
      else if ((curr[1]+1 == ranges[k][0]) || curr[0] == ranges[k][1]+1) {
        curr[0] = min(curr[0], ranges[k][0]);
        curr[1] = max(curr[1], ranges[k][1]);
        ranges.erase(ranges.begin() + k);
        ranges.push_back(curr);
        added = true;
        break;
      }
    }
    if (!added) {
      maxRanges.push_back(curr);
    }
  }
  return maxRanges;
}

// Compile with "g++ -std=c++20 -o dec15 dec15.cpp"
// run with "./dec15 < input.txt"
// not that minRow, maxRow and row needs to be set manually
int main(void) {
  string line, data;
  int x, y, bx, by, ans1 = 0;
  vector<int> sensors;
  vector<int> beacons;
  map<int, set<int> > numBonRow;
  while(getline(cin, line)) {
    data = line.substr(12, line.find(",")-12);
    sensors.push_back(stoi(data));
    line = line.substr(line.find(",")+1, line.length());
    data = line.substr(3, line.find(":")-3);
    sensors.push_back(stoi(data));
    line = line.substr(line.find(":")+1, line.length());
    data = line.substr(24, line.find(",")-24);
    bx = stoi(data);
    beacons.push_back(bx);
    line = line.substr(line.find(",")+1, line.length());
    data = line.substr(3, line.length());
    by = stoi(data);
    beacons.push_back(by);
    numBonRow[by].insert(bx);
  }

  int numSensors = sensors.size()/2;
  long long ansX, ansY;
  vector< vector<int> > ranges, ans1Ranges;
  // int minRow = 0, maxRow = 20;
  // int row = 10;
  int minRow = 0, maxRow = 4000000;
  int row = 2000000;
  for (int i = minRow; i <= maxRow; i++) {
    if (i > 0 && i % 100000 == 0) {
      cout << "Number of rows checked: " << i << endl;
    }
    for (int j = 0; j < numSensors; j++) {
      x = sensors[2*j];
      y = sensors[2*j+1];
      bx = beacons[2*j];
      by = beacons[2*j+1];

      int dx = abs(x - bx);
      int dy = abs(y - by);
      int drow = abs(i - y);
      if ((dx + dy) > drow) {
        int left = (dx + dy) - drow;
        vector<int> range, ans1r;
        int min = x-left;
        int max = x+left;
        if (i == row) {
          ans1r.push_back(min);
          ans1r.push_back(max);
          ans1Ranges.push_back(ans1r);
        }
        if (min <= maxRow && max >= minRow) {
          if (min < minRow) {
            min = minRow;
          }
          if (max > maxRow) {
            max = maxRow;
          }
          range.push_back(min);
          range.push_back(max);
          ranges.push_back(range);
        }
      }
    }

    vector< vector<int> > maxRanges = mergeRanges(ranges);

    if (maxRanges.size() > 1) {
      ansY = i;
      ansX = min(maxRanges[0][1], maxRanges[1][1]) + 1;
    } else if (maxRanges.size() == 1) {
      // minRow not in range
      if (maxRanges[0][0] > minRow) {
        ansY = i;
        ansX = minRow;
      // maxRow not in range
      } else if (maxRanges[0][1] < maxRow) {
        ansY = i;
        ansX = maxRow;
      }
    }

    maxRanges.clear();
    ranges.clear();
  }

  vector< vector<int> > maxRans1 = mergeRanges(ans1Ranges);
  for(auto r : maxRans1) {
    ans1 += r[1] - r[0] + 1;
  }

  ans1 -= numBonRow[row].size();
  long long ans2 = 4000000 * ansX + ansY;
  cout << "Part 1 - " << ans1 << endl;
  cout << "Part 2 - " << ans2 << endl;
  return 0;
}