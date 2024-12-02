import sys
import math

ans1, ans2 = 0, 0
parts = open(sys.argv[1]).read().split('\n\n')
m = {}
instructions, p2 = parts[0], parts[1]

starts = []
tToZ = []
for line in p2.split('\n'):
    name, dirs = line.split(' = ')
    l, r = dirs.split(', ')
    l, r = l[1:], r[:-1]
    m[name] = [l,r]
    if name[-1] == 'A':
        starts.append(name)
        tToZ.append(None)

def allFin(s, e):
    test = list(zip(s, e))
    return len([a for a, b in test if a != b]) == 0

node = 'AAA'
while node != 'ZZZ':
    for inst in [x for x in instructions]:
        if inst == 'R':
            node = m[node][1]
        else:
            node = m[node][0]
        ans1 += 1

done = False
while not done:
    for inst in [x for x in instructions]:
        ans2 += 1
        for i in range(len(starts)):
            if inst == 'R':
                starts[i] = m[starts[i]][1]
            else:
                starts[i] = m[starts[i]][0]
            if starts[i][-1] == 'Z':
                tToZ[i] = ans2
        if len([x for x in tToZ if x != None]) == len(tToZ):
            done = True
            break

ans2 = math.lcm(*tToZ)

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')