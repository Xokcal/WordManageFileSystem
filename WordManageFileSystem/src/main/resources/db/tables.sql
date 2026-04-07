
create table public_vocabulary
(
    id             int primary key auto_increment comment '单词id',
    word           varchar(100) not null unique comment '单词',
    meaning        varchar(100) not null comment '释义',
    part_of_speech varchar(20) comment '词性',
    belong_grade   varchar(50) comment '所属阶段',
    similar_word   varchar(200) comment '相似单词',
    phrase         varchar(200) comment '短语',
    accuracy double comment '这个不需要，但是不想重写代码了',
    belong         int comment '这里也不需要',
    list           varchar(50) comment '所属list清单'
)comment '公共单词表';

# 用户表
create table user
(
    id       int primary key auto_increment comment '用户id',
    username varchar(100) not null unique comment '用户名',
    password varchar(100) not null comment '用户密码'
) comment '用户表';

create table private_notebook
(
    id       int primary key auto_increment comment '单词本id',
    userId   int          not null comment '创建单词本的用户id',
    notename varchar(100) not null comment '名字'
)comment '单词本';

# 单词准确率
create table word_accuracy
(
    id       int primary key auto_increment comment '准确率id',
    user_id  int not null comment '关联用户id',
    word_id  int not null comment '关联单词id',
    total    int not null default 1 comment '抽查总数',
    is_right int not null default 0 comment '正确总数',
    accuracy double not null default 0 comment '准确率',
    foreign key (user_id) references user (id) on delete cascade,
    foreign key (word_id) references public_vocabulary (id) on delete cascade,
    constraint uk_user_public_word unique (user_id, word_id)
) comment '公共单词准确率表';

# 这里是储存用户的个人单词表
create table private_vocabulary
(
    id             int primary key auto_increment comment '私人单词id',
    user_id        int          not null comment '用户专属（查询）id',
    note_id        int          not null comment '单词本',
    word           varchar(100) not null comment '单词',
    meaning        varchar(100) not null comment '释义',
    part_of_speech varchar(20) comment '词性',
    belong_grade   varchar(50) comment '所属阶段',
    similar_word   varchar(200) comment '相似单词',
    phrase         varchar(200) comment '短语',
    constraint uk_user_private_vocabulary unique (user_id, word),
    foreign key (user_id) references user (id) on delete cascade
)comment '私人单词表';

# 这里是个人单词表的准确率表
create table private_word_accuracy
(
    id       int primary key auto_increment comment '私人单词准确率id',
    user_id  int not null comment '相关用户id',
    word_id  int not null comment '相关私人单词id',
    note_id  int not null comment '所属单词本id',
    total    int not null default 1 comment '抽查总数',
    is_right int not null default 0 comment '正确总数',
    accuracy double not null default 0 comment '准确率',
    foreign key (user_id) references user (id) on delete cascade,
    foreign key (word_id) references private_vocabulary (id) on delete cascade,
    foreign key (note_id) references private_notebook (id) on delete cascade,
    constraint uk_user_private_word unique (user_id, word_id)
) comment '私人单词准确率表';

# 记录昨天的四项数据对接用户
create table yesterday_data
(
    id                        int auto_increment primary key comment '昨天数据id',
    userId                    int not null unique comment '对应用户id',
    yesterday_accuracy double comment '昨天准确率数据',
    yesterday_new_word        int comment '昨天新增单词数据',
    yesterday_officials_total int comment '昨天官方收集单词总数数据',
    yesterday_less_word       int comment '昨天易错单词数据',
    latest_update_time        DATETIME comment '最新更新时间',
    foreign key (userId) references user (id) on delete cascade
) comment '昨天的四项数据';

#用户喜好设置表
create table user_setting
(
    id           int auto_increment primary key comment 'id',
    user_id      int     not null comment '外键用户id',
    is_save_page boolean not null comment '是否保留当前页面',
    save_page    int     not null comment '用户保存的当前页码',

    foreign key (user_id) references user (id) on delete cascade
)comment '用户喜好设置表';

-- 建表
create table chat_msg
(
    id           int auto_increment primary key comment 'id',
    match_id     varchar(300)  not null comment '聊天匹配唯一id',
    send_user_id int           not null comment '发送方id',
    text         varchar(2000) not null comment '信息内容',
    sender_img   varchar(500) comment '发送方头像',
    send_time    datetime      not null comment '发送时间'
)comment '聊天表';

-- 添加好友表
create table add_friend
(
    id       int primary key auto_increment comment 'id',
    user_id  int not null comment '添加方id',
    add_id   int not null comment '被添加方id',
    msg_name varchar(100) comment '被添加方昵称',
    msg_img  varchar(500) comment '被添加方头像'
)comment '添加好友表';

create table note_book
(
    id          int auto_increment primary key comment '笔记本数据id',
    user_id     int         not null comment '对应用户id',
    book_name   varchar(50) not null comment '笔记本名称',
    create_time date comment '笔记本创建时间',
    word_count  long comment '笔记本单词数量',
    accurcay_avg double comment '笔记本平均准确率',
    foreign key (user_id) references user (id) on delete cascade
) comment '笔记本数据表';

create table note_word
(
    id             int primary key auto_increment comment '单词id',
    user_id        int          not null comment '用户id',
    note_id        int          not null comment '单词本id',
    word           varchar(100) not null comment '单词',
    meaning        varchar(100) not null comment '释义',
    part_of_speech varchar(20) comment '词性',
    belong_grade   varchar(50) comment '所属阶段',
    similar_word   varchar(200) comment '相似单词',
    phrase         varchar(200) comment '短语',
    belong         int comment '这里也不需要',
    foreign key (user_id) references user (id) on delete cascade,
    foreign key (note_id) references note_book (id) on delete cascade

)comment '公共单词表';

-- 建表
create table user_msg
(
    id           int primary key auto_increment comment '基础信息id',
    user_id      int not null comment '用户id',
    name         varchar(200) comment '昵称数据',
    phone_number varchar(200) comment '手机号码',
    birth_day    date comment '生日',
    img          varchar(500) comment '头像',
    grade        varchar(200) comment '学历',
    gender       varchar(200) comment '性别',
    city         varchar(100) comment '城市',
    foreign key (user_id) references user (id) on delete cascade

)comment '用户基础信息表';

create table user_check_data
(
    id          int auto_increment primary key comment 'id',
    user_id     int not null comment '用户id',
    total       int not null comment '抽查总数',
    right_num   int comment '正确数',
    mistake_num int comment '错误数',
    update_time datetime comment '更新/创建时间',
    foreign key (user_id) references user (id) on delete cascade
)comment '用户抽查数据';

create table report_date
(
    id        int auto_increment primary key comment '数据id',
    data_name varchar(50) not null comment '数据行名字',
    fourAgo   int         not null comment '四天前数据',
    thirdAgo  int         not null comment '三天前数据',
    twoAgo    int         not null comment '两天前数据',
    oneAgo    int         not null comment '一天前数据',
    today     int         not null comment '今天的数据'
)comment '时间化数据表';