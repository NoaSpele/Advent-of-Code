import sys

ans1, ans2 = 0, 0
G = []
nums = []
gears = {}
lines = open(sys.argv[1]).read().split('\n')
for r, line in enumerate(lines):
    row = []
    ds = -1
    for c, col in enumerate(line):
        if col.isdigit():
            if ds == -1:
                ds = c
        else:
            if ds != -1:
                nums.append((r,ds,c-1))
                ds = -1
        row.append(col)
    if ds != -1:
        nums.append((r, ds, c))
    G.append(row)

w = len(G[0])
h = len(G)
for (r,cs,ce) in nums:
    isPartNum = False
    toCheck = []
    toCheck += [(cs-1,r-1),(cs-1,r),(cs-1,r+1)]
    toCheck += [(ce+1,r-1),(ce+1,r),(ce+1,r+1)]
    val = G[r][cs:ce+1]
    val = int(''.join(val))
    for i in range(cs, ce+1):
        toCheck += [(i,r+1), (i,r-1)]
    for x, y in toCheck:
        if 0<=x<w and 0<=y<h:
            if G[y][x] != '.':
                isPartNum = True
                if G[y][x] == '*':
                    if (x,y) in gears:
                        ans2 += gears[(x,y)] * val
                    else:
                        gears[(x,y)] = val
    if isPartNum:
        ans1 += val

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')