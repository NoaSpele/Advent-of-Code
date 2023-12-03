const readline = require('readline');

let rl = readline.createInterface({
  input: process.stdin
});

let nodes = [];
let names = [];
let done = {};
let numCalls = 0;
rl.on('line', function (line) {
  let name = line.substring(6, line.length).split(" has")[0];
  line = line.split("rate=")[1];
  let rate = parseInt(line.split(":")[0]);
  line = line.split("valve")[1];
  let edges = [];
  if (line[0] === "s") line = line.substring(1, line.length);
  line.split(",").forEach(node => {
    edges.push(node.substring(1, node.length));
  });
  names.push(name);
  nodes[name] ={
    "rate": rate,
    "edges": edges
  };
});

const getDists = (node, list, nodes) => {
  let ret = {};
  let visited = [node];
  let q = [[node,0]]
  while(q.length > 0) {
    let curr = q.shift();
    nodes[curr[0]].edges.forEach(n => {
      if (!visited.includes(n)) {
        q.push([n, curr[1] + 1]);
        visited.push(n);
        if (list.includes(n)) ret[n] = curr[1] + 1;
      }
    });
  }
  return ret;
}

const getMax = (pos, dists, times, val, unopen) => {
  if (Math.max(times) < 1) {
    return val;
  }

  let idx = times.indexOf(Math.max(...times));
  let k1 = pos + times + unopen;
  let k2 = [...pos].reverse() + [...times].reverse() + unopen;
  if (done[k1]) {
    return Math.max(done[k1], val);
  } else if (done[k2]) {
    return Math.max(done[k2], val);
  }

  let curr = [[val, '']];
  if (unopen.includes(pos[idx])) {
    let newT = [...times];
    let newPos = [...pos];
    let newUnopen = [...unopen].filter(n => n !== pos[idx]);
    newT[idx] = newT[idx] - 1;
    let newVal = val + newT[idx] * nodes[pos[idx]].rate;
    curr.push([getMax(newPos, dists, newT, newVal, newUnopen), pos[idx]]);
  }

  for (const [key, value] of Object.entries(dists[pos[idx]])) {
    if (unopen.includes(key) && times[idx] > value + 1) {
      let newT = [...times];
      let newPos = [...pos];
      let newUnopen = [...unopen].filter(n => n !== key);
      newT[idx] = newT[idx] - (value + 1);
      newPos[idx] = key;
      let newVal = val + newT[idx] * nodes[key].rate;
      curr.push([getMax(newPos, dists, newT, newVal, newUnopen), key]);
    }
  };
  let m = Math.max(...curr.map(c => c[0]));
  if (unopen.length > 1) {
    done[k1] = m;
  }
  return m;
}

rl.on('close', function (cmd) {
  let start = "AA";
  let interesting = [];

  names.forEach(name => {
    if (nodes[name].rate > 0) interesting.push(name);
  });

  // Find shortest distance for each node with rate > 0 to each other node with rate > 9
  let dists = {};
  [start, ...interesting].forEach(name => {
    dists[name] = getDists(name, [start, ...interesting], nodes);
  });

  let ans1 = getMax([start], dists, [30], 0, [start, ...interesting], {});
  console.log("Part 1 - " + ans1);
  console.log("JS can't handle the needed recursion depth for problem 2");
});