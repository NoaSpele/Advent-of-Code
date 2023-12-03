const readline = require('readline');

let rl = readline.createInterface({
  input: process.stdin
});

let blizzards = []; // {x: 1, y: 1, type: '<'}
const numBliz = [];
const numBoards = [];
const board = [];

rl.on('line', function (line) {
  let row = [];
  let nums = [];
  let idx = 0;
  for(let c of line) {
    if (c === '#') {
      row.push(-1);
      nums.push(0);
    } else if(c === '.') {
      row.push(0);
      nums.push(0);
    } else {
      row.push(0);
      nums.push(1);
      blizzards.push({x: idx, y: board.length, type: c});
    }
    idx++;
  }
  board.push(row);
  numBliz.push(nums);
});

const getNewVal = (val, max) => {
  if (val < 1) return max - 2;
  if (val > max - 2) return 1;
  return val;
}

const cloneArray = (arr) => arr.map(a => ([...a]));

const moveBliz = (bliz, w, h) => {
  if(bliz.type === '<') return {x: getNewVal(bliz.x-1, w), y: bliz.y, type: bliz.type};
  if(bliz.type === '>') return {x: getNewVal(bliz.x+1, w), y: bliz.y, type: bliz.type};
  if(bliz.type === '^') return {x: bliz.x, y: getNewVal(bliz.y-1, h), type: bliz.type};
  return {x: bliz.x, y: getNewVal(bliz.y+1, h), type: bliz.type};
}

const findBoards = (w, h, nums) => {
  let idx = 0;
  while(idx < w * h) {
    numBoards.push(cloneArray(nums));
    blizzards = blizzards.map(bliz => {
      nums[bliz.y][bliz.x]--;
      let newBliz = moveBliz(bliz, w, h);
      nums[newBliz.y][newBliz.x]++;
      return newBliz;
    });
    idx++;
  }
}

const solve = (posX, posY, t, endX, endY, w, h) => {
  const max = w*h;
  let q = [[posX, posY, t]];
  const seen = new Set();
  let ittr = 0;
  while (q.length > 0) {
    let [x, y, t] = q.shift();

    if(x === endX && y === endY) {
      return t;
    }
    // next num state
    let curr = numBoards[(t+1) % max];

    if(seen.has("x"+x+"y"+y+"t"+(t%max))) continue;

    if(curr[y][x] === 0) q.push([x, y, t+1]);

    if(x+1 < w && board[y][x+1] !== -1 && curr[y][x+1] === 0) {
      q.push([x+1, y, t+1]);
    }

    if(x-1 >= 0 && board[y][x-1] !== -1 && curr[y][x-1] === 0) {
      q.push([x-1, y, t+1]);
    }

    if(y+1 < h && board[y+1][x] !== -1 && curr[y+1][x] === 0) {
      q.push([x, y+1, t+1]);
    }

    if(y-1 >= 0 && board[y-1][x] !== -1 && curr[y-1][x] === 0) {
      q.push([x, y-1, t+1]);
    }

    seen.add("x"+x+"y"+y+"t"+(t%max));
  }
  return -1;
}

rl.on('close', function (cmd) {
  const w = board[0].length;
  const h = board.length;
  const startX = 1; const startY = 0;
  const endX = w - 2; const endY = h - 1;

  console.log("Finding all possible states");
  findBoards(w, h, cloneArray(numBliz));

  let ans1 = solve(startX, startY, 0, endX, endY, w, h);
  console.log("Part 1 - " + ans1);

  let t2 = solve(endX, endY, ans1, startX, startY, w, h);
  let t3 = solve(startX, startY, t2, endX, endY, w, h);
  console.log("Part 2 - " + t3);
});