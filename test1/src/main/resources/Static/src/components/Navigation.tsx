import React, { useState, useEffect } from 'react';
import { 
  CodeXml, 
  Terminal, 
  Cpu, 
  Trophy, 
  GitBranch, 
  FolderOpen, 
  Mail 
} from 'lucide-react';

interface NavItem {
  id: string;
  label: string;
  icon: React.ElementType;
}

export const NAV_ITEMS: NavItem[] = [
  { id: 'hero', label: 'Home', icon: CodeXml },
  { id: 'about', label: 'About', icon: Terminal },
  { id: 'skills', label: 'Skills', icon: Cpu },
  { id: 'cp-stats', label: 'CP Stats', icon: Trophy },
  { id: 'awards', label: 'Awards', icon: GitBranch },
  { id: 'projects', label: 'Projects', icon: FolderOpen },
  { id: 'contact', label: 'Contact', icon: Mail },
];

export const Navigation: React.FC = () => {
  const [activeSection, setActiveSection] = useState<string>('hero');

  useEffect(() => {
    const handleScroll = () => {
      const scrollPosition = window.scrollY + 250;
      for (const item of NAV_ITEMS) {
        const element = document.getElementById(item.id);
        if (element) {
          const top = element.offsetTop;
          const height = element.offsetHeight;
          if (scrollPosition >= top && scrollPosition < top + height) {
            setActiveSection(item.id);
            break;
          }
        }
      }
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const scrollTo = (id: string) => {
    setActiveSection(id);
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <>
      {/* Desktop Right HUD Quick Jump Dots - Positioned closer to right edge */}
      <div className="fixed right-2 sm:right-3 lg:right-4 top-1/2 -translate-y-1/2 z-50 hidden lg:flex flex-col items-center gap-4">
        <div className="absolute top-0 bottom-0 w-px bg-gradient-to-b from-transparent via-orange-500/20 to-transparent -z-10" />
        {NAV_ITEMS.map((item) => {
          const Icon = item.icon;
          const isActive = activeSection === item.id;
          return (
            <button
              key={item.id}
              onClick={() => scrollTo(item.id)}
              className="group relative flex items-center justify-center w-12 h-12 outline-none focus:outline-none cursor-pointer"
              aria-label={item.label}
            >
              {/* Floating Label Tooltip */}
              <div className="absolute right-14 px-3 py-1.5 rounded-md bg-[#161922] border border-orange-500/30 text-xs font-mono whitespace-nowrap opacity-0 group-hover:opacity-100 transition-all duration-300 translate-x-2 group-hover:translate-x-0 pointer-events-none shadow-[0_0_15px_rgba(249,115,22,0.15)] flex items-center gap-2 z-50">
                <span className="text-orange-400">&gt;</span>
                <span className={isActive ? 'text-orange-400 font-bold' : 'text-slate-300'}>{item.label}</span>
              </div>

              {/* HUD Target Ring Indicator */}
              <div className="relative w-full h-full flex items-center justify-center">
                <div
                  className={`absolute inset-0 m-auto rounded-full border border-orange-500/50 transition-all duration-500 ease-out ${
                    isActive ? 'w-full h-full opacity-100 rotate-180 scale-100' : 'w-4 h-4 opacity-0 scale-0'
                  }`}
                  style={{ borderStyle: 'dashed' }}
                />
                <div
                  className={`rounded-full transition-all duration-300 ease-out flex items-center justify-center relative z-10 ${
                    isActive
                      ? 'w-8 h-8 bg-[#0d0e12] border border-orange-400 text-orange-400 shadow-[0_0_20px_rgba(249,115,22,0.4)]'
                      : 'w-3 h-3 bg-slate-800 border border-slate-700 group-hover:w-4 group-hover:h-4 group-hover:border-orange-400/50 group-hover:bg-orange-500/20'
                  }`}
                >
                  <Icon className={`transition-all duration-300 ${isActive ? 'w-4 h-4 opacity-100 scale-100' : 'w-0 h-0 opacity-0 scale-0'}`} />
                </div>
              </div>
            </button>
          );
        })}
      </div>

      {/* Mobile Bottom Dock Bar */}
      <nav 
        className="fixed bottom-0 left-0 right-0 border-t border-slate-800/80 bg-[#12141c]/95 backdrop-blur-md z-50 lg:hidden"
        style={{ paddingBottom: 'env(safe-area-inset-bottom)' }}
      >
        <div className="grid grid-cols-7 items-center px-2 py-2">
          {NAV_ITEMS.map((item) => {
            const Icon = item.icon;
            const isActive = activeSection === item.id;
            return (
              <button
                key={item.id}
                onClick={() => scrollTo(item.id)}
                className={`mx-auto inline-flex h-10 w-10 items-center justify-center rounded-lg transition-colors ${
                  isActive ? 'text-orange-400 bg-orange-500/10 border border-orange-500/30' : 'text-slate-400 hover:text-slate-200'
                }`}
                aria-label={item.label}
              >
                <Icon className="w-5 h-5" />
              </button>
            );
          })}
        </div>
      </nav>
    </>
  );
};
