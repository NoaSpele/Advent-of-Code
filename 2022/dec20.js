const readline = require('readline');

let rl = readline.createInterface({
  input: process.stdin
});

let encryptedFile = [];
let currPos = {};
let index = 0; let pos0;
rl.on('line', function (line) {
  let currNum = parseInt(line);
  encryptedFile.push(currNum);
  currPos[index] = index;
  if (currNum === 0) {
    pos0 = index;
  }
  index++;
});

const shiftFile = (index, amount, decFile, posEnc, posDec) => {
  let decIdx = posEnc[index];
  let newDecIdx = (decIdx + amount) % decFile.length;
  if (newDecIdx < 0) newDecIdx += decFile.length;

  // index update
  let tempEnc = posDec[newDecIdx]
  posDec[newDecIdx] = index;
  posDec[decIdx] = tempEnc;
  posEnc[index] = newDecIdx;
  posEnc[tempEnc] = decIdx;

  // value update
  let tempDecVal = decFile[newDecIdx];
  decFile[newDecIdx] = decFile[decIdx];
  decFile[decIdx] = tempDecVal;
};

const solve = (rep, key, decFile, posEnc, posDec) => {
  for (var k = 0; k < rep; k++) {
    for (var i = 0; i < encryptedFile.length; i++) {
      if (encryptedFile[i] === 0) continue;

      let stepChange = encryptedFile[i] < 0 ? -1 : 1;
      let decVal = encryptedFile[i] * key;
      let num = Math.abs(decVal % encryptedFile.length);
      let offset = Math.floor(Math.abs(decVal / encryptedFile.length));
      while(offset > encryptedFile.length) {
        num += offset % encryptedFile.length;
        offset = Math.floor(offset / encryptedFile.length);
      }
      num += offset;

      for (var j = 0; j < num; j++) {
        shiftFile(i, stepChange, decFile, posEnc, posDec);
      }
    }
  }

  let v1 = decFile[(posEnc[pos0] + 1000) % decFile.length];
  let v2 = decFile[(posEnc[pos0] + 2000) % decFile.length];
  let v3 = decFile[(posEnc[pos0] + 3000) % decFile.length];

  return (v1 + v2 + v3) * key;
};

rl.on('close', function (cmd) {
  const key = 811589153;
  console.log("= Solving day 20 =");
  let ans1 = solve(1, 1, [...encryptedFile], {...currPos}, {...currPos});
  console.log("Part 1 calculated!");
  let ans2 = solve(10, key, [...encryptedFile], {...currPos}, {...currPos});
  console.log("Part 2 calculated!");
  console.log("==================\n");

  console.log("Part 1 - " + ans1);
  console.log("Part 2 - " + ans2);
});