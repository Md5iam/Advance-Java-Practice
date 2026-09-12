import React from 'react';
import { CheckCircle, AlertCircle, Info, X } from 'lucide-react';

export default function ToastContainer({ toasts, onDismiss }) {
  if (!toasts || toasts.length === 0) return null;

  return (
    <div className="toast-container" aria-live="polite">
      {toasts.map((toast) => {
        const Icon =
          toast.type === 'success'
            ? CheckCircle
            : toast.type === 'error'
            ? AlertCircle
            : Info;

        return (
          <div key={toast.id} className={`toast ${toast.type || 'info'}`}>
            <Icon
              size={18}
              color={
                toast.type === 'success'
                  ? '#10b981'
                  : toast.type === 'error'
                  ? '#f43f5e'
                  : '#818cf8'
              }
            />
            <div style={{ flex: 1 }}>{toast.message}</div>
            <button
              onClick={() => onDismiss(toast.id)}
              style={{ background: 'none', color: 'var(--text-muted)', display: 'flex' }}
              aria-label="Dismiss notification"
            >
              <X size={14} />
            </button>
          </div>
        );
      })}
    </div>
  );
}
