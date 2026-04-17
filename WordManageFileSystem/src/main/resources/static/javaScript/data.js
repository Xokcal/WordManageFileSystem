
//用户id


// 用户数据Echart
var userDataEchart = echarts.init(document.getElementById('userData'));

function loadUserDataEchart(){
    axios.get('/report/dataOverLook')
        .then(response => {
            const result = response.data;
            const userDataEchartData = result.data;

            userDataEchartOption = {
                title: {},
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['四天前', '三天前', '两天前', '一天前', '今天']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['四天前', '三天前', '两天前', '一天前', '今天']
                },
                yAxis: {
                    type: 'value'
                },
                // 👇 下面我帮你统一配置好 浅色+面积填充
                series: userDataEchartData.map((item, index) => {
                    // 定义你要的 浅粉、浅紫、浅橙 循环配色
                    const colors = [
                        { line: '#F8BBD0', area: 'rgba(248, 187, 208, 0.3)' }, // 浅粉
                        { line: '#D1C4E9', area: 'rgba(209, 196, 233, 0.3)' }, // 浅紫
                        { line: '#FFE0B2', area: 'rgba(255, 224, 178, 0.3)' }, // 浅橙
                        { line: '#B2EBF2', area: 'rgba(178, 235, 242, 0.3)' }, // 浅蓝（补充）
                        { line: '#C8E6C9', area: 'rgba(200, 230, 201, 0.3)' }  // 浅绿（补充）
                    ];
                    const color = colors[index % colors.length];

                    return {
                        ...item,
                        type: 'line',
                        smooth: true,        // 平滑曲线
                        lineStyle: {
                            color: color.line,
                            width: 2
                        },
                        areaStyle: {
                            // 浅色从上到下渐变（超级柔和）
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: color.area },
                                { offset: 1, color: color.area.replace('0.3', '0.02') }
                            ])
                        }
                    };
                })
            };

            // userDataEchartOption = {
            //     title: {},
            //     tooltip: {
            //         trigger: 'axis'
            //     },
            //     legend: {
            //         data: ['四天前', '三天前', '两天前', '一天前', '今天']
            //     },
            //     grid: {
            //         left: '3%',
            //         right: '4%',
            //         bottom: '3%',
            //         containLabel: true
            //     },
            //     toolbox: {
            //         feature: {
            //             saveAsImage: {}
            //         }
            //     },
            //     xAxis: {
            //         type: 'category',
            //         boundaryGap: false,
            //         data: ['四天前', '三天前', '两天前', '一天前', '今天']
            //     },
            //     yAxis: {
            //         type: 'value'
            //     },
            //     series: userDataEchartData
            //
            // };

            userDataEchart.setOption(userDataEchartOption);
        })
}

//开局自动启动函数
document.addEventListener('DOMContentLoaded' , function (){
    loadUserDataEchart();
})

//数据总览文本数据分配
const wordTotalDoc = document.getElementById('wordTotal');
const accuracyAvgDoc = document.getElementById('accuracyAvg');
const reedAvgDoc = document.getElementById('reedAvg');
const userTotalDoc = document.getElementById('userTotal');

function overLookText(){
    axios.get('/report/overLookText')
        .then(response => {
            const result = response.data;
            const data = result.data;
            const wordTotal = data.wordTotal;
            const userAccuracyAvg = data.userAccuracyAvg;
            const userReedAvg = data.userReedAvg;
            const userTotal = data.userTotal;

            wordTotalDoc.innerHTML = wordTotal;
            accuracyAvgDoc.innerHTML = userAccuracyAvg;
            reedAvgDoc.innerHTML = userReedAvg;
            userTotalDoc.innerHTML = userTotal;
        })
}

document.addEventListener('DOMContentLoaded' , function (){
    overLookText();
})

// 单词抽查数据
// 单词抽查数据
// 单词抽查数据

var checkDataEchart = echarts.init(document.getElementById('checkData'));

