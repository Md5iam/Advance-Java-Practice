import React from 'react';
import { SearchX, PlusCircle } from 'lucide-react';

export default function EmptyState({ isFiltered, onResetFilter, onOpenCreate }) {
  return (
    <div className="empty-state">
      <div className="empty-state-icon">
        <SearchX size={34} />
      </div>
      <h3>{isFiltered ? 'No matching items found' : 'No items reported yet'}</h3>
      <p>
        {isFiltered
          ? 'Try adjusting your search terms or filters to find what you are looking for.'
          : 'Be the first to report a lost item to help reunite possessions!'}
      </p>
      <div style={{ display: 'flex', gap: '0.75rem', justifyContent: 'center' }}>
        {isFiltered ? (
          <button className="btn btn-secondary" onClick={onResetFilter}>
            Clear Filters
          </button>
        ) : (
          <button className="btn btn-primary" onClick={onOpenCreate}>
            <PlusCircle size={16} />
            <span>Report Lost Item</span>
          </button>
        )}
      </div>
    </div>
  );
}
