import React from 'react';
import { Search, X, Layers, AlertCircle, CheckCircle2 } from 'lucide-react';
import { CATEGORIES } from '../../constants/categories';

export default function FilterBar({
  activeType,
  onSelectType,
  selectedCategory,
  onSelectCategory,
  searchQuery,
  onSearchChange,
  counts,
}) {
  return (
    <div className="filter-bar-container">
      {/* Type Toggle Tabs */}
      <div className="type-tabs" role="tablist">
        <button
          className={`tab-btn ${activeType === 'ALL' ? 'active' : ''}`}
          onClick={() => onSelectType('ALL')}
          role="tab"
          aria-selected={activeType === 'ALL'}
        >
          <Layers size={15} />
          <span>All Items ({counts.all})</span>
        </button>

        <button
          className={`tab-btn ${activeType === 'LOST' ? 'active lost' : ''}`}
          onClick={() => onSelectType('LOST')}
          role="tab"
          aria-selected={activeType === 'LOST'}
        >
          <AlertCircle size={15} />
          <span>Lost ({counts.lost})</span>
        </button>

        <button
          className={`tab-btn ${activeType === 'FOUND' ? 'active found' : ''}`}
          onClick={() => onSelectType('FOUND')}
          role="tab"
          aria-selected={activeType === 'FOUND'}
        >
          <CheckCircle2 size={15} />
          <span>Found ({counts.found})</span>
        </button>
      </div>

      {/* Filter Controls (Search & Category) */}
      <div className="filter-controls">
        <div className="search-input-wrapper">
          <Search size={16} className="search-icon" />
          <input
            type="text"
            className="search-input"
            placeholder="Search title, location, description..."
            value={searchQuery}
            onChange={(e) => onSearchChange(e.target.value)}
          />
          {searchQuery && (
            <button
              className="search-clear-btn"
              onClick={() => onSearchChange('')}
              title="Clear search"
            >
              <X size={14} />
            </button>
          )}
        </div>

        <select
          className="category-select"
          value={selectedCategory}
          onChange={(e) => onSelectCategory(e.target.value)}
        >
          {CATEGORIES.map((cat) => (
            <option key={cat.id} value={cat.id}>
              {cat.label}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
}
