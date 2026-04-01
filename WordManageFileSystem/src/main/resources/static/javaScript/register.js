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

//DOC获取集
var usernameDoc = document.getElementById('username');
var passwordDoc = document.getElementById('password');

//注册处理
function registerHander(){
    // 1. 判空
    if (usernameDoc.value === "" || passwordDoc.value === ""){
        showMiniToast("用户名和密码不能为空!!");
        return;
    }

    // 2. 用户名校验：长度 >=8
    if (usernameDoc.value.length > 8) {
        showMiniToast("用户名长度不能大于8个字符！");
        return;
    }

    // 3. 密码校验：字母开头 + 必须有数字 + 长度 >=8
    const pwdReg = /^[A-Za-z](?=.*\d).{7,}$/;
    if (!pwdReg.test(passwordDoc.value)) {
        showMiniToast("密码必须：字母开头、包含数字、长度不小于8位！");
        return;
    }

    // 4. 都通过才发请求
    const registerBody = {
        username : usernameDoc.value,
        password : passwordDoc.value
    }
    axios.post('/register/insert' , registerBody)
        .then(response => {
            const result = response.data;
            const registerCondition = result.data.registerCondition;
            const username = result.data.username;
            const password = result.data.password;
            clearInput();
            showMiniToast("注册成功！！\n"+"用户名："+username+"\n"+"密码："+password);
            setTimeout(() => {
                window.location.href = "../html/login.html";
            }, 2000);
        });
}

function clearInput(){
    usernameDoc.value = "";
    passwordDoc.value = "";
}