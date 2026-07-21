import React, { useState } from 'react';
import { Play, RotateCcw, GitBranch, CheckCircle2, Layers, Zap, ArrowRight } from 'lucide-react';

interface GraphNode {
  id: number;
  label: string;
  subLabel: string;
  x: number; // percentage
  y: number; // percentage
}

interface GraphEdge {
  from: number;
  to: number;
  weight: number;
}

const GRAPH_NODES: Record<number, GraphNode> = {
  1: { id: 1, label: 'Siam (Source)', subLabel: 'Start Node', x: 12, y: 50 },
  2: { id: 2, label: 'CP Engine', subLabel: '2000+ Solves', x: 35, y: 22 },
  3: { id: 3, label: 'Algorithms & DS', subLabel: 'Graph & DP', x: 35, y: 78 },
  4: { id: 4, label: 'Codeforces', subLabel: 'Specialist 1405', x: 60, y: 15 },
  5: { id: 5, label: 'Codechef', subLabel: '3★ Rating 1534', x: 60, y: 45 },
  6: { id: 6, label: 'Go & Gin Backend', subLabel: 'Microservices', x: 60, y: 85 },
  7: { id: 7, label: 'FastAPI & Python', subLabel: 'REST APIs', x: 80, y: 70 },
  8: { id: 8, label: 'ICPC Regionalist', subLabel: 'Asia Dhaka (Target)', x: 88, y: 35 },
};

const GRAPH_EDGES: GraphEdge[] = [
  { from: 1, to: 2, weight: 2 },
  { from: 1, to: 3, weight: 4 },
  { from: 1, to: 6, weight: 7 },
  { from: 2, to: 4, weight: 3 },
  { from: 2, to: 5, weight: 2 },
  { from: 3, to: 2, weight: 1 }, // cross edge
  { from: 3, to: 6, weight: 3 },
  { from: 3, to: 7, weight: 4 },
  { from: 4, to: 8, weight: 5 },
  { from: 5, to: 8, weight: 2 }, // optimal path edge!
  { from: 6, to: 7, weight: 2 },
  { from: 7, to: 8, weight: 1 },
];

