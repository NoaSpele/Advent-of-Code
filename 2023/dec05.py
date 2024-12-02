import sys

ans1, ans2 = None, None
parts = open(sys.argv[1]).read().split('\n\n')
seeds = [int(x) for x in parts[0].split(': ')[1].split()]

maps = []
for part in parts[1:]:
    lines = part.split('\n')
    name = lines[0][:-5]
    curr = []
    for line in lines[1:]:
        drs, srs, rl = [int(x) for x in line.split()]
        curr.append((drs, srs, rl))
    cmin = min([s1 for _, s1, _ in curr]) - 1
    if cmin >= 0:
        curr.append((0,0,cmin+1))
    maps.append(curr)

def findNew(val, m):
    for s2, s1, l in m:
        if s1<=val<=s1+l-1:
            return s2 + val - s1
    return val

for seed in seeds:
    num = seed
    for map in maps:
        num = findNew(num, map)
    if ans1 == None or num < ans1:
        ans1 = num

ranges = [[[s1, s1+l-1], [s2, s2+l-1]] for s2, s1, l in maps[0]]
for map in maps[1:]:
    newRanges = []
    while ranges:
        found = False
        r1, r2 = ranges.pop(0)
        s1, e1 = r1
        s2, e2 = r2
        for cs2, cs1, cl in map:
            ce1 = cs1+cl-1
            if s2<=cs1<=e2 or s2<=ce1<=e2 or cs1<=s2<=ce1 or cs1<=e2<=ce1:
                r1 = [s1, e1]
                r2 = [cs2, cs2+cl-1]
                if cs1 <= s2:
                    r2[0] += s2 - cs1
                else:
                    d = cs1 - s2
                    r1[0] += d
                    ranges.append([[s1,s1+d-1],[s2,s2+d-1]])

                if e2 <= ce1:
                    r2[1] -= ce1 - e2
                else:
                    d = e2 - ce1
                    r1[1] -= d
                    ranges.append([[e1-d+1,e1],[e2-d+1,e2]])

                if not [r1,r2] in newRanges:
                    newRanges.append([r1,r2])
                found = True
        if not found:
            newRanges.append([r1, r2])
    ranges = newRanges

ansIdx = -1
for i in range(len(seeds) // 2):
    start = seeds[i*2]
    len_ = seeds[i*2+1]
    end = start + len_ - 1
    val = start
    for r1, r2 in ranges:
        low = start
        if r1[0]<=start<=r1[1] or r1[0]<=end<=r1[1] or start<=r1[0]<=end or start<=r1[1]<=end:
            low = start if start >= r1[0] else r1[0]
            val = r2[0]+(low-r1[0])
        if ans2 == None or val < ans2:
            ansIdx = low
            ans2 = val

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2} ({ansIdx})')