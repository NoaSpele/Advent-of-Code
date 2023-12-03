import sys

maxR, maxG, maxB, ans1, ans2 = 12, 13, 14, 0, 0
lines = open(sys.argv[1]).read().split('\n')
for i, line in enumerate(lines):
    rvs, gvs, bvs = [], [], []
    for round in line.split(': ')[1].split('; '):
        for cube in round.split(', '):
            v, c = cube.split(' ')
            v = int(v)
            if c == 'red':
                rvs.append(v)
            if c == 'green':
                gvs.append(v)
            if c == 'blue':
                bvs.append(v)
    if max(rvs) <= maxR and max(gvs) <= maxG and max(bvs) <= maxB:
        ans1 += i+1
    ans2 += max(rvs) * max(gvs) * max(bvs)

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')