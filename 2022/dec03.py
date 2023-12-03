import sys

scores = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
scoreMap = {}
totScore = 0
totScore2 = 0

groupLines = []

for i in range(len(scores)):
    scoreMap[scores[i]] = i+1

for line in sys.stdin:
    # Line break seems to be added
    parts = line.split()
    input = parts[0]
    groupLines.append(input)
    l = len(input)
    h1 = input[0:l//2]
    h2 = input[l//2:l]
    match = ''
    for char1 in h1:
        if char1 in h2:
            match = char1
            totScore += scoreMap[match]
            break
    print(f'First half - {h1}')
    print(f'Second half - {h2}')
    print(f'Found match: {match}, score: {scoreMap[match]}')
    print()

    if len(groupLines) == 3:
        print("Group found!")
        common = []
        for c in groupLines[0]:
            if (c in groupLines[1]):
                common.append(c)

        for c in common:
            if (c in groupLines[2]):
                totScore2 += scoreMap[c]
                break
        groupLines = []

print(f'Part 1 - total score: {totScore}')
print(f'Part 2 - total score: {totScore2}')