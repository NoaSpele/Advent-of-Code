import sys

ans1, ans2 = 0, 0
lines = open(sys.argv[1]).read().split('\n')
g, sx, sy = [], None, None
for y, row in enumerate(lines):
    for x, c in enumerate(row):
        if c == 'S': sx, sy = x, y
    g += [[c for c in row]]
h, w = len(g), len(g[0])

L1, stepLimit = 64, 26501365
seen, seen1, dists, gs = set(), set(), {}, 3
q = [(sx, sy, 0)]
test = 0
while q:
    x, y, d = q.pop(0)
    if g[y%h][x%w] == '#': continue
    if (2*gs+1)*h < stepLimit and (abs(x//w) > gs or abs(y//h) > gs): continue
    if (x//w, x%w, y//h, y%w, d%2) in seen or d > stepLimit: continue
    if d <= L1 and x//w == 0 and x//h == 0:
        seen1.add((x, y, d%2))
    seen.add((x//w, x%w, y//h, y%h, d%2))
    dists[(x//w, x%w, y//h, y%h)] = d
    for dx, dy in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
        q.append((x+dx, y+dy, d+1))

ans1 = len(set([(x,y) for x, y, d in seen1 if d % 2 == L1 % 2]))
numCorrect, numIncorrect, numSide = 0, 0, stepLimit//h - 1 # last might not reach all of square
for y in range(h):
    for x in range(w):
        if g[y][x] == '#' or not (0,x,0,y) in dists: continue

        for dx, dy in [(gs,gs), (gs,-gs), (-gs,gs), (-gs,-gs)]:
            if not (dx,x,dy,y) in dists: continue
            d = (numSide-2*gs+1) * h + dists[(dx,x,dy,y)]
            if d % 2 == stepLimit % 2 and d <= stepLimit:
                ans2 += numSide
            if (d + h) % 2 == stepLimit % 2 and d + h <= stepLimit:
                ans2 += numSide + 1

        for dx, dy in [(gs,0), (-gs,0), (0,gs), (0,-gs)]:
            if not (dx,x,dy,y) in dists: continue
            d = (numSide - gs+1)*h + dists[(dx,x,dy,y)]
            if (d-h)%2 == 0 and d-h > stepLimit+1:
                ans2 -= 1
            if d <= stepLimit and d % 2 == stepLimit % 2:
                ans2 += 1

        if (gs,x,gs,y) in dists:
            d = dists[(gs,x,gs,y)]
            if (d % 2) != stepLimit % 2: numIncorrect += 1
            if (d % 2) == stepLimit % 2: numCorrect += 1

half = ((2*gs+1)**2-1)/2
smallBox, largeBox = numSide**2, (numSide+1)**2
cl = smallBox if numSide%2 == 1 else largeBox
il = largeBox if numSide%2 == 1 else smallBox
cbs, ibs = cl - (half+1), il - half
ans2 += cbs * numCorrect + ibs * numIncorrect
ans2 += len(set([(xd, xm, yd, ym) for xd, xm, yd, ym, d in seen if (d % 2) == (stepLimit % 2)]))

if (2*gs+1)*h > stepLimit:
    ans2 = len(set([(xd, xm, yd, ym) for xd, xm, yd, ym, d in seen if (d % 2) == (stepLimit % 2)]))
print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {int(ans2)}')