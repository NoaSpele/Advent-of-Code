import sys

def hashStr(s):
    sum = 0
    for c in s:
        sum = ((sum + ord(c)) * 17) % 256
    return sum

ans1, ans2, boxes = 0, 0, {}
lines = open(sys.argv[1]).read().split('\n')
for s in lines[0].split(','):
    ans1 += hashStr(s)

    if '-' in s:
        label = s[:-1]
        hash = hashStr(label)
        if not hash in boxes: continue
        found = [x for x in boxes[hash] if x[0] == label]
        if found:
            boxes[hash].remove(found[0])
    elif '=' in s:
        label, f = s.split('=')
        hash, f = hashStr(label), int(f)
        if not hash in boxes:
            boxes[hash] = [[label, f]]
        else:
            found = [x for x in boxes[hash] if x[0] == label]
            if found:
                idx = boxes[hash].index(found[0])
                boxes[hash][idx] = [label, f]
            else:
                boxes[hash].append([label, f])

for key, lenses in boxes.items():
    for i, [_, f] in enumerate(lenses):
        ans2 += (key + 1) * (i + 1) * f

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')