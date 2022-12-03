import sys

resultPoints = [0,3,6]
choicePoints = {"X":1,"Y":2,"Z":3}
wantedResult = {"X":0,"Y":1,"Z":2}
matchUps = {
    "AX":1, "AY":2, "AZ":0,
    "BX":0, "BY":1, "BZ":2,
    "CX":2, "CY":0, "CZ":1
}

totScore = 0
totScore2 = 0
score = 0
for line in sys.stdin:
    choices = line.split()
    opponent = choices[0]
    me = choices[1]

    # Part 1
    score = choicePoints[me] + resultPoints[matchUps[opponent+me]]
    totScore += score
    print(f'Part 1 - opponent: {opponent}, me: {me}, score: {score}')

    # Part 2
    test = ["X", "Y", "Z"]
    for curr in test:
        if (matchUps[opponent + curr] == wantedResult[me]):
            score = choicePoints[curr] + resultPoints[matchUps[opponent + curr]]
            totScore2 += score
            print(f'Part 2 - opponent: {opponent}, me: {curr}, score: {score}')
            break

    print()

print(f'Part 1 - Total score: {totScore}')
print(f'Part 2 - Total score: {totScore2}')