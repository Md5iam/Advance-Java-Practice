import React, { useState } from 'react';
import { FolderOpen, Github, Star, GitFork, X, CheckCircle, Code, ExternalLink } from 'lucide-react';
import { SIAM_DATA, ProjectData } from '../data/siamData';

export const Projects: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'All' | 'Backend' | 'Systems' | 'Web'>('All');
  const [selectedProject, setSelectedProject] = useState<ProjectData | null>(null);

  const filteredProjects = activeTab === 'All'
    ? SIAM_DATA.projects
    : SIAM_DATA.projects.filter(p => p.category === activeTab);

  return (
    <section id="projects" className="min-h-screen py-20 px-6 lg:px-12 relative">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="flex items-center gap-4 mb-8">
          <FolderOpen className="w-6 h-6 text-orange-400" />
          <h2 className="font-mono text-2xl lg:text-3xl font-bold">
            <span className="text-slate-500">$</span> ls -la ~/projects
          </h2>
        </div>

        {/* Category Filter Tabs */}
        <div className="flex flex-wrap gap-2 mb-10">
          {(['All', 'Backend', 'Systems', 'Web'] as const).map((tab) => (
            <button
              key={tab}
              onClick={() => setActiveTab(tab)}
              className={`px-4 py-2 rounded-lg font-mono text-xs transition-all cursor-pointer ${
                activeTab === tab
                  ? 'bg-orange-500/20 text-orange-400 border border-orange-500/40 font-bold shadow-lg'
                  : 'bg-[#161922] text-slate-400 hover:text-slate-200 border border-slate-800'
              }`}
            >
              ./{tab.toLowerCase()}
            </button>
          ))}
        </div>

        {/* Projects Grid */}
        <div className="grid md:grid-cols-2 gap-8">
          {filteredProjects.map((project) => (
            <div
              key={project.id}
              className="bg-[#161922] rounded-xl border border-slate-800 hover:border-orange-500/40 transition-all duration-300 shadow-2xl flex flex-col group hover:-translate-y-1 overflow-hidden"
            >
              {/* Card Header */}
              <div className="bg-[#1f2330] px-5 py-3 border-b border-slate-800 flex items-center justify-between font-mono text-xs">
                <div className="flex items-center gap-2">
                  <span className="w-2.5 h-2.5 rounded-full bg-green-500/80" />
                  <span className="text-slate-300 font-bold">{project.id}</span>
                </div>
                <span className="px-2 py-0.5 rounded bg-slate-800 text-slate-400 text-[10px] border border-slate-700">
                  {project.category}
                </span>
              </div>

              {/* Card Body */}
              <div className="p-6 flex-1 flex flex-col justify-between space-y-6">
                <div>
                  <h3 className="text-xl font-bold text-white mb-2 group-hover:text-orange-400 transition-colors">
                    {project.title}
                  </h3>

                  <div className="space-y-2 text-slate-300 font-mono text-xs leading-relaxed mb-4">
                    {project.description.map((bullet, idx) => (
                      <p key={idx} className="flex items-start gap-2">
                        <span className="text-orange-400 shrink-0">➜</span>
                        <span>{bullet}</span>
                      </p>
                    ))}
                  </div>
                </div>

                {/* Tech Stack Badges */}
                <div className="space-y-4">
                  <div className="flex flex-wrap gap-2">
                    {project.tools.map((tool) => (
                      <span
                        key={tool}
                        className="px-2.5 py-1 bg-orange-500/5 text-orange-300 border border-orange-500/20 text-[10px] font-mono rounded"
                      >
                        {tool}
                      </span>
                    ))}
                  </div>

                  {/* Actions Bar */}
                  <div className="flex items-center justify-between border-t border-slate-800/80 pt-4 font-mono text-xs">
                    <div className="flex items-center gap-4 text-slate-500">
                      <div className="flex items-center gap-1">
                        <Star className="w-3.5 h-3.5 text-amber-400" />
                        <span>{project.stars || 15}</span>
                      </div>
                      <div className="flex items-center gap-1">
                        <GitFork className="w-3.5 h-3.5 text-cyan-400" />
                        <span>{project.forks || 4}</span>
                      </div>
                    </div>

                    <div className="flex items-center gap-2">
                      <button
                        onClick={() => setSelectedProject(project)}
                        className="px-3 py-1.5 rounded bg-slate-800 hover:bg-slate-700 text-slate-300 hover:text-white transition-colors text-xs font-mono cursor-pointer"
                      >
                        Details
                      </button>
                      {project.demoUrl && (
                        <a
                          href={project.demoUrl}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="p-1.5 rounded bg-cyan-500/10 text-cyan-400 border border-cyan-500/30 hover:bg-cyan-500/20 transition-colors"
                          title="Live Demo"
                        >
                          <ExternalLink className="w-4 h-4" />
                        </a>
                      )}
                      <a
                        href={project.githubUrl || SIAM_DATA.github}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="p-1.5 rounded bg-orange-500/10 text-orange-400 border border-orange-500/30 hover:bg-orange-500/20 transition-colors"
                        title="View Repository"
                      >
                        <Github className="w-4 h-4" />
                      </a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Project Detail Modal */}
        {selectedProject && (
          <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm animate-fade-in">
            <div className="bg-[#161922] border border-orange-500/40 rounded-2xl max-w-2xl w-full overflow-hidden shadow-2xl">
              <div className="bg-[#1f2330] px-6 py-4 border-b border-slate-800 flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <Code className="w-5 h-5 text-orange-400" />
                  <h3 className="font-mono font-bold text-lg text-white">
                    {selectedProject.title}
                  </h3>
                </div>
                <button
                  onClick={() => setSelectedProject(null)}
                  className="p-1 rounded text-slate-400 hover:text-white hover:bg-slate-800 transition-colors cursor-pointer"
                >
                  <X className="w-5 h-5" />
                </button>
              </div>

              <div className="p-6 space-y-6 font-mono text-sm">
                <div>
                  <h4 className="text-xs text-slate-400 uppercase tracking-wider mb-2">Category &amp; Architecture</h4>
                  <div className="text-slate-200">
                    Category: <span className="text-orange-400 font-bold">{selectedProject.category}</span>
                  </div>
                </div>

                <div>
                  <h4 className="text-xs text-slate-400 uppercase tracking-wider mb-2">Key Implementation Highlights</h4>
                  <div className="space-y-2 text-slate-300 text-xs leading-relaxed bg-[#0d0e12] p-4 rounded-xl border border-slate-800">
                    {selectedProject.description.map((desc, i) => (
                      <div key={i} className="flex items-start gap-2">
                        <CheckCircle className="w-4 h-4 text-green-400 shrink-0 mt-0.5" />
                        <span>{desc}</span>
                      </div>
                    ))}
                  </div>
                </div>

                <div>
                  <h4 className="text-xs text-slate-400 uppercase tracking-wider mb-2">Technologies &amp; Frameworks</h4>
                  <div className="flex flex-wrap gap-2">
                    {selectedProject.tools.map((t) => (
                      <span key={t} className="px-3 py-1 bg-orange-500/10 border border-orange-500/30 text-orange-300 rounded text-xs">
                        {t}
                      </span>
                    ))}
                  </div>
                </div>

                <div className="pt-4 border-t border-slate-800 flex justify-end gap-3">
                  <button
                    onClick={() => setSelectedProject(null)}
                    className="px-4 py-2 rounded bg-slate-800 text-slate-300 hover:bg-slate-700 transition-colors text-xs cursor-pointer"
                  >
                    Close Window
                  </button>
                  {selectedProject.demoUrl && (
                    <a
                      href={selectedProject.demoUrl}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="px-4 py-2 rounded bg-cyan-500 text-white hover:bg-cyan-600 transition-colors text-xs font-bold flex items-center gap-2"
                    >
                      <ExternalLink className="w-4 h-4" />
                      <span>Live Demo</span>
                    </a>
                  )}
                  <a
                    href={selectedProject.githubUrl || SIAM_DATA.github}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="px-4 py-2 rounded bg-orange-500 text-white hover:bg-orange-600 transition-colors text-xs font-bold flex items-center gap-2"
                  >
                    <Github className="w-4 h-4" />
                    <span>View Repository</span>
                  </a>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </section>
  );
};
