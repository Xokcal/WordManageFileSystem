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

//DEC
var searchWordDOC = document.getElementById('searchWord');
var searchMeaningDOC = document.getElementById('searchMeaning');
var pageDOC = document.getElementById('page');
var wordTbodyDOC = document.querySelector('#wordTbody');
var currPageDOC = document.getElementById('currPage');
var searchWordInputDOC = document.getElementById('searchWordInput');
var searchMeaningInputDOC = document.getElementById('searchMeaningInput');
var delBarDOC = document.getElementById('delBar');
var currPageSpan = document.getElementById('currPageSpan');

let currPage = pageDOC.value || 1;
currPageSpan.innerHTML = 1;

//清空input
function clearBut(){
    searchWordInputDOC.value = '';
    searchMeaningInputDOC.value = '';
    wordPage();
    return;
}

//查询分页单词
async function wordPage() {
    //得到token
    var token = localStorage.getItem("token");
    //得到当前笔记本id
    var noteId = localStorage.getItem("noteId");
    const r = await axios.get('/noteEntre/select',
        {headers:{userToken:token} , params:{noteId:noteId , page:pageDOC.value}})
    const data = r.data.data;
    const words = data.words;
    console.log(words);
    wordPageForeach(words);
}

//分页查询单词渲染
function wordPageForeach(word){
    wordTbodyDOC.innerHTML = '';
    word.forEach(e => {
        wordTbodyDOC.innerHTML += `<tr>
            <th><input type="checkbox"></th>
<!--            <th><h4>${e.id}</h4></th>-->
            <th><h4 style="color: rgb(96, 39, 129);font-size: 12.5px"><i>${e.word}</i></h4></th>
            <th><h4 style="color: rgb(59, 59, 59);">${e.meaning}</h4></th>
            <th><h4>${e.partOfSpeech}</h4></th>
            <th><h4>${e.belongGrade}</h4></th>
            <th><h4>${e.similarWord}</h4></th>
            <th><h4>${e.phrase}</h4></th>
            <th><div class="passPercent_Best"><h4></h4></div></th>
            <th>
                <span class="delete_span"><button style="cursor: pointer;" onclick="openDel(${e.id})">删除</button></span>
                <span class="revise_span"><button style="cursor: pointer;" onclick="openUpdateBar(${e.id})">修改</button></span>
            </th>
        </tr>`
    })
}

var passwordVerifyInputDOC = document.getElementById('passwordVerifyInput');

//打开删除笔记本的界面
function openNoteDel(){
    document.getElementById('delNoteBar').style.display = "flex";
    return;
}

//取消删除笔记本的界面
function cancelNoteDel(){
    document.getElementById('delNoteBar').style.display = "none";
    passwordVerifyInputDOC.value = '';
    return;
}

//确认删除
async function confrimNoteDel() {
    var token = localStorage.getItem("token");
    var noteId = localStorage.getItem("noteId");
    const r = await axios.delete('/noteEntre/delete-note', {
        headers: {
            userToken: token
        },
        params: {
            noteId: noteId,
            password:passwordVerifyInputDOC.value
        }
    });
    const result = r.data;
    const row = result.data;
    if (row === 1){
        showMiniToast("笔记本删除成功！！");
        window.location.href = "note.html";
        return;
    }
    showMiniToast("密码不一致！！");
}

//删除单词
async function deleteWord() {
    var token = localStorage.getItem("token");
    var noteId = localStorage.getItem("noteId");
    var noteWordId = localStorage.getItem("noteWordId");
    const r = await axios.delete('/noteEntre/delete',{
        headers: {
            userToken: token
        },
        params: {
            noteId: noteId,
            wordId:noteWordId
        }})

    closeDel();
    showMiniToast("删除成功！！");
    wordPage();
    return;
}

//打开删除单词界面
function openDel(id){
    delBarDOC.style.display = "flex";
    localStorage.setItem("noteWordId" , id);
    return;
}

//关闭删除界面
function closeDel(){
    delBarDOC.style.display = "none";
    localStorage.setItem("noteWordId" , null);
    return;
}

// 提取添加单词弹窗所有 input 的 ID → 生成 DOM 对象
const wordAddDOC = document.getElementById('wordAdd');
const meaningAddDOC = document.getElementById('meaningAdd');
const partOfSpeechAddDOC = document.getElementById('partOfSpeechAdd');
const belongGradeAddDOC = document.getElementById('belongGradeAdd');
const similarWordAddDOC = document.getElementById('similarWordAdd');
const phraseAddDOC = document.getElementById('phraseAdd');

//添加单词
async function confirmAddWord() {
    const NoteWordBody = {
        word: wordAddDOC.value.trim(),
        meaning: meaningAddDOC.value.trim(),
        partOfSpeech: partOfSpeechAddDOC.value.trim(),
        belongGrade: belongGradeAddDOC.value.trim(),
        similarWord: similarWordAddDOC.value.trim(),
        phrase: phraseAddDOC.value.trim()
    };
    var token = localStorage.getItem("token");
    var noteId = localStorage.getItem("noteId");
    const r = await axios.post('/noteEntre/insert', NoteWordBody, {
        headers: {
            userToken: token  // 请求头
        },
        params: {
            noteId: noteId   // URL 参数
        }
    });

    closeAddBar();
    showMiniToast("添加成功！！");
    wordPage();
    return;

}

