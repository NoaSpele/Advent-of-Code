import sys

def findIdx(cost, q):
    idx = None
    for i in range(0, len(q)):
        if cost < q[i][0]:
            idx = i
            break
    if idx == None: return len(q)
    return idx

def solve(minStep, maxStep, g):
    h, w = len(g), len(g[0])
    # 0 = up, 1 = down, 2 = left, 3 = right
    s, q = set(), [(g[h-1][w-1], w-2, h-1, 2, 1), (g[h-1][w-1], w-1, h-2, 0, 1)]
    while q:
        # Note that q is a priority queue were the lowest value is the first element
        # that will be popped. This algorithm is Dijkstra's algorithm
        cost, x, y, d, ns = q.pop(0)
        if x == 0 and y == 0: return cost
        if (x, y, d, ns) in s: continue

        idx = findIdx(cost + g[y][x], q)
        if ns < minStep:
            dx, dy = [(0, -1), (0, 1), (-1, 0), (1, 0)][d]
            if 0<=x+dx<w and 0<=y+dy<h:
                q.insert(idx, (cost + g[y][x], x+dx, y+dy, d, ns+1))
                s.add((x, y, d, ns))
            continue

        s.add((x, y, d, ns))
        if x-1 >= 0 and d != 3 and not (d == 2 and ns == maxStep):
            q.insert(idx, (cost + g[y][x], x-1, y, 2, 1 if d != 2 else ns+1))
        if y-1 >= 0 and d != 1 and not (d == 0 and ns == maxStep):
            q.insert(idx, (cost + g[y][x], x, y-1, 0, 1 if d != 0 else ns+1))
        if x+1 < w and d != 2 and not (d == 3 and ns == maxStep):
            q.insert(idx, (cost + g[y][x], x+1, y, 3, 1 if d != 3 else ns+1))
        if y+1 < h and d != 0 and not (d == 1 and ns == maxStep):
            q.insert(idx, (cost + g[y][x], x, y+1, 1, 1 if d != 1 else ns+1))

lines = open(sys.argv[1]).read().split('\n')
g = [[int(c) for c in row] for row in lines]
print("Calculating part 1, it takes around 35 sek on my computer")
print(f'Answer Part 1: {solve(1, 3, g)}')
print("Calculating part 2, it takes around 9 min on my computer")
print(f'Answer Part 2: {solve(4, 10, g)}')