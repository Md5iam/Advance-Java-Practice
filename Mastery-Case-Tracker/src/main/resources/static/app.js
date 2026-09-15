const API_BASE = '/api/cases';

const form = document.getElementById('crud-form');
const editIdInput = document.getElementById('edit-id');
const cancelBtn = document.getElementById('cancel-btn');
const formTitle = document.getElementById('form-title');
const saveBtn = document.getElementById('save-btn');
const searchInput = document.getElementById('search-input');
const searchBtn = document.getElementById('search-btn');
const tableBody = document.getElementById('table-body');
const feedbackBox = document.getElementById('form-feedback');

const statTotal = document.getElementById('stat-total');
const statOpen = document.getElementById('stat-open');
const statHigh = document.getElementById('stat-high');

let casesCache = [];

async function fetchStats() {
    try {
        const response = await fetch(`${API_BASE}/stats`);
        if (!response.ok) throw new Error('Failed to load stats');
        const stats = await response.json();
        statTotal.textContent = stats.totalCrimeFiles;
        statOpen.textContent = stats.openActiveCases;
        statHigh.textContent = stats.highPriorityCases;
    } catch (err) {
        statTotal.textContent = casesCache.length;
        statOpen.textContent = casesCache.filter(c => c.status === 'OPEN' || c.status === 'IN_PROGRESS').length;
        statHigh.textContent = casesCache.filter(c => c.priority === 'HIGH').length;
    }
}

async function loadCases(searchQuery = '') {
    try {
        const url = searchQuery
            ? `${API_BASE}/search?query=${encodeURIComponent(searchQuery)}`
            : API_BASE;

        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to fetch cases');
        casesCache = await response.json();

        if (casesCache.length === 0 && !searchQuery) {
            await seedInitialData();
            return;
        }

        renderTable(casesCache);
        await fetchStats();
    } catch (err) {
        showFeedback('Could not connect to backend service. Please check server status.', true);
    }
}

async function seedInitialData() {
    const seedCases = [
        { caseId: "CASE-101", title: "Shadow In Fog", leadDetective: "Sherlock H.", priority: "HIGH", status: "IN_PROGRESS" },
        { caseId: "CASE-104", title: "Missing Sapphire", leadDetective: "Hercule P.", priority: "MEDIUM", status: "CLOSED" },
        { caseId: "CASE-108", title: "Midnight Caller", leadDetective: "Nancy D.", priority: "HIGH", status: "OPEN" }
    ];

    try {
        for (const item of seedCases) {
            await fetch(API_BASE, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(item)
            });
        }
        const res = await fetch(API_BASE);
        casesCache = await res.json();
        renderTable(casesCache);
        await fetchStats();
    } catch (e) {
        renderTable([]);
    }
}

function formatPriorityText(priority) {
    if (!priority) return '';
    return priority.charAt(0).toUpperCase() + priority.slice(1).toLowerCase();
}

function formatStatusText(status) {
    if (!status) return '';
    if (status === 'IN_PROGRESS') return 'In Progress';
    return status.charAt(0).toUpperCase() + status.slice(1).toLowerCase();
}

function getPriorityClass(priority) {
    const p = (priority || '').toLowerCase();
    return `priority-${p}`;
}

function getStatusClass(status) {
    const s = (status || '').toLowerCase().replace('_', '-');
    return `status-${s}`;
}

function renderTable(cases) {
    tableBody.innerHTML = '';

    if (cases.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="6" class="empty-state">No investigation files found.</td></tr>`;
        return;
    }

    cases.forEach(item => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><code>${item.caseId}</code></td>
            <td><strong>${item.title}</strong></td>
            <td>${item.leadDetective}</td>
            <td><span class="badge ${getPriorityClass(item.priority)}">${formatPriorityText(item.priority)}</span></td>
            <td><span class="badge ${getStatusClass(item.status)}">${formatStatusText(item.status)}</span></td>
            <td>
                <div class="action-btns">
                    <button class="btn-action btn-edit" onclick="editCase('${item.id || item.caseId}')">
                        <i class="fa-solid fa-pen"></i> Edit
                    </button>
                    <button class="btn-action btn-delete" onclick="deleteCase('${item.id || item.caseId}')">
                        <i class="fa-solid fa-trash"></i> Delete
                    </button>
                </div>
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

function showFeedback(message, isError = false) {
    feedbackBox.textContent = message;
    feedbackBox.className = `feedback-msg ${isError ? 'feedback-error' : 'feedback-success'}`;
    feedbackBox.classList.remove('hidden');
    setTimeout(() => {
        feedbackBox.classList.add('hidden');
    }, 4500);
}

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const editId = editIdInput.value.trim();
    const payload = {
        caseId: document.getElementById('input-caseId').value.trim(),
        title: document.getElementById('input-title').value.trim(),
        leadDetective: document.getElementById('input-detective').value.trim(),
        priority: document.getElementById('input-priority').value,
        status: document.getElementById('input-status').value
    };

    try {
        const isEdit = editId !== '';
        const url = isEdit ? `${API_BASE}/${editId}` : API_BASE;
        const method = isEdit ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const errData = await response.json().catch(() => ({}));
            let msg = errData.message || 'Operation failed.';
            if (errData.validationErrors) {
                msg = Object.values(errData.validationErrors).join('; ');
            }
            showFeedback(msg, true);
            return;
        }

        showFeedback(isEdit ? 'Case record updated successfully.' : 'New case record filed successfully.', false);
        resetForm();
        await loadCases();
    } catch (err) {
        showFeedback('Network error while saving case record.', true);
    }
});

window.editCase = function(id) {
    const item = casesCache.find(c => c.id === id || c.caseId === id);
    if (!item) return;

    document.getElementById('input-caseId').value = item.caseId;
    document.getElementById('input-title').value = item.title;
    document.getElementById('input-detective').value = item.leadDetective;
    document.getElementById('input-priority').value = item.priority;
    document.getElementById('input-status').value = item.status;

    editIdInput.value = item.id || item.caseId;
    formTitle.textContent = 'Edit Case Record';
    saveBtn.textContent = 'Update Record';
    cancelBtn.classList.remove('hidden');

    window.scrollTo({ top: form.offsetTop - 50, behavior: 'smooth' });
};

window.deleteCase = async function(id) {
    const confirmed = confirm('Close and purge this criminal record file?');
    if (!confirmed) return;

    try {
        const response = await fetch(`${API_BASE}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            showFeedback('Failed to delete case record.', true);
            return;
        }

        showFeedback('Case record purged successfully.', false);
        if (editIdInput.value === id) {
            resetForm();
        }
        await loadCases();
    } catch (err) {
        showFeedback('Network error while deleting record.', true);
    }
};

function resetForm() {
    form.reset();
    editIdInput.value = '';
    formTitle.textContent = 'File New Case';
    saveBtn.textContent = 'File Record';
    cancelBtn.classList.add('hidden');
    feedbackBox.classList.add('hidden');
}

cancelBtn.addEventListener('click', resetForm);

searchInput.addEventListener('input', (e) => {
    const query = e.target.value.trim();
    loadCases(query);
});

searchBtn.addEventListener('click', () => {
    loadCases(searchInput.value.trim());
});

document.addEventListener('DOMContentLoaded', () => {
    loadCases();
});
