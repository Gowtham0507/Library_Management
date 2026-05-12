const API_BASE = '';

// =======================
// UI UTILITIES
// =======================
function showAlert(message, type = 'success') {
  const container = document.getElementById('alertContainer');
  if (!container) return;
  const alertDiv = document.createElement('div');
  alertDiv.className = `alert alert-${type} alert-dismissible fade show alert-premium`;
  if (type === 'success') {
    alertDiv.classList.add('alert-success-custom');
    alertDiv.innerHTML = `<i class="fas fa-check-circle"></i> ${message}`;
  } else {
    alertDiv.classList.add('alert-danger-custom');
    alertDiv.innerHTML = `<i class="fas fa-exclamation-circle"></i> ${message}`;
  }
  container.appendChild(alertDiv);
  setTimeout(() => {
    alertDiv.classList.remove('show');
    setTimeout(() => alertDiv.remove(), 300);
  }, 4000);
}

function toggleSidebar() {
  const sidebar = document.getElementById('sidebar');
  sidebar.classList.toggle('active');
}

// =======================
// DASHBOARD
// =======================
async function loadDashboardStats() {
  try {
    const [booksRes, membersRes, issuesRes] = await Promise.all([
      fetch('/books'),
      fetch('/members'),
      fetch('/issues/active')
    ]);

    if (booksRes.ok && membersRes.ok && issuesRes.ok) {
      const books = await booksRes.json();
      const members = await membersRes.json();
      const issues = await issuesRes.json();

      document.getElementById('totalBooks').textContent = books.length;
      document.getElementById('availableBooks').textContent = books.filter(b => b.availability).length;
      document.getElementById('totalMembers').textContent = members.length;
      document.getElementById('totalIssues').textContent = issues.length;

      // Render recent issues
      const recentIssues = issues.slice(0, 5); // top 5
      const tbody = document.getElementById('recentTbody');
      tbody.innerHTML = '';
      if (recentIssues.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center py-4 text-muted">No active issues.</td></tr>';
      } else {
        recentIssues.forEach((issue, idx) => {
          tbody.innerHTML += `
            <tr>
              <td>${idx + 1}</td>
              <td class="td-title">${issue.book.title}</td>
              <td>${issue.member.name}</td>
              <td>${new Date(issue.issueDate).toLocaleDateString()}</td>
              <td><span class="badge-issued">Issued</span></td>
            </tr>
          `;
        });
      }
    }
  } catch (err) {
    console.error('Error loading dashboard stats:', err);
  }
}

// =======================
// BOOKS
// =======================
let allBooks = [];

async function fetchBooks() {
  try {
    const res = await fetch('/books');
    if (res.ok) {
      allBooks = await res.json();
      renderBooksTable(allBooks);
    }
  } catch (err) {
    showAlert('Failed to fetch books', 'danger');
  }
}

function renderBooksTable(books) {
  const tbody = document.getElementById('booksTbody');
  if (!tbody) return;
  tbody.innerHTML = '';
  if (books.length === 0) {
    tbody.innerHTML = '<tr><td colspan="4" class="text-center py-4 text-muted">No books found.</td></tr>';
    return;
  }
  books.forEach(b => {
    tbody.innerHTML += `
      <tr>
        <td>#${b.bookId}</td>
        <td>
          <div class="d-flex align-items-center gap-3">
            <div class="book-cover"><i class="fas fa-book"></i></div>
            <div>
              <div class="td-title">${b.title}</div>
              <div class="td-sub">${b.author}</div>
            </div>
          </div>
        </td>
        <td>${b.availability ? '<span class="badge-available">Available</span>' : '<span class="badge-issued">Issued</span>'}</td>
        <td class="text-end">
          <button class="btn-outline-custom" onclick="deleteBook(${b.bookId})"><i class="fas fa-trash"></i></button>
        </td>
      </tr>
    `;
  });
}

function handleSearch(query) {
  if (!query) {
    renderBooksTable(allBooks);
    return;
  }
  const q = query.toLowerCase();
  const filtered = allBooks.filter(b => b.title.toLowerCase().includes(q) || b.author.toLowerCase().includes(q));
  renderBooksTable(filtered);
}

function openAddBook() {
  document.getElementById('bookForm').reset();
  document.getElementById('bookId').value = '';
  new bootstrap.Modal(document.getElementById('bookModal')).show();
}

async function saveBook(e) {
  e.preventDefault();
  const title = document.getElementById('bookTitle').value;
  const author = document.getElementById('bookAuthor').value;
  try {
    const res = await fetch('/books', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title, author, availability: true })
    });
    if (res.ok) {
      bootstrap.Modal.getInstance(document.getElementById('bookModal')).hide();
      showAlert('Book added successfully');
      fetchBooks();
    } else {
      showAlert('Error saving book', 'danger');
    }
  } catch (err) {
    showAlert('Error saving book', 'danger');
  }
}

async function deleteBook(id) {
  if (!confirm('Are you sure you want to delete this book?')) return;
  try {
    const res = await fetch(`/books/${id}`, { method: 'DELETE' });
    if (res.ok) {
      showAlert('Book deleted');
      fetchBooks();
    } else {
      showAlert('Cannot delete book (it might be currently issued)', 'danger');
    }
  } catch (err) {
    showAlert('Error deleting book', 'danger');
  }
}

