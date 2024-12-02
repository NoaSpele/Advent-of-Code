import sys

lines = open(sys.argv[1]).read().split('\n')
modules = {}
sender = None
for line in lines:
    p1, dests = line.split(' -> ')
    type, name = p1[0], p1[1:]
    dests = dests.split(', ')
    if 'rx' in dests: sender = name
    if type == 'b': modules['broadcast'] = {'t':type, 'd':dests}
    elif type == '%': modules[name] = {'t':type, 's':0, 'd':dests}
    else: modules[name] = {'t':type, 's':dict(), 'd': dests}

for conN in [x for x, m in modules.items() if m['t'] == '&']:
    for n, m in modules.items():
        if conN in m['d']: modules[conN]['s'][n] = 'l'

found = []
left = [x for x in modules[sender]['s']]
def handleSignal(type, name, src):
    if name in modules:
        m = modules[name]
        if m['t'] == 'b': return [('l', x, name) for x in m['d']]
        elif m['t'] == '%' and type == 'l':
            newT = 'l' if m['s'] else 'h'
            m['s'] = not m['s']
            return [(newT, x, name) for x in m['d']]
        elif m['t'] == '&':
            m['s'][src] = type
            newT = 'l' if all(x=='h' for x in m['s'].values()) else 'h'
            return [(newT, x, name) for x in m['d']]
    return []

numLow, numHigh = 0, 0
for i in range(100000):
    numLow += 1
    sigs = handleSignal('l', 'broadcast', 'button')
    while sigs:
        newSigs = []
        for s, d, src in sigs:
            if src == 'vr' and any(x == 'h' for x in modules[sender]['s'].values()):
                hn = [x for x, y in modules[sender]['s'].items() if y == 'h']
                for x in hn:
                    if x in left:
                        left.remove(x)
                        found.append(i+1)
            if s == 'l': numLow += 1
            else: numHigh += 1
            newSigs += handleSignal(s, d, src)
        sigs = [*newSigs]
        if not left: break
    if i+1 == 1000: ans1 = numLow * numHigh

ans2 = 1
for x in found: ans2 *= x
print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')