//打开添加栏
function openAddBar(){
    document.getElementById('addBar').style.display = "flex";
    return;
}

//关闭添加栏
function closeAddBar(){
    document.getElementById('addBar').style.display = "none";
    return;
}


var wordUpdateIDOC = document.getElementById('wordUpdateI');
var meaningUpdateIDOC = document.getElementById('meaningUpdateI');
var partOfSpeechUpdateIDOC = document.getElementById('partOfSpeechUpdateI');
var belongGradeUpdateIDOC = document.getElementById('belongGradeUpdateI');
var similarWordUpdateIDOC = document.getElementById('similarWordUpdateI');
var phraseUpdateIDOC = document.getElementById('phraseUpdateI');

//打开修改
async function openUpdateBar(id) {
    document.getElementById('updateBar').style.display = "flex";
    var token = localStorage.getItem("token");
    var noteId = localStorage.getItem("noteId");
    const r = await axios.get('/noteEntre/query-by-id',{
        headers: { userToken: token },
        params: { noteId: noteId, wordId: id}
    });
    const e = r.data.data;
    wordUpdateIDOC.value = e.word;
    meaningUpdateIDOC.value = e.meaning;
    partOfSpeechUpdateIDOC.value = e.partOfSpeech;
    belongGradeUpdateIDOC.value = e.belongGrade;
    similarWordUpdateIDOC.value = e.similarWord;
    phraseUpdateIDOC.value = e.phrase;
    localStorage.setItem("noteWordId" , id);
    return;
}

//确认修改
async function confirmUpdate() {
    const noteWordBody = {
        word:wordUpdateIDOC.value,
        meaning:meaningUpdateIDOC.value,
        partOfSpeech:partOfSpeechUpdateIDOC.value,
        belongGrade:belongGradeUpdateIDOC.value,
        similarWord:similarWordUpdateIDOC.value,
        phrase:phraseUpdateIDOC.value,
    };
    var token = localStorage.getItem("token");
    var noteId = localStorage.getItem("noteId");
    var noteWordId = localStorage.getItem("noteWordId");
    const r = await axios.put('/noteEntre/update',
        noteWordBody,
        {
            headers: { userToken: token },
            params: { noteId: noteId, wordId: noteWordId }
        }
    );
    cancelUpdate();
    showMiniToast("修改成功！！");
    wordPage();
}

//退出修改
function cancelUpdate(){
    document.getElementById('updateBar').style.display = "none";
    localStorage.setItem("noteWordId" , null);
    return;
}

//初始化加载
window.onload = function () {
    wordPage();
};

let totalPage = 2;

//下一页
async function nextPage() {
        // 1. 基础获取
        var token = localStorage.getItem("token");
        var noteId = localStorage.getItem("noteId");
        var currentPage = parseInt(pageDOC.value) || 1;
        var nextPage = currentPage + 1;
        if (nextPage > totalPage){
            return;
        }

        const r = await axios.get('/noteEntre/select', {
            headers: { userToken: token },
            params: { noteId: noteId, page: nextPage }
        });
        const data = r.data.data;
        const words = data.words;
        const maxPage = data.maxPage;
        totalPage = maxPage;
        console.log("当前页:" + nextPage, "最大页:" + maxPage);
        currPageSpan.innerHTML = nextPage;
        pageDOC.value = nextPage;
        wordPageForeach(words);
}

//上一页
async function previousPage() {
    //得到token
    var token = localStorage.getItem("token");
    //得到当前笔记本id
    var noteId = localStorage.getItem("noteId");
    //得到下一页数
    var nextPage = parseInt(pageDOC.value) - 1;
    if (nextPage === 0){
        nextPage = 1;
    }
    const r = await axios.get('/noteEntre/select',
        {headers: {userToken: token}, params: {noteId: noteId, page: nextPage}})
    pageDOC.value = nextPage;
    const data = r.data.data;
    const words = data.words;
    currPageSpan.innerHTML = nextPage;
    console.log(words);
    wordPageForeach(words);
}

//返回到单词本主界面
function returnNoteIndex(){
    window.location.href = "note.html";
    localStorage.setItem("noteId" , null);
    return;
}

// 查询单词：根据单词
async function searchWord() {
    var token = localStorage.getItem("token");
    var noteId = localStorage.getItem("noteId");
    var page = pageDOC.value;

    // 安全处理单词
    var word = searchWordInputDOC.value.trim();
    const r = await axios.get('/noteEntre/query-by-word', {
        headers: { userToken: token },
        params: {
            noteId: noteId,
            page: page,
            word: word
        }
    });

    const words = r.data.data;
    wordPageForeach(words);
}

//查询单词：根据释义
async function searchMeaning() {
    var token = localStorage.getItem("token");
    var noteId = localStorage.getItem("noteId");
    var page = pageDOC.value;

    // 安全处理单词
    var meaning = searchMeaningInputDOC.value.trim();
    const r = await axios.get('/noteEntre/query-by-meaning', {
        headers: {userToken: token},
        params: {
            noteId: noteId,
            page: page,
            meaning: meaning
        }
    });

    const words = r.data.data;
    wordPageForeach(words);
}



















