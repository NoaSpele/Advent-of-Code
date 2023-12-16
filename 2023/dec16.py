import sys

lines = open(sys.argv[1]).read().split('\n')
g = [[c for c in row] for row in lines]
h, w = len(g), len(g[0])
dirFS = {(1,0):(0,-1), (-1,0):(0,1), (0,1):(-1,0), (0,-1):(1,0)}
dirBS = {(1,0):(0,1), (-1,0):(0,-1), (0,1):(1,0), (0,-1):(-1,0)}

def findEnergized(start, h, w):
    v = set()
    q = [start]
    while q:
        x,y,dx,dy = q.pop(0)
        if (x,y,dx,dy) in v: continue
        if x<0 or x>w-1 or y<0 or y>h-1: continue
        v.add((x,y,dx,dy))
        if g[y][x] == '/':
            dx2, dy2 = dirFS[(dx,dy)]
            q.append((x + dx2, y + dy2, dx2, dy2))
        elif g[y][x] == '\\':
            dx2, dy2 = dirBS[(dx,dy)]
            q.append((x + dx2, y + dy2, dx2, dy2))
        elif g[y][x] == '|' and dx != 0:
            q.append((x,y-1,0,-1))
            q.append((x,y+1,0,1))
        elif g[y][x] == '-' and dy != 0:
            q.append((x-1,y,-1,0))
            q.append((x+1,y,1,0))
        else:
            q.append((x+dx,y+dy,dx,dy))

    return len(set((x,y) for x,y,_,_ in v))

print(f'Answer Part 1: {findEnergized((0,0,1,0),h,w)}')
numEn = []
for y in range(h):
    numEn.append(findEnergized((0,y,1,0), h, w))
    numEn.append(findEnergized((w-1,y,-1,0), h, w))

for x in range(w):
    numEn.append(findEnergized((x,0,0,1), h, w))
    numEn.append(findEnergized((x,h-1,0,-1), h, w))

print(f'Answer Part 2: {max(numEn)}')