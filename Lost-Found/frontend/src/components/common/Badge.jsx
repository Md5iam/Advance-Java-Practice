import React from 'react';
import { AlertCircle, CheckCircle2 } from 'lucide-react';

export default function Badge({ type }) {
  const isLost = type === 'LOST';

  return (
    <span className={`status-badge ${isLost ? 'lost' : 'found'}`}>
      {isLost ? (
        <>
          <AlertCircle size={12} strokeWidth={2.5} />
          <span>Lost</span>
        </>
      ) : (
        <>
          <CheckCircle2 size={12} strokeWidth={2.5} />
          <span>Found</span>
        </>
      )}
    </span>
  );
}
