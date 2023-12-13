import sys

ans1, ans2 = 0, 0
parts = open(sys.argv[1]).read().split('\n\n')
for part in parts:
    g = [[x for x in line] for line in part.split('\n')]
    h, w = len(g), len(g[0])
    for c in range(w-1):
        c1, c2, diff = c, c+1, 0
        while c1 >= 0 and c2 < w:
            p1 = [x[c1] for x in g]
            p2 = [x[c2] for x in g]
            diff += len([x for x,y in zip(p1, p2) if x != y])
            c1, c2 = c1 - 1, c2 + 1
        if diff == 0: ans1 += c+1
        if diff == 1: ans2 += c+1

    for r, (r1, r2) in enumerate(zip(g[:-1], g[1:])):
        r1, r2, diff = r, r+1, 0
        while r1 >= 0 and r2 < h:
            diff += len([x for x,y in zip(g[r1], g[r2]) if x != y])
            r1, r2 = r1 - 1, r2 + 1
        if diff == 0: ans1 += 100*(r+1)
        if diff == 1: ans2 += 100*(r+1)

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')