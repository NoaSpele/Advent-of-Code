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

function bfs(s, w, h, grid) {
  queue = [];
  queue.push([s[0], s[1], 0]);

  let visited = initVisited(w, h);
  visited[s[0]][s[1]] = true;

  while(queue.length > 0) {
    let currPos = queue.shift();
    if (grid[currPos[0]][currPos[1]] === "E") {
      return currPos[2];
    }

    let currX;
    let currY;
    currX = Math.max(0, currPos[0]-1);
    if (!visited[currX][currPos[1]] &&
        checkIfLegalMove(
          grid[currPos[0]][currPos[1]],
          grid[currX][currPos[1]]
        )) {
      visited[currX][currPos[1]] = true;
      queue.push([currX, currPos[1], currPos[2]+1]);
    }

    currX = Math.min(currPos[0]+1, h-1);
    if (!visited[currX][currPos[1]] &&
        checkIfLegalMove(
          grid[currPos[0]][currPos[1]],
          grid[currX][currPos[1]]
        )) {
      visited[currX][currPos[1]] = true;
      queue.push([currX, currPos[1], currPos[2]+1]);
    }

    currY = Math.max(0, currPos[1]-1);
    if (!visited[currPos[0]][currY] &&
        checkIfLegalMove(
          grid[currPos[0]][currPos[1]],
          grid[currPos[0]][currY]
        )) {
      visited[currPos[0]][currY] = true;
      queue.push([currPos[0], currY, currPos[2]+1]);
    }

    currY = Math.min(currPos[1]+1, w-1);
    if (!visited[currPos[0]][currY] &&
        checkIfLegalMove(
          grid[currPos[0]][currPos[1]],
          grid[currPos[0]][currY]
        )) {
      visited[currPos[0]][currY] = true;
      queue.push([currPos[0], currY, currPos[2]+1]);
    }
  }
  return -1;
}

rl.on('close', function (cmd) {
  let h = heigthMap.length;
  let w = heigthMap[0].length;

  let ans1 = bfs(S, w, h, heigthMap);
  let sols = [ans1];
  aPos.forEach(pos => {
    let sol = bfs(pos, w, h, heigthMap);
    if(sol > 0) sols.push(sol);
  });
  let ans2 = Math.min(...sols);
  console.log("Part 1 - " + ans1);
  console.log("Part 2 - " + ans2);
});