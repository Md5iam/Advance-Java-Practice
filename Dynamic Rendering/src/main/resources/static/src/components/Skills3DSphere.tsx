import React, { useEffect, useRef, useState } from 'react';
import { Cpu } from 'lucide-react';

interface SkillItem {
  id: string;
  name: string;
  deviconClass: string;
}

const SKILLS_LIST: SkillItem[] = [
  { id: 'cpp', name: 'C++', deviconClass: 'devicon-cplusplus-plain colored' },
  { id: 'c', name: 'C', deviconClass: 'devicon-c-plain colored' },
  { id: 'go', name: 'Go', deviconClass: 'devicon-go-original-wordmark colored' },
  { id: 'java', name: 'Java', deviconClass: 'devicon-java-plain colored' },
  { id: 'springboot', name: 'Spring Boot', deviconClass: 'devicon-spring-plain colored' },
  { id: 'python', name: 'Python', deviconClass: 'devicon-python-plain colored' },
  { id: 'fastapi', name: 'FastAPI', deviconClass: 'devicon-fastapi-plain colored' },
  { id: 'react', name: 'React', deviconClass: 'devicon-react-original colored' },
  { id: 'ts', name: 'TypeScript', deviconClass: 'devicon-typescript-plain colored' },
  { id: 'postgres', name: 'PostgreSQL', deviconClass: 'devicon-postgresql-plain colored' },
  { id: 'mysql', name: 'MySQL', deviconClass: 'devicon-mysql-plain colored' },
  { id: 'docker', name: 'Docker', deviconClass: 'devicon-docker-plain colored' },
  { id: 'postman', name: 'Postman', deviconClass: 'devicon-postman-plain colored' },
  { id: 'git', name: 'Git', deviconClass: 'devicon-git-plain colored' },
  { id: 'github', name: 'GitHub', deviconClass: 'devicon-github-original' },
  { id: 'linux', name: 'Linux', deviconClass: 'devicon-linux-plain text-slate-200' },
  { id: 'bash', name: 'Bash', deviconClass: 'devicon-bash-plain colored' },
  { id: 'html5', name: 'HTML5', deviconClass: 'devicon-html5-plain colored' },
  { id: 'css3', name: 'CSS3', deviconClass: 'devicon-css3-plain colored' },
  { id: 'vscode', name: 'VS Code', deviconClass: 'devicon-vscode-plain colored' }
];

interface Point3D {
  x: number;
  y: number;
  z: number;
}

interface Tag3D {
  item: SkillItem;
  x: number;
  y: number;
  z: number;
}

