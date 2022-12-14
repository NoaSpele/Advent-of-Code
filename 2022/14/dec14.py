import sys

def addPath(p1, p2):
    if (p1[0] - p2[0]) == 0:
        for i in range(min(p1[1], p2[1]), max(p1[1], p2[1]) + 1):
            state.add((p1[0],i))
    elif (p1[1] - p2[1]) == 0:
        for i in range(min(p1[0], p2[0]), max(p1[0], p2[0]) + 1):
            state.add((i,p1[1]))

def addSand(part):
    if part == 2 and ((500, 0) in state):
        return False
    currPos = startPos
    maxY = maxDepth
    if part == 2:
        maxY += 1
    while currPos[1] < maxY:
        x = currPos[0]
        y = currPos[1]
        if not (x, y+1) in state:
            currPos = [x, y+1]
        elif not (x-1, y+1) in state:
            currPos = [x-1, y+1]
        elif not (x+1, y+1) in state:
            currPos = [x+1, y+1]
        else:
            state.add((x, y))
            return True
    if part == 1:
        return False
    state.add((currPos[0], currPos[1]))
    return True

state = set()
maxDepth = -1
startPos = [500, 0]
ans1 = 0
ans2 = 0

for line in sys.stdin:
    if "\n" in line:
        line = line[0:-1]

    prev = None
    for c in line.split(" -> "):
        x = int(c.split(",")[0])
        y = int(c.split(",")[1])
        if y > maxDepth:
            maxDepth = y
        if prev is not None:
            addPath([x, y], prev)
        prev = [x, y]

initialState = set(state)
while addSand(1):
    ans1 += 1

state = set(initialState)
while addSand(2):
    ans2 += 1

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')