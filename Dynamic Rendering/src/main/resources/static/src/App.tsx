import React, { useEffect, useState } from 'react';
import { Navigation } from './components/Navigation';
import { Hero } from './components/Hero';
import { About } from './components/About';
import { Skills3DSphere } from './components/Skills3DSphere';
import { CPStats } from './components/CPStats';
import { AwardsTimeline } from './components/AwardsTimeline';
import { Projects } from './components/Projects';
import { Contact } from './components/Contact';
import { Footer } from './components/Footer';

export const App: React.FC = () => {
  const [cursorPos, setCursorPos] = useState({ x: 0, y: 0 });

  useEffect(() => {
    const handleMouseMove = (e: MouseEvent) => {
      setCursorPos({ x: e.clientX, y: e.clientY });
    };

    window.addEventListener('mousemove', handleMouseMove);
    return () => window.removeEventListener('mousemove', handleMouseMove);
  }, []);

  return (
    <div className="min-h-screen bg-[#0d0e12] text-slate-100 relative overflow-x-hidden">
      {/* Background Cursor Radial Glow */}
      <div
        className="cursor-glow hidden lg:block"
        style={{
          left: `${cursorPos.x}px`,
          top: `${cursorPos.y}px`,
        }}
      />

      {/* Cyber Grid Overlay */}
      <div className="fixed inset-0 grid-bg pointer-events-none z-0 opacity-40" />

      {/* Navigation */}
      <Navigation />

      {/* Main Sections */}
      <main className="relative z-10 pb-20 md:pb-0">
        <Hero />
        <About />
        <Skills3DSphere />
        <CPStats />
        <AwardsTimeline />
        <Projects />
        <Contact />
      </main>

      {/* Footer */}
      <Footer />
    </div>
  );
};

export default App;
