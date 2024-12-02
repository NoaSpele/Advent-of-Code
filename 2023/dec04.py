import sys

ans1, ans2 = 0, 0
winnings, numNums, numCard = [], [], []
lines = open(sys.argv[1]).read().split('\n')
for card in lines:
    curr, numWon = 0, 0
    won, nums = card.split(': ')[1].split(' | ')
    won = [int(x) for x in won.split(' ') if x != '']
    nums = [int(x) for x in nums.split(' ') if x != '']

    for num in nums:
        if num in won:
            numWon += 1
            if curr == 0:
                curr = 1
            else:
                curr *= 2
    ans1 += curr
    winnings.append(curr)
    numNums.append(numWon)
    numCard.append(1)

for i, win in enumerate(winnings):
    ans2 += numCard[i]
    if win > 0:
        for _ in range(numCard[i]):
            for j in range(i+1,i+1+numNums[i]):
                numCard[j] += 1

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')