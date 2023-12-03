#include <iostream>
#include <algorithm>

using namespace std;

// Run: "make dec8" and "./dec8 < input.txt"
int main(void) {
  string line;
  cin >> line;
  int l = line.length();
  // assuming quadratic input
  int trees[l][l];

  // Read input
  for (size_t i = 0; i < l; i++) {
    if (i > 0) {
      cin >> line;
    }
    for (size_t j = 0; j < l; j++) {
      trees[i][j] = stoi(line.substr(j,1));
    }
  }

  // Since both input are l > 2
  int tot1 = 2*l + 2*(l-2);
  int bestScenicScore = 0;
  // Check all visible and calculate scenic score
  for (size_t i = 1; i < l-1; i++) {
    for (size_t j = 1; j < l-1; j++) {
      bool v1 = true, v2 = true, v3 = true, v4 = true;
      int sc1 = 0, sc2 = 0, sc3 = 0, sc4 = 0, sc = 0;
      for (int k = i+1; k < l; k++) {
        sc1++;
        if (trees[i][j] <= trees[k][j]) {
          v1 = false;
          break;
        }
      }
      for (int k = i-1; k >= 0; k--) {
        sc2++;
        if (trees[i][j] <= trees[k][j]) {
          v2 = false;
          break;
        }
      }
      for (int k = j+1; k < l; k++) {
        sc3++;
        if (trees[i][j] <= trees[i][k]) {
          v3 = false;
          break;
        }
      }
      for (int k = j-1; k >= 0; k--) {
        sc4++;
        if (trees[i][j] <= trees[i][k]) {
          v4 = false;
          break;
        }
      }
      if (v1 || v2 || v3 || v4) {
        tot1++;
      }
      sc = sc1 * sc2 * sc3 * sc4;
      if (sc > bestScenicScore) {
        bestScenicScore = sc;
      }
    }
  }
  cout << "Part 1 - Number visible: " << tot1 << endl;
  cout << "Part 2 - Best scenic score: " << bestScenicScore << endl;
  return 0;
}