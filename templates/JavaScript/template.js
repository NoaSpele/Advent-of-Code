const fs = require('fs');

let ans1 = 0;
let ans2 = 0;
const lines = fs.readFileSync(process.argv[2], 'utf-8').trim().split('\n');
lines.forEach(line => {
  console.log(line);
});

console.log("Part 1: " + ans1);
console.log("Part 2: " + ans2);