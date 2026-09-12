import React, { useState, useRef } from 'react';
import Modal from '../common/Modal';
import { CATEGORIES, ITEM_TYPES } from '../../constants/categories';
import {
  UploadCloud,
  X,
  AlertCircle,
  Plus,
} from 'lucide-react';

export default function CreateItemModal({
  isOpen,
  onClose,
  onSubmit,
  isSubmitting,
}) {
  const [formData, setFormData] = useState({
    title: '',
    type: ITEM_TYPES.LOST,
    category: 'Electronics',
    description: '',
    location: '',
    contactInfo: '',
  });

  const [imageFile, setImageFile] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);
  const [formError, setFormError] = useState('');
  const fileInputRef = useRef(null);

  // Clear errors when opened
  React.useEffect(() => {
    if (isOpen) {
      setFormError('');
    }
  }, [isOpen]);

  const handleChange = (field, value) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
    if (formError) setFormError('');
  };

  const handleFileSelect = (e) => {
    const file = e.target.files?.[0];
    if (!file) return;

    if (!file.type.startsWith('image/')) {
      setFormError('Please select a valid image file (JPG, PNG, WebP, etc.).');
      return;
    }

    if (file.size > 10 * 1024 * 1024) {
      setFormError('Image size exceeds maximum limit of 10MB.');
      return;
    }

    setImageFile(file);
    setImagePreview(URL.createObjectURL(file));
    setFormError('');
  };

  const handleRemoveImage = (e) => {
    e.stopPropagation();
    setImageFile(null);
    if (imagePreview) {
      URL.revokeObjectURL(imagePreview);
      setImagePreview(null);
    }
    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.title.trim()) {
      setFormError('Item title is required.');
      return;
    }
    if (!formData.location.trim()) {
      setFormError('Location where item was lost is required.');
      return;
    }
    if (!formData.contactInfo.trim()) {
      setFormError('Contact information is required so finders can reach you.');
      return;
    }

    try {
      await onSubmit({ ...formData, type: ITEM_TYPES.LOST }, imageFile);
      // Reset form on success
      setFormData({
        title: '',
        type: ITEM_TYPES.LOST,
        category: 'Electronics',
        description: '',
        location: '',
        contactInfo: '',
      });
      setImageFile(null);
      setImagePreview(null);
      onClose();
    } catch (err) {
      setFormError(err.message || 'Failed to submit lost item report.');
    }
  };

  return (
    <Modal
      isOpen={isOpen}
      onClose={onClose}
      title="Report Lost Item"
      maxWidth="580px"
    >
      <form onSubmit={handleSubmit}>
        <div className="modal-body">
          {formError && (
            <div
              style={{
                background: 'rgba(244, 63, 94, 0.15)',
                border: '1px solid rgba(244, 63, 94, 0.3)',
                color: '#fb7185',
                padding: '0.75rem 1rem',
                borderRadius: 'var(--radius-md)',
                marginBottom: '1.25rem',
                fontSize: '0.88rem',
                display: 'flex',
                alignItems: 'center',
                gap: '0.5rem',
              }}
            >
              <AlertCircle size={16} />
              <span>{formError}</span>
            </div>
          )}

          {/* Item Title */}
          <div className="form-group">
            <label className="form-label">
              Item Title <span className="req">*</span>
            </label>
            <input
              type="text"
              className="form-input"
              placeholder="e.g., Midnight Blue iPhone 14 Pro"
              value={formData.title}
              onChange={(e) => handleChange('title', e.target.value)}
              required
            />
          </div>

          {/* Category */}
          <div className="form-group">
            <label className="form-label">Category</label>
            <select
              className="form-select"
              value={formData.category}
              onChange={(e) => handleChange('category', e.target.value)}
            >
              {CATEGORIES.filter((c) => c.id !== 'all').map((cat) => (
                <option key={cat.id} value={cat.id}>
                  {cat.label}
                </option>
              ))}
            </select>
          </div>

          {/* Location & Contact Info Row */}
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">
                Lost Location <span className="req">*</span>
              </label>
              <input
                type="text"
                className="form-input"
                placeholder="e.g., Central Library, 2nd Floor"
                value={formData.location}
                onChange={(e) => handleChange('location', e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">
                Your Contact Info <span className="req">*</span>
              </label>
              <input
                type="text"
                className="form-input"
                placeholder="Phone number, email or handle"
                value={formData.contactInfo}
                onChange={(e) => handleChange('contactInfo', e.target.value)}
                required
              />
            </div>
          </div>

          {/* Description */}
          <div className="form-group">
            <label className="form-label">Detailed Description</label>
            <textarea
              className="form-textarea"
              placeholder="Include unique distinguishing features, serial numbers, colors, stickers, etc..."
              value={formData.description}
              onChange={(e) => handleChange('description', e.target.value)}
            />
          </div>

          {/* Image Upload Dropzone */}
          <div className="form-group">
            <label className="form-label">Upload Photo (Cloudinary)</label>
            <input
              type="file"
              ref={fileInputRef}
              accept="image/*"
              style={{ display: 'none' }}
              onChange={handleFileSelect}
            />

            {imagePreview ? (
              <div className="dropzone-preview-box">
                <img src={imagePreview} alt="Preview" className="dropzone-preview-img" />
                <button
                  type="button"
                  className="remove-img-btn"
                  onClick={handleRemoveImage}
                  title="Remove image"
                >
                  <X size={16} />
                </button>
              </div>
            ) : (
              <div className="dropzone" onClick={() => fileInputRef.current?.click()}>
                <UploadCloud
                  size={32}
                  color="#818cf8"
                  style={{ margin: '0 auto 0.5rem', display: 'block' }}
                />
                <p style={{ fontWeight: 600, fontSize: '0.9rem', marginBottom: '0.2rem' }}>
                  Click to choose a photo
                </p>
                <p style={{ fontSize: '0.78rem', color: 'var(--text-muted)' }}>
                  PNG, JPG, WebP up to 10MB
                </p>
              </div>
            )}
          </div>
        </div>

        {/* Footer */}
        <div className="modal-footer">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={onClose}
            disabled={isSubmitting}
          >
            Cancel
          </button>
          <button
            type="submit"
            className="btn btn-primary"
            disabled={isSubmitting}
          >
            {isSubmitting ? (
              <>
                <span className="spinner" />
                <span>Uploading...</span>
              </>
            ) : (
              <>
                <Plus size={16} />
                <span>Report Lost Item</span>
              </>
            )}
          </button>
        </div>
      </form>
    </Modal>
  );
}
