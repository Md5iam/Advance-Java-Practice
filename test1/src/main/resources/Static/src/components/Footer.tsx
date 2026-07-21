import React from 'react';
import { Github, Linkedin, Mail, ArrowUp } from 'lucide-react';
import { SIAM_DATA } from '../data/siamData';

export const Footer: React.FC = () => {
  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <footer className="border-t border-slate-800 bg-[#0d0e12] py-12 px-6 lg:px-12 font-mono text-xs text-slate-400 relative">
      <div className="max-w-6xl mx-auto flex flex-col md:flex-row items-center justify-between gap-6 text-center md:text-left">
        <div className="space-y-1">
          <div className="font-bold text-slate-200 text-sm flex items-center justify-center md:justify-start gap-2">
            <span>{SIAM_DATA.name}</span>
            <span className="text-orange-400">|</span>
            <span className="text-slate-400 font-normal">{SIAM_DATA.role}</span>
          </div>
        </div>

        <div className="flex items-center gap-4">
          <a
            href={SIAM_DATA.github}
            target="_blank"
            rel="noopener noreferrer"
            className="p-2 rounded-lg bg-[#161922] border border-slate-800 hover:border-orange-500/40 text-slate-400 hover:text-orange-400 transition-colors"
            title="GitHub"
          >
            <Github className="w-4 h-4" />
          </a>
          <a
            href={SIAM_DATA.linkedin}
            target="_blank"
            rel="noopener noreferrer"
            className="p-2 rounded-lg bg-[#161922] border border-slate-800 hover:border-cyan-500/40 text-slate-400 hover:text-cyan-400 transition-colors"
            title="LinkedIn"
          >
            <Linkedin className="w-4 h-4" />
          </a>
          <a
            href={`mailto:${SIAM_DATA.email}`}
            className="p-2 rounded-lg bg-[#161922] border border-slate-800 hover:border-green-500/40 text-slate-400 hover:text-green-400 transition-colors"
            title="Email"
          >
            <Mail className="w-4 h-4" />
          </a>

          <button
            onClick={scrollToTop}
            className="p-2 rounded-lg bg-orange-500/10 border border-orange-500/30 text-orange-400 hover:bg-orange-500/20 transition-colors ml-2 cursor-pointer"
            title="Scroll to top"
          >
            <ArrowUp className="w-4 h-4" />
          </button>
        </div>
      </div>

      <div className="max-w-6xl mx-auto mt-8 pt-6 border-t border-slate-800/60 text-center text-[10px] text-slate-600">
        &copy; {new Date().getFullYear()} {SIAM_DATA.name}. All rights reserved.
      </div>
    </footer>
  );
};