function checkDailyDataEchart(){
    const token = localStorage.getItem('token');
    if (token === null){
        alert("token 为null")
    }
    axios.get('/report/checkEchart' , {headers : {userToken : token}})
        .then(response => {
            const result = response.data;
            const dataEchart = result.data;

            // checkDataEchartOption = {
            //     title: {
            //         text: '每日单词抽查'
            //     },
            //     tooltip: {
            //         trigger: 'axis',
            //         axisPointer: {
            //             type: 'shadow'
            //         }
            //     },
            //     legend: {},
            //     xAxis: {
            //         type: 'value',
            //         boundaryGap: [0, 0.01]
            //     },
            //     yAxis: {
            //         type: 'category',
            //         data: ['准确数', '错误数', '总次数']
            //     },
            //     series: dataEchart
            // };
            checkDataEchartOption = {
                title: {
                    text: '每日单词抽查',
                    textStyle: {
                        color: '#333',
                        fontSize: 16,
                        fontWeight: 500
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow',
                        shadowStyle: {
                            color: 'rgba(123, 31, 162, 0.05)' // 紫色系轻微阴影，呼应主色调
                        }
                    },
                    backgroundColor: '#fff',
                    borderColor: '#eee',
                    borderWidth: 1,
                    textStyle: { color: '#333' }
                },
                legend: {},
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01],
                    axisLine: { lineStyle: { color: '#eee' } },
                    axisLabel: { color: '#666' },
                    splitLine: { lineStyle: { color: '#f5f5f5' } }
                },
                yAxis: {
                    type: 'category',
                    data: ['准确数', '错误数', '总次数'],
                    axisLine: { lineStyle: { color: '#eee' } },
                    axisLabel: { color: '#666' }
                },
                series: dataEchart.map((item, index) => {
                    // 紫色系梯度配色（区分度高+视觉和谐）
                    const colorList = ['#818ec2'];
                    return {
                        ...item,
                        itemStyle: {
                            // 轻渐变提升质感，想要纯纯色可删LinearGradient部分
                            color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                                { offset: 1, color: colorList[index] }
                            ])
                        },
                        emphasis: {
                            itemStyle: {
                                color: colorList[index],
                                shadowBlur: 5,
                                shadowColor: colorList[index] + '50'
                            }
                        }
                    };
                })
            };

            checkDataEchart.setOption(checkDataEchartOption);
        })
}


document.addEventListener('DOMContentLoaded' , function (){
    checkDailyDataEchart();
})

// 用户准确率数据
var accurcyDataEchart = echarts.init(document.getElementById('accurcyData'));

function loadAccuracyEChartData(){
    const token = localStorage.getItem('token');
    if (token === null){
        alert("token 为null")
    }
    axios.get('/report/accuracyEchart' , {headers : {userToken : token}})
        .then(response => {
            const result = response.data;
            const accurcyDataEchartData = result.data;

            // accurcyDataEchartOption = {
            //     tooltip: {
            //         trigger: 'item'
            //     },
            //     legend: {
            //         top: '5%',
            //         left: 'center'
            //     },
            //     series: [
            //         {
            //             name: 'Access From',
            //             type: 'pie',
            //             radius: ['40%', '70%'],
            //             center: ['50%', '70%'],
            //             // adjust the start and end angle
            //             startAngle: 180,
            //             endAngle: 360,
            //             data: accurcyDataEchartData
            //         }
            //     ]
            // };
            accurcyDataEchartOption = {
                tooltip: {
                    trigger: 'item',
                    backgroundColor: '#fff',
                    borderColor: '#eee',
                    borderWidth: 1,
                    padding: 8,
                    textStyle: { color: '#333', fontSize: 12 }
                },
                legend: {
                    top: '5%',
                    left: 'center',
                    textStyle: { color: '#666', fontSize: 12 },
                    icon: 'circle',
                    itemWidth: 6,
                    itemHeight: 6
                },
                series: [
                    {
                        name: '答题准确率',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        center: ['50%', '50%'], // 中心上移，避免下半部分被截断
                        startAngle: 180,
                        endAngle: 360,
                        data: accurcyDataEchartData.map((item, index) => {
                            const colorList = ['#54b5de', '#98d298'];
                            return {
                                ...item,
                                itemStyle: {
                                    color: colorList[index % colorList.length],
                                    shadowBlur: 3,
                                    shadowColor: 'rgba(0,0,0,0.05)',
                                    shadowOffsetY: 2
                                },
                                label: {
                                    color: '#666',
                                    fontSize: 11
                                },
                                labelLine: {
                                    lineStyle: { color: '#ddd' }
                                }
                            };
                        })
                    }
                ]
            };

            accurcyDataEchart.setOption(accurcyDataEchartOption);
        })
}

document.addEventListener('DOMContentLoaded' , function (){
    loadAccuracyEChartData();
})


//单词本单词数量
//单词本单词数量
//单词本单词数量
var noteNumberEchart = echarts.init(document.getElementById('noteNumber'));

