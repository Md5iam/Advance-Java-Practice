import React, { useState } from 'react';
import Modal from '../common/Modal';
import Badge from '../common/Badge';
import {
  MapPin,
  Calendar,
  Phone,
  Mail,
  Copy,
  Check,
  Trash2,
  CheckCircle2,
  AlertCircle,
  Tag,
  Image as ImageIcon,
} from 'lucide-react';

export default function ItemDetailsModal({
  item,
  isOpen,
  onClose,
  onToggleStatus,
  onConfirmDelete,
}) {
  const [copied, setCopied] = useState(false);

  if (!item) return null;

  const isLost = item.type === 'LOST';

  const handleCopyContact = () => {
    if (item.contactInfo) {
      navigator.clipboard.writeText(item.contactInfo);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  };

  const isEmail = item.contactInfo && item.contactInfo.includes('@');
  const isPhone = item.contactInfo && /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/.test(item.contactInfo.trim());

  return (
    <Modal isOpen={isOpen} onClose={onClose} title={item.title} maxWidth="640px">
      <div className="modal-body">
        {/* Full Image */}
        {item.imageUrl ? (
          <div className="details-image-container">
            <img src={item.imageUrl} alt={item.title} className="details-image" />
          </div>
        ) : (
          <div
            className="details-image-container"
            style={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              justifyContent: 'center',
              color: 'var(--text-muted)',
              gap: '0.5rem',
            }}
          >
            <ImageIcon size={44} opacity={0.4} />
            <span>No photo uploaded for this item</span>
          </div>
        )}

        {/* Tags row */}
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.6rem', marginBottom: '1.25rem' }}>
          <Badge type={item.type} />
          {item.category && (
            <span className="category-chip" style={{ display: 'inline-flex', alignItems: 'center', gap: '0.35rem' }}>
              <Tag size={12} />
              {item.category}
            </span>
          )}
        </div>

        {/* Description */}
        <div style={{ marginBottom: '1.25rem' }}>
          <h4 style={{ fontSize: '0.95rem', color: 'var(--text-secondary)', marginBottom: '0.4rem' }}>
            Description
          </h4>
          <p style={{ color: 'var(--text-primary)', whiteSpace: 'pre-wrap', lineHeight: '1.6' }}>
            {item.description || 'No detailed description available.'}
          </p>
        </div>

        {/* Meta Info Grid */}
        <div className="details-meta-grid">
          <div className="details-meta-item">
            <span className="details-meta-label">Location Found / Lost</span>
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
              <MapPin size={16} color="#818cf8" />
              <span className="details-meta-value">{item.location || 'Not specified'}</span>
            </div>
          </div>

          <div className="details-meta-item">
            <span className="details-meta-label">Date Reported</span>
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
              <Calendar size={16} color="#818cf8" />
              <span className="details-meta-value">
                {item.createdAt ? new Date(item.createdAt).toLocaleString() : 'N/A'}
              </span>
            </div>
          </div>
        </div>

        {/* Contact Info Box */}
        {item.contactInfo && (
          <div className="details-contact-box">
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
              <div
                style={{
                  width: 36,
                  height: 36,
                  borderRadius: '50%',
                  background: 'rgba(99, 102, 241, 0.2)',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  color: '#818cf8',
                }}
              >
                {isEmail ? <Mail size={18} /> : <Phone size={18} />}
              </div>
              <div>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase' }}>
                  Contact Information
                </div>
                <div style={{ fontWeight: 600, color: 'var(--text-primary)', fontSize: '0.95rem' }}>
                  {item.contactInfo}
                </div>
              </div>
            </div>

            <div style={{ display: 'flex', gap: '0.5rem' }}>
              {isEmail && (
                <a
                  href={`mailto:${item.contactInfo}`}
                  className="btn btn-secondary btn-sm"
                  title="Send Email"
                >
                  <Mail size={14} />
                  <span>Email</span>
                </a>
              )}
              {isPhone && (
                <a
                  href={`tel:${item.contactInfo}`}
                  className="btn btn-secondary btn-sm"
                  title="Call Contact"
                >
                  <Phone size={14} />
                  <span>Call</span>
                </a>
              )}
              <button
                className="btn btn-secondary btn-sm"
                onClick={handleCopyContact}
                title="Copy Contact Info"
              >
                {copied ? <Check size={14} color="#10b981" /> : <Copy size={14} />}
                <span>{copied ? 'Copied' : 'Copy'}</span>
              </button>
            </div>
          </div>
        )}
      </div>

      {/* Footer Actions */}
      <div className="modal-footer" style={{ justifyContent: 'space-between' }}>
        <button
          className="btn btn-outline-danger btn-sm"
          onClick={() => {
            onClose();
            onConfirmDelete(item);
          }}
        >
          <Trash2 size={15} />
          <span>Delete Listing</span>
        </button>

        <div style={{ display: 'flex', gap: '0.75rem' }}>
          <button
            className={`btn ${isLost ? 'btn-success' : 'btn-danger'} btn-sm`}
            onClick={() => {
              onToggleStatus(item.id, isLost ? 'FOUND' : 'LOST');
              onClose();
            }}
          >
            {isLost ? (
              <>
                <CheckCircle2 size={15} />
                <span>Mark as Found</span>
              </>
            ) : (
              <>
                <AlertCircle size={15} />
                <span>Mark as Lost</span>
              </>
            )}
          </button>
          <button className="btn btn-secondary btn-sm" onClick={onClose}>
            Close
          </button>
        </div>
      </div>
    </Modal>
  );
}
