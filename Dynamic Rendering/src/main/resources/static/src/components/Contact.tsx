import React, { useState } from 'react';
import { Mail, Send, Github, Linkedin, CheckCircle2, Copy, Check } from 'lucide-react';
import { SIAM_DATA } from '../data/siamData';

export const Contact: React.FC = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    subject: '',
    message: ''
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [copiedEmail, setCopiedEmail] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.name || !formData.email || !formData.message) return;

    setIsSubmitting(true);
    setTimeout(() => {
      setIsSubmitting(false);
      setIsSuccess(true);
      setFormData({ name: '', email: '', subject: '', message: '' });

      setTimeout(() => setIsSuccess(false), 5000);
    }, 1200);
  };

  const copyEmailToClipboard = () => {
    navigator.clipboard.writeText(SIAM_DATA.email);
    setCopiedEmail(true);
    setTimeout(() => setCopiedEmail(false), 3000);
  };

  return (
    <section id="contact" className="min-h-screen py-20 px-6 lg:px-12 relative">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="flex items-center gap-4 mb-12">
          <Mail className="w-6 h-6 text-orange-400" />
          <h2 className="font-mono text-2xl lg:text-3xl font-bold">
            <span className="text-slate-500">$</span> ./contact.exe
          </h2>
        </div>

        <div className="grid lg:grid-cols-2 gap-12 items-start">
          {/* Left Column: Terminal Contact Info */}
          <div className="space-y-6">
            <div className="bg-[#161922] rounded-xl border border-slate-800 font-mono text-xs sm:text-sm shadow-2xl overflow-hidden">
              <div className="bg-[#1f2330] px-4 py-2.5 flex items-center justify-between border-b border-slate-800">
                <div className="flex items-center gap-2">
                  <span className="w-2.5 h-2.5 rounded-full bg-green-400 animate-pulse" />
                  <span className="text-slate-300">contact_info.json</span>
                </div>
                <span className="text-[10px] text-slate-500">// READY</span>
              </div>

              <div className="p-6 text-slate-300 space-y-4">
                <div>
                  <span className="text-purple-400">&#123;</span>
                </div>

                <div className="pl-6 space-y-2">
                  <div>
                    <span className="text-cyan-400 font-bold">"operator"</span>: <span className="text-green-400">"{SIAM_DATA.name}"</span>,
                  </div>
                  <div>
                    <span className="text-cyan-400 font-bold">"email"</span>: <span className="text-amber-400">"{SIAM_DATA.email}"</span>,
                  </div>
                  <div>
                    <span className="text-cyan-400 font-bold">"socials"</span>: &#123;
                    <div className="pl-6 space-y-1">
                      <div><span className="text-orange-400">"github"</span>: <span className="text-slate-200">"@{SIAM_DATA.handle}"</span>,</div>
                      <div><span className="text-orange-400">"linkedin"</span>: <span className="text-slate-200">"in/{SIAM_DATA.handle}"</span></div>
                    </div>
                    &#125;,
                  </div>
                  <div>
                    <span className="text-cyan-400 font-bold">"location"</span>: <span className="text-green-400">"{SIAM_DATA.location}"</span>
                  </div>
                </div>

                <div>
                  <span className="text-purple-400">&#125;</span>
                </div>
              </div>
            </div>

            {/* Quick Actions Card */}
            <div className="bg-[#161922] p-6 rounded-xl border border-slate-800 space-y-4 font-mono text-xs">
              <div className="flex items-center justify-between">
                <span className="text-slate-400">DIRECT EMAIL:</span>
                <button
                  onClick={copyEmailToClipboard}
                  className="flex items-center gap-2 px-3 py-1.5 rounded bg-orange-500/10 text-orange-400 border border-orange-500/30 hover:bg-orange-500/20 transition-colors cursor-pointer"
                >
                  {copiedEmail ? <Check className="w-3.5 h-3.5" /> : <Copy className="w-3.5 h-3.5" />}
                  <span>{copiedEmail ? 'Copied!' : 'Copy Email'}</span>
                </button>
              </div>

              <div className="grid grid-cols-2 gap-3 pt-2">
                <a
                  href={SIAM_DATA.github}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="flex items-center justify-center gap-2 p-3 bg-slate-800/80 hover:bg-slate-700/80 rounded-lg text-slate-200 transition-colors"
                >
                  <Github className="w-4 h-4 text-orange-400" />
                  <span>GitHub</span>
                </a>
                <a
                  href={SIAM_DATA.linkedin}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="flex items-center justify-center gap-2 p-3 bg-slate-800/80 hover:bg-slate-700/80 rounded-lg text-slate-200 transition-colors"
                >
                  <Linkedin className="w-4 h-4 text-cyan-400" />
                  <span>LinkedIn</span>
                </a>
              </div>
            </div>
          </div>

          {/* Right Column: Mail Compose Terminal Form */}
          <div className="bg-[#161922] rounded-xl border border-slate-800 font-mono shadow-2xl overflow-hidden">
            <div className="bg-[#1f2330] px-4 py-3 border-b border-slate-800 flex items-center justify-between text-xs text-slate-300">
              <div className="flex items-center gap-2">
                <Send className="w-4 h-4 text-orange-400" />
                <span className="font-bold">mail.compose</span>
              </div>
              <span className="text-[10px] text-slate-500">SECURE CHANNEL</span>
            </div>

            <form onSubmit={handleSubmit} className="p-6 space-y-4 text-xs">
              {isSuccess && (
                <div className="p-4 bg-green-500/10 border border-green-500/30 rounded-lg text-green-400 flex items-center gap-3">
                  <CheckCircle2 className="w-5 h-5 shrink-0" />
                  <div>
                    <div className="font-bold">Message Transmitted!</div>
                    <div className="text-[11px] text-slate-300">Thank you for getting in touch. I will respond shortly.</div>
                  </div>
                </div>
              )}

              <div>
                <label className="block text-slate-400 mb-1.5">NAME *</label>
                <input
                  type="text"
                  required
                  value={formData.name}
                  onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                  placeholder="Your Name / Handle"
                  className="w-full bg-[#0d0e12] border border-slate-800 rounded-lg px-4 py-2.5 text-slate-200 focus:outline-none focus:border-orange-500/60 transition-colors"
                />
              </div>

              <div>
                <label className="block text-slate-400 mb-1.5">EMAIL *</label>
                <input
                  type="email"
                  required
                  value={formData.email}
                  onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                  placeholder="your.email@example.com"
                  className="w-full bg-[#0d0e12] border border-slate-800 rounded-lg px-4 py-2.5 text-slate-200 focus:outline-none focus:border-orange-500/60 transition-colors"
                />
              </div>

              <div>
                <label className="block text-slate-400 mb-1.5">SUBJECT</label>
                <input
                  type="text"
                  value={formData.subject}
                  onChange={(e) => setFormData({ ...formData, subject: e.target.value })}
                  placeholder="Project Inquiry / Opportunity"
                  className="w-full bg-[#0d0e12] border border-slate-800 rounded-lg px-4 py-2.5 text-slate-200 focus:outline-none focus:border-orange-500/60 transition-colors"
                />
              </div>

              <div>
                <label className="block text-slate-400 mb-1.5">MESSAGE *</label>
                <textarea
                  required
                  rows={4}
                  value={formData.message}
                  onChange={(e) => setFormData({ ...formData, message: e.target.value })}
                  placeholder="Type your message here..."
                  className="w-full bg-[#0d0e12] border border-slate-800 rounded-lg px-4 py-2.5 text-slate-200 focus:outline-none focus:border-orange-500/60 transition-colors resize-none"
                />
              </div>

              <button
                type="submit"
                disabled={isSubmitting}
                className="w-full flex items-center justify-center gap-2 py-3 bg-gradient-to-r from-orange-500 to-amber-500 text-white font-bold rounded-lg hover:from-orange-600 hover:to-amber-600 transition-all shadow-lg shadow-orange-500/20 disabled:opacity-50 cursor-pointer"
              >
                <Send className={`w-4 h-4 ${isSubmitting ? 'animate-bounce' : ''}`} />
                <span>{isSubmitting ? 'Transmitting Message...' : 'Send Message'}</span>
              </button>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
};
