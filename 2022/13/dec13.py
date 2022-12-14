import sys

def findParts(string):
    ret = []
    while(len(string) > 0):
        curr = string[0]
        if curr.isnumeric():
            idx2 = string.find(",")
            if idx2 == -1:
                ret.append(int(string))
                string = ""
            else:
                ret.append(int(string[:idx2]))
                string = string[idx2+1:]
        elif curr == "[":
            idx2 = findClosing(string[1:])
            # +1 to remove ]
            ret.append(findParts(string[1:idx2+1]))
            string = string[idx2+1:]
        else:
            string = string[1:]
    return ret

def findClosing(string):
    count = 0
    for idx, c in enumerate(string):
        if (c == "]" and count == 0):
            return idx
        elif c == "]":
            count -= 1
        elif c == "[":
            count += 1
    return -1

def compare(a, b):
    if isinstance(a, list) and isinstance(b, list):
        while (len(a) > 0) or (len(b) > 0):
            if len(a) == 0:
                return 1
            elif len(b) == 0:
                return -1
            check = compare(a[0], b[0])
            if check != 0:
                return check
            a = a[1:]
            b = b[1:]
        return 0
    elif isinstance(a, list):
        return compare(a, [b])
    elif isinstance(b, list):
        return compare([a], b)
    if a < b:
        return 1
    elif a > b:
        return -1
    return 0

packets = []
for line in sys.stdin:
    if line == "\n":
        continue
    # remove [ ] and \n
    if "\n" in line:
        packets.append(findParts(line[1:-2]))
    else:
        packets.append(findParts(line[1:-1]))

ans1 = 0
ans2 = 0
for i in range(len(packets)//2):
    p1 = packets[2*i]
    p2 = packets[2*i+1]
    ret = compare(p1, p2)
    if ret == 1:
        ans1 += (i+1)

divPack1 = [[2]]
divPack2 = [[6]]
packets.append(divPack1)
packets.append(divPack2)

sorted = []
while(len(packets) > 0):
    smallest = packets[0]
    for pack in packets[1:]:
        if compare(pack, smallest) == 1:
            smallest = pack
    sorted.append(smallest)
    packets.remove(smallest)

ans2 = (sorted.index(divPack1)+1) * (sorted.index(divPack2)+1)

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')