noteNumberEchartOption = {
    grid: {
        left: '10%',    // 绘图区距离容器左侧的距离
        right: '10%',   // 绘图区距离容器右侧的距离
        top: '10%',     // 绘图区距离容器顶部的距离
        bottom: '10%',  // 绘图区距离容器底部的距离
        containLabel: true // 重要：确保坐标轴的标签在grid区域内
    },
    xAxis: {
        type: 'category',
        data: ['单词本1', '单词本2', '单词本3', '单词本4']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: [342, 245, 631],
            type: 'bar'
        }
    ]
};

noteNumberEchart.setOption(noteNumberEchartOption);


// 阅读题准确率
var reedAccuracyEchart = echarts.init(document.getElementById('reedAccuracy'));

reedAccuracyEchartOption = {
    tooltip: {
        trigger: 'item'
    },
    // 删除整个 legend 配置
    series: [
        {
            name: 'Access From',
            type: 'pie',
            radius: '60%',
            data: [
                {value: 95, name: '初中'},
                {value: 84, name: '高中'},
                {value: 80, name: '四级'},
                {value: 65, name: '六级'},
                {value: 45, name: '雅思'}
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

reedAccuracyEchart.setOption(reedAccuracyEchartOption);


// 阅读数量
var reedNumberEchart = echarts.init(document.getElementById('reedNumber'));
reedNumberEchartOption = {
    grid: {
        left: '10%',    // 绘图区距离容器左侧的距离
        right: '10%',   // 绘图区距离容器右侧的距离
        top: '10%',     // 绘图区距离容器顶部的距离
        bottom: '10%',  // 绘图区距离容器底部的距离
        containLabel: true // 重要：确保坐标轴的标签在grid区域内
    },
    xAxis: {
        type: 'category',
        data: ['四天前', '三天前', '两天前', '一天前', '今天']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: [2, 3, 3, 4, 2],
            type: 'line'
        }
    ]
};

reedNumberEchart.setOption(reedNumberEchartOption);


// 用户总和得分
var scoreEchart = echarts.init(document.getElementById('score'));

scoreEchartOption = {

    radar: {
        // shape: 'circle',
        indicator: [
            {name: '阅读能力', max: 100},
            {name: '单词抽查', max: 100},
            {name: '持之以恒', max: 100},
            {name: '持之以恒', max: 100},
            {name: '持之以恒', max: 100},
        ]
    },
    series: [
        {
            name: 'Budget vs spending',
            type: 'radar',
            data: [
                {
                    value: [34, 78, 98, 98, 65],
                    name: '综合评分'
                },
                {
                    value: [61.56, 34, 75, 74],
                    name: '平均'
                }
            ]
        }
    ]
};

scoreEchart.setOption(scoreEchartOption);

// 第三大栏三等分数据

//数据1
var chuGaoEchart = echarts.init(document.getElementById('chuGao'));

chuGaoEchartOption = {
    grid: {
        top: '0%',    // 上边距，可以调整这个值让图形上移
        left: '10%',
        right: '10%',
        bottom: '30%'
    },
    legend: {
        top: '93%',
        button: '5%',
        left: 'right'
    },
    series: [
        {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
            },
            label: {
                show: true,
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: 12,
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [
                {value: 1048, name: '初高中'},
                {value: 735, name: '四六级'},
                {value: 580, name: '雅思'},
            ]
        }
    ]
};

chuGaoEchart.setOption(chuGaoEchartOption);

//数据2
var siLiuEchart = echarts.init(document.getElementById('siLiu'));

siLiuEchartOption = {
    grid: {
        top: '0%',    // 上边距，可以调整这个值让图形上移
        left: '10%',
        right: '10%',
        bottom: '30%'
    },
    legend: {
        top: '93%',
        button: '5%',
        left: 'right'
    },
    series: [
        {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
            },
            label: {
                show: true,
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: 12,
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [
                {value: 1048, name: '初高中'},
                {value: 735, name: '四六级'},
                {value: 580, name: '雅思'},
            ]
        }
    ]
};

siLiuEchart.setOption(siLiuEchartOption);

//数据3
var yasiEchart = echarts.init(document.getElementById('yasi'));

yasiEchartOption = {
    grid: {
        top: '0%',    // 上边距，可以调整这个值让图形上移
        left: '10%',
        right: '10%',
        bottom: '30%'
    },
    legend: {
        top: '93%',
        button: '5%',
        left: 'right'
    },
    series: [
        {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
            },
            label: {
                show: true,
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: 12,
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [
                {value: 1048, name: '初高中'},
                {value: 735, name: '四六级'},
                {value: 580, name: '雅思'},
            ]
        }
    ]
};

yasiEchart.setOption(yasiEchartOption);