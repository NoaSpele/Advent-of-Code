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

def compare(hand1, h1s, hand2, h2s, useJ):
    if h1s > h2s:
        return 1
    elif h2s > h1s:
        return -1
    else:
        for c1, c2 in list(zip(hand1, hand2)):
            idx1 = cs2.index(c1) if useJ else cs1.index(c1)
            idx2 = cs2.index(c2) if useJ else cs1.index(c2)
            if idx1 > idx2:
                return 1
            elif idx2 > idx1:
                return -1
    return 0

def customSort(hands, useJ):
    sortedHands = []
    while hands:
        sh, sb, ss1, ss2 = hands[0]
        for h, b, s1, s2 in hands[1:]:
            score1, score2 = s2 if useJ else s1, ss2 if useJ else ss1
            if compare(h, score1, sh, score2, useJ) == -1:
                sh, sb, ss1, ss2 = h, b, s1, s2
        hands.remove([sh,sb,ss1,ss2])
        sortedHands.append([sh, sb, ss1, ss2])
    return sortedHands

hands = customSort(hands, False)
for i, h in enumerate(hands):
    ans1 += h[1] * (i+1)

hands = customSort(hands, True)
for i, h in enumerate(hands):
    ans2 += h[1] * (i+1)

print(f'Answer Part 1: {ans1}')
print(f'Answer Part 2: {ans2}')