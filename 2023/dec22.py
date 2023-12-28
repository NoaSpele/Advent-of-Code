import sys

def getPoints(p1, p2):
    points = []
    x1, y1, z1 = p1
    x2, y2, z2 = p2
    for x in range(min(x1, x2), max(x1, x2)+1):
        for y in range(min(y1, y2), max(y1, y2)+1):
            for z in range(min(z1, z2), max(z1, z2)+1):
                points.append((x, y, z))
    return points

def canMove(org, points):
    for p in points:
        if p[2] < 1: return False, org
        if p in map and not p in org: return False, org
    return True, points

lines = open(sys.argv[1]).read().split('\n')
map = set()
bricks = []
for line in lines:
    p1, p2 = line.split('~')
    x1, y1, z1 = [int(i) for i in p1.split(',')]
    x2, y2, z2 = [int(i) for i in p2.split(',')]

    # No points intersect
    # bricks only extends in two dims
    p1, p2 = (x1, y1, z1), (x2, y2, z2)
    points = getPoints(p1, p2)
    stable = any(z == 1 for _,_,z in points)
    for x,y,z in points:
        map.add((x,y,z))
    bricks.append({'s':stable, 'p1':p1, 'p2':p2})

print("==== Bricks are falling ====")
while True:
    anyMoved = False
    for idx, brick in enumerate(bricks):
        if brick['s']: continue
        x1, y1, z1 = brick['p1']
        x2, y2, z2 = brick['p2']
        points = getPoints((x1, y1, z1), (x2, y2, z2))
        for p in points: map.remove(p)
        moved = getPoints((x1, y1, z1-1), (x2, y2, z2-1))
        can, newPoints = canMove(points, moved)
        for p in newPoints: map.add(p)
        if can:
            anyMoved = True
            brick['p1'] = (x1, y1, z1-1)
            brick['p2'] = (x2, y2, z2-1)
    if not anyMoved: break

ans1, ans2 = 0, 0
print("==== Checking if disintigrating causes movement ====")
for idx, brick in enumerate(bricks):
    if (idx+1) % 100 == 0: print(idx+1)
    canBeDis = True
    x1, y1, z1 = brick['p1']
    x2, y2, z2 = brick['p2']
    points = getPoints((x1, y1, z1), (x2, y2, z2))
    for p in points: map.remove(p)

    hasMoved = set()
    orgp, movp = [], []
    bricks2 = bricks[:idx] + bricks[idx+1:]
    minZ = min(z1, z2)
    while True:
        anyMoved = False
        for idx2, b2 in enumerate(bricks2):
            if idx2 in hasMoved: continue
            xx1, yy1, zz1 = b2['p1']
            xx2, yy2, zz2 = b2['p2']
            if zz1 < minZ or zz2 < minZ: continue
            org = getPoints((xx1, yy1, zz1), (xx2, yy2, zz2))
            p2 = getPoints((xx1, yy1, zz1-1), (xx2, yy2, zz2-1))
            can, _ = canMove(org, p2)
            if can:
                for p in org: map.remove(p)
                for p in p2: map.add(p)
                orgp += org
                movp += p2
                hasMoved.add(idx2)
                anyMoved = True
                canBeDis = False
                break
        if not anyMoved: break

    ans2 += len(hasMoved)
    for p in movp: map.remove(p)
    for p in orgp: map.add(p)
    if canBeDis: ans1 += 1
    for p in points: map.add(p)

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')