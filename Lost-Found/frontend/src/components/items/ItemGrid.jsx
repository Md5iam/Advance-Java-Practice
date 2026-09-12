import React from 'react';
import ItemCard from './ItemCard';
import EmptyState from '../common/EmptyState';

export default function ItemGrid({
  items,
  isLoading,
  isFiltered,
  onResetFilter,
  onOpenCreate,
  onViewDetails,
  onToggleStatus,
  onConfirmDelete,
  updatingItemId,
}) {
  if (isLoading) {
    return (
      <div className="items-grid">
        {[1, 2, 3, 4, 5, 6].map((idx) => (
          <div key={idx} className="skeleton-card" />
        ))}
      </div>
    );
  }

  if (!items || items.length === 0) {
    return (
      <EmptyState
        isFiltered={isFiltered}
        onResetFilter={onResetFilter}
        onOpenCreate={onOpenCreate}
      />
    );
  }

  return (
    <div className="items-grid">
      {items.map((item) => (
        <ItemCard
          key={item.id}
          item={item}
          onViewDetails={onViewDetails}
          onToggleStatus={onToggleStatus}
          onConfirmDelete={onConfirmDelete}
          isStatusUpdating={updatingItemId === item.id}
        />
      ))}
    </div>
  );
}
