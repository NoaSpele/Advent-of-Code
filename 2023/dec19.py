import sys
from copy import deepcopy

p1, p2 = open(sys.argv[1]).read().split('\n\n')
workflows = {}
for line in p1.split('\n'):
    name, info = line.split('{')
    *rulesStr, default = info[:-1].split(',')
    rules = []
    for str in rulesStr:
        check, action = str.split(':')
        v, op, num = check[0], check[1], int(check[2:])
        rules.append({'v':v, 'op':op, 'num':num, 'action':action})
    workflows[name] = {'rules':rules, 'default':default}

def performWf(wf, vals):
    res = None
    for r in workflows[wf]['rules']:
        if r['op'] == '<':
            if vals[r['v']] < r['num']:
                res = r['action']
        else:
            if vals[r['v']] > r['num']:
                res = r['action']
        if res != None: break
    if res == None: return workflows[wf]['default']
    return res

ans1 = 0
for line in p2.split('\n'):
    x, m, a, s = line[1:-1].split(',')
    x, m, a, s = int(x[2:]), int(m[2:]), int(a[2:]), int(s[2:])
    currWf = 'in'
    while True:
        res = performWf(currWf, {'x':x, 'm':m, 'a':a, 's':s})
        if res == 'R': break
        if res == 'A':
            ans1 += x+m+a+s
            break
        currWf = res

def findRanges(wf, r):
    if wf == 'R': return [r]
    elif wf == 'A': return []
    ans = []
    for cr in workflows[wf]['rules']:
        nr = deepcopy(r)
        if cr['op'] == '<':
            nr[cr['v']][1] = cr['num'] - 1
            r[cr['v']][0] = cr['num']
            ans += findRanges(cr['action'], nr)
        else:
            nr[cr['v']][0] = cr['num'] + 1
            r[cr['v']][1] = cr['num']
            ans += findRanges(cr['action'], nr)
    return ans + findRanges(workflows[wf]['default'], r)

sr = {'x':[1,4000],'m':[1,4000],'a':[1,4000],'s':[1,4000]}
currWf = 'in'
ranges = findRanges(currWf, sr)

ans2 = 2.56*10**14 # 4000 ^ 4
for r in ranges:
    curr = 1
    for v in r.values():
        curr *= v[1] - v[0] + 1
    ans2 -= curr
print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {int(ans2)}')