export const TreeVisualizer: React.FC = () => {
  const [isSimulating, setIsSimulating] = useState(false);
  const [visitedNodes, setVisitedNodes] = useState<number[]>([]);
  const [currentNode, setCurrentNode] = useState<number | null>(null);
  const [distArray, setDistArray] = useState<Record<number, number | string>>({
    1: 0, 2: 'INF', 3: 'INF', 4: 'INF', 5: 'INF', 6: 'INF', 7: 'INF', 8: 'INF'
  });
  const [pqState, setPqState] = useState<string[]>([]);
  const [shortestPathEdges, setShortestPathEdges] = useState<string[]>([]);
  const [finalPathNodes, setFinalPathNodes] = useState<number[]>([]);

  // Dijkstra's Shortest Path Solver Simulation
  const runDijkstra = async () => {
    setIsSimulating(true);
    setVisitedNodes([]);
    setCurrentNode(null);
    setShortestPathEdges([]);
    setFinalPathNodes([]);

    const dist: Record<number, number> = { 1: 0, 2: 999, 3: 999, 4: 999, 5: 999, 6: 999, 7: 999, 8: 999 };
    const parent: Record<number, number | null> = { 1: null, 2: null, 3: null, 4: null, 5: null, 6: null, 7: null, 8: null };
    const visitedSet = new Set<number>();
    const order: number[] = [];

    // Min-Heap Priority Queue simulation [(dist, node)]
    let pq: { u: number; d: number }[] = [{ u: 1, d: 0 }];

    setDistArray({ 1: 0, 2: 'INF', 3: 'INF', 4: 'INF', 5: 'INF', 6: 'INF', 7: 'INF', 8: 'INF' });

    while (pq.length > 0) {
      pq.sort((a, b) => a.d - b.d);
      const { u, d } = pq.shift()!;

      if (visitedSet.has(u)) continue;
      visitedSet.add(u);
      order.push(u);

      setCurrentNode(u);
      setVisitedNodes([...order]);
      setPqState(pq.map(item => `${GRAPH_NODES[item.u].label}(d=${item.d})`));

      await new Promise((res) => setTimeout(res, 750));

      // Find outgoing edges from u
      const outgoing = GRAPH_EDGES.filter(e => e.from === u);
      for (const edge of outgoing) {
        const v = edge.to;
        const w = edge.weight;

        if (dist[u] + w < dist[v]) {
          dist[v] = dist[u] + w;
          parent[v] = u;
          pq.push({ u: v, d: dist[v] });

          setDistArray(prev => ({ ...prev, [v]: dist[v] }));
        }
      }
    }

    // Reconstruct shortest path from Source (1) to Target (8)
    const path: number[] = [];
    let curr: number | null = 8;
    while (curr !== null) {
      path.unshift(curr);
      curr = parent[curr];
    }

    const pathEdgeKeys: string[] = [];
    for (let i = 0; i < path.length - 1; i++) {
      pathEdgeKeys.push(`${path[i]}-${path[i + 1]}`);
    }

    setShortestPathEdges(pathEdgeKeys);
    setFinalPathNodes(path);
    setCurrentNode(null);
    setPqState([]);
    setIsSimulating(false);
  };

  const handleReset = () => {
    if (isSimulating) return;
    setVisitedNodes([]);
    setCurrentNode(null);
    setPqState([]);
    setShortestPathEdges([]);
    setFinalPathNodes([]);
    setDistArray({ 1: 0, 2: 'INF', 3: 'INF', 4: 'INF', 5: 'INF', 6: 'INF', 7: 'INF', 8: 'INF' });
  };

  return (
    <div className="w-full bg-transparent font-mono relative">
      {/* Controls Bar - Floating Glass */}
      <div className="flex flex-wrap items-center justify-between gap-3 bg-[#161922]/75 backdrop-blur-md p-3.5 rounded-xl border border-orange-500/30 text-xs mb-4 shadow-xl">
        <div className="flex items-center gap-2">
          <Zap className="w-4 h-4 text-orange-400 fill-current" />
          <span className="text-slate-100 font-bold">Complex Network :: Dijkstra's Shortest Path</span>
        </div>

        {/* Play & Reset Controls */}
        <div className="flex items-center gap-2">
          <button
            onClick={runDijkstra}
            disabled={isSimulating}
            className="flex items-center gap-1.5 px-4 py-1.5 bg-gradient-to-r from-orange-500 to-amber-500 text-white rounded-lg hover:from-orange-600 hover:to-amber-600 font-bold text-xs transition-all disabled:opacity-40 cursor-pointer shadow-lg shadow-orange-500/20"
          >
            <Play className={`w-3.5 h-3.5 ${isSimulating ? 'animate-spin' : ''}`} />
            <span>{isSimulating ? 'Computing Dijkstra...' : "Run Dijkstra's Engine"}</span>
          </button>
          <button
            onClick={handleReset}
            disabled={isSimulating}
            className="p-1.5 bg-slate-800/80 border border-slate-700 text-slate-400 hover:text-slate-200 rounded-lg transition-colors cursor-pointer"
            title="Reset Network"
          >
            <RotateCcw className="w-3.5 h-3.5" />
          </button>
        </div>
      </div>

      {/* Floating Complex Network Canvas (Transparent Background) */}
      <div className="relative w-full h-[380px] bg-transparent rounded-xl overflow-visible">
        {/* SVG Directed Edge Lines & Arrow Markers */}
        <svg className="absolute inset-0 w-full h-full pointer-events-none overflow-visible">
          <defs>
            <marker id="arrow-default" viewBox="0 0 10 10" refX="18" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
              <path d="M 0 0 L 10 5 L 0 10 z" fill="rgba(255,255,255,0.3)" />
            </marker>
            <marker id="arrow-visited" viewBox="0 0 10 10" refX="18" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
              <path d="M 0 0 L 10 5 L 0 10 z" fill="#f97316" />
            </marker>
            <marker id="arrow-shortest" viewBox="0 0 10 10" refX="18" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
              <path d="M 0 0 L 10 5 L 0 10 z" fill="#22c55e" />
            </marker>
          </defs>

          {GRAPH_EDGES.map((edge) => {
            const uNode = GRAPH_NODES[edge.from];
            const vNode = GRAPH_NODES[edge.to];
            const edgeKey = `${edge.from}-${edge.to}`;
            const isShortest = shortestPathEdges.includes(edgeKey);
            const isVisitedEdge = visitedNodes.includes(edge.from) && visitedNodes.includes(edge.to);

            const midX = (uNode.x + vNode.x) / 2;
            const midY = (uNode.y + vNode.y) / 2;

            return (
              <g key={edgeKey}>
                <line
                  x1={`${uNode.x}%`} y1={`${uNode.y}%`}
                  x2={`${vNode.x}%`} y2={`${vNode.y}%`}
                  stroke={isShortest ? '#22c55e' : isVisitedEdge ? '#f97316' : 'rgba(255,255,255,0.18)'}
                  strokeWidth={isShortest ? '4' : isVisitedEdge ? '2.5' : '1.5'}
                  strokeDasharray={isShortest ? 'none' : isVisitedEdge ? 'none' : '4 4'}
                  markerEnd={isShortest ? 'url(#arrow-shortest)' : isVisitedEdge ? 'url(#arrow-visited)' : 'url(#arrow-default)'}
                  className="transition-all duration-300"
                />
                {/* Edge Weight Pill Badge */}
                <text
                  x={`${midX}%`}
                  y={`${midY}%`}
                  fill={isShortest ? '#22c55e' : isVisitedEdge ? '#f97316' : '#06b6d4'}
                  fontSize="11"
                  fontFamily="JetBrains Mono"
                  fontWeight="bold"
                  textAnchor="middle"
                  dy="-4"
                >
                  w={edge.weight}
                </text>
              </g>
            );
          })}
        </svg>

        {/* Floating Nodes */}
        {Object.values(GRAPH_NODES).map((node) => {
          const isCurrent = currentNode === node.id;
          const isVisited = visitedNodes.includes(node.id);
          const isShortestNode = finalPathNodes.includes(node.id);
          const distVal = distArray[node.id];

          return (
            <div
              key={node.id}
              className="absolute transform -translate-x-1/2 -translate-y-1/2 flex flex-col items-center transition-all duration-300 z-10"
              style={{ left: `${node.x}%`, top: `${node.y}%` }}
            >
              {/* Node Circle */}
              <div
                className={`w-11 h-11 rounded-full flex items-center justify-center font-extrabold text-xs transition-all duration-300 shadow-2xl backdrop-blur-md cursor-pointer ${
                  isCurrent
                    ? 'bg-amber-400 text-black scale-125 ring-4 ring-amber-400/60 shadow-[0_0_30px_rgba(251,191,36,0.9)] animate-pulse'
                    : isShortestNode
                    ? 'bg-green-500 text-black font-extrabold ring-4 ring-green-500/50 shadow-[0_0_25px_rgba(34,197,94,0.8)] scale-110'
                    : isVisited
                    ? 'bg-gradient-to-br from-orange-500 to-cyan-500 text-white ring-2 ring-orange-400/50 shadow-[0_0_20px_rgba(249,115,22,0.6)]'
                    : 'bg-[#161922]/85 border border-slate-700/80 text-slate-300 hover:border-orange-500/50 hover:text-white shadow-lg'
                }`}
              >
                {node.id}
              </div>

              {/* Node Label Badge */}
              <div
                className={`mt-1.5 px-2.5 py-1 rounded-md text-[10px] whitespace-nowrap font-mono transition-all backdrop-blur-md shadow-lg ${
                  isCurrent
                    ? 'bg-amber-400/25 text-amber-300 border border-amber-400/70 font-bold scale-105'
                    : isShortestNode
                    ? 'bg-green-500/20 text-green-300 border border-green-500/60 font-bold'
                    : isVisited
                    ? 'bg-[#161922]/90 text-slate-100 border border-orange-500/40'
                    : 'bg-[#12141c]/80 text-slate-400 border border-slate-800'
                }`}
              >
                <div className="font-semibold text-center">{node.label}</div>
                <div className="text-[9px] text-cyan-400 text-center font-bold">
                  dist[{node.id}] = {distVal}
                </div>
              </div>
            </div>
          );
        })}
      </div>

      {/* Execution Log & Min-Heap Priority Queue */}
      <div className="bg-[#161922]/75 backdrop-blur-md p-3.5 rounded-xl border border-slate-800 space-y-2 text-xs mt-4 shadow-xl">
        <div className="flex flex-wrap items-center justify-between gap-2">
          <div className="flex items-center gap-2">
            <span className="text-slate-400 font-semibold">Shortest Path Result:</span>
            <div className="flex items-center gap-1.5 font-bold text-green-400 flex-wrap">
              {finalPathNodes.length > 0 ? (
                finalPathNodes.map((n, i) => (
                  <span key={i} className="flex items-center gap-1">
                    <span className="px-2 py-0.5 bg-green-500/10 border border-green-500/40 rounded text-[11px] text-green-300">
                      {GRAPH_NODES[n].label}
                    </span>
                    {i < finalPathNodes.length - 1 && <ArrowRight className="w-3.5 h-3.5 text-green-400" />}
                  </span>
                ))
              ) : (
                <span className="text-slate-500 font-normal italic">Click "Run Dijkstra's Engine" to calculate shortest path from Node 1 ➔ Node 8</span>
              )}
            </div>
          </div>

          {finalPathNodes.length > 0 && (
            <div className="flex items-center gap-1.5 text-green-400 font-bold text-[11px] animate-pulse">
              <CheckCircle2 className="w-4 h-4" />
              <span>MIN COST: {distArray[8]} :: OPTIMAL PATH</span>
            </div>
          )}
        </div>

        {/* Priority Queue Heap State */}
        {pqState.length > 0 && (
          <div className="flex items-center gap-2 text-[11px] text-slate-400 border-t border-slate-800/80 pt-2">
            <Layers className="w-3.5 h-3.5 text-cyan-400" />
            <span>Priority Queue Min-Heap:</span>
            <span className="text-cyan-300 font-bold">
              [{pqState.join(', ')}]
            </span>
          </div>
        )}
      </div>
    </div>
  );
};
