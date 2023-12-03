const readline = require('readline');

let rl = readline.createInterface({
  input: process.stdin
});

let S;
let aPos = [];
let heigthMap = [];
rl.on('line', function (line) {
  let row = [];
  for(let c of line) {
    if (c === "S") S = [heigthMap.length, row.length];
    if (c === "a") aPos.push([heigthMap.length, row.length]);
    row.push(c);
  }
  heigthMap.push(row);
});

const chars = "abcdefghijklmnopqrstuvwxyz";
const checkIfLegalMove = (a,b) => {
  if (b === "E") {
    return a === "z" || a === "y";
  }
  return chars.substring(0,chars.indexOf(a)+2).includes(b);
};

const initVisited = (w, h) => {
  let ret = [];
  for (var i = 0; i < h; i++) {
    let curr = [];
    for (var j = 0; j < w; j++) {
      curr.push(false);
    }
    ret.push(curr);
  }
  return ret
};

let queue;
let visited;
const checkPos = (p1, p2) => {
  if (!visited[p2[0]][p2[1]] &&
      checkIfLegalMove(
        heigthMap[p1[0]][p1[1]],
        heigthMap[p2[0]][p2[1]]
      )) {
    visited[p2[0]][p2[1]] = true;
    queue.push([p2[0], p2[1], p1[2]+1]);
  }
}

const bfs = (s, w, h) => {
  queue = [];
  queue.push([s[0], s[1], 0]);

  visited = initVisited(w, h);
  visited[s[0]][s[1]] = true;

  while(queue.length > 0) {
    let currPos = queue.shift();
    if (heigthMap[currPos[0]][currPos[1]] === "E") return currPos[2];

    [[Math.max(0, currPos[0]-1), currPos[1]],
      [Math.min(currPos[0]+1, h-1), currPos[1]],
      [currPos[0], Math.max(0, currPos[1]-1)],
      [currPos[0], Math.min(currPos[1]+1, w-1)]
    ].forEach(p => checkPos(currPos, p));
  }
  return -1;
}

rl.on('close', function (cmd) {
  let h = heigthMap.length;
  let w = heigthMap[0].length;

  let ans1 = bfs(S, w, h, heigthMap);
  let sols = [ans1];
  aPos.forEach(pos => {
    let sol = bfs(pos, w, h);
    if(sol > 0) sols.push(sol);
  });
  let ans2 = Math.min(...sols);
  console.log("Part 1 - " + ans1);
  console.log("Part 2 - " + ans2);
});