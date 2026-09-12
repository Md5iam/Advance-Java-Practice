import React from 'react';
import Modal from './Modal';
import { AlertTriangle } from 'lucide-react';

export default function ConfirmModal({ isOpen, onClose, onConfirm, title, message, isDeleting }) {
  return (
    <Modal isOpen={isOpen} onClose={onClose} title={title || 'Confirm Deletion'} maxWidth="450px">
      <div className="modal-body" style={{ display: 'flex', gap: '1rem', alignItems: 'flex-start' }}>
        <div
          style={{
            width: 44,
            height: 44,
            borderRadius: '50%',
            background: 'rgba(244, 63, 94, 0.15)',
            color: '#f43f5e',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            flexShrink: 0,
          }}
        >
          <AlertTriangle size={22} />
        </div>
        <div>
          <p style={{ color: 'var(--text-primary)', fontWeight: 600, marginBottom: '0.35rem' }}>
            Are you sure you want to proceed?
          </p>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.88rem' }}>
            {message || 'This action cannot be undone. This item and its uploaded image will be permanently removed.'}
          </p>
        </div>
      </div>
      <div className="modal-footer">
        <button className="btn btn-secondary" onClick={onClose} disabled={isDeleting}>
          Cancel
        </button>
        <button className="btn btn-danger" onClick={onConfirm} disabled={isDeleting}>
          {isDeleting ? (
            <>
              <span className="spinner" />
              <span>Deleting...</span>
            </>
          ) : (
            'Delete Forever'
          )}
        </button>
      </div>
    </Modal>
  );
}
