def solve(seq):
    if all(x==0 for x in seq): return 0
    return seq[-1] + solve([a-b for a,b in zip(seq[1:], seq[:-1])])

lines = [[int(x) for x in l.split()] for l in open(0).read().split('\n')]
print(f'Part 1: {sum(solve(l) for l in lines)}')
print(f'Part 2: {sum(solve(l[::-1]) for l in lines)}')