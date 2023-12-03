import re
import sys
import math
from copy import deepcopy
from collections import deque

# One line comment
"""
Multi line comment
"""

#==== Logging ====
print("hello world!")
print(f'hello {1+2}')

#==== basics ====
def func(a, b):
    return a+1, b+2

for idx in range(10):
    pass # pass means do nothing

for _ in range(123):
    pass

for idx, a in enumerate(['a','b','c']):
    print(f'idx: {idx}, a: {a}')

if(1+2 == 3 and 2+2 == 4 or not 5-4==1):
    pass
elif(1+2 == 4):
    pass
else:
    pass

#==== String ====
s = "wergiwetgn" + \
    "ergkjnrtgjnr"
s = ("a" + "b" +
     "c" + "d")
print(s)
print(len("abcde"))
print("   wetgetjbn  ".strip())
print('$'.join(['s','r','e','g','s']))
print('a'.join( [str(x) for x in [1,2,3,4]] )) # join only works with strings

print("123\n456\n789".split('\n'))

print(4 + int('123435346'))
print(3.0 + float('.1415'))

# Regex
print(re.findall(r'\d+| [a-zA-Z][a-zA-Z] ', "123 ab 23 njj 654 c"))

#==== Math ====
print(f'min: {min([1,4,2,5,7,4])}')
print(f'max: {max([1,5,3,5,7,5])}')
print(f'abs: {abs(-4)}')
print(f'int div: {7//2}')
print(f'mod: {7%2}')

print(f'3^2: {math.pow(3,2)}')
print(f'4^1/2: {math.sqrt(4)}')

#==== Data Structures ====
l = []; l = list()
l.append('a'); l.append('b'); l.append('c')
l.remove('b')
l.pop(1)
print(f'l: {l}')
l.clear()
print(f'sorted: {sorted([2,1,4,2,6,4,2,9])}')
l = [1,2,3,4,5,6]
print(f'reverse: {l[::-1]}')
print(f'1 to end -2: {l[1:-2]}')

map = {}; map = dict()
map['a'] = 3; map['b'] = 4
print(map['a'])
print(map.get('c','default if not in dict'))
print({*map, 'f'}) # desctructuring
map2 = map.copy() # shallow
map2 = map # shallow
map2 = deepcopy(map) # deep copy, itterables copied to
print(map.values())

tuple = (1,'be',3.4,'blabla')
print(tuple[2])

set1 = {"123", 1, True, False, tuple}; set2 = set()
set2.add('a'); set2.add('b'); set2.add('b'); set2.add('c')
set2.remove('c') # will throw exception if key not in set
set2.discard('not') # will NOT throw
print(set2)

print('a' in set2)
print('a' not in map) # checks if 'a' is key in dict
print(2 in [1,3,5,6,3])

#==== algorithms ====
graph = {
  'A' : ['B','C'],
  'B' : ['D', 'E'],
  'C' : ['F'],
  'D' : [],
  'E' : ['F'],
  'F' : []
}

def bfs(node):
  visited = [node]; queue = [node]
  while queue:
    s = queue.pop(0)
    print(s, end = " ") # after print add " " instead of \n
    for neighbour in graph[s]:
      if neighbour not in visited:
        visited.append(neighbour)
        queue.append(neighbour)

def dfs(visited, node):
    if node not in visited:
        print(node, end = " ")
        visited.append(node)
        for neighbour in graph[node]:
            dfs(visited, neighbour)

print('dfs')
dfs([], 'A')
print('\nbfs:')
bfs('A')
print('')

#==== Tricks ====
print(ord('d') - ord('a'))
print(isinstance(12, int))
print(isinstance({}, dict))
print(isinstance([], list))
print(" abcdefg".index('c'))
print(" abcdefg"[3])
