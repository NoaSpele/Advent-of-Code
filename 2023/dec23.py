import sys

lines = open(sys.argv[1]).read().split('\n')
g = [[c for c in row] for row in lines]
h, w = len(g), len(g[0])
sx, sy = g[0].index('.'), 0
ex, ey = g[-1].index('.'), h-1

def getNextPositions(x,y,d,p,part):
    next = []
    if part == 2:
        if x > 0 and g[y][x-1] != '#':
            next.append((x-1, y, d+1, {*p, (x-1,y)}))
        if x < w-1 and g[y][x+1] != '#':
            next.append((x+1, y, d+1, {*p, (x+1,y)}))
        if y > 0 and g[y-1][x] != '#':
            next.append((x, y-1, d+1, {*p, (x,y-1)}))
        if y < h-1 and g[y+1][x] != '#':
            next.append((x, y+1, d+1, {*p, (x,y+1)}))
    else:
        if x > 0 and g[y][x-1] == '.':
            next.append((x-1, y, d+1, {*p, (x-1,y)}))
        if x > 1 and g[y][x-1] == '<':
            next.append((x-2, y, d+2, {*p, (x-1,y), (x-2,y)}))
        if x < w-1 and g[y][x+1] == '.':
            next.append((x+1, y, d+1, {*p, (x+1,y)}))
        if x < w-2 and g[y][x+1] == '>':
            next.append((x+2, y, d+2, {*p, (x+1,y), (x+2,y)}))
        if y > 0 and g[y-1][x] == '.':
            next.append((x, y-1, d+1, {*p, (x,y-1)}))
        if y > 1 and g[y-1][x] == '^':
            next.append((x, y-2, d+2, {*p, (x,y-1), (x,y-2)}))
        if y < h-1 and g[y+1][x] == '.':
            next.append((x, y+1, d+1, {*p, (x,y+1)}))
        if y < h-2 and g[y+1][x] == 'v':
            next.append((x, y+2, d+2, {*p, (x,y+1), (x,y+2)}))
    return [np for np in next if not (np[0], np[1]) in p]

def findLongestPath1(sx, sy, ex, ey):
    visited = [[-1 for _ in range(w)] for _ in range(h)]
    q = [(sx, sy, 0, {(sx, sy)})]
    while q:
        x, y, d, p = q.pop()
        if visited[y][x] > d: continue
        visited[y][x] = d
        if x == ex and y == ey: continue
        q += getNextPositions(x,y,d,p,1)
    return visited[ey][ex]

# For part 1 you can only go in one direction from a multi way
print(f'Answer Part 1: {findLongestPath1(sx, sy, ex, ey)}')

# find intersections
inters = {(sx, sy), (ex, ey)}
for y in range(1,h-1):
    for x in range(1,w-1):
        if g[y][x] == '#': continue
        if len(getNextPositions(x,y,0,{},2)) > 2: inters.add((x,y))

def findDists(sx,sy,inters):
    dists, q = {}, [(sx, sy, 0, {(sx,sy)})]
    while q:
        x,y,d,p = q.pop(0)
        if (x,y) in inters: dists[(x,y)] = d
        else: q += getNextPositions(x,y,d,p,2)
    return dists

edges = {}
for p in inters:
    other = set(x for x in inters if x != p)
    edges[p] = findDists(p[0], p[1], other)

count = 1
def findLongestPath2(sp, ep, d, seen):
    global count
    count += 1
    if count % 3000000 == 0: print(count)
    if sp == ep: return d
    maxDist = -1
    for np, nd in edges[sp].items():
        if np in seen: continue
        dist = findLongestPath2((np[0], np[1]), ep, d+nd, {*seen, np})
        if dist != -1: maxDist = max(maxDist, dist)
    return maxDist

print(f'Answer Part 2: {findLongestPath2((sx, sy), (ex, ey), 0, set((sx,sy)))}')