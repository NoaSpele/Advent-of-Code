import sys

def findSymmetry(g):
    a1, a2 = 0, 0
    for r, (r1, r2) in enumerate(zip(g[:-1], g[1:])):
        r1, r2, diff = r, r+1, 0
        while r1 >= 0 and r2 < len(g):
            diff += len([x for x,y in zip(g[r1], g[r2]) if x != y])
            r1, r2 = r1 - 1, r2 + 1
        if diff == 0: a1 = r+1
        if diff == 1: a2 = r+1
    return a1, a2

ans1, ans2 = 0, 0
parts = open(sys.argv[1]).read().split('\n\n')
for part in parts:
    g = [[x for x in line] for line in part.split('\n')]
    r1, r2 = findSymmetry(g)
    c1, c2 = findSymmetry(list(zip(*g))) # = transpose of g
    ans1 += 100 * r1 + c1
    ans2 += 100 * r2 + c2

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')