import sys

ans1, ans2 = 0, 0
lines = open(sys.argv[1]).read().split('\n')
m, gs, er, ec = [], [], [], []
for r, line in enumerate(lines):
    row = []
    for c, ch in enumerate(line):
        row.append(ch)
        if ch == '#':
            gs.append([c,r])
    if not '#' in row:
        er.append(r)
    m.append(row)

for i in range(len(m[0])):
    if not '#' in [r[i] for r in m]:
        ec.append(i)

for i in range(len(gs) - 1):
    for j in range(i+1, len(gs)):
        minX, maxX = min(gs[i][0], gs[j][0]), max(gs[i][0], gs[j][0])
        minY, maxY = min(gs[i][1], gs[j][1]), max(gs[i][1], gs[j][1])
        d = maxX-minX + maxY-minY
        ans1, ans2 = ans1 + d, ans2 + d
        for k in range(minX, maxX+1):
            if k in ec:
                ans1 += 1
                ans2 += 999999
        for k in range(minY, maxY+1):
            if k in er:
                ans1 += 1
                ans2 += 999999

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')