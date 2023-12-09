import sys

ans1, ans2 = 0, 0
lines = open(sys.argv[1]).read().split('\n')
for line in lines:
    currSeq = [int(x) for x in line.split()]
    seqs = [currSeq]
    while not all(x == 0 for x in currSeq):
        newSeq = []
        for i in range(1,len(currSeq)):
            newSeq.append(currSeq[i] - currSeq[i-1])
        seqs.append(newSeq)
        currSeq = newSeq

    next, prev = 0, 0
    for d1, d2 in [(x[-1], x[0]) for x in seqs[::-1][1:]]:
        next += d1
        prev = d2 - prev
    ans1 += next
    ans2 += prev

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')