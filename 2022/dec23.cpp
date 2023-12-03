#include <iostream>
#include <algorithm>
#include <tuple>
#include <map>
#include <set>
#include <vector>

using namespace std;

// Run: "g++ -std=c++20 -o dec23 dec23.cpp"
// and "./dec23 < input.txt"

bool hasElf(tuple<int, int> pos, set<tuple<int, int>> &elves) {
  return elves.contains(pos);
}

bool shouldMove(int x, int y, set<tuple<int, int>> &elves) {
  return hasElf(make_tuple(x+1, y), elves)   || hasElf(make_tuple(x-1, y), elves) ||
         hasElf(make_tuple(x, y+1), elves)   || hasElf(make_tuple(x, y-1), elves) ||
         hasElf(make_tuple(x+1, y+1), elves) || hasElf(make_tuple(x-1, y-1), elves) ||
         hasElf(make_tuple(x+1, y-1), elves) || hasElf(make_tuple(x-1, y+1), elves);
}

bool canMove(int x, int y, const tuple<int, int> &elf, set<tuple<int, int>> &elves,
    map<tuple<int, int>, vector<tuple<int, int>>> &moves, bool isX) {
  tuple<int, int> move = make_tuple(x, y);
  if (!hasElf(move, elves)) {
    if ((isX && !hasElf(make_tuple(x, y-1), elves) &&
          !hasElf(make_tuple(x, y+1), elves)) ||
          (!hasElf(make_tuple(x-1, y), elves) &&
          !hasElf(make_tuple(x+1, y), elves))) {
        if (moves.contains(move)) {
          moves[move].push_back(elf);
        } else {
          vector<tuple<int, int>> m{elf};
          moves[move] = m;
        }
        return true;
    }
  }
  return false;
}

set<tuple<int, int>> elves;
vector<char> moveOrder{'n', 's', 'w', 'e'};
map<tuple<int, int>, vector<tuple<int, int>>> moves;
int ans1 = 0, ans2;

int main(void) {
  string line;
  int row = 0, col = 0;
  while(getline(cin, line)) {
    col = 0;
    for(char c : line) {
      if (c == '#') elves.insert(make_tuple(col, row));
      col++;
    }
    row++;
  }

  int round = 0;
  while (true) {
    round++;
    if (round % 100 == 0) cout << "Round: " << round << endl;
    moves.clear();
    int idx = 0;
    for(const tuple<int, int> &elf : elves) {
      if(shouldMove(get<0>(elf), get<1>(elf), elves)) {
        int x = get<0>(elf);
        int y = get<1>(elf);
        bool moved = false;
        for (char dir : moveOrder) {
          tuple<int, int> move;
          switch (dir) {
            case 'n':
              moved = canMove(x, y-1, elf, elves, moves, false);
              break;
            case 's':
              moved = canMove(x, y+1, elf, elves, moves, false);
              break;
            case 'w':
              moved = canMove(x-1, y, elf, elves, moves, true);
              break;
            default: // E
              moved = canMove(x+1, y, elf, elves, moves, true);
              break;
          }
          if (moved) break;
        }
      }
      idx++;
    }

    bool madeMove = false;
    for(const auto &m : moves) {
      if (m.second.size() == 1) {
        tuple<int, int> elf = m.second[0];
        int x = get<0>(elf), y = get<1>(elf);
        int nx = get<0>(m.first), ny = get<1>(m.first);
        elves.erase(make_tuple(x, y));
        elves.insert(make_tuple(nx, ny));
        madeMove = true;
      }
    }

    if (!madeMove) {
      ans2 = round;
      break;
    }

    char toAdd = moveOrder[0];
    moveOrder.erase(moveOrder.begin());
    moveOrder.push_back(toAdd);

    if (round == 10) {
      tuple<int, int> first = *elves.begin();
      int xmax = get<0>(first), ymax = get<1>(first);
      int xmin = get<0>(first), ymin = get<1>(first);
      // find x,y min/max
      for (auto &e : elves) {
        if (get<0>(e) > xmax) xmax = get<0>(e);
        if (get<0>(e) < xmin) xmin = get<0>(e);
        if (get<1>(e) > ymax) ymax = get<1>(e);
        if (get<1>(e) < ymin) ymin = get<1>(e);
      }

      // itterate over region check num empty space
      for (int i = ymin; i <= ymax; i++) {
        for (int j = xmin; j <= xmax; j++) {
          if (!hasElf(make_tuple(j, i), elves)) ans1++;
        }
      }

      cout << "Part 1 - " << ans1 << endl;
    }
  }
  cout << "Part 2 - " << ans2 << endl;
  return 0;
}