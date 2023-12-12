import sys

dp = {}
def findNum(numToPlace, s, nums):
    if numToPlace <= 0 or not nums:
        return 1 if not '#' in s else 0
    if numToPlace > len(s):
        return 0

    key = (numToPlace, len(s), len(nums))
    if key in dp:
        return dp[key]

    cn, num = nums[0], 0
    if s[0] == '.' or s[0] == '?':
        num += findNum(numToPlace, s[1:], nums)

    if len(s) == cn and not '.' in s:
        num += findNum(numToPlace - cn, "", nums[1:])
    elif len(s) > cn and not '.' in s[:cn] and s[cn] !='#':
        numQ = len([x for x in s[:cn] if x == '?'])
        num += findNum(numToPlace - numQ, s[cn+1:], nums[1:])

    dp[key] = num
    return num


ans1, ans2 = 0, 0
lines = open(sys.argv[1]).read().split('\n')
for i, line in enumerate(lines):
    s, nums = line.split()
    nums = [int(x) for x in nums.split(',')]
    placed = len([x for x in s if x == '#'])
    numToPlace = sum(nums) - placed
    d1 = findNum(numToPlace, s, nums)
    d2 = findNum(numToPlace*5, (('?' + s)*5)[1:], nums * 5 )
    ans1 += d1
    ans2 += d2
    dp.clear()

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')