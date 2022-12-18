import sys
from collections import deque

ans1 = 0; ans2 = 0;
maxX = 0; maxY = 0; maxZ = 0
cubes = set()

for line in sys.stdin:
    x = int(line.split(",")[0])
    maxX = max(maxX, x)
    y = int(line.split(",")[1])
    maxY = max(maxY, y)
    z = int(line.split(",")[2])
    maxZ = max(maxZ, z)
    cubes.add((x,y,z))

# Part 1
for x, y, z in cubes:
    area = 6
    if (x-1,y,z) in cubes:
        area -= 1
    if (x+1,y,z) in cubes:
        area -= 1
    if (x,y-1,z) in cubes:
        area -= 1
    if (x,y+1,z) in cubes:
        area -= 1
    if (x,y,z-1) in cubes:
        area -= 1
    if (x,y,z+1) in cubes:
        area -= 1
    ans1 += area

def isTrapped(x, y, z):
    seen = set()
    queue = deque()
    queue.append((x,y,z))
    while len(queue) > 0:
        cx, cy, cz = queue.popleft()
        # No negative input
        if (cx < 0 or cx > maxX) or (cy < 0 or cy > maxY) or (cz < 0 or cz > maxZ):
            return False
        if ((cx,cy,cz) in cubes) or ((cx,cy,cz) in seen):
            continue
        seen.add((cx,cy,cz))
        queue.append((cx-1,cy,cz))
        queue.append((cx+1,cy,cz))
        queue.append((cx,cy-1,cz))
        queue.append((cx,cy+1,cz))
        queue.append((cx,cy,cz-1))
        queue.append((cx,cy,cz+1))
    return True

# Part 2
numTrapped = 0;
for x, y, z in cubes:
    if isTrapped(x-1,y,z):
        numTrapped += 1
    if isTrapped(x+1,y,z):
        numTrapped += 1
    if isTrapped(x,y-1,z):
        numTrapped += 1
    if isTrapped(x,y+1,z):
        numTrapped += 1
    if isTrapped(x,y,z-1):
        numTrapped += 1
    if isTrapped(x,y,z+1):
        numTrapped += 1

ans2 = len(cubes)*6 - numTrapped
print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')