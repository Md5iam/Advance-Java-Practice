import React, { useState, useEffect } from 'react';

interface VerdictNode {
  id: number;
  label: string;
  color: string;
  bg: string;
  border: string;
  glow: string;
}

// 6 unique CP verdict nodes
const VERDICT_NODES: VerdictNode[] = [
  {
    id: 1,
    label: 'Accepted',
    color: '#22c55e', // Green
    bg: 'rgba(34, 197, 94, 0.15)',
    border: 'rgba(34, 197, 94, 0.5)',
    glow: 'rgba(34, 197, 94, 0.6)'
  },
  {
    id: 2,
    label: 'Runtime error',
    color: '#a855f7', // Purple
    bg: 'rgba(168, 85, 247, 0.15)',
    border: 'rgba(168, 85, 247, 0.5)',
    glow: 'rgba(168, 85, 247, 0.6)'
  },
  {
    id: 3,
    label: 'Time limit exceeded',
    color: '#f97316', // Orange
    bg: 'rgba(249, 115, 22, 0.15)',
    border: 'rgba(249, 115, 22, 0.5)',
    glow: 'rgba(249, 115, 22, 0.6)'
  },
  {
    id: 4,
    label: 'Memory limit exceeded',
    color: '#3b82f6', // Blue
    bg: 'rgba(59, 130, 246, 0.15)',
    border: 'rgba(59, 130, 246, 0.5)',
    glow: 'rgba(59, 130, 246, 0.6)'
  },
  {
    id: 5,
    label: 'Wrong answer',
    color: '#ef4444', // Red
    bg: 'rgba(239, 68, 68, 0.15)',
    border: 'rgba(239, 68, 68, 0.5)',
    glow: 'rgba(239, 68, 68, 0.6)'
  },
  {
    id: 6,
    label: 'Compilation error',
    color: '#eab308', // Yellow / Amber
    bg: 'rgba(234, 179, 8, 0.15)',
    border: 'rgba(234, 179, 8, 0.5)',
    glow: 'rgba(234, 179, 8, 0.6)'
  }
];

// Hexagon ring & star network cross edges
const EDGES: [number, number][] = [
  // Outer Hexagon Ring
  [0, 1], [1, 2], [2, 3], [3, 4], [4, 5], [5, 0],
  // Inner Cross Network Edges
  [0, 3], [1, 4], [2, 5], [0, 2], [1, 3], [3, 5]
];

export const MysticTopicSphere: React.FC = () => {
  const [angleRad, setAngleRad] = useState(0);

  useEffect(() => {
    let animationFrameId: number;
    let lastTime = performance.now();

    const animate = (time: number) => {
      const delta = time - lastTime;
      lastTime = time;

      setAngleRad((prev) => (prev + delta * 0.0003) % (Math.PI * 2));

      animationFrameId = requestAnimationFrame(animate);
    };

    animationFrameId = requestAnimationFrame(animate);

    return () => cancelAnimationFrame(animationFrameId);
  }, []);

  const totalNodes = VERDICT_NODES.length;
  const radius = window.innerWidth < 640 ? 135 : 175;

  const nodePositions = VERDICT_NODES.map((node, idx) => {
    const theta = (idx * (2 * Math.PI / totalNodes)) + angleRad;
    const x = Math.cos(theta) * radius;
    const y = Math.sin(theta) * radius;
    return { ...node, x, y };
  });

  return (
    <div className="relative w-full max-w-lg mx-auto h-[420px] sm:h-[480px] flex items-center justify-center font-mono overflow-visible select-none">
      {/* Ambient Background Aura */}
      <div className="absolute inset-0 flex items-center justify-center pointer-events-none">
        <div className="w-[300px] h-[300px] sm:w-[390px] sm:h-[390px] rounded-full bg-gradient-to-r from-green-500/10 via-amber-500/10 to-purple-500/10 blur-3xl animate-pulse" />
      </div>

      {/* GRAPH CANVAS (6 VERDICT NODES) - FIXED CONTAINER */}
      <div className="absolute inset-0 flex items-center justify-center pointer-events-none z-0">
        {/* SVG Network Edges */}
        <svg className="absolute inset-0 w-full h-full pointer-events-none overflow-visible">
          {EDGES.map(([fromIdx, toIdx], edgeIdx) => {
            const u = nodePositions[fromIdx];
            const v = nodePositions[toIdx];

            const centerX = window.innerWidth < 640 ? 190 : 256;
            const centerY = window.innerWidth < 640 ? 210 : 240;

            const x1 = centerX + u.x;
            const y1 = centerY + u.y;
            const x2 = centerX + v.x;
            const y2 = centerY + v.y;

            return (
              <line
                key={edgeIdx}
                x1={x1}
                y1={y1}
                x2={x2}
                y2={y2}
                stroke={u.color}
                strokeOpacity="0.35"
                strokeWidth="1.5"
                strokeDasharray={edgeIdx % 2 === 0 ? '4 4' : 'none'}
              />
            );
          })}
        </svg>

        {/* Orbiting Nodes */}
        {nodePositions.map((node) => (
          <div
            key={node.id}
            className="absolute transform -translate-x-1/2 -translate-y-1/2 pointer-events-auto transition-transform duration-75"
            style={{
              left: `calc(50% + ${node.x}px)`,
              top: `calc(50% + ${node.y}px)`,
            }}
          >
            <div
              className="flex items-center gap-1.5 px-3 py-1 rounded-full font-mono text-[11px] sm:text-xs font-extrabold whitespace-nowrap backdrop-blur-md shadow-xl transition-all hover:scale-110"
              style={{
                backgroundColor: node.bg,
                borderColor: node.border,
                borderWidth: '1px',
                borderStyle: 'solid',
                color: node.color,
                boxShadow: `0 0 14px ${node.glow}`
              }}
            >
              <div
                className="w-2.5 h-2.5 rounded-full animate-ping shrink-0"
                style={{ backgroundColor: node.color }}
              />
              <span>{node.label}</span>
            </div>
          </div>
        ))}
      </div>

      {/* LEVITATING CHARACTER IMAGE */}
      <div className="relative z-10 flex flex-col items-center justify-center pointer-events-none">
        <div className="relative group animate-float">
          {/* Glowing Backlight Aura */}
          <div className="absolute inset-0 bg-gradient-to-t from-orange-500/25 via-green-500/20 to-purple-500/20 blur-2xl rounded-full" />

          {/* Character Image */}
          <img
            src="/images/doctor_strange_siam.png"
            alt="Sorcerer of Algorithms"
            className="w-[240px] sm:w-[310px] h-auto object-contain drop-shadow-[0_0_30px_rgba(34,197,94,0.4)] select-none"
          />
        </div>
      </div>
    </div>
  );
};
