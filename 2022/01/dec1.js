const readline = require('readline');
let maxCalories = 0;
let sum = 0;
let sums = [];

let rl = readline.createInterface({
  input: process.stdin
});

rl.on('line', function (line) {
  if (line === "") {
    if(maxCalories < sum) maxCalories = sum;
    sums.push(sum);
    sum = 0;
  } else {
    sum += parseInt(line);
  }
});

rl.on('close', function (cmd) {
  console.log("Part 1: " + maxCalories);

  sums.sort((a,b) => b-a);
  const topThreeSum = sums[0] + sums[1] + sums[2];
  console.log("Part 2: " + topThreeSum);
});