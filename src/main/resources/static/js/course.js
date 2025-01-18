// 动态加载教师选项（示例函数，实际需要从后端获取数据）
function loadTeachers() {
    // 模拟从后端获取教师数据
    const teachers = [
        { id: 1, name: '张三' },
        { id: 2, name: '李四' }
    ];

    const teacherSelect = document.querySelector('select[name="teacherId"]');
    teachers.forEach(teacher => {
        const option = document.createElement('option');
        option.value = teacher.id;
        option.textContent = teacher.name;
        teacherSelect.appendChild(option);
    });
}

// 显示添加课程模态框
function showAddCourse() {
    const courseModal = new bootstrap.Modal(document.getElementById('courseModal'));
    document.getElementById('courseModalLabel').textContent = '添加课程';
    document.getElementById('courseForm').reset();
    loadTeachers(); // 动态加载教师选项
    courseModal.show();
}

// 保存课程（示例函数，实际需要发送数据到后端）
function saveCourse() {
    const form = document.getElementById('courseForm');
    const formData = new FormData(form);
    // 这里需要使用 AJAX 或 fetch API 将 formData 发送到后端
    console.log('保存课程数据:', formData);
    const courseModal = bootstrap.Modal.getInstance(document.getElementById('courseModal'));
    courseModal.hide();
}

// 编辑课程（示例函数，实际需要根据课程 ID 获取课程详细信息并填充表单）
function editCourse(courseId) {
    const courseModal = new bootstrap.Modal(document.getElementById('courseModal'));
    document.getElementById('courseModalLabel').textContent = '编辑课程';
    // 这里需要使用 AJAX 或 fetch API 根据 courseId 获取课程详细信息并填充表单
    loadTeachers(); // 动态加载教师选项
    courseModal.show();
}

// 删除课程（示例函数，实际需要发送删除请求到后端）
function deleteCourse(courseId) {
    const deleteConfirmModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
    deleteConfirmModal.show();

    // 确认删除
    window.confirmDelete = function() {
        // 这里需要使用 AJAX 或 fetch API 发送删除请求到后端
        console.log('删除课程 ID:', courseId);
        deleteConfirmModal.hide();
    };
}

// 页面加载完成后执行的初始化函数
document.addEventListener('DOMContentLoaded', function() {
    // 动态加载教师选项
    loadTeachers();

    // 示例：模拟获取课程数据并展示
    const courseList = document.querySelector('.course-list');
    const courses = [
        {
            id: 1,
            courseName: 'Java程序设计',
            place: '教学楼A-301',
            lessonType: '必修',
            credit: 4,
            teacherId: 1,
            hours: 64
        }
    ];

    courses.forEach(course => {
        const courseCard = document.createElement('div');
        courseCard.classList.add('course-card');
        courseCard.innerHTML = `
            <div class="course-icon">
                <i class="fas fa-book"></i>
            </div>
            <div class="course-info">
                <div class="course-name">${course.courseName}</div>
                <div class="course-details">
                    <span class="type-badge type-${course.lessonType === '必修' ? 'required' : 'optional'}">${course.lessonType}</span>
                    <span class="credit-badge">${course.credit}学分</span>
                    <span class="course-detail">
                        <i class="fas fa-user"></i>
                        张三
                    </span>
                    <span class="course-detail">
                        <i class="fas fa-clock"></i>
                        ${course.hours}学时
                    </span>
                    <span class="course-detail">
                        <i class="fas fa-map-marker-alt"></i>
                        ${course.place}
                    </span>
                </div>
            </div>
            <div class="action-buttons">
                <button class="btn btn-primary btn-sm" onclick="editCourse(${course.id})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-danger btn-sm" onclick="deleteCourse(${course.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        `;
        courseList.appendChild(courseCard);
    });
});