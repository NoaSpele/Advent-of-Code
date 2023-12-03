const fs = require('fs');

let sums = [];
const parts = fs.readFileSync(process.argv[2], 'utf-8').trim().split('\n\n');
parts.forEach(part => {
  let curr = 0;
  part.split('\n').forEach(line => {
    curr += parseInt(line);
  });
  sums.push(curr);
});

sums.sort((a,b) => b-a);
console.log("Part 1: " + sums[0]);
console.log("Part 2: " + (sums[0] + sums[1] + sums[2]));