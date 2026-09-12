import React from 'react';
import { Package, HelpCircle, CheckCircle, TrendingUp } from 'lucide-react';

export default function StatsOverview({ items = [] }) {
  const total = items.length;
  const lostCount = items.filter((item) => item.type === 'LOST').length;
  const foundCount = items.filter((item) => item.type === 'FOUND').length;
  const recoveryRate = total > 0 ? Math.round((foundCount / total) * 100) : 0;

  return (
    <section className="hero-section">

      <h1 className="hero-title">
        Lost Something? <br />
        <span className="hero-title-highlight">We Help Reunite Possessions</span>
      </h1>

      <p className="hero-subtitle">
        Search, report, and trace lost and found possessions with ease. Instant updates, direct contact,
        and secure Cloudinary photo verification.
      </p>

      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-icon-box total">
            <Package size={22} />
          </div>
          <div>
            <div className="stat-value">{total}</div>
            <div className="stat-label">Total Listings</div>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon-box lost">
            <HelpCircle size={22} />
          </div>
          <div>
            <div className="stat-value">{lostCount}</div>
            <div className="stat-label">Reported Lost</div>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon-box found">
            <CheckCircle size={22} />
          </div>
          <div>
            <div className="stat-value">{foundCount}</div>
            <div className="stat-label">Recovered / Found</div>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon-box rate">
            <TrendingUp size={22} />
          </div>
          <div>
            <div className="stat-value">{recoveryRate}%</div>
            <div className="stat-label">Resolution Rate</div>
          </div>
        </div>
      </div>
    </section>
  );
}
