import sys

X = 1
toCheck = [20, 60, 100, 140, 180, 220]
cmds = []
values = []
ans = 0;

for line in sys.stdin:
    if len(cmds) > 0:
        X = X + cmds[0]
        cmds = []
    if "addx" in line:
        cmds.append(int(line.split()[1]))
        values.append(X)
    values.append(X)

for pos in toCheck:
    ans = ans + (values[pos-1]*pos)

print(f'Part 1 - Total signal strength: {ans}')
print()
print("================= Part 2 ==================")
for yPos in range(6):
    line = ""
    for xPos in range(40):
        val = values[yPos*40+xPos] - xPos
        if val == 1 or val == 0 or val == -1:
            line = f'{line}#'
        else:
            line = f'{line}.'
    print(line)
print("===========================================")
