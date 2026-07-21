import React from 'react';
import { GitBranch, Calendar, Trophy, Award, CheckCircle } from 'lucide-react';
import { SIAM_DATA } from '../data/siamData';

export const AwardsTimeline: React.FC = () => {
  return (
    <section id="awards" className="min-h-screen py-20 px-6 lg:px-12 relative">
      <div className="max-w-5xl mx-auto relative z-10">
        {/* Header */}
        <div className="flex items-center gap-4 mb-16">
          <GitBranch className="w-6 h-6 text-orange-400" />
          <h2 className="font-mono text-2xl lg:text-3xl font-bold">
            <span className="text-slate-500">$</span> git log --stat --oneline
          </h2>
        </div>

        {/* Git Timeline Container */}
        <div className="relative pl-6 lg:pl-0">
          {/* Vertical Branch Line */}
          <div className="absolute left-6 lg:left-1/2 top-0 bottom-0 w-px bg-gradient-to-b from-orange-500/50 via-cyan-500/30 to-slate-800 transform -translate-x-1/2" />

          <div className="space-y-12">
            {/* 1. ICPC Regionalist & Meta Hacker Cup */}
            <div className="relative flex flex-col lg:flex-row items-center gap-8 lg:flex-row-reverse">
              <div className="hidden lg:flex w-1/2 justify-start">
                <div className="flex items-center gap-2 bg-[#161922] px-4 py-2 rounded-full border border-orange-500/30 text-xs font-mono text-slate-300 shadow-lg">
                  <Calendar className="w-3.5 h-3.5 text-orange-400" />
                  <span>2025</span>
                </div>
              </div>

              {/* Git Node Circle */}
              <div className="absolute left-6 lg:left-1/2 transform -translate-x-1/2 flex items-center justify-center z-10">
                <div className="w-5 h-5 rounded-full bg-[#0d0e12] border-2 border-orange-400 relative ring-4 ring-orange-500/20">
                  <div className="absolute inset-0 bg-orange-400 rounded-full opacity-60 animate-ping" />
                </div>
              </div>

              <div className="w-full lg:w-1/2 pl-10 lg:pl-0">
                <div className="bg-[#161922] rounded-xl border border-slate-800 hover:border-orange-500/40 transition-all duration-300 shadow-2xl p-6 group hover:-translate-y-1">
                  <div className="flex items-center justify-between border-b border-slate-800 pb-3 mb-4 font-mono text-xs">
                    <div className="flex items-center gap-2">
                      <span className="text-amber-400 font-bold">c0mm1t_icpc</span>
                      <span className="px-2 py-0.5 rounded bg-orange-500/10 text-orange-400 border border-orange-500/30 text-[10px]">
                        HEAD -&gt; regionalist
                      </span>
                    </div>
                    <span className="text-slate-500">Dhaka, Bangladesh</span>
                  </div>

                  <h3 className="text-lg font-bold text-white mb-2 group-hover:text-orange-400 transition-colors flex items-center gap-2">
                    <Trophy className="w-4 h-4 text-orange-400" />
                    ICPC Asia Dhaka Regionalist 2025
                  </h3>
                  <p className="text-sm font-mono text-slate-400 leading-relaxed border-l-2 border-orange-500/30 pl-4 py-1 mb-4">
                    Qualified as Regionalist with <span className="text-slate-200 font-semibold">Team AlgoFlare ≫ SEU</span>. Solved complex algorithmic tasks in the prestigious ICPC regional contest.
                  </p>

                  <div className="bg-cyan-500/10 border border-cyan-500/30 rounded-lg p-3 font-mono text-xs text-cyan-300 flex items-center gap-2 mb-4">
                    <Award className="w-4 h-4 shrink-0 text-cyan-400" />
                    <span>Meta Hacker Cup 2025 :: Qualified for Round 2 (International)</span>
                  </div>

                  <div className="flex flex-wrap gap-2 text-[10px] font-mono">
                    <span className="px-2 py-1 rounded bg-orange-500/10 text-orange-300 border border-orange-500/20">ICPC Regionalist</span>
                    <span className="px-2 py-1 rounded bg-cyan-500/10 text-cyan-300 border border-cyan-500/20">Meta Hacker Cup R2</span>
                    <span className="px-2 py-1 rounded bg-slate-800 text-slate-300">AlgoFlare</span>
                  </div>
                </div>
              </div>
            </div>

            {/* 2. IUPC Contests & Honors (Reordered as requested + SUST IUPC 2026 added) */}
            <div className="relative flex flex-col lg:flex-row items-center gap-8">
              <div className="hidden lg:flex w-1/2 justify-end">
                <div className="flex items-center gap-2 bg-[#161922] px-4 py-2 rounded-full border border-cyan-500/30 text-xs font-mono text-slate-300 shadow-lg">
                  <Calendar className="w-3.5 h-3.5 text-cyan-400" />
                  <span>2024 – 2026</span>
                </div>
              </div>

              {/* Git Node Circle */}
              <div className="absolute left-6 lg:left-1/2 transform -translate-x-1/2 flex items-center justify-center z-10">
                <div className="w-4 h-4 rounded-full bg-[#0d0e12] border-2 border-cyan-400 relative ring-4 ring-cyan-500/20" />
              </div>

              <div className="w-full lg:w-1/2 pl-10 lg:pl-0">
                <div className="bg-[#161922] rounded-xl border border-slate-800 hover:border-cyan-500/40 transition-all duration-300 shadow-2xl p-6 group hover:-translate-y-1">
                  <div className="flex items-center justify-between border-b border-slate-800 pb-3 mb-4 font-mono text-xs">
                    <div className="flex items-center gap-2">
                      <span className="text-yellow-400 font-bold">iupc_ranks</span>
                      <span className="px-2 py-0.5 rounded bg-cyan-500/10 text-cyan-400 border border-cyan-500/30 text-[10px]">
                        national_iupc
                      </span>
                    </div>
                    <span className="text-slate-500">Bangladesh</span>
                  </div>

                  <h3 className="text-lg font-bold text-white mb-2 group-hover:text-cyan-400 transition-colors">
                    Inter-University Programming Contests (IUPCs)
                  </h3>

                  <div className="space-y-2 font-mono text-xs text-slate-300 mb-4">
                    {/* 1st Requested Order */}
                    <div className="flex items-center gap-2">
                      <CheckCircle className="w-3.5 h-3.5 text-green-400 shrink-0" />
                      <span><strong className="text-white">31st Place</strong> — BUET IUPC 2024 (Team SEU1)</span>
                    </div>

                    {/* 2nd Requested Order */}
                    <div className="flex items-center gap-2">
                      <CheckCircle className="w-3.5 h-3.5 text-green-400 shrink-0" />
                      <span><strong className="text-white">19th Place</strong> — CodeClash Oscillon 2025 (AlgoFlare)</span>
                    </div>

                    {/* 3rd Requested Order */}
                    <div className="flex items-center gap-2">
                      <CheckCircle className="w-3.5 h-3.5 text-green-400 shrink-0" />
                      <span><strong className="text-white">51st Place</strong> — BUBT IUCPC 2025 (AlgoFlare)</span>
                    </div>

                    {/* SUST IUPC 2026 Included */}
                    <div className="text-slate-400 text-[11px] pt-1">
                      Participated in SUST IUPC 2026, BUET IUPC 2026, AUST IUPC 2025, DUET IUPC 2025, UU IUPC 2025, CUET IUPC 2025.
                    </div>
                  </div>

                  <div className="flex flex-wrap gap-2 text-[10px] font-mono">
                    <span className="px-2 py-1 rounded bg-slate-800 text-slate-300">BUET IUPC #31</span>
                    <span className="px-2 py-1 rounded bg-slate-800 text-slate-300">CodeClash #19</span>
                    <span className="px-2 py-1 rounded bg-slate-800 text-slate-300">BUBT IUPC #51</span>
                    <span className="px-2 py-1 rounded bg-slate-800 text-slate-300">SUST IUPC 2026</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Root Initial Commit */}
          <div className="mt-16 text-center relative z-10">
            <div className="inline-flex items-center gap-2 px-6 py-3 rounded-full bg-[#161922] border border-slate-800 text-xs font-mono text-slate-400 hover:text-orange-400 transition-colors hover:border-orange-500/40">
              <Award className="w-4 h-4 text-orange-400" />
              <span>Initial Commit (Hello World)</span>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
