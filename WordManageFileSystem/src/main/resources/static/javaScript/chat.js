// ========== 核心弹窗：极简粉色款 ==========
function injectToastStyle() {
    if (document.getElementById('mini-toast-style')) return;
    const style = document.createElement('style');
    style.id = 'mini-toast-style';
    style.innerHTML = `
        .mini-toast {
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            padding: 10px 22px;
            background: #fff;
            border: 1px solid #fce4ec;
            border-radius: 6px;
            box-shadow: 0 1px 6px rgba(255, 105, 180, 0.15);
            z-index: 9999;
            font-size: 14px;
            color: #333;
            text-align: center;
            font-family: -apple-system, BlinkMacSystemFont, "Microsoft YaHei", sans-serif;
            animation: slideIn 0.25s ease-out forwards, fadeOut 0.25s ease-in 3.75s forwards;
        }
        .mini-toast::before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            height: 100%;
            width: 2px;
            background: #ff80ab;
            border-radius: 6px 0 0 6px;
        }
        @keyframes slideIn {
            0% { top: -30px; opacity: 0; transform: translateX(-50%) scale(0.98); }
            100% { top: 20px; opacity: 1; transform: translateX(-50%) scale(1); }
        }
        @keyframes fadeOut {
            0% { opacity: 1; transform: translateX(-50%) scale(1); }
            100% { opacity: 0; transform: translateX(-50%) scale(0.98); }
        }
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
        .msg-toast.success { background: #7ccc62; }
        .msg-toast.warning { background: #f0b95e; }
        .msg-toast.error { background: #f87272; }
        .msg-toast.info { background: #64b5f6; }
    `;
    document.head.appendChild(style);
}

function showMiniToast(content, duration = 3000) {
    injectToastStyle();
    const oldToast = document.querySelector('.mini-toast');
    if (oldToast) oldToast.remove();
    const toast = document.createElement('div');
    toast.className = 'mini-toast';
    toast.innerHTML = content;
    document.body.appendChild(toast);
    setTimeout(() => toast.remove(), duration);
}


// ========== 基础DOM和事件 ==========
// 提前获取DOM，避免重复查询
const input = document.getElementById('inputContent');
const place = document.getElementById('inputPlace');
const chatDoc = document.getElementById('chat');
const scrollContainer = document.querySelector('.chat');

var searchInput = document.getElementById('searchInput');
var showFriend = document.getElementById('showFriend');

var allFriend = document.getElementById('allFriend');

// 输入框焦点事件
input?.addEventListener('focus', () => {
    place?.classList.add('active');
});
input?.addEventListener('blur', () => {
    place?.classList.remove('active');
});

// ========== 核心工具函数 ==========
// 滚动到最底部（通用）
// function scrollToBottom() {
//     if (scrollContainer) {
//         scrollContainer.scrollTop = scrollContainer.scrollHeight;
//     }
// }

// 1. 刷新列表前，先记录滚动位置
// function refreshFriendList() {
//     // 记录当前滚动位置
//     const scrollTop = scrollContainer.scrollTop;
//
//     // 2. 执行你的刷新逻辑（比如重新调接口、渲染列表）
//     getChatMsg().then(list => {
//         printListMsg(list);
//
//         // 3. 渲染完成后，恢复滚动位置（加个微延迟，确保DOM渲染完）
//         setTimeout(() => {
//             friendContainer.scrollTop = scrollTop;
//         }, 50); // 50ms足够，不用改
//     });
// }



var addFriendDiv = document.getElementById('addFriendDiv');

function addOpen() {
    addFriendDiv.style.display = 'flex';
}

//退出搜索好友栏
function addExcape() {
    showFriend.innerHTML = '';
    searchInput.value = '';
    addFriendDiv.style.display = 'none';
}


// ========== 业务逻辑：发送消息 ==========
async function submition() {
    const text = input.value.trim();
    if (!text) return showMiniToast('消息不能为空！');

    const token = localStorage.getItem("token");
    if (!token) return showMiniToast('请先登录！');

    // 先清空输入框，给用户「已发送」的反馈
    input.value = '';

    try {
        const counterId = localStorage.getItem("chatFriendId");
        // 发送请求
        await axios.post('/chat/send', {text}, {
            headers: {userToken: token},
            params: {idB: counterId}
        });
        // ✅ 发送成功后，立刻刷新聊天列表
        await getChatMsg();

        showMiniToast('发送成功～');
    } catch (err) {
        console.error(err);
        showMiniToast('发送失败，请重试');
    }
}

setInterval(getChatMsg, 400);



// ========== 业务逻辑：获取并渲染消息 ==========
async function getChatMsg() {

    // 校验DOM和Token
    if (!chatDoc) {
        showMiniToast('聊天容器未找到！');
        return;
    }
    const token = localStorage.getItem("token"); //2
    if (!token) {
        showMiniToast('请先登录！');
        return;
    }

    try {
        // 发送GET请求（修正axios格式）
        const chatFriendId = localStorage.getItem("chatFriendId"); //1
        const res = await axios.get('/chat/select', {
            headers: {userToken: token},
            params: {idB: chatFriendId}
        });

        const list = res.data?.data || [];
        console.log('获取到聊天记录：', list);

        // 清空旧内容，避免重复渲染
        chatDoc.innerHTML = '';

        console.log(list);

        // 渲染消息列表
        printListMsg(list);
        scrollToBottom(); // 滚到底部
    } catch (error) {
        console.error('获取聊天记录失败：', error);
        showMiniToast('获取聊天记录失败！');
    }
}

