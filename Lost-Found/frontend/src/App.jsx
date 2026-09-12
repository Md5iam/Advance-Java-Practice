import React, { useState } from 'react';
import Navbar from './components/common/Navbar';
import StatsOverview from './components/stats/StatsOverview';
import FilterBar from './components/items/FilterBar';
import ItemGrid from './components/items/ItemGrid';
import CreateItemModal from './components/items/CreateItemModal';
import ItemDetailsModal from './components/items/ItemDetailsModal';
import ConfirmModal from './components/common/ConfirmModal';
import ToastContainer from './components/common/Toast';
import { useItems } from './hooks/useItems';
import { ITEM_TYPES } from './constants/categories';

export default function App() {
  const {
    items,
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
    removeToast,
    loadItems,
    createItem,
    toggleItemStatus,
    deleteItem,
  } = useItems();

  // Modals state
  const [createModalOpen, setCreateModalOpen] = useState(false);
  const [selectedItemForDetails, setSelectedItemForDetails] = useState(null);
  const [itemToDelete, setItemToDelete] = useState(null);
  const [isDeleting, setIsDeleting] = useState(false);

  // Handlers
  const handleOpenCreate = () => {
    setCreateModalOpen(true);
  };

  const handleConfirmDelete = async () => {
    if (!itemToDelete) return;
    setIsDeleting(true);
    try {
      await deleteItem(itemToDelete.id);
      setItemToDelete(null);
    } catch {
      // Toast already shown in hook
    } finally {
      setIsDeleting(false);
    }
  };

  return (
    <>
      <Navbar
        onOpenCreate={handleOpenCreate}
        onRefresh={() => loadItems(true)}
        isRefreshing={isRefreshing}
      />

      <main className="main-content">
        {/* Hero & Quick Statistics */}
        <StatsOverview items={allItems} />

        {/* Filter Bar with Search, Type Tabs & Categories */}
        <FilterBar
          activeType={activeType}
          onSelectType={setActiveType}
          selectedCategory={selectedCategory}
          onSelectCategory={setSelectedCategory}
          searchQuery={searchQuery}
          onSearchChange={setSearchQuery}
          counts={counts}
        />

        {/* Item Cards Grid */}
        <ItemGrid
          items={items}
          isLoading={isLoading}
          isFiltered={isFiltered}
          onResetFilter={resetFilters}
          onOpenCreate={handleOpenCreate}
          onViewDetails={(item) => setSelectedItemForDetails(item)}
          onToggleStatus={toggleItemStatus}
          onConfirmDelete={(item) => setItemToDelete(item)}
          updatingItemId={updatingItemId}
        />
      </main>

      {/* Footer */}
      <footer
        style={{
          borderTop: '1px solid var(--border-subtle)',
          padding: '2rem 1.5rem',
          textAlign: 'center',
          color: 'var(--text-muted)',
          fontSize: '0.85rem',
          marginTop: 'auto',
        }}
      >
        <div style={{ maxWidth: 1280, margin: '0 auto' }}>
          <p style={{ marginBottom: '0.35rem', color: 'var(--text-secondary)' }}>
            FindNest Community Lost &amp; Found Portal
          </p>
          <p>
            Connected to Spring Boot REST Backend &bull; MongoDB Atlas &bull; Cloudinary CDN
          </p>
        </div>
      </footer>

      {/* Create Modal */}
      <CreateItemModal
        isOpen={createModalOpen}
        onClose={() => setCreateModalOpen(false)}
        onSubmit={createItem}
        isSubmitting={isSubmitting}
      />

      {/* Details Modal */}
      <ItemDetailsModal
        isOpen={Boolean(selectedItemForDetails)}
        onClose={() => setSelectedItemForDetails(null)}
        item={selectedItemForDetails}
        onToggleStatus={toggleItemStatus}
        onConfirmDelete={(item) => setItemToDelete(item)}
      />

      {/* Delete Confirmation Modal */}
      <ConfirmModal
        isOpen={Boolean(itemToDelete)}
        onClose={() => setItemToDelete(null)}
        onConfirm={handleConfirmDelete}
        title="Delete Item Report"
        message={
          itemToDelete
            ? `Are you sure you want to permanently delete "${itemToDelete.title}"? This cannot be undone.`
            : ''
        }
        isDeleting={isDeleting}
      />

      {/* Toast Notifications */}
      <ToastContainer toasts={toasts} onDismiss={removeToast} />
    </>
  );
}
