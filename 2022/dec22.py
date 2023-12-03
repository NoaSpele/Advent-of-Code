import re
import sys
import math
from copy import deepcopy
from collections import deque

parts = open(sys.argv[1]).read().split('\n\n')
lines = parts[0].split('\n')
w = max([len(x) for x in lines])
h = len(lines)
cubeSide = max(w, h)//4

map = []
dir = '>'
x, y, x1, y1 = None, None, None, None
for r, line in enumerate(lines):
    mapRow = []
    for c, v in enumerate(line):
        if v != ' ':
            mapRow.append(1) if v == '.' else mapRow.append(0)
            if not x:
                x, y, x1, y1 = c, r, c, r
        else:
            mapRow.append(-1)
    while len(mapRow) < w:
        mapRow.append(-1)
    map.append(mapRow)

insLine = parts[1].strip()
ins = re.findall(r'\d+|[a-zA-Z]', insLine)

dir2int = {'>': 0, 'v': 1, '<': 2, '^': 3}
dir2delta = {'>': (1, 0), 'v': (0, 1), '<': (-1, 0), '^': (0, -1)}
clock = {'>': 'v', 'v': '<', '<': '^', '^': '>'} # R
antiClock = {'>': '^', 'v': '>', '<': 'v', '^': '<'} # L

def findNext(x, y, goX, d):
    found = False
    if goX:
        sx = 0 if d > 0 else w-1
        while map[y][sx] != 0:
            if map[y][sx] == 1:
                x = sx
                found = True
                break
            sx += d
    else:
        sy = 0 if d > 0 else h-1
        while map[sy][x] != 0:
            if map[sy][x] == 1:
                y = sy
                found = True
                break
            sy += d
    return x, y, found

# .12
# .3.
# 45.
# 6..
def findNext2(x, y, dir):
    wx = x
    wy = y
    newdir = dir
    if dir == '^' and y == 0 and x >= 50 and x < 100: # A
        wx = 0
        wy = x + 100
        newdir = '>'
    elif dir == '^' and y == 0 and x >= 100 and x < 150: # B
        wx = x - 100
        wy = 199
    elif dir == '<' and x == 50 and y >= 0 and y < 50: # G
        wx = 0
        wy = -y + 149
        newdir = '>'
    elif dir == '>' and x == 149 and y >= 0 and y < 50: # D
        wx = 99
        wy = 149 - y
        newdir = '<'
    elif dir == '<' and x == 50 and y >= 50 and y < 100: # F
        wx = y - 50
        wy = 100
        newdir = 'v'
    elif dir == '>' and x == 99 and y >= 50 and y < 100: # E
        wx = y + 50
        wy = 49
        newdir = '^'
    elif dir == '^' and y == 100 and x >= 0 and x < 50: # F
        wx = 50
        wy = x + 50
        newdir = '>'
    elif dir == '<' and x == 0 and y >= 100 and y < 150: # G
        wx = 50
        wy = 149 - y
        newdir = '>'
    elif dir == '>' and x == 99 and y >= 100 and y < 150: # D
        wx = 149
        wy = -y + 149
        newdir = '<'
    elif dir == 'v' and y == 149 and x >= 50 and x < 100: # C
        wx = 49
        wy = x + 100
        newdir = '<'
    elif dir == '<' and x == 0 and y >= 150 and y < 200: # A
        wx = y - 100
        wy = 0
        newdir = 'v'
    elif dir == '>' and x == 49 and y >= 150 and y < 200: # C
        wx = y - 100
        wy = 149
        newdir = '^'
    elif dir == 'v' and y == 199 and x >= 0 and x < 50: # B
        wx = x + 100
        wy = 0
        newdir = 'v'
    elif dir == 'v' and y == 49 and x >= 100 and x < 150: # E
        wx = 99
        wy = x - 50
        newdir = '<'
    else:
        print("Should not be reached!!!")
        wx = None
        wy = None
        newdir = None

    if map[wy][wx] == 1:
        return wx, wy, newdir, True
    else:
        return x, y, dir, False

# ..1.  # .2.
# 234.  # .3.
# ..56  # 145
        # ..6
