import sys
# Shoelace formula: A = abs( 1/2 * sum( xi * yi+1 - xi+1 * xi for i in range(0,n)) )
#  - Note this calculates the area of a simple polygon in a coordinate plane
#    Due to the nature of the problem, the shoelace formula calculates the area of the mid points of
#    each square A' (since the coordinate is the first edge of each square). To fill
#    the missing area we need to add 1/2 for each square in the outer layer
#    and also add 1 for the corners (essentially because we warp one turn, which
#    leads to more missing area) => A = A' + b'/2 + 1 (b' = num boxes in edges)

# Pick's theorem: A = #interior points + #boundary points / 2 - 1
# If A' = i' + b'/2 - 1 => A' + b'/2 + 1 = A' - b'/2 + 1 + b' = i' + b'
# Using Pick's theorem it can also be shown that A = A' - b'/2 + 1 + b' = i' + b'
# This is saying that if you extend the shape with 0.5 in each direction the new area is now
# 1 for each interior point and 1 for each boundary point

lines = open(sys.argv[1]).read().split('\n')
x, y, x2, y2 = 0, 0, 0, 0
px1, py1, px2, py2 = 0,0,0,0
A1, l1, A2, l2 = 0,0,0,0
for line in lines:
    d, num, c = line.split()
    num, c = int(num), c[1:-1]
    if   d == 'R': x += num
    elif d == 'L': x -= num
    elif d == 'U': y += num
    else:          y -= num
    l1 += num
    A1 += (px1 * y) - (x * py1)
    px1, py1 = x, y

    num2 = int('0x' + c[1:-1], 16)
    d2 = 'RDLU'[int(c[-1])]
    if   d2 == 'R': x2 += num2
    elif d2 == 'L': x2 -= num2
    elif d2 == 'U': y2 += num2
    else:           y2 -= num2
    l2 += num2
    A2 += (px2 * y2) - (x2 * py2)
    px2, py2 = x2, y2

print(f'Answer Part 1: {int(abs(A1/2) + l1/2 + 1)} = {int(l1 + abs(A1/2) - l1/2 + 1)}')
print(f'Answer Part 2: {int(abs(A2/2) + l2/2 + 1)} = {int(l2 + abs(A2/2) - l2/2 + 1)}')