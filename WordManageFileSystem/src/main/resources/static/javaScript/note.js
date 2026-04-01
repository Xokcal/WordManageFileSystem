//响应式
//响应式
//响应式
//响应式
// 单词本页面 - 移动端侧边栏控制
document.addEventListener('DOMContentLoaded', function() {
    // 创建菜单按钮
    const menuBtn = document.createElement('button');
    menuBtn.className = 'mobile-menu-btn';
    menuBtn.innerHTML = '<span></span>';
    document.body.prepend(menuBtn);

    // 创建遮罩层
    const mask = document.createElement('div');
    mask.className = 'sidebar-mask';
    document.body.appendChild(mask);

    // 侧边栏元素
    const sidebar = document.querySelector('.sideMainBar');

    // 按钮点击切换侧边栏
    menuBtn.addEventListener('click', function() {
        sidebar.classList.toggle('active');
        mask.classList.toggle('active');
    });

    // 遮罩层点击关闭侧边栏
    mask.addEventListener('click', function() {
        sidebar.classList.remove('active');
        mask.classList.remove('active');
    });
});



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

//得到报表数据
async function noteAndNoteWordCountReport() {
    var token = localStorage.getItem("token");
    const r = await axios.get('/note/note-word-report' ,{
        headers:{userToken : token}
    });
    const data = r.data;
    const actualData = data.data;
    const noteList = actualData.noteList;
    const noteWordCountList = actualData.noteWordCountList;

    const noteDataOption = {
        title: {
            text: '近7天单词背诵量',
            left: 'center',
            textStyle: {
                color: '#2c3e50',
                fontSize: 16,
                fontWeight: 'normal'
            }
        },
        tooltip: {
            trigger: 'axis',
            backgroundColor: 'rgba(255,255,255,0.9)',
            borderColor: '#ddd',
            borderWidth: 1,
            textStyle: {color: '#333'}
        },
        legend: {
            data: ['已背单词', '单词本词汇数量'],
            top: '40px',
            textStyle: {color: '#666'}
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: noteList, // ✅ 真实笔记本名字（正确）
            axisLine: {lineStyle: {color: '#eee'}},
            axisLabel: {color: '#666'}
        },
        yAxis: {
            type: 'value',
            axisLine: {show: false},
            splitLine: {lineStyle: {color: '#eee'}},
            axisLabel: {color: '#666'}
        },
        series: [
            {
                name: '已背单词',
                type: 'line',
                data: noteWordCountList, // ✅ 这里换成真实数据！
                smooth: true,
                itemStyle: {color: '#409EFF'},
                areaStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        {offset: 0, color: 'rgba(64, 158, 255, 0.3)'},
                        {offset: 1, color: 'rgba(64, 158, 255, 0.05)'}
                    ])
                },
                lineStyle: {width: 2}
            },
            {
                name: '单词本词汇数量',
                type: 'bar',
                data: noteWordCountList, // ✅ 柱子也用真实数据
                itemStyle: {color: '#997af0'},
                barWidth: '60%'
            }
        ]
    };

    noteDataEchart.setOption(noteDataOption);
}


// 添加处理
var noteNameDoc = document.getElementById('noteName');

async function addNoteBook() {
    if (noteNameDoc.value === null || noteNameDoc.value === '' || noteNameDoc.value === "") {
        showMiniToast("请输入笔记本名称！");
        return;
    }
    const token = localStorage.getItem("token");
    const res = await axios.post('/note', {},
        {params: {bookName: noteNameDoc.value}, headers: {userToken: token}});
    const data = res.data.data;
    if (data <= 0) {
        showMiniToast("单词本添加失败！");
    }
    showMiniToast("单词本添加成功！！");

    getDoc.click();
}


var getDoc = document.getElementById('get');
var searchBookDoc = document.getElementById('searchBook');
var itemMainDoc = document.getElementById('itemMain');
var bookCountDoc = document.getElementById('bookCount');

//查询所有笔记本
async function selectAllNote() {
    itemMainDoc.innerHTML = ""; //每次点击都初始化
    const token = localStorage.getItem("token");
    var bookName = null; //初始化bookName
    if (searchBookDoc.value != "" || searchBookDoc.value != '' || searchBookDoc != null) {
        bookName = searchBookDoc.value; //非null就给他赋值
    }
    const res = await axios.get('/note', {
        params: {bookName: bookName},
        headers: {userToken: token}
    })
    const list = res.data.data; //此时是数组
    console.log(list);

    for (var i = 0; i < list.length; i++) {
        console.log(list[i].bookName);
        var getId = parseInt(list[i].id);
        var row = `<div class="itemMain_body">
                <div class="item"><img src="../home_Img/动效.png" alt="">
                    <h4>${list[i].bookName}</h4>
                    <h5>${list[i].createTime}</h5>
                    <h5 style="color: rgb(98, 50, 211);">${list[i].wordCount}</h5>
                    <div class="accuracy"><h5>${list[i].accurcayAvg}</h5></div>
                    <button style="cursor: pointer;" onclick="entreNote(${getId})"></button>
                </div>
            </div>`;
        itemMainDoc.innerHTML += row;
    }
    bookCountDoc.innerHTML = list.length;
}

// getDoc.addEventListener('click', async () => {
//     itemMainDoc.innerHTML = ""; //每次点击都初始化
//     const token = localStorage.getItem("token");
//     var bookName = null; //初始化bookName
//     if (searchBookDoc.value != "" || searchBookDoc.value != '' || searchBookDoc != null) {
//         bookName = searchBookDoc.value; //非null就给他赋值
//     }
//     const res = await axios.get('/note', {
//         params: {bookName: bookName},
//         headers: {userToken: token}
//     })
//     const list = res.data.data; //此时是数组
//     console.log(list);
//
//     for (var i = 0; i < list.length; i++) {
//         console.log(list[i].bookName);
//         var getId = parseInt(list[i].id);
//         var row = `<div class="itemMain_body">
//                 <div class="item"><img src="../home_Img/动效.png" alt="">
//                     <h4>${list[i].bookName}</h4>
//                     <h5>${list[i].createTime}</h5>
//                     <h5 style="color: rgb(98, 50, 211);">${list[i].wordCount}</h5>
//                     <div class="accuracy"><h5>${list[i].accurcayAvg}</h5></div>
//                     <button style="cursor: pointer;" onclick="entreNote(${getId})"></button>
//                 </div>
//             </div>`;
//         itemMainDoc.innerHTML += row;
//     }
//     bookCountDoc.innerHTML = list.length;
// });

//清除input内容
function clearInput(){
    document.getElementById('noteName').value = '';
    document.getElementById('searchBook').value = '';
    selectAllNote();
    return;
}

//进入此单词本
function entreNote(id){
    localStorage.setItem('noteId' , id);
    window.location.href = `noteEntre.html`;
}

window.onload = function (){
    selectAllNote();
    noteAndNoteWordCountReport();
}

getDoc.click();


