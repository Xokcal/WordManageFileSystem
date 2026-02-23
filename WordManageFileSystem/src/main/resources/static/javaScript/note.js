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