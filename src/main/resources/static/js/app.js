const API_URL = '/api';

// --- Auth Functions ---

function showTab(tab) {
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.form-section').forEach(sec => sec.classList.add('hidden'));
    
    if (tab === 'login') {
        document.querySelector('button[onclick="showTab(\'login\')"]').classList.add('active');
        document.getElementById('login-form').classList.remove('hidden');
    } else {
        document.querySelector('button[onclick="showTab(\'register\')"]').classList.add('active');
        document.getElementById('register-form').classList.remove('hidden');
    }
}

async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    try {
        const res = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (res.ok) {
            const data = await res.json();
            localStorage.setItem('token', data.accessToken);
            localStorage.setItem('role', data.role);
            localStorage.setItem('username', username);
            window.location.href = 'dashboard.html';
        } else {
            alert('Login failed');
        }
    } catch (err) {
        console.error(err);
        alert('Error logging in');
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const username = document.getElementById('reg-username').value;
    const password = document.getElementById('reg-password').value;
    const role = document.getElementById('reg-role').value;

    try {
        const res = await fetch(`${API_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password, role })
        });

        if (res.ok) {
            alert('Registration successful! Please login.');
            showTab('login');
        } else {
            alert('Registration failed');
        }
    } catch (err) {
        console.error(err);
        alert('Error registering');
    }
}

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}

// --- Dashboard Functions ---

let currentPaperId = null;

function loadDashboard() {
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');
    
    const formattedRole = role.replace('ROLE_', '');
    document.getElementById('user-display').textContent = `${username} | ${formattedRole}`;

    if (role === 'ROLE_ADMIN') {
        document.querySelector('.admin-only').classList.remove('hidden');
    }
}

function showSection(sectionId) {
    document.querySelectorAll('.content-section').forEach(sec => sec.classList.add('hidden'));
    document.querySelectorAll('.nav-btn').forEach(btn => btn.classList.remove('active'));

    if (sectionId === 'generate') {
        document.getElementById('generate-section').classList.remove('hidden');
        document.querySelector('button[onclick="showSection(\'generate\')"]').classList.add('active');
    } else if (sectionId === 'history') {
        document.getElementById('history-section').classList.remove('hidden');
        document.querySelector('button[onclick="showSection(\'history\')"]').classList.add('active');
        loadHistory('my');
    } else if (sectionId === 'all-history') {
        document.getElementById('all-history-section').classList.remove('hidden');
        document.querySelector('button[onclick="showSection(\'all-history\')"]').classList.add('active');
        loadHistory('all');
    }
}

async function handleGenerate(e) {
    e.preventDefault();
    const btn = document.getElementById('generate-btn');
    btn.disabled = true;
    btn.textContent = 'Generating...';

    const payload = {
        title: document.getElementById('paper-title').value,
        subject: document.getElementById('paper-subject').value,
        difficulty: document.getElementById('paper-difficulty').value,
        count: document.getElementById('paper-count').value
    };

    try {
        const res = await fetch(`${API_URL}/papers/generate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            const paper = await res.json();
            currentPaperId = paper.id;
            document.getElementById('generation-result').classList.remove('hidden');
            document.getElementById('result-message').textContent = `Successfully generated paper: ${paper.title}`;
        } else {
            alert('Generation failed');
        }
    } catch (err) {
        console.error(err);
        alert('Error generating paper');
    } finally {
        btn.disabled = false;
        btn.textContent = 'Generate with AI';
    }
}

function downloadCurrentPaper() {
    if (currentPaperId) {
        downloadPaper(currentPaperId);
    }
}

async function downloadPaper(id) {
    try {
        const res = await fetch(`${API_URL}/papers/${id}/download`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });
        
        if (res.ok) {
            const blob = await res.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = `paper_${id}.pdf`;
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            alert('Download failed');
        }
    } catch (err) {
        console.error(err);
        alert('Error downloading');
    }
}

async function loadHistory(type) {
    const endpoint = type === 'my' ? 'my-history' : 'all-history';
    const containerId = type === 'my' ? 'history-list' : 'all-history-list';
    
    try {
        const res = await fetch(`${API_URL}/papers/${endpoint}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });

        if (res.ok) {
            const papers = await res.json();
            const container = document.getElementById(containerId);
            container.innerHTML = '';
            
            papers.forEach(paper => {
                const div = document.createElement('div');
                div.className = 'paper-item';
                div.innerHTML = `
                    <div>
                        <strong>${paper.title}</strong><br>
                        <small>${paper.subject} | ${new Date(paper.createdAt).toLocaleString()}</small>
                    </div>
                    <button onclick="downloadPaper(${paper.id})" class="btn-secondary">Download PDF</button>
                `;
                container.appendChild(div);
            });
        }
    } catch (err) {
        console.error(err);
    }
}
