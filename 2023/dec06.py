import sys

ans1, ans2 = 1, 0
lines = open(sys.argv[1]).read().split('\n')
times, dists = lines[0].split(), lines[1].split()
times, dists = times[1:], dists[1:]

for i in range(len(times)):
    t, hs = int(times[i]), int(dists[i])
    curr = 0
    for j in range(1,t):
        cs = (t-j)*j
        if cs > hs:
            curr += 1
    ans1 *= curr

print(f'Answer Part 1: {ans1}')

time, dist = int(''.join(times)), int(''.join(dists))
mint = 0
for t in range(1,time):
    if (time-t)*t > dist:
        mint = t
        break

maxt, t = 0, time-1
while t > 1:
    if (time-t)*t > dist:
        maxt = t
        break
    t -= 1

ans2 = maxt - mint + 1
print(f'Answer Part 2: {ans2}')