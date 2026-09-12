import React from 'react';
import {
  MapPin,
  Calendar,
  Eye,
  Trash2,
  CheckCircle2,
  AlertCircle,
  HelpCircle,
  Image as ImageIcon,
} from 'lucide-react';
import Badge from '../common/Badge';

export default function ItemCard({
  item,
  onViewDetails,
  onToggleStatus,
  onConfirmDelete,
  isStatusUpdating,
}) {
  const isLost = item.type === 'LOST';

  // Format date nicely
  const formatDate = (dateString) => {
    if (!dateString) return 'Recently';
    try {
      const d = new Date(dateString);
      return d.toLocaleDateString(undefined, {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
      });
    } catch {
      return dateString;
    }
  };

  return (
    <div className="item-card">
      <div className="item-card-image-wrapper" onClick={() => onViewDetails(item)}>
        {item.imageUrl ? (
          <img
            src={item.imageUrl}
            alt={item.title}
            className="item-card-image"
            loading="lazy"
            onError={(e) => {
              e.currentTarget.style.display = 'none';
              e.currentTarget.parentElement.classList.add('has-error');
            }}
          />
        ) : (
          <div className="item-card-placeholder">
            <ImageIcon size={32} opacity={0.5} />
            <span>No Image Provided</span>
          </div>
        )}

        <div className="item-card-badges">
          <Badge type={item.type} />
          {item.category && <span className="category-chip">{item.category}</span>}
        </div>
      </div>

      <div className="item-card-body">
        <h3
          className="item-card-title"
          onClick={() => onViewDetails(item)}
          title={item.title}
        >
          {item.title}
        </h3>

        <p className="item-card-description">
          {item.description || 'No additional description provided.'}
        </p>

        <div className="item-card-meta">
          <div className="meta-row" title={item.location}>
            <MapPin size={14} />
            <span>{item.location || 'Location unspecified'}</span>
          </div>
          <div className="meta-row">
            <Calendar size={14} />
            <span>{formatDate(item.createdAt)}</span>
          </div>
        </div>

        <div className="item-card-actions">
          {/* Status Switcher Button */}
          <button
            className={`status-toggle-btn ${isLost ? 'mark-found' : 'mark-lost'}`}
            onClick={() => onToggleStatus(item.id, isLost ? 'FOUND' : 'LOST')}
            disabled={isStatusUpdating}
            title={isLost ? 'Mark this item as Found' : 'Mark back to Lost'}
          >
            {isLost ? (
              <>
                <CheckCircle2 size={14} />
                <span>Mark Found</span>
              </>
            ) : (
              <>
                <AlertCircle size={14} />
                <span>Mark Lost</span>
              </>
            )}
          </button>

          {/* View Details */}
          <button
            className="icon-btn"
            onClick={() => onViewDetails(item)}
            title="View details & contact info"
            aria-label="View details"
          >
            <Eye size={16} />
          </button>

          {/* Delete Record */}
          <button
            className="icon-btn danger"
            onClick={() => onConfirmDelete(item)}
            title="Delete record"
            aria-label="Delete item"
          >
            <Trash2 size={16} />
          </button>
        </div>
      </div>
    </div>
  );
}
