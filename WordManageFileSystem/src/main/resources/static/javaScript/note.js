// ========== 核心弹窗：极简粉色款（showMiniToast） ==========
function injectToastStyle() {
    if (document.getElementById('mini-toast-style')) return;
    const style = document.createElement('style');
    style.id = 'mini-toast-style';
    style.innerHTML = `
        /* 极简粉色弹窗 - 更精致的简洁风格 */
        .mini-toast {
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            padding: 10px 22px; /* 精简内边距，更小巧 */
            background: #fff;
            border: 1px solid #fce4ec; /* 更浅的粉色边框，更柔和 */
            border-radius: 6px; /* 更精致的小圆角 */
            box-shadow: 0 1px 6px rgba(255, 105, 180, 0.15); /* 更轻的阴影，不抢眼 */
            z-index: 9999;
            font-size: 14px;
            color: #333;
            text-align: center;
            font-family: -apple-system, BlinkMacSystemFont, "Microsoft YaHei", sans-serif;
            /* 更丝滑的动画 */
            animation: slideIn 0.25s ease-out forwards, fadeOut 0.25s ease-in 3.75s forwards;
        }
        /* 粉色小标识 - 更细更精致 */
        .mini-toast::before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            height: 100%;
            width: 2px; /* 缩窄竖线，更简洁 */
            background: #ff80ab; /* 更温柔的粉色，不刺眼 */
            border-radius: 6px 0 0 6px;
        }
        /* 滑入动画 - 更自然 */
        @keyframes slideIn {
            0% { top: -30px; opacity: 0; transform: translateX(-50%) scale(0.98); }
            100% { top: 20px; opacity: 1; transform: translateX(-50%) scale(1); }
        }
        /* 渐隐动画 - 更丝滑 */
        @keyframes fadeOut {
            0% { opacity: 1; transform: translateX(-50%) scale(1); }
            100% { opacity: 0; transform: translateX(-50%) scale(0.98); }
        }

        /* ========== 功能型弹窗（showMessage）也优化成简洁风格 ========== */
        .msg-toast {
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            padding: 9px 20px;
            border-radius: 6px;
            color: #fff;
            z-index: 9999;
            font-size: 14px;
            text-align: center;
            font-family: -apple-system, BlinkMacSystemFont, "Microsoft YaHei", sans-serif;
            box-shadow: 0 1px 6px rgba(0, 0, 0, 0.1);
            transition: all 0.25s ease;
            opacity: 1;
        }
        /* 不同类型的颜色 - 更柔和的色调 */
        .msg-toast.success { background: #7ccc62; }
        .msg-toast.warning { background: #f0b95e; }
        .msg-toast.error { background: #f87272; }
        .msg-toast.info { background: #64b5f6; }
    `;
    document.head.appendChild(style);
}

// 极简粉色弹窗函数（保留核心逻辑，样式优化）
function showMiniToast(content, duration = 3000) {
    injectToastStyle();
    // 移除旧弹窗，避免叠加
    const oldToast = document.querySelector('.mini-toast');
    if (oldToast) oldToast.remove();
    // 创建新弹窗
    const toast = document.createElement('div');
    toast.className = 'mini-toast';
    toast.innerHTML = content;
    document.body.appendChild(toast);
    // 自动消失
    setTimeout(() => {
        toast.remove();
    }, duration);
}


var noteDataEchart = echarts.init(document.getElementById('noteData'));

const noteDataOption = {
    // 标题：定制文字、颜色、位置
    title: {
        text: '近7天单词背诵量',
        left: 'center',
        textStyle: {
            color: '#2c3e50', // 和你界面文字颜色一致
            fontSize: 16,
            fontWeight: 'normal' // 取消粗体，更简约
        }
    },
    // 提示框：定制悬浮提示的样式
    tooltip: {
        trigger: 'axis',
        backgroundColor: 'rgba(255,255,255,0.9)', // 半透明白底
        borderColor: '#ddd',
        borderWidth: 1,
        textStyle: { color: '#333' }
    },
    // 图例：定制位置和样式
    legend: {
        data: ['已背单词', '新增单词'],
        top: '40px',
        textStyle: { color: '#666' }
    },
    // 网格：定制图表内边距，避免内容贴边
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    // x轴：定制样式
    xAxis: {
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
        axisLine: { lineStyle: { color: '#eee' } }, // 轴线浅灰色
        axisLabel: { color: '#666' } // 文字颜色
    },
    // y轴：定制样式
    yAxis: {
        type: 'value',
        axisLine: { show: false }, // 隐藏y轴线
        splitLine: { lineStyle: { color: '#eee' } }, // 网格线浅灰色
        axisLabel: { color: '#666' }
    },
    // 系列数据：替换成你的真实数据，定制颜色
    series: [
        {
            name: '已背单词',
            type: 'line', // 折线图
            data: [120, 200, 150, 80, 70, 110, 130], // 替换成你的数据
            smooth: true, // 折线平滑
            itemStyle: { color: '#409EFF' }, // 线条颜色（和你按钮的蓝色一致）
            areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
                    { offset: 1, color: 'rgba(64, 158, 255, 0.05)' }
                ])
            }, // 渐变填充
            lineStyle: { width: 2 } // 线条粗细
        },
        {
            name: '新增单词',
            type: 'bar', // 柱状图
            data: [30, 50, 40, 20, 15, 35, 45], // 替换成你的数据
            itemStyle: { color: '#997af0' }, // 紫色（和你界面的浅紫匹配）
            barWidth: '60%' // 柱子宽度
        }
    ]
};

noteDataEchart.setOption(noteDataOption);


// 添加处理
var noteNameDoc = document.getElementById('noteName');

async function addNoteBook() {
    if (noteNameDoc.value === null || noteNameDoc.value === '' || noteNameDoc.value === ""){
        showMiniToast("请输入笔记本名称！");
        return;
    }
    const token = localStorage.getItem("token");
    const res = await axios.post('http://localhost:8080/note', {} ,
        {params: {bookName: noteNameDoc.value}, headers: {userToken: token}});
    const data = res.data.data;
    if (data <= 0){
        showMiniToast("单词本添加失败！");
    }
    showMiniToast("单词本添加成功！！");

}

