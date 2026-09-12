import React from 'react';
import { Compass, Plus, RefreshCw } from 'lucide-react';

export default function Navbar({ onOpenCreate, onRefresh, isRefreshing }) {
  return (
    <header className="navbar">
      <div className="navbar-container">
        <div className="brand">
          <div className="brand-icon-wrapper">
            <Compass size={22} color="#ffffff" strokeWidth={2.5} />
          </div>
          <span className="brand-title">
            Find<span>Nest</span>
          </span>
        </div>

        <div className="navbar-actions">
          <button
            className="btn btn-secondary btn-sm"
            onClick={onRefresh}
            disabled={isRefreshing}
            title="Refresh item list"
          >
            <RefreshCw
              size={15}
              style={{ animation: isRefreshing ? 'spin 0.8s linear infinite' : 'none' }}
            />
            <span className="hide-mobile">Refresh</span>
          </button>

          <button
            className="btn btn-primary btn-sm"
            onClick={onOpenCreate}
          >
            <Plus size={16} />
            <span>Report Lost Item</span>
          </button>
        </div>
      </div>
    </header>
  );
}