// =======================
// MEMBERS
// =======================
async function fetchMembers() {
  try {
    const res = await fetch('/members');
    if (res.ok) {
      const members = await res.json();
      const tbody = document.getElementById('membersTbody');
      if (!tbody) return;
      tbody.innerHTML = '';
      if (members.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" class="text-center py-4 text-muted">No members found.</td></tr>';
        return;
      }
      members.forEach(m => {
        tbody.innerHTML += `
          <tr>
            <td>#${m.memberId}</td>
            <td>
              <div class="d-flex align-items-center gap-3">
                <div class="member-avatar">${m.name.charAt(0).toUpperCase()}</div>
                <div class="td-title">${m.name}</div>
              </div>
            </td>
            <td>${m.email}</td>
            <td class="text-end">
              <button class="btn-outline-custom" onclick="deleteMember(${m.memberId})"><i class="fas fa-trash"></i></button>
            </td>
          </tr>
        `;
      });
    }
  } catch (err) {
    showAlert('Failed to fetch members', 'danger');
  }
}

function openAddMember() {
  document.getElementById('memberForm').reset();
  document.getElementById('memberId').value = '';
  new bootstrap.Modal(document.getElementById('memberModal')).show();
}

async function saveMember(e) {
  e.preventDefault();
  const name = document.getElementById('memberName').value;
  const email = document.getElementById('memberEmail').value;
  try {
    const res = await fetch('/members', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email })
    });
    if (res.ok) {
      bootstrap.Modal.getInstance(document.getElementById('memberModal')).hide();
      showAlert('Member added successfully');
      fetchMembers();
    } else {
      showAlert('Error saving member (email might exist)', 'danger');
    }
  } catch (err) {
    showAlert('Error saving member', 'danger');
  }
}

async function deleteMember(id) {
  if (!confirm('Are you sure?')) return;
  try {
    const res = await fetch(`/members/${id}`, { method: 'DELETE' });
    if (res.ok) {
      showAlert('Member deleted');
      fetchMembers();
    } else {
      showAlert('Cannot delete member (they might have active issues)', 'danger');
    }
  } catch (err) {
    showAlert('Error deleting member', 'danger');
  }
}

// =======================
// ISSUES & RETURNS
// =======================
async function fetchActiveIssues() {
  try {
    const res = await fetch('/issues/active');
    if (res.ok) {
      const issues = await res.json();
      const badge = document.getElementById('issueCountBadge');
      if (badge) badge.textContent = issues.length;
      
      const tbody = document.getElementById('issuesTbody');
      if (!tbody) return;
      tbody.innerHTML = '';
      if (issues.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center py-4 text-muted">No active issues.</td></tr>';
        return;
      }
      issues.forEach((issue, idx) => {
        tbody.innerHTML += `
          <tr>
            <td>${idx + 1}</td>
            <td class="td-title">${issue.book.title}</td>
            <td>${issue.member.name}</td>
            <td>${new Date(issue.issueDate).toLocaleDateString()}</td>
            <td class="text-end">
              <button class="btn-success-custom" onclick="returnBook(${issue.issueId})">
                <i class="fas fa-undo me-1"></i> Return
              </button>
            </td>
          </tr>
        `;
      });
    }
  } catch (err) {
    showAlert('Failed to fetch active issues', 'danger');
  }
}

function openIssueModal() {
  loadMembersForSelect();
  loadBooksForSelect();
  new bootstrap.Modal(document.getElementById('issueModal')).show();
}

async function loadMembersForSelect() {
  try {
    const res = await fetch('/members');
    if (res.ok) {
      const members = await res.json();
      const select = document.getElementById('issueMember');
      select.innerHTML = '<option value="">– Select a Member –</option>';
      members.forEach(m => {
        select.innerHTML += `<option value="${m.memberId}">${m.name} (${m.email})</option>`;
      });
    }
  } catch (err) { console.error(err); }
}

async function loadBooksForSelect() {
  try {
    const res = await fetch('/books');
    if (res.ok) {
      const books = await res.json();
      const select = document.getElementById('issueBook');
      select.innerHTML = '<option value="">– Select a Book –</option>';
      books.filter(b => b.availability).forEach(b => {
        select.innerHTML += `<option value="${b.bookId}">${b.title} (by ${b.author})</option>`;
      });
    }
  } catch (err) { console.error(err); }
}

async function handleIssueSubmit(e) {
  e.preventDefault();
  const memberId = document.getElementById('issueMember').value;
  const bookId = document.getElementById('issueBook').value;
  
  if (!memberId || !bookId) {
    showAlert('Please select both member and book', 'danger');
    return;
  }
  
  try {
    const res = await fetch('/issues/issue', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ memberId: Number(memberId), bookId: Number(bookId) })
    });
    
    if (res.ok) {
      bootstrap.Modal.getInstance(document.getElementById('issueModal')).hide();
      showAlert('Book issued successfully!');
      if (document.getElementById('issuesTbody')) fetchActiveIssues();
    } else {
      const errorMsg = await res.text();
      showAlert(errorMsg || 'Failed to issue book (Member might have 3 books already)', 'danger');
    }
  } catch (err) {
    showAlert('Network error', 'danger');
  }
}

async function returnBook(issueId) {
  try {
    const res = await fetch(`/issues/return/${issueId}`, { method: 'PUT' });
    if (res.ok) {
      showAlert('Book returned successfully!');
      fetchActiveIssues();
    } else {
      showAlert('Failed to return book', 'danger');
    }
  } catch (err) {
    showAlert('Network error', 'danger');
  }
}
