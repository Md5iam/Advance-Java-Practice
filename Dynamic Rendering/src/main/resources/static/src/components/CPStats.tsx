import React from 'react';
import { Trophy, ExternalLink, Zap } from 'lucide-react';
import { SIAM_DATA } from '../data/siamData';

export const CPStats: React.FC = () => {
  return (
    <section id="cp-stats" className="min-h-screen py-20 px-6 lg:px-12 relative">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="flex items-center gap-4 mb-12">
          <Trophy className="w-6 h-6 text-orange-400" />
          <h2 className="font-mono text-2xl lg:text-3xl font-bold">
            <span className="text-slate-500">$</span> cat cp_stats.json
          </h2>
        </div>

        {/* Global Solved Banner */}
        <div className="mb-12 relative overflow-hidden bg-gradient-to-r from-orange-500/10 via-amber-500/10 to-cyan-500/10 border border-orange-500/30 rounded-2xl p-6 lg:p-8 shadow-2xl">
          <div className="flex flex-col md:flex-row items-center justify-between gap-6 relative z-10">
            <div className="space-y-2 text-center md:text-left">
              <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-orange-500/10 text-orange-400 font-mono text-xs border border-orange-500/30">
                <Zap className="w-3.5 h-3.5 fill-current" />
                OVERALL COMPETITIVE PROGRAMMING MILESTONE
              </div>
              <h3 className="text-3xl font-extrabold text-white font-mono">
                2,000+ Problems Solved
              </h3>
              <p className="text-sm text-slate-400 max-w-xl">
                Active problem solver across online judges including Codeforces, Codechef, and Regional ICPC / IUPC Contests. Specializing in Graph Theory, Dynamic Programming, and Low-Latency Algorithm Design.
              </p>
            </div>

            <div className="flex items-center gap-4 shrink-0 font-mono">
              <div className="text-center p-4 bg-[#161922] border border-slate-800 rounded-xl">
                <div className="text-2xl font-bold text-cyan-400">ICPC</div>
                <div className="text-[10px] text-slate-400">Regionalist 2025</div>
              </div>
              <div className="text-center p-4 bg-[#161922] border border-slate-800 rounded-xl">
                <div className="text-2xl font-bold text-amber-400">Meta</div>
                <div className="text-[10px] text-slate-400">Hacker Cup R2</div>
              </div>
            </div>
          </div>
        </div>

        {/* Platform Cards Grid */}
        <div className="grid md:grid-cols-3 gap-6">
          {SIAM_DATA.cpPlatforms.map((platform, idx) => (
            <a
              key={idx}
              href={platform.profileUrl}
              target="_blank"
              rel="noopener noreferrer"
              className="group relative bg-[#161922] border border-slate-800 hover:border-orange-500/40 rounded-xl p-6 transition-all duration-300 shadow-xl hover:-translate-y-1 block"
            >
              <div className="flex justify-between items-start mb-6">
                <div>
                  <h4 className="font-mono text-xs text-slate-400 uppercase tracking-wider mb-1">
                    {platform.platform}
                  </h4>
                  <div className="font-mono font-bold text-lg text-white group-hover:text-orange-400 transition-colors flex items-center gap-2">
                    <span>@{platform.handle}</span>
                    <ExternalLink className="w-3.5 h-3.5 opacity-0 group-hover:opacity-100 transition-opacity" />
                  </div>
                </div>

                <div 
                  className="px-3 py-1 rounded-full text-xs font-mono font-bold shadow-md"
                  style={{ backgroundColor: `${platform.color}15`, color: platform.color, border: `1px solid ${platform.color}40` }}
                >
                  {platform.title}
                </div>
              </div>

              <div className="space-y-3 font-mono text-sm border-t border-slate-800/80 pt-4">
                <div className="flex justify-between items-center text-xs">
                  <span className="text-slate-500">MAX RATING</span>
                  <span className="text-white font-bold" style={{ color: platform.color }}>
                    {platform.maxRating}
                  </span>
                </div>
                <div className="flex justify-between items-center text-xs">
                  <span className="text-slate-500">SOLVED PROBLEMS</span>
                  <span className="text-slate-200">{platform.solveCount}</span>
                </div>
              </div>
            </a>
          ))}
        </div>
      </div>
    </section>
  );
};