export const Skills3DSphere: React.FC = () => {
  const containerRef = useRef<HTMLDivElement | null>(null);
  const canvasRef = useRef<HTMLCanvasElement | null>(null);
  const [hoveredSkillId, setHoveredSkillId] = useState<string | null>(null);
  const [projectedTags, setProjectedTags] = useState<{ item: SkillItem; screenX: number; screenY: number; scale: number; alpha: number; zIndex: number }[]>([]);

  useEffect(() => {
    const canvas = canvasRef.current;
    const container = containerRef.current;
    if (!canvas || !container) return;

    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    let width = (canvas.width = container.clientWidth || 700);
    let height = (canvas.height = 500);

    const handleResize = () => {
      if (container) {
        width = canvas.width = container.clientWidth;
        height = canvas.height = 500;
      }
    };

    window.addEventListener('resize', handleResize);

    const radius = Math.min(width, height) * 0.38;
    const totalCount = SKILLS_LIST.length;
    const tags: Tag3D[] = [];

    // Distribute skills on 3D sphere using Fibonacci algorithm
    const phi = Math.PI * (3 - Math.sqrt(5));
    for (let i = 0; i < totalCount; i++) {
      const y = 1 - (i / (totalCount - 1)) * 2;
      const r = Math.sqrt(1 - y * y);
      const theta = phi * i;

      tags.push({
        item: SKILLS_LIST[i],
        x: r * Math.cos(theta) * radius,
        y: y * radius,
        z: r * Math.sin(theta) * radius,
      });
    }

    let rx = 0.007;
    let ry = 0.012;
    let isDragging = false;
    let lastMouseX = 0;
    let lastMouseY = 0;

    const onMouseDown = (e: MouseEvent | TouchEvent) => {
      isDragging = true;
      const clientX = 'touches' in e ? e.touches[0].clientX : e.clientX;
      const clientY = 'touches' in e ? e.touches[0].clientY : e.clientY;
      lastMouseX = clientX;
      lastMouseY = clientY;
    };

    const onMouseMove = (e: MouseEvent | TouchEvent) => {
      if (!isDragging) return;
      const clientX = 'touches' in e ? e.touches[0].clientX : e.clientX;
      const clientY = 'touches' in e ? e.touches[0].clientY : e.clientY;

      const dx = clientX - lastMouseX;
      const dy = clientY - lastMouseY;

      ry = -dx * 0.004;
      rx = -dy * 0.004;

      lastMouseX = clientX;
      lastMouseY = clientY;
    };

    const onMouseUp = () => {
      isDragging = false;
    };

    container.addEventListener('mousedown', onMouseDown);
    container.addEventListener('mousemove', onMouseMove);
    window.addEventListener('mouseup', onMouseUp);

    container.addEventListener('touchstart', onMouseDown);
    container.addEventListener('touchmove', onMouseMove);
    window.addEventListener('touchend', onMouseUp);

    let animationFrameId: number;

    const render = () => {
      ctx.clearRect(0, 0, width, height);

      const sinX = Math.sin(rx);
      const cosX = Math.cos(rx);
      const sinY = Math.sin(ry);
      const cosY = Math.cos(ry);

      // Rotate sphere tags
      for (const tag of tags) {
        const x1 = tag.x * cosY - tag.z * sinY;
        const z1 = tag.z * cosY + tag.x * sinY;

        const y1 = tag.y * cosX - z1 * sinX;
        const z2 = z1 * cosX + tag.y * sinX;

        tag.x = x1;
        tag.y = y1;
        tag.z = z2;
      }

      const centerX = width / 2;
      const centerY = height / 2;

      // Draw inner glowing sphere core
      ctx.beginPath();
      ctx.arc(centerX, centerY, radius * 0.95, 0, Math.PI * 2);
      ctx.fillStyle = 'rgba(22, 25, 34, 0.4)';
      ctx.fill();
      ctx.strokeStyle = 'rgba(249, 115, 22, 0.2)';
      ctx.lineWidth = 1.5;
      ctx.stroke();

      // Connect 3D mesh lines between surface nodes
      for (let i = 0; i < tags.length; i++) {
        for (let j = i + 1; j < tags.length; j++) {
          const dx = tags[i].x - tags[j].x;
          const dy = tags[i].y - tags[j].y;
          const dz = tags[i].z - tags[j].z;
          const dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

          if (dist < radius * 1.4) {
            const avgZ = (tags[i].z + tags[j].z) / 2;
            const lineAlpha = Math.max(0.05, (avgZ + radius) / (radius * 3));

            ctx.beginPath();
            ctx.moveTo(centerX + tags[i].x, centerY + tags[i].y);
            ctx.lineTo(centerX + tags[j].x, centerY + tags[j].y);
            ctx.strokeStyle = avgZ > 0 ? `rgba(249, 115, 22, ${lineAlpha * 0.6})` : `rgba(255, 255, 255, ${lineAlpha * 0.15})`;
            ctx.lineWidth = avgZ > 0 ? 1.2 : 0.8;
            ctx.stroke();
          }
        }
      }

      // Project tags for HTML overlay with logos
      const projected = tags.map((tag) => {
        const scale = (tag.z + radius * 2) / (radius * 3);
        const alpha = Math.max(0.15, (tag.z + radius) / (radius * 2));
        return {
          item: tag.item,
          screenX: centerX + tag.x,
          screenY: centerY + tag.y,
          scale: Math.max(0.55, scale),
          alpha: Math.min(1, alpha),
          zIndex: Math.floor(tag.z + radius)
        };
      });

      setProjectedTags(projected);

      // Smooth fast continuous auto-rotation
      if (!isDragging) {
        rx *= 0.98;
        ry *= 0.98;
        if (Math.abs(rx) < 0.005) rx = 0.006;
        if (Math.abs(ry) < 0.009) ry = 0.011;
      }

      animationFrameId = requestAnimationFrame(render);
    };

    render();

    return () => {
      cancelAnimationFrame(animationFrameId);
      window.removeEventListener('resize', handleResize);
      container.removeEventListener('mousedown', onMouseDown);
      container.removeEventListener('mousemove', onMouseMove);
      window.removeEventListener('mouseup', onMouseUp);
      container.removeEventListener('touchstart', onMouseDown);
      container.removeEventListener('touchmove', onMouseMove);
      window.removeEventListener('touchend', onMouseUp);
    };
  }, []);

  return (
    <section id="skills" className="min-h-screen py-20 px-6 lg:px-12">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="flex items-center gap-4 mb-8">
          <Cpu className="w-6 h-6 text-orange-400" />
          <h2 className="font-mono text-2xl lg:text-3xl font-bold">
            <span className="text-slate-500">#</span> Skills.json
          </h2>
        </div>

        {/* 3D Sphere Container */}
        <div
          ref={containerRef}
          className="relative w-full h-[500px] bg-[#12141c]/60 border border-slate-800 rounded-2xl overflow-hidden shadow-2xl flex items-center justify-center cursor-grab active:cursor-grabbing select-none"
        >
          {/* 3D Canvas Mesh */}
          <canvas ref={canvasRef} className="absolute inset-0 block w-full h-full" />

          {/* Floating HTML Tech Icons & Labels */}
          <div className="absolute inset-0 pointer-events-none">
            {projectedTags.map((p) => {
              const isHovered = hoveredSkillId === p.item.id;

              return (
                <div
                  key={p.item.id}
                  onMouseEnter={() => setHoveredSkillId(p.item.id)}
                  onMouseLeave={() => setHoveredSkillId(null)}
                  className="absolute transform -translate-x-1/2 -translate-y-1/2 flex flex-col items-center justify-center pointer-events-auto cursor-pointer transition-transform duration-75"
                  style={{
                    left: `${p.screenX}px`,
                    top: `${p.screenY}px`,
                    transform: `translate(-50%, -50%) scale(${isHovered ? p.scale * 1.3 : p.scale})`,
                    opacity: isHovered ? 1 : p.alpha,
                    zIndex: isHovered ? 9999 : p.zIndex,
                  }}
                >
                  {/* Devicon Tech Logo Card with Neon Hover Glow */}
                  <div
                    className={`w-12 h-12 sm:w-14 sm:h-14 rounded-2xl backdrop-blur-md flex items-center justify-center p-2 mb-1 transition-all duration-300 ${
                      isHovered
                        ? 'bg-gradient-to-br from-orange-500/30 via-amber-500/30 to-cyan-500/30 border-2 border-orange-400 shadow-[0_0_30px_rgba(249,115,22,0.9)] scale-125 ring-4 ring-orange-500/20'
                        : 'bg-[#161922]/90 border border-slate-700/80 shadow-2xl hover:border-orange-500/50'
                    }`}
                  >
                    <i className={`${p.item.deviconClass} text-2xl sm:text-3xl ${isHovered ? 'animate-bounce' : ''}`} />
                  </div>

                  {/* Skill Name Label */}
                  <span
                    className={`font-mono text-[10px] sm:text-xs font-bold transition-colors whitespace-nowrap ${
                      isHovered
                        ? 'text-orange-400 bg-[#161922] px-2.5 py-1 rounded-full border border-orange-400 shadow-[0_0_15px_rgba(249,115,22,0.6)]'
                        : 'text-slate-200 bg-[#0d0e12]/80 px-2 py-0.5 rounded border border-slate-800 shadow'
                    }`}
                  >
                    {p.item.name}
                  </span>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </section>
  );
};
