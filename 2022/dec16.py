import re
import sys
import math
import time
from copy import deepcopy
from collections import deque

def getDists(node, interesting, valves):
    ret = {}
    v = [node]
    q = [(node, 0)]
    while len(q) > 0:
        curr = q.pop(0)
        for next in valves[curr[0]]['conns']:
            if not next in v:
                v.append(next)
                q.append((next, curr[1] + 1))
                if next in interesting:
                    ret[interesting.index(next)] = curr[1] + 1
    return ret

valves = {}
lines = open(sys.argv[1]).read().split('\n')
for line in lines:
    p1, p2 = line.split('; ')
    arr1 = p1.split(' ')
    name = arr1[1]
    flow = int(re.findall(r'\d+', arr1[4])[0])
    arr2 = p2.split(' ')[4:]
    conns = [s[0:2] for s in arr2]
    valves[name] = {'flow': flow, 'conns': conns}

dists = []
flows = []
interesting = ['AA'] + [k for k, x in valves.items() if x['flow'] > 0]
for i, node in enumerate(interesting):
    dists.append(getDists(node, interesting, valves))
    flows.append(valves[node]['flow'])

seen = {}
def findMax(pos, t, unopen):
    findMax.counter += 1
    if findMax.counter % 1000000 == 0:
        print(findMax.counter)

    if max(t) < 3:
        return 0

    if (*pos, *t, unopen) in seen:
        return seen[(*pos, *t, unopen)]
    if (*pos[::-1], *t[::-1], unopen) in seen:
        return seen[(*pos[::-1], *t[::-1], unopen)]

    idx = t.index(max(t))
    ps = [0]
    for next, dist in dists[pos[idx]].items():
        bit = 1 << next
        if not unopen & bit and t[idx] > dist + 2:
            newT = [*t]
            newPos = [*pos]
            newT[idx] = t[idx] - (dist + 1)
            newPos[idx] = next
            ps.append(newT[idx] * flows[next] + findMax(newPos, newT, unopen | bit))

    seen[(*pos, *t, unopen)] = max(ps)
    return max(ps)

findMax.counter = 0
ans1 = findMax([0], [30], 1) # 1 means 'AA' is open since flow = 0
print(f'Answer Part 1: {ans1}')

seen = {}
start = time.time()
ans2 = findMax([0, 0], [26, 26], 1)
end = time.time()
print(f'Answer Part 2: {ans2} it took: {end-start}')