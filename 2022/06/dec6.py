import sys

for line in sys.stdin:
    charSet = {}
    charSet2 = {}
    ans1 = 0
    ans2 = 0
    currPos = 0
    while currPos < len(line):
        # Part 1
        if not len(charSet.keys()) >= 4:
            if not line[currPos] in charSet:
                charSet[line[currPos]] = 1
            else:
                charSet[line[currPos]] = charSet[line[currPos]] + 1
            if currPos >= 4:
                if line[currPos - 4] in charSet:
                    charSet[line[currPos-4]] = charSet[line[currPos-4]] - 1
                    if charSet[line[currPos-4]] <= 0:
                        del charSet[line[currPos-4]]
            if len(charSet.keys()) >= 4:
                ans1 = currPos

        # # Part 1
        if not len(charSet2.keys()) >= 14:
            if not line[currPos] in charSet2:
                charSet2[line[currPos]] = 1
            else:
                charSet2[line[currPos]] = charSet2[line[currPos]] + 1
            if currPos >= 14:
                if line[currPos - 14] in charSet2:
                    charSet2[line[currPos-14]] = charSet2[line[currPos-14]] - 1
                    if charSet2[line[currPos-14]] <= 0:
                        del charSet2[line[currPos-14]]
            if len(charSet2.keys()) >= 14:
                ans2 = currPos
        currPos = currPos + 1
    print(f'Answer Part 1: {ans1 + 1}')
    print(f'Line: {line[currPos-4:currPos]}')
    
    print(f'Answer Part 2: {ans2 + 1}')
    print(f'Line: {line[currPos-14:currPos]}')
