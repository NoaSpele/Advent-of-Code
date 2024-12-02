import re
import sys

def isFullfiled(str, nums):
    placed = re.findall('#+', str)
    placed = [len(x) for x in placed]
    return placed == nums

dp = {}
def findNum(numToPlace, open, s, nums):
    if numToPlace == 0:
        return 1 if isFullfiled(s, nums) else 0
    if numToPlace > len(open):
        return 0

    sign = re.findall('#+', s[:open[0]])
    sign = [len(x) for x in sign[:-1]]
    if len(sign) > 0 and nums[:len(sign)] != sign:
        return 0

    key = (numToPlace, len(sign), open[0])
    if key in dp:
        return dp[key]
    num = 0
    for i, idx in enumerate(open):
        str2 = s[:idx] + '#' + s[idx+1:]
        newOpen = open[i+1:]
        num += findNum(numToPlace - 1, newOpen, str2, nums)
    dp[key] = num
    return num


ans1, ans2 = 0, 0
lines = open(sys.argv[1]).read().split('\n')
for i, line in enumerate(lines):
    if i>0 and i % 100 == 0:
        print(i)
    s, nums = line.split()
    nums = [int(x) for x in nums.split(',')]
    open = [i for i,x in enumerate(s) if x == '?']
    placed = [i for i,x in enumerate(s) if x == '#']
    numToPlace = sum(nums) - len(placed)
    d1 = findNum(numToPlace, open, s, nums)

    s5 = (('?' + s)*5)[1:]
    nums5 = nums * 5
    open = [i for i,x in enumerate(s5) if x == '?']
    d2 = findNum(numToPlace*5, open, s5, nums5)
    ans1 += d1
    ans2 += d2
    dp.clear()

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')