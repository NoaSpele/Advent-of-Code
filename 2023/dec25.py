import sys
from copy import deepcopy

lines = open(sys.argv[1]).read().split('\n')
wires, cons = {}, []
for line in lines:
    name, cs = line.split(': ')
    cs = cs.split(' ')
    if not name in wires:
        wires[name] = len(wires)
    for con in cs:
        if not con in wires:
            wires[con] = len(wires)
        cons.append((name, con))

E = [[-1 for _ in range(len(wires))] for _ in range(len(wires))]
for v1, v2 in cons:
    E[ wires[v1] ][ wires[v2] ] = 1
    E[ wires[v2] ][ wires[v1] ] = 1

edgeWeights = {}
def bfs(sv, E):
    q, s = [], set([sv])
    for i in [x for x, e in enumerate(E[sv]) if e == 1]:
        q.append((i, [sv, i]))
    while q:
        v, p = q.pop(0)
        if v in s: continue
        s.add(v)
        for i in range(len(p)-1):
            k = (p[i], p[i+1])
            if not k in edgeWeights: edgeWeights[k] = 0
            edgeWeights[k] += 1
        for i in [x for x, e in enumerate(E[v]) if e == 1]:
            q.append((i, [*p, i]))
    # print(s)
    return s

# Do bfs from each vertex to find shortest path to all other vertecies
# for each path add 1 to each edge in the path
print("Started to count usage of edges in shortest paths")
wireIdx = list(wires.items())
curr = 0
for i in range(0, len(wires), 5):
    curr += 1
    if curr % 50 == 0: print(curr, '/', len(wires)//5)
    v = wireIdx[i][1]
    bfs(v, E)

# find the top X edges used, which should be the three conncecting the components
edges = []
for i in range(len(wires)):
    for j in range(i+1, len(wires)):
        if not (i,j) in edgeWeights and not (j,i) in edgeWeights: continue
        tot = 0
        if (i, j) in edgeWeights: tot += edgeWeights[(i,j)]
        if (j, i) in edgeWeights: tot += edgeWeights[(j,i)]
        edges.append((tot, i, j))

edges = sorted(edges)[-10:][::-1]

# remove combinations of these vertecies to find the component sizes.
def findCut():
    for i in range(len(edges)-2):
        for j in range(i+1, len(edges)-1):
            for k in range(j+1, len(edges)):
                currE = deepcopy(E)
                for e in [edges[i], edges[j], edges[k]]:
                    currE[e[1]][e[2]] = -1
                    currE[e[2]][e[1]] = -1
                found = bfs(0, currE)
                if len(found) != len(wires):
                    print("found edges to remove: ", end = ' ')
                    print('(' + wireIdx[edges[i][1]][0] + ', ' + wireIdx[edges[i][2]][0] + '), ', end = '')
                    print('(' + wireIdx[edges[j][1]][0] + ', ' + wireIdx[edges[j][2]][0] + '), ', end = '')
                    print('(' + wireIdx[edges[k][1]][0] + ', ' + wireIdx[edges[k][2]][0] + ')')
                    return len(found) * (len(wires) - len(found))
    return -1

print(f'Answer Part 1: {findCut()}')