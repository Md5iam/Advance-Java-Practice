import React from 'react';
import { Terminal, ExternalLink, GitFork, ChevronDown } from 'lucide-react';
import { SIAM_DATA } from '../data/siamData';
import { MysticTopicSphere } from './MysticTopicSphere';

export const Hero: React.FC = () => {
  const scrollToSection = (id: string) => {
    const el = document.getElementById(id);
    if (el) el.scrollIntoView({ behavior: 'smooth' });
  };

  return (
    <section id="hero" className="min-h-screen flex items-center justify-center relative overflow-hidden py-24 lg:py-16">
      {/* Background Cyber Grid & Glow */}
      <div className="absolute inset-0 z-0 select-none pointer-events-none">
        <div className="absolute inset-0 grid-bg opacity-30" />
        <div className="absolute inset-0 bg-gradient-to-b from-[#0d0e12] via-transparent to-[#0d0e12] z-10" />
      </div>

      <div className="relative z-10 max-w-7xl mx-auto px-6 grid lg:grid-cols-12 gap-8 lg:gap-12 items-center w-full">
        {/* Left Column: Text & Hero Actions */}
        <div className="lg:col-span-7 xl:col-span-7 space-y-8 text-center lg:text-left order-2 lg:order-1">
          <div className="space-y-4">
            {/* Headline */}
            <h1 className="text-4xl sm:text-6xl lg:text-7xl font-extrabold tracking-tight leading-tight">
              <span className="text-slate-100">Hello, I’m </span>
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-orange-400 via-amber-400 to-cyan-400 relative inline-block">
                {SIAM_DATA.name}
                <div className="absolute -bottom-2 left-0 w-full h-1 bg-orange-500/50 rounded-full blur-sm" />
              </span>
            </h1>

            {/* Sub-headline */}
            <p className="text-base sm:text-lg text-slate-400 max-w-2xl mx-auto lg:mx-0 font-light leading-relaxed">
              <span className="text-orange-400 font-mono font-medium">&lt;CompetitiveProgrammer /&gt;</span>{' '}
              Turning challenging problems into efficient solutions through logic, clean code, and a strong passion for software engineering.
            </p>
          </div>

          {/* Action Button Cards */}
          <div className="flex flex-col sm:flex-row gap-4 items-stretch w-full max-w-xl mx-auto lg:mx-0">
            {/* Initialize OS Button */}
            <button
              onClick={() => scrollToSection('cp-stats')}
              className="relative group cursor-pointer flex-1 text-left outline-none"
            >
              <div 
                className="absolute -inset-0.5 bg-gradient-to-r from-orange-500 to-cyan-500 opacity-40 group-hover:opacity-80 transition duration-500 rounded-lg blur-xs"
              />
              <div className="relative bg-[#161922] border border-orange-500/30 p-4 rounded-lg flex items-center gap-4 hover:bg-[#1f2330] transition-colors shadow-2xl h-full">
                <div className="w-10 h-10 shrink-0 flex items-center justify-center bg-orange-500/10 rounded border border-orange-500/30 group-hover:scale-105 transition-all">
                  <Terminal className="w-5 h-5 text-orange-400" />
                </div>
                <div className="flex-1 min-w-0">
                  <div className="flex justify-between items-center mb-1">
                    <h3 className="text-sm font-bold text-white group-hover:text-orange-400 transition-colors truncate">
                      Initialize OS
                    </h3>
                    <ExternalLink className="w-3.5 h-3.5 text-slate-500 group-hover:text-orange-400 transition-colors ml-2" />
                  </div>
                  <div className="w-full bg-slate-800 h-1.5 rounded-full overflow-hidden mb-1">
                    <div className="h-full bg-orange-500 w-[75%] group-hover:w-[100%] transition-all duration-700 ease-out relative" />
                  </div>
                  <div className="flex justify-between font-mono text-[10px] text-slate-400">
                    <span>&gt; sudo boot_solver</span>
                    <span className="text-orange-400 group-hover:animate-pulse">ONLINE</span>
                  </div>
                </div>
              </div>
            </button>

            {/* GitHub Card Link */}
            <a
              href={SIAM_DATA.github}
              target="_blank"
              rel="noopener noreferrer"
              className="relative group cursor-pointer shrink-0 outline-none"
            >
              <div className="absolute -inset-0.5 bg-gradient-to-r from-cyan-500 to-blue-500 opacity-30 group-hover:opacity-70 transition duration-500 rounded-lg blur-xs" />
              <div className="relative bg-[#161922] border border-slate-700/80 group-hover:border-cyan-500/50 p-4 rounded-lg flex items-center justify-center gap-3 hover:bg-[#1f2330] transition-colors shadow-xl h-full min-w-[130px]">
                <GitFork className="w-5 h-5 text-white group-hover:text-cyan-400 transition-colors" />
                <div className="text-left">
                  <div className="text-[10px] text-slate-400 font-mono leading-none mb-1">GitHub</div>
                  <div className="font-bold text-white text-sm group-hover:text-cyan-400 transition-colors leading-none">
                    @md5iam
                  </div>
                </div>
              </div>
            </a>
          </div>

          {/* Module Tech Badges */}
          <div className="pt-2 flex flex-wrap justify-center lg:justify-start gap-2 opacity-90">
            <span className="text-xs font-mono text-slate-500 mr-2 py-1">LOADED_MODULES:</span>
            {['C', 'C++', 'GO', 'JAVA', 'PYTHON', 'JAVASCRIPT'].map((mod) => (
              <span
                key={mod}
                className="px-2.5 py-1 bg-orange-500/5 border border-orange-500/20 text-orange-400/90 text-[10px] font-mono rounded tracking-wider hover:bg-orange-500/15 hover:border-orange-500/40 transition-colors cursor-default"
              >
                {mod}
              </span>
            ))}
          </div>
        </div>

        {/* Right Column: Compact Mystic Doctor Strange Orbit */}
        <div className="lg:col-span-5 xl:col-span-5 order-1 lg:order-2 flex justify-center lg:justify-center lg:-ml-12">
          <MysticTopicSphere />
        </div>
      </div>

      {/* Bounce Chevron Indicator */}
      <button 
        onClick={() => scrollToSection('about')}
        className="absolute bottom-6 left-1/2 -translate-x-1/2 flex flex-col items-center gap-2 text-slate-500 hover:text-orange-400 transition-colors animate-bounce cursor-pointer outline-none"
        aria-label="Scroll to About section"
      >
        <ChevronDown className="w-6 h-6" />
      </button>
    </section>
  );
};
