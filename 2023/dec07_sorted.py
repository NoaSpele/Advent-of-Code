import sys

def getHandMap(hand):
    ret = {}
    for c in hand:
        ret[c] = 1 if not c in ret else ret[c] + 1
    return ret

def getBestHand(hmap):
    if not 'J' in hmap:
        return hmap
    if hmap['J'] == 5:
        return {'A':5}
    numJ = hmap['J']
    del hmap['J']
    highest = None
    for k, v in hmap.items():
        if highest == None or v > highest[1]:
            highest = [k,v]
    hmap[highest[0]] += numJ
    return hmap

def getHandScore(hmap):
    if len(hmap) == 1: # 5 of kind
        return 7
    elif len(hmap) == 2: # 4 of a kind or full house
        return 6 if max(hmap.values()) == 4 else 5
    elif len(hmap) == 3: # 3 of a kind or two pair
        return 4 if max(hmap.values()) == 3 else 3
    else: # pair or high card
        return 2 if len(hmap) == 4 else 1

cs1 = 'AKQJT98765432'[::-1]
cs2 = 'AKQT98765432J'[::-1]
ans1, ans2 = 0, 0
lines = open(sys.argv[1]).read().split('\n')
hands = []
for line in lines:
    hand, bid = line.split()
    bid = int(bid)
    hand = [x for x in hand]
    hmap = getHandMap(hand)
    score1 = getHandScore(hmap)
    bhmap = getBestHand(hmap)
    score2 = getHandScore(bhmap)
    hands.append([hand, bid, score1, score2])

# python sorts first by first elem in array and than next value in array and so on
hands = sorted(hands, key=lambda h:(h[2],[cs1.index(x) for x in h[0]]))
for i, h in enumerate(hands):
    ans1 += h[1] * (i+1)

hands = sorted(hands, key=lambda h:(h[3],[cs2.index(x) for x in h[0]]))
for i, h in enumerate(hands):
    ans2 += h[1] * (i+1)

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')