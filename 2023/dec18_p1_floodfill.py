import sys

lines = open(sys.argv[1]).read().split('\n')
x, y, tunnel = 0, 0, [[0,0]]
for line in lines:
    d, num, c = line.split()
    num, c = int(num), c[1:-1]
    for _ in range(num):
        if   d == 'R': x += 1
        elif d == 'L': x -= 1
        elif d == 'U': y += 1
        else:          y -= 1
        tunnel.append([x,y])

minY, maxY = min([y for (x,y) in tunnel]), max([y for (x,y) in tunnel])
minX, maxX = min([x for (x,y) in tunnel]), max([x for (x,y) in tunnel])
ts = set((x,y) for x, y in tunnel)
A = (maxX - minX + 3) * (maxY - minY + 3)

v = set()
q = [[minX-1, minY-1]]
while q:
    x, y = q.pop(0)
    if (x,y) in v or x<minX-1 or x > maxX+1 or y < minY-1 or y > maxY+1:
        continue
    v.add((x,y))
    for dx, dy in [[1,0],[-1,0],[0,1],[0,-1]]:
        if not (x+dx,y+dy) in ts:
            q.append([x+dx, y+dy])

ans1 = A - len(v)
print(f'Answer Part 1: {ans1}')