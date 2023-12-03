import re
import sys
import math
from copy import deepcopy
from collections import deque

ans1, ans2 = 0, 0
lines = open(sys.argv[1]).read().split('\n')
for line in lines:
    print(line)

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')