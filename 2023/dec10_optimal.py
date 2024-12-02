import sys

def getDirs(pipe):
    # [up, duown, left, right]
    if   pipe == '|': return [1,1,0,0]
    elif pipe == '-': return [0,0,1,1]
    elif pipe == 'L': return [1,0,0,1]
    elif pipe == 'J': return [1,0,1,0]
    elif pipe == '7': return [0,1,1,0]
    elif pipe == 'F': return [0,1,0,1]
    elif pipe == '.' or pipe == 'S':
        return [0,0,0,0]
    else:
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

ans2 = 0
posInCycle = set([x for x in dists])
for r in range(h):
    inside, offset = False, 0
    for c in range(w):
        if not (c,r) in posInCycle:
            if inside: ans2 += 1
        else:
            if m[r][c][0] and m[r][c][1]:
                inside = not inside
                offset = 0
            elif m[r][c][0]:
                if offset == 1:
                    inside = not inside
                offset = -1 if offset == 0 else 0
            elif m[r][c][1]:
                if offset == -1:
                    inside = not inside
                offset = 1 if offset == 0 else 0

print(f'Answer Part 2: {ans2}')