def findNext3(x, y, dir):
    wx = x
    wy = y
    newdir = dir
    if dir == '^' and y == 0 and x >= 3*cubeSide and x < 4*cubeSide: # A
        wx = -x + 3*cubeSide
        wy = cubeSide
        newdir = 'v'
    elif dir == '^' and y == cubeSide and x >= 0 and x < cubeSide: # A
        wx = 3*cubeSide - x
        wy = 0
        dir = 'v'
    elif dir == '<' and x == 2*cubeSide and y >= cubeSide and y < 2*cubeSide: # B
        wx = y + cubeSide
        wy = cubeSide
        newdir = 'v'
    elif dir == '^' and y == cubeSide and x >= cubeSide and y < 2*cubeSide: # B
        wx = 2*cubeSide
        wy = x - cubeSide
        newdir = '>'
    elif dir == '>' and x == 3*cubeSide-1 and y >= 0 and y < cubeSide: # C
        wx = 4*cubeSide-1
        wy = 3*cubeSide-1 - y
        newdir = '<'
    elif dir == '>' and x == 4*cubeSide-1 and y >= 2*cubeSide and y < 3*cubeSide: # C
        wx = 3*cubeSide-1
        wy = 3*cubeSide-1 - y
        newdir = '<'
    elif dir == '>' and x == 3*cubeSide-1 and y >= cubeSide and y < 2*cubeSide: # D
        wx = 5*cubeSide-1 - y
        wy = 2*cubeSide
        newdir = 'v'
    elif dir == '^' and y == 2*cubeSide and x >= 3*cubeSide and x < 4*cubeSide: # D
        wx = 3*cubeSide-1
        wy = 5*cubeSide-1 - x
        newdir = '<'
    elif dir == 'v' and y == 2*cubeSide-1 and x >= cubeSide and x < 2*cubeSide: # E
        wx = 2*cubeSide
        wy = 4*cubeSide-1 - x
        newdir = '>'
    elif dir == '<' and x == 2*cubeSide and y >= 2*cubeSide and y < 3*cubeSide: # E
        wx = 4*cubeSide-1 - y
        wy = 2*cubeSide-1
        newdir = '^'
    elif dir == 'v' and y == 2*cubeSide-1 and x >= 0 and x < cubeSide: # F
        wx = 3*cubeSide-1 - x
        wy = 3*cubeSide-1
        newdir = '^'
    elif dir == 'v' and y == 3*cubeSide-1 and y >= 2*cubeSide and y < 3*cubeSide: # F
        wx = 3*cubeSide-1 - x
        wy = 2*cubeSide-1
        newdir = '^'
    elif dir == '<' and x == 0 and y >= cubeSide and y < 2*cubeSide: # G
        wx = y + 2*cubeSide
        wy = 3*cubeSide-1
        newdir = '^'
    elif dir == 'v' and y == 3*cubeSide-1 and x >= 3*cubeSide and x < 4*cubeSide: # G
        wx = 0
        wy = x - 2*cubeSide
        newdir = '>'
    else:
        print("Should not be reached!!!")
        wx = None
        wy = None
        newdir = None

    if map[wy][wx] == 1:
        # print(f'Moved to {wx} {wy}')
        return wx, wy, newdir, True
    else:
        return x, y, dir, False

def solve(x, y, dir, part):
    for i, instruction in enumerate(ins):
        if instruction.isnumeric():
            for _ in range(int(instruction)):
                (dx, dy) = dir2delta[dir]
                nx, ny = x+dx, y+dy
                if nx < w and nx >= 0 and ny < h and ny >= 0:
                    if map[ny][nx] == 1:
                        x, y = nx, ny
                        # print(f'Moved to {nx} {ny}')
                    elif map[ny][nx] == 0:
                        break
                    else:
                        if part == 1:
                            x, y, found = findNext(x, y, True if dx != 0 else False, dx if dx != 0 else dy)
                        elif part == 2:
                            x, y, dir, found = findNext2(x, y, dir)
                        else:
                            x, y, dir, found = findNext3(x, y, dir)
                        if not found:
                            break
                else:
                    if part == 1:
                        x, y, found = findNext(x, y, True if dx != 0 else False, dx if dx != 0 else dy)
                    elif part == 2:
                        x, y, dir, found = findNext2(x, y, dir)
                    else:
                        x, y, dir, found = findNext3(x, y, dir)
                    if not found:
                        break
        else:
            oldDir = dir
            dir = clock[dir] if instruction == "R" else antiClock[dir]
    return x, y, dir

x, y, dir = solve(x, y, dir, 1)
ans1 = 1000 * (y+1) + 4 * (x+1) + dir2int[dir]
print(f'Answer Part 1: {ans1}')

x, y, dir = x1, y1, '>'
if sys.argv[1] == 'input.txt':
    x, y, dir = solve(x, y, dir, 2)
    ans2 = 1000 * (y+1) + 4 * (x+1) + dir2int[dir]
    print(f'Answer Part 2: {ans2}')

if sys.argv[1] == 'test.txt':
    x, y, dir = solve(x, y, dir, 3)
    ans2 = 1000 * (y+1) + 4 * (x+1) + dir2int[dir]
    print(f'Answer Part 2: {ans2}')