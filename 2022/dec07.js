const readline = require('readline');
let currDir;
let rootDir = {
  "parent": null,
  "name": "/",
  "files": [],
  "dirs": {},
};
let dirs = [];
let data;

let rl = readline.createInterface({
  input: process.stdin
});

rl.on('line', function (line) {
  if (line.charAt(0) === "$") {
    data = line.split(" ");
    if (data[1] === "cd") {
      if (data[2] !== "..") {
        if (data[2] === "/") {
          currDir = rootDir;
        } else {
          currDir = currDir.dirs[data[2]]
        }
      } else {
        currDir = currDir.parent ? currDir.parent : rootDir;
      }
    }
  } else {
    data = line.split(" ");
    if (data[0] === "dir") {
      currDir.dirs[data[1]] = {
        "parent": currDir,
        "name": data[1],
        "files": [],
        "dirs": {},
      };
      dirs.push(currDir.dirs[data[1]]);
    } else {
      let newFile = {"name":data[1], "size":parseInt(data[0])};
      currDir.files.push(newFile);
    }
  }
});

rl.on('close', function (cmd) {
  let ans1 = {
    "sum": 0,
    "dirs": []
  };
  recursiveSum(rootDir, ans1);
  let ans2 = {
    "dir": "/",
    "size": rootDir.size
  }
  let needToFree = 30000000 - (70000000 - rootDir.size);
  dirs.forEach(dir => {
    if ((dir.size > needToFree) && (dir.size < ans2.size)) {
      ans2.dir = dir.name;
      ans2.size = dir.size;
    }
  });
  console.log("Part 1 - total size is: " + ans1.sum);
  console.log("Part 2 - dir: " + ans2.dir + " with size: " + ans2.size + " should be removed");
});

recursiveSum = (node, ans) => {
  node["size"] = node.files.reduce((a,b) => a + b.size,0);
  if (node.dirs.length !== 0) {
    Object.entries(node.dirs).forEach(d => node["size"] += recursiveSum(d[1], ans));
  }
  if (node["size"] <= 100000) {
    ans.sum += node["size"];
    ans.dirs.push(node.name);
  }
  return node["size"];
}