// REST API service for interacting with the Spring Boot ItemController
const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1/items';

/**
 * Fetch items list, optionally filtered by type (LOST | FOUND)
 * @param {string} [type] - 'LOST', 'FOUND', or null/empty for all
 * @returns {Promise<Array>}
 */
export async function getItems(type = '') {
  const url = type && type !== 'ALL' ? `${API_URL}?type=${type}` : API_URL;
  const res = await fetch(url);
  if (!res.ok) {
    const errorText = await res.text().catch(() => '');
    throw new Error(`Failed to fetch items: ${res.status} ${errorText}`);
  }
  return res.json();
}

/**
 * Create a new lost or found item record with optional image upload
 * Uses multipart/form-data as expected by ItemController
 * @param {Object} itemData - { title, type, category, description, location, contactInfo }
 * @param {File|null} imageFile - optional image file to upload to Cloudinary
 * @returns {Promise<Object>}
 */
export async function createItem(itemData, imageFile = null) {
  const formData = new FormData();
  formData.append('title', itemData.title || '');
  formData.append('type', itemData.type || 'LOST');
  formData.append('category', itemData.category || 'Other');
  formData.append('description', itemData.description || '');
  formData.append('location', itemData.location || '');
  formData.append('contactInfo', itemData.contactInfo || '');

  if (imageFile) {
    formData.append('image', imageFile);
  }

  const res = await fetch(API_URL, {
    method: 'POST',
    body: formData,
  });

  if (!res.ok) {
    const errorText = await res.text().catch(() => '');
    throw new Error(`Failed to create item: ${res.status} ${errorText}`);
  }

  return res.json();
}

/**
 * Update item type between LOST and FOUND
 * Calls PATCH /api/v1/items/{id}/type?type={newType}
 * @param {string} id - Mongo item ID
 * @param {'LOST'|'FOUND'} newType
 * @returns {Promise<Object>}
 */
export async function updateItemType(id, newType) {
  const url = `${API_URL}/${id}/type?type=${encodeURIComponent(newType)}`;
  const res = await fetch(url, {
    method: 'PATCH',
  });

  if (!res.ok) {
    const errorText = await res.text().catch(() => '');
    throw new Error(`Failed to update item status: ${res.status} ${errorText}`);
  }

  return res.json();
}

/**
 * Delete item record and its Cloudinary asset
 * Calls DELETE /api/v1/items/{id}
 * @param {string} id - Mongo item ID
 * @returns {Promise<void>}
 */
export async function deleteItem(id) {
  const res = await fetch(`${API_URL}/${id}`, {
    method: 'DELETE',
  });

  if (!res.ok && res.status !== 204) {
    const errorText = await res.text().catch(() => '');
    throw new Error(`Failed to delete item: ${res.status} ${errorText}`);
  }
}
