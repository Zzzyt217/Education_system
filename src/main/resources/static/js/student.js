let currentStudentId = null;

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    loadStudents();
    loadStatistics();
});

// 加载学生列表
/* function loadStudents(params = {}) {
    let url = '/api/students';
    if (Object.keys(params).length > 0) {
        url += '?' + new URLSearchParams(params).toString();
    }
    
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('获取学生列表失败');
            }
            return response.json();
        })
        .then(data => {
            const studentList = document.querySelector('.student-list');
            studentList.innerHTML = '';
            data.forEach(student => {
                studentList.innerHTML += createStudentCard(student);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('加载学生列表失败，请重试');
        });
}
 */
// 加载统计数据
function loadStatistics() {
    // 这里应该从后端API获取数据，这里使用模拟数据
    const data = {
        total: 1204,
        male: 680,
        female: 524,
        majors: {
            'Computer Science': 450,
            'Software Engineering': 380,
            'Information Systems': 374
        }
    };

    // 更新统计卡片
    document.getElementById('totalStudents').textContent = data.total;
    document.getElementById('maleStudents').textContent = data.male;
    document.getElementById('femaleStudents').textContent = data.female;

    // 创建性别比例饼图
    const genderCtx = document.getElementById('genderChart').getContext('2d');
    new Chart(genderCtx, {
        type: 'pie',
        data: {
            labels: ['男生', '女生'],
            datasets: [{
                data: [data.male, data.female],
                backgroundColor: [
                    'rgba(52,152,219,0.8)',
                    'rgba(155,89,182,0.8)'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });

    // 创建专业分布饼图
    const majorCtx = document.getElementById('majorChart').getContext('2d');
    new Chart(majorCtx, {
        type: 'pie',
        data: {
            labels: Object.keys(data.majors),
            datasets: [{
                data: Object.values(data.majors),
                backgroundColor: [
                    'rgba(46,204,113,0.8)',
                    'rgba(52,152,219,0.8)',
                    'rgba(155,89,182,0.8)'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
}

// 创建学生卡片HTML
function createStudentCard(student) {
    return `
        <div class="student-card">
            <div class="student-avatar">
                <i class="fas fa-user-graduate"></i>
            </div>
            <div class="student-info">
                <div class="student-name">${student.name}</div>
                <div class="student-details">
                    <span class="student-detail">
                        <i class="fas fa-id-card"></i>
                        ${student.studentId}
                    </span>
                    <span class="class-badge">${student.major} ${student.className}</span>
                    <span class="status-badge ${student.status === 'active' ? 'status-active' : 'status-inactive'}">
                        ${student.status === 'active' ? '在读' : '休学'}
                    </span>
                    <span class="student-detail">
                        <i class="fas fa-phone"></i>
                        ${student.phone}
                    </span>
                    <span class="student-detail">
                        <i class="fas fa-envelope"></i>
                        ${student.email}
                    </span>
                </div>
            </div>
            <div class="action-buttons">
                <button class="btn btn-primary btn-sm" onclick="editStudent(${student.id})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-danger btn-sm" onclick="deleteStudent(${student.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        </div>
    `;
}

// 显示添加学生模态框
function showAddStudent() {
    currentStudentId = null;
    document.getElementById('studentModalLabel').textContent = '添加学生';
    document.getElementById('studentForm').reset();
    new bootstrap.Modal(document.getElementById('studentModal')).show();
}

// 编辑学生
/* function editStudent(id) {
    currentStudentId = id;
    document.getElementById('studentModalLabel').textContent = '编辑学生';
    
    fetch(`/api/students/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('获取学生信息失败');
            }
            return response.json();
        })
        .then(student => {
            const form = document.getElementById('studentForm');
            form.studentId.value = student.studentId;
            form.name.value = student.name;
            form.gender.value = student.gender;
            form.major.value = student.major;
            form.class.value = student.class;
            form.phone.value = student.phone;
            form.email.value = student.email;
            
            new bootstrap.Modal(document.getElementById('studentModal')).show();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('获取学生信息失败，请重试');
        });
} */

// 保存学生信息
function saveStudent() {
    const form = document.getElementById('studentForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    
    const url = currentStudentId ? `/api/students/${currentStudentId}` : '/api/students';
    const method = currentStudentId ? 'PUT' : 'POST';
    
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('保存失败');
        }
        return response.json();
    })
    .then(() => {
        bootstrap.Modal.getInstance(document.getElementById('studentModal')).hide();
        loadStudents();
        alert(currentStudentId ? '学生信息更新成功' : '学生添加成功');
    })
    .catch(error => {
        console.error('Error:', error);
        alert('保存失败，请重试');
    });
}

// 删除学生
function deleteStudent(id) {
    currentStudentId = id;
    new bootstrap.Modal(document.getElementById('deleteConfirmModal')).show();
}

// 确认删除
function confirmDelete() {
    if (!currentStudentId) return;
    
    fetch(`/api/students/${currentStudentId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('删除失败');
        }
        bootstrap.Modal.getInstance(document.getElementById('deleteConfirmModal')).hide();
        loadStudents();
        alert('删除成功');
    })
    .catch(error => {
        console.error('Error:', error);
        alert('删除失败，请重试');
    });
}

// 修改搜索相关函数
function searchStudents() {
    const keyword = $('#searchKeyword').val().trim();
    if (keyword === '') {
        // 如果搜索框为空，加载所有学生
        $.get("/api/student/all", function(data) {
            updateStudentTable(data);
        });
        return;
    }
    
    $.ajax({
        url: '/api/student/search',
        method: 'GET',
        data: { keyword: keyword },
        success: function(data) {
            updateStudentTable(data);
        },
        error: function(xhr) {
            alert('搜索失败：' + xhr.statusText);
        }
    });
}

// 更新表格内容的函数
function updateStudentTable(data) {
    var table = $("table");
    table.find("tbody").remove();
    var tbody = $("<tbody>");
    $.each(data, function(index, student) {
        tbody.append(`<tr>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.studentId}</td>
            <td>${student.className}</td>
            <td>${student.major}</td>
            <td>${student.phone}</td>
            <td>${student.email}</td>
            <td>
                <button class='btn btn-primary btn-sm editStudent' data-id='${student.id}'>修改</button>
                <button class='btn btn-danger btn-sm deleteStudent' data-id='${student.id}'>删除</button>
            </td>
        </tr>`);
    });
    table.append(tbody);
}

// 添加输入监听和防抖
let searchTimeout;
$('#searchKeyword').on('input', function() {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
        searchStudents();
    }, 300);
});

// 添加回车搜索支持
$('#searchKeyword').on('keypress', function(e) {
    if (e.which === 13) { // 回车键
        searchStudents();
    }
});

// 保持原有的搜索按钮点击事件
$('#searchBtn').click(function() {
    searchStudents();
});

// 初始化时加载学生列表
$(document).ready(function() {
    $.get("/api/student/all", function(data) {
        updateStudentTable(data);
    });
}); 