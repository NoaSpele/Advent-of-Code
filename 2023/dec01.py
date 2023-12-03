import re
import sys

ans1, ans2 = 0, 0
# reg = r'(?=(one|two|three|four|five|six|seven|eight|nine|\d))'
reg1, reg2 = r'\d', 'one|two|three|four|five|six|seven|eight|nine'
map = {'one':1, 'two':2, 'three':3, 'four':4, 'five':5, 'six':6, 'seven':7, 'eight':8, 'nine':9}

lines = open(sys.argv[1]).read().split('\n')
for line in lines:
    p1 = re.findall(r'\d', line)

    # p2 = re.findall(reg, line)
    # a = map[p2[0]] if p2[0] in map else int(p2[0])
    # b = map[p2[-1]] if p2[-1] in map else int(p2[-1])

    p21 = re.findall(reg1 + '|' + reg2, line)
    p22 = re.findall(reg1 + '|' + reg2[::-1], line[::-1])
    a = map[p21[0]] if p21[0] in map else int(p21[0])
    b = map[p22[0][::-1]] if p22[0][::-1] in map else int(p22[0])

    ans1 += int(p1[0] + p1[-1])
    ans2 += 10*a + b

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')