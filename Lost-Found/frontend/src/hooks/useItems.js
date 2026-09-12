import { useState, useEffect, useMemo, useCallback } from 'react';
import * as itemApi from '../api/itemApi';

export function useItems() {
  const [allItems, setAllItems] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isRefreshing, setIsRefreshing] = useState(false);
  const [error, setError] = useState(null);

  // Filter States
  const [activeType, setActiveType] = useState('ALL'); // 'ALL' | 'LOST' | 'FOUND'
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [searchQuery, setSearchQuery] = useState('');

  // Mutation states
  const [updatingItemId, setUpdatingItemId] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Toast Notifications
  const [toasts, setToasts] = useState([]);

  const addToast = useCallback((message, type = 'info', duration = 4000) => {
    const id = Date.now() + Math.random().toString(36).substring(2, 6);
    setToasts((prev) => [...prev, { id, message, type }]);

    if (duration > 0) {
      setTimeout(() => {
        setToasts((prev) => prev.filter((t) => t.id !== id));
      }, duration);
    }
  }, []);

  const removeToast = useCallback((id) => {
    setToasts((prev) => prev.filter((t) => t.id !== id));
  }, []);

  // Fetch Items from Backend
  const loadItems = useCallback(async (isManualRefresh = false) => {
    if (isManualRefresh) {
      setIsRefreshing(true);
    } else {
      setIsLoading(true);
    }
    setError(null);

    try {
      // Fetch all items from Spring Boot backend (we can filter client-side for smooth UX)
      const data = await itemApi.getItems();
      setAllItems(Array.isArray(data) ? data : []);
      if (isManualRefresh) {
        addToast('Items updated successfully', 'success', 2500);
      }
    } catch (err) {
      const msg = err.message || 'Could not connect to backend server';
      setError(msg);
      addToast(msg, 'error', 5000);
    } finally {
      setIsLoading(false);
      setIsRefreshing(false);
    }
  }, [addToast]);

  // Initial Load on Mount
  useEffect(() => {
    loadItems(false);
  }, [loadItems]);

  // Create new Item
  const createItem = async (formData, imageFile) => {
    setIsSubmitting(true);
    try {
      const savedItem = await itemApi.createItem(formData, imageFile);
      setAllItems((prev) => [savedItem, ...prev]);
      addToast(
        `Successfully reported ${savedItem.type.toLowerCase()} item: "${savedItem.title}"`,
        'success'
      );
      return savedItem;
    } catch (err) {
      addToast(err.message || 'Failed to submit report', 'error');
      throw err;
    } finally {
      setIsSubmitting(false);
    }
  };

  // Toggle Item Status (LOST <-> FOUND)
  const toggleItemStatus = async (id, newType) => {
    setUpdatingItemId(id);
    try {
      const updatedItem = await itemApi.updateItemType(id, newType);
      setAllItems((prev) =>
        prev.map((item) => (item.id === id ? updatedItem : item))
      );
      addToast(
        `Item status marked as ${newType.toLowerCase()}`,
        'success'
      );
    } catch (err) {
      addToast(err.message || 'Failed to update item status', 'error');
    } finally {
      setUpdatingItemId(null);
    }
  };

  // Delete Item
  const deleteItem = async (id) => {
    try {
      await itemApi.deleteItem(id);
      setAllItems((prev) => prev.filter((item) => item.id !== id));
      addToast('Item and attached image removed', 'info');
    } catch (err) {
      addToast(err.message || 'Failed to delete item', 'error');
      throw err;
    }
  };

  // Counts
  const counts = useMemo(() => {
    const all = allItems.length;
    const lost = allItems.filter((i) => i.type === 'LOST').length;
    const found = allItems.filter((i) => i.type === 'FOUND').length;
    return { all, lost, found };
  }, [allItems]);

  // Filtered and Searched Items
  const filteredItems = useMemo(() => {
    return allItems.filter((item) => {
      // Type filter
      if (activeType !== 'ALL' && item.type !== activeType) {
        return false;
      }

      // Category filter
      if (selectedCategory !== 'all' && item.category !== selectedCategory) {
        return false;
      }

      // Search query filter
      if (searchQuery.trim()) {
        const query = searchQuery.toLowerCase();
        const titleMatch = item.title?.toLowerCase().includes(query);
        const descMatch = item.description?.toLowerCase().includes(query);
        const locMatch = item.location?.toLowerCase().includes(query);
        const catMatch = item.category?.toLowerCase().includes(query);
        if (!titleMatch && !descMatch && !locMatch && !catMatch) {
          return false;
        }
      }

      return true;
    });
  }, [allItems, activeType, selectedCategory, searchQuery]);

  const isFiltered =
    activeType !== 'ALL' || selectedCategory !== 'all' || searchQuery.trim() !== '';

  const resetFilters = () => {
    setActiveType('ALL');
    setSelectedCategory('all');
    setSearchQuery('');
  };

  return {
    items: filteredItems,
    allItems,
    isLoading,
    isRefreshing,
    error,
    activeType,
    setActiveType,
    selectedCategory,
    setSelectedCategory,
    searchQuery,
    setSearchQuery,
    isFiltered,
    resetFilters,
    counts,
    updatingItemId,
    isSubmitting,
    toasts,
    addToast,
    removeToast,
    loadItems,
    createItem,
    toggleItemStatus,
    deleteItem,
  };
}
