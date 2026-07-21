import React from 'react';
import { Terminal, Trophy, UserCheck, Code2 } from 'lucide-react';
import { SIAM_DATA } from '../data/siamData';

export const About: React.FC = () => {
  return (
    <section id="about" className="min-h-screen flex items-center py-20 px-6 lg:px-12 relative">
      <div className="max-w-6xl mx-auto w-full">
        {/* Section Header */}
        <div className="flex items-center gap-4 mb-12">
          <Terminal className="w-6 h-6 text-orange-400" />
          <h2 className="font-mono text-2xl lg:text-3xl font-bold">
            <span className="text-slate-500">#</span> About.system
          </h2>
        </div>

        <div className="grid lg:grid-cols-2 gap-12 lg:gap-16 items-start">
          {/* Left Column: Cyber HUD Operator Card */}
          <div className="relative group mt-4 lg:mt-8">
            <div className="absolute -inset-1 bg-gradient-to-r from-orange-500 to-cyan-500 rounded-2xl blur opacity-20 group-hover:opacity-40 transition duration-1000" />
            
            <div className="relative bg-[#161922] border border-orange-500/30 rounded-2xl p-6 lg:p-8 overflow-hidden shadow-2xl">
              {/* Animated Profile Avatar Ring */}
              <div className="relative w-48 h-48 mx-auto mb-8">
                <div className="absolute inset-0 rounded-full border-2 border-orange-500/30 animate-spin-slow border-t-transparent border-b-transparent" />
                <div className="absolute inset-2 rounded-full border-2 border-cyan-500/30 animate-spin-slow-reverse border-l-transparent border-r-transparent" />
                
                {/* Avatar Box with User Photo */}
                <div className="absolute inset-4 rounded-full bg-[#0d0e12] border-2 border-slate-700 flex items-center justify-center overflow-hidden shadow-2xl group-hover:border-orange-500/60 transition-colors">
                  <img
                    src="/images/siam_profile.jpg"
                    alt="Md. Siam"
                    className="w-full h-full object-cover object-center group-hover:scale-105 transition-transform duration-500"
                  />
                </div>

                {/* Status Dot */}
                <div className="absolute bottom-3 right-3 w-4 h-4 bg-green-500 rounded-full border-2 border-[#161922] animate-pulse z-20" title="Status: Open" />
              </div>

              {/* Operator Info Grid */}
              <div className="font-mono text-xs sm:text-sm space-y-3">
                <div className="flex justify-between items-center border-b border-slate-800 pb-2">
                  <span className="text-slate-500">OPERATOR</span>
                  <span className="text-orange-400 font-bold tracking-widest">{SIAM_DATA.name.toUpperCase()}</span>
                </div>
                <div className="flex justify-between items-center border-b border-slate-800 pb-2">
                  <span className="text-slate-500">ROLE</span>
                  <span className="text-cyan-400">BACKEND_DEVELOPER</span>
                </div>
                <div className="flex justify-between items-center border-b border-slate-800 pb-2">
                  <span className="text-slate-500">LOCATION</span>
                  <span className="text-slate-200">{SIAM_DATA.location}</span>
                </div>
                <div className="flex justify-between items-center pb-1">
                  <span className="text-slate-500">STATUS</span>
                  <span className="bg-green-500/10 text-green-400 px-2.5 py-0.5 rounded text-xs border border-green-500/20 font-bold">
                    OPEN
                  </span>
                </div>
              </div>

              {/* Overlay Scanline */}
              <div className="absolute top-0 left-0 w-full h-1 bg-orange-400/30 shadow-[0_0_15px_rgba(249,115,22,0.5)] animate-scanline pointer-events-none" />
            </div>
          </div>

          {/* Right Column: Profile Log & Stats */}
          <div className="space-y-8">
            {/* Log Terminal Window */}
            <div className="bg-[#161922] rounded-xl border border-slate-800 font-mono text-xs sm:text-sm shadow-2xl overflow-hidden group hover:border-orange-500/30 transition-colors">
              <div className="bg-[#1f2330] px-4 py-2.5 flex items-center gap-2 border-b border-slate-800">
                <Terminal className="w-3.5 h-3.5 text-slate-400" />
                <span className="text-slate-400 text-xs">user_profile.log</span>
              </div>
              <div className="p-6 text-slate-300 leading-relaxed space-y-4">
                <p>
                  <span className="text-cyan-400">➜</span> <span className="text-purple-400">whoami</span>
                </p>
                <div className="pl-4 border-l-2 border-slate-700 space-y-2 text-slate-300">
                  {SIAM_DATA.bioLog.map((log, i) => (
                    <p key={i}>{log}</p>
                  ))}
                </div>

                <p>
                  <span className="text-cyan-400">➜</span> <span className="text-purple-400">cat</span> <span className="text-yellow-400">mission.txt</span>
                </p>
                <p className="pl-4 border-l-2 border-slate-700 text-slate-400">
                  My primary focus is <span className="text-orange-400 font-semibold">competitive programming</span> — striving to excel on the <span className="text-cyan-400 font-semibold">ICPC Regional &amp; national stage</span>, while engineering high-concurrency, reliable <span className="text-white font-semibold">backend systems</span>.
                </p>
              </div>
            </div>

            {/* Stat Counters Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
              <div className="bg-[#161922] p-4 rounded-xl border border-slate-800 hover:border-orange-500/40 transition-all group">
                <div className="flex items-center gap-2 mb-1">
                  <Trophy className="w-4 h-4 text-orange-400" />
                  <span className="text-[10px] text-slate-400 font-mono uppercase">SOLVED</span>
                </div>
                <div className="text-2xl font-bold text-white font-mono group-hover:text-orange-400 transition-colors">
                  {SIAM_DATA.statsCounters.solvedProblems}
                </div>
                <div className="text-[10px] text-slate-500 font-mono">Problems</div>
              </div>

              <div className="bg-[#161922] p-4 rounded-xl border border-slate-800 hover:border-cyan-500/40 transition-all group">
                <div className="flex items-center gap-2 mb-1">
                  <Code2 className="w-4 h-4 text-cyan-400" />
                  <span className="text-[10px] text-slate-400 font-mono uppercase">CODEFORCES</span>
                </div>
                <div className="text-2xl font-bold text-white font-mono group-hover:text-cyan-400 transition-colors">
                  1405
                </div>
                <div className="text-[10px] text-slate-500 font-mono">Specialist</div>
              </div>

              <div className="bg-[#161922] p-4 rounded-xl border border-slate-800 hover:border-green-500/40 transition-all group">
                <div className="flex items-center gap-2 mb-1">
                  <UserCheck className="w-4 h-4 text-green-400" />
                  <span className="text-[10px] text-slate-400 font-mono uppercase">ICPC</span>
                </div>
                <div className="text-2xl font-bold text-white font-mono group-hover:text-green-400 transition-colors">
                  {SIAM_DATA.statsCounters.icpcRegionalist}
                </div>
                <div className="text-[10px] text-slate-500 font-mono">Regionalist</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
