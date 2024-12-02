import sys
import sympy

lines = open(sys.argv[1]).read().split('\n')
paths = []
for line in lines:
    pos, vel = line.split(' @ ')
    x, y, z = (int(x) for x in pos.split(', '))
    dx, dy, dz = (int(x) for x in vel.split(', '))
    k = dy/dx
    m = y - k*x
    paths.append((k, m, x, y, z, dx, dy, dz))

# l, h = 7, 27
l, h, ans1 = 200000000000000, 400000000000000, 0
for i, p1 in enumerate(paths):
    for p2 in paths[i+1:]:
        if p1[0] == p2[0]: continue
        # y1 = k1x1 + m1, y2 = k2x2 + m2
        # k1x + m1 = k2x + m2
        # (k1-k2)x = m2-m1
        # x = (m2-m1)/(k1-k2)
        # find intersection
        ix = (p2[1]-p1[1])/(p1[0]-p2[0])
        iy = p1[0]*ix + p1[1]

        # Check if intersection is in future
        t1 = (ix - p1[2]) / p1[5]
        t2 = (ix - p2[2]) / p2[5]
        if l<=ix<=h and l<=iy<=h and t1 > 0 and t2 > 0: ans1 += 1
print(f'Answer Part 1: {ans1}')

# use sympy to:
#   - define symbols: x, y, z, ... = sympy.symbols("x, y, z, ...")
#   - solve equeations: sympy.solve(<list of questions>)
# Note that sympy needs equations with = 0
x0, y0, z0, dx0, dy0, dz0 = sympy.symbols("x0, y0, z0, dx0, dy0, dz0")
tSym = [sympy.symbols("t" + str(i)) for i in range(3)]

eqs = []
# This assumes that only one integer solution exists for first 3 lines
# this assumption is made since this is the least amount of lines needed
# for a unique solution, since it is the first time #eqs >= #unknowns
for i, p in enumerate(paths[:3]):
    _, _, x, y, z, dx, dy, dz = p
    eqs.append(x0 + tSym[i]*dx0 - x - tSym[i]*dx)
    eqs.append(y0 + tSym[i]*dy0 - y - tSym[i]*dy)
    eqs.append(z0 + tSym[i]*dz0 - z - tSym[i]*dz)

ans = sympy.solve(eqs)
assert len(ans) == 1
print(f'Answer Part 2: {ans[0][x0] + ans[0][y0] + ans[0][z0]}')