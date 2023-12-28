import sys
import random

def getDirs(pipe):
    # [up, duown, left, right]
    if pipe == '|': return [1,1,0,0]
    if pipe == '-': return [0,0,1,1]
    if pipe == 'L': return [1,0,0,1]
    if pipe == 'J': return [1,0,1,0]
    if pipe == '7': return [0,1,1,0]
    if pipe == 'F': return [0,1,0,1]
    if pipe == '.' or pipe == 'S': return [0,0,0,0]
    print(f'No pipe {pipe}')
    assert False

m, spos = [], ()
lines = open(sys.argv[1]).read().split('\n')
for r, line in enumerate(lines):
    row = []
    for c, p in enumerate(line):
        if p == 'S': spos = (c,r)
        row.append(getDirs(p))
    m.append(row)

sc, sr = spos
h, w = len(m), len(m[0])
m[sr][sc][1] = m[sr+1][sc][0] if sr+1<h else 0
m[sr][sc][0] = m[sr-1][sc][1] if 0<=sr-1 else 0
m[sr][sc][3] = m[sr][sc+1][2] if sc+1<w else 0
m[sr][sc][2] = m[sr][sc-1][3] if 0<=sc-1 else 0

def bfs(start):
    dists, v, q = {}, set([start]), [[start, 0]]
    while q:
        (x, y), d = q.pop(0)
        dists[(x,y)] = d
        if 0<=x+1<w and m[y][x][3] and m[y][x+1][2]:
            if not (x+1,y) in v:
                q.append([(x+1,y), d+1])
                v.add((x+1,y))
        if 0<=x-1<w and m[y][x][2] and m[y][x-1][3]:
            if not (x-1,y) in v:
                q.append([(x-1,y), d+1])
                v.add((x-1,y))
        if 0<=y+1<h and m[y][x][1] and m[y+1][x][0]:
            if not (x,y+1) in v:
                q.append([(x,y+1), d+1])
                v.add((x,y+1))
        if 0<=y-1<h and m[y][x][0] and m[y-1][x][1]:
            if not (x,y-1) in v:
                q.append([(x,y-1), d+1])
                v.add((x,y-1))
    return dists

dists = bfs(spos)
ans1 = max(dists.values())
print(f'Answer Part 1: {ans1}')

def get3Pipe(pipe):
    # [up, duown, left, right]
    # Transforms pipes to 3 x 3 grid
    #       ___
    #      | # |
    # | -> | # |
    #      |_#_|
    l = [(1,1)]
    if pipe == [1,1,0,0]: return l + [(1, 0), (1, 2)]
    if pipe == [1,0,1,0]: return l + [(1, 0), (0, 1)]
    if pipe == [1,0,0,1]: return l + [(1, 0), (2, 1)]
    if pipe == [0,1,1,0]: return l + [(0, 1), (1, 2)]
    if pipe == [0,1,0,1]: return l + [(2, 1), (1, 2)]
    if pipe == [0,0,1,1]: return l + [(0, 1), (2, 1)]
    print(pipe)
    assert False

posInCycle, posOut = set([x for x in dists]), []
minX, maxX = min(x for x,y in posInCycle), max(x for x,y in posInCycle)
minY, maxY = min(y for x,y in posInCycle), max(y for x,y in posInCycle)
dy, dx = maxY-minY+1, maxX-minX+1
tm = []
for y in range(dy*3):
    tm.append(['.' for _ in range(dx*3)])

for y in range(minY, maxY + 1):
    for x in range(minX, maxX + 1):
        if (x, y) in posInCycle:
            curr = m[y][x]
            toAdd = get3Pipe(curr)
            for ax, ay in toAdd:
                xx = 3*(x-minX) + ax
                yy = 3*(y-minY) + ay
                tm[yy][xx] = '#'
        else:
            posOut.append((3*(x-minX)+1, 3*(y-minY)+1))

def isEnclosed(pos, tm):
    v = set([pos])
    q = [pos]
    while q:
        x,y = q.pop(0)
        if x<=0 or x>=len(tm[0])-1 or y<=0 or y>=len(tm)-1:
            return False, v
        if not tm[y][x+1] == '#' and not (x+1,y) in v:
            q.append((x+1,y))
            v.add((x+1,y))
        if not tm[y][x-1] == '#' and not (x-1,y) in v:
            q.append((x-1,y))
            v.add((x-1,y))
        if not tm[y+1][x] == '#' and not (x,y+1) in v:
            q.append((x,y+1))
            v.add((x,y+1))
        if not tm[y-1][x] == '#' and not (x,y-1) in v:
            q.append((x,y-1))
            v.add((x,y-1))
    return True, v

ans2 = 0
# Random shuffle to hopefully get one of the inner once first.
# Due to the transform all inner points are connected.
random.shuffle(posOut)
while posOut:
    p = posOut.pop(0)
    enc, poses = isEnclosed(p, tm)
    if enc:
        ans2 = len([x for x, y in poses if (x-1)%3 == 0 and (y-1)%3 == 0])
        break # Since if we find one inner => we found all

print(f'Answer Part 2: {ans2}')