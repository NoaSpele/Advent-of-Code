import sys

lines = open(sys.argv[1]).read().split('\n')
h, w = len(lines), len(lines[0])
g = [[c for c in row] for row in lines]

def tilt(g, dx, dy, flipedX, flipedY):
    for i in range(h):
        y = h-1 - i if flipedY else i
        for j in range(w):
            x = w-1 - j if flipedX else j
            if g[y][x] != 'O': continue
            cx, cy = x, y
            while 0<=cy+dy<h and 0<=cx+dx<w:
                if g[cy+dy][cx+dx] == '.':
                    cy += dy
                    cx += dx
                else: break
            g[y][x], g[cy][cx] = '.', 'O'
    return g

def getPre(g):
    tot = 0
    for y, row in enumerate(g):
        tot += (h-y) * len([c for c in row if c == 'O'])
    return tot

g = tilt(g, 0, -1, False, False) # N
print(f'Answer Part 1: {getPre(g)}')
g = tilt(g, -1, 0, False, False) # W
g = tilt(g, 0, 1, False, True) # S
g = tilt(g, 1, 0, True, False) # E

numCycles, scores, cl = 1, [], -1
while True:
    g = tilt(g, 0, -1, False, False)
    g = tilt(g, -1, 0, False, False)
    g = tilt(g, 0, 1, False, True)
    g = tilt(g, 1, 0, True, False)
    numCycles += 1
    # Assuming that cycle starts before 150
    if numCycles > 150:
        scores.append(getPre(g))
        if len(scores) % 2 == 0:
            half = int(len(scores)/2)
            if scores[:half] == scores[half:]:
                cl = half
                break

# end - numCycles = how many more we need to do
# answer is value at idx = offset - 1
offset = (1000000000 - (numCycles + 1)) % cl
print(f'Answer Part 2: {scores[offset]}')