// ========== 渲染消息列表 ==========
function printListMsg(list) {
    if (!Array.isArray(list) || list.length === 0) {
        chatDoc.innerHTML = '<div style="text-align:center; color:#999; padding:20px;">暂无聊天记录～</div>';
        return;
    }

    const userId = localStorage.getItem('userId');
    if (!userId) {
        showMiniToast('未获取到用户信息！');
        return;
    }
    const currentUserId = parseInt(userId);

    // 遍历渲染每条消息
    list.forEach(e => {
        let row = '';
        // 兜底空值，避免undefined显示
        const msgText = e.text || '';
        const imgSrc = e.senderImg || '/default-avatar.png';

        // 区分自己/对方的消息
        if (e.sendUserId === currentUserId) {
            // 自己的消息（右侧）
            row = `
                <div class="chat_item_self">
                    <div class="chat_msg">${msgText}</div>
                    <div class="headImg">
                        <img src="${imgSrc}" alt="头像" onerror="this.src='/default-avatar.png'">
                    </div>
                </div>
            `;
        } else {
            // 对方的消息（左侧）
            row = `
                <div class="chat_item">
                    <div class="headImg">
                        <img src="${imgSrc}" alt="头像" onerror="this.src='/default-avatar.png'">
                    </div>
                    <div class="chat_msg">${msgText}</div>
                </div>
            `;
        }
        // 追加到聊天容器
        chatDoc.innerHTML += row;
    });
}

//查询搜索好友
async function searchFriend() {
    const res = await axios.post('/chat/search-friend',{},
        {params: {name : searchInput.value}});
    const result = res.data.data; //这里是List集合
    showFriend.innerHTML = '';
    searchList(result);
}

//展示搜索到的集合
function searchList(list) {
    list.forEach(e => {
        let row = '';

        // 自己的消息（右侧）
        row = `
                <div class="showFriend_item">
                <div class="showFriend_item_Img">
                    <img src="${e.img}" alt="">
                </div>
                <div class="msgBar">
                    <div class="upName">${e.name}</div>
                    <div class="downGender">${e.gender}</div>
                </div>
                <div class="addBut">
                    <button onclick="addFriend(${e.id}
                    , '${e.name}', '${e.img}', '${e.gender}')"
                    >添加</button>
                </div>
            </div>
            `;
        // 追加到聊天容器
        showFriend.innerHTML += row;
    });
}

/*
* {
    "id": 3,
    "name": "哈基米",
    "img": "https://java-web-xiaoka.oss-cn-beijing.aliyuncs.com/uploads/177329211521jpg",
    "gender": "女"
}*/

//添加好友
async function addFriend(id , name , img , gender) {
    //添加好友的json
    const searchFriendBody = {
        "id" : id,
        "name" : name,
        "img" : img,
        "gender" : gender
    };
    const token = localStorage.getItem("token");
    const res = await axios.post('/chat/add-friend', searchFriendBody,
        {headers : {userToken : token}});
    const r = res.data.data;
    showMiniToast(r);
    selectAllFriend(); //重新刷新已添加的好友栏
}

/*
* {
      "id": 1,
      "name": "小卡爱吃糖",
      "img": "https://java-web-xiaoka.oss-cn-beijing.aliyuncs.com/uploads/1773292115216.jpg",
      "gender": "女"
 }*/

// ========== 查询所有好友 ==========
async function selectAllFriend() {
    const token = localStorage.getItem("token");
    const res = await axios.get('/chat/select-all',{headers : {userToken : token}})
    const list = res.data.data;
    allFriend.innerHTML = '';
    selectAllFriend_foreach(list);
}

function selectAllFriend_foreach(list){
    list.forEach(e => {
        let row = '';

        // 自己的消息（右侧）
        row = `
                <div class="friendItem" onclick="addLocalFriendId(${e.addId} , '${e.msgName}')">
                <div class="friendItem_img">
                    <img src="${e.msgImg}" alt="">
                </div>
                <div class="friendItem_text">
                    <h4>${e.msgName}</h4>
                </div>
            </div>
            `;
        // 追加到聊天容器
        allFriend.innerHTML += row;
    });
}

setInterval(selectAllFriend, 300);

var friendNameDoc = document.getElementById('friendName');

//点击某好友操作
function addLocalFriendId(addId , msgName){
    friendNameDoc.innerHTML = msgName;
    localStorage.setItem("chatFriendId" , addId);
}



// ========== enter键按下，实现发送功能 ==========
var submitionDoc = document.getElementById('submition');

submitionDoc.addEventListener('keydown' , function (e){
    if (e.key === 'Enter' || e.keyCode === 13) {
        e.preventDefault(); // 阻止页面刷新/输入框换行
        // 调用你要执行的方法（比如搜索好友）
        submition();
    }
})


// ========== 开局执行（仅保留一个，避免冲突） ==========
window.onload = async function () {
    // 确保DOM和资源加载完成后执行
    await getChatMsg();
    await selectAllFriend();
    scrollToBottom();
};

