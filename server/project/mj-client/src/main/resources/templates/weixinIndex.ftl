<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">
    <meta http-equiv="Cache-Control" content="no-transform">
    <meta content="麻将,${title},约麻将" name="keywords">
    <meta content="${title}" name="description">
    <meta content="${title}" name="author">
    <meta content="2014-2016 bjmajiang.com Inc." name="copyright">
    <meta content="${title}" name="application-name">
    <meta content="${title}" name="msapplication-tooltip">
    <meta name='full-screen' content='true'/>
    <meta content="name=${title};action-uri=http://www.bjmajiang.com;icon-uri=http://www.bjmajiang.com/favicon.ico"
          name="msapplication-task">
    <meta name='laya' logoimg='icon/144.png' logobkcolor='#ffffff' screenorientation='landscape' cacheid='fffffffff'/>

    <meta name="apple-mobile-web-app-title" content="${title}">
    <!-- 添加到主屏后的标题（iOS 6 新增） -->
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <!-- 是否启用 WebApp 全屏模式，删除苹果默认的工具栏和菜单栏 -->
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <!-- 设置苹果工具栏颜色 -->

    <!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
    <meta name="HandheldFriendly" content="true">
    <!-- 微软的老式浏览器 -->
    <meta name="MobileOptimized" content="320">
    <!-- uc强制竖屏 -->
    <meta name="screen-orientation" content="landscape">
    <!-- QQ强制竖屏 -->
    <meta name="x5-orientation" content="landscape">
    <!-- UC强制全屏 -->
    <meta name="full-screen" content="yes">
    <!-- QQ强制全屏 -->
    <meta name="x5-fullscreen" content="true">
    <!-- UC应用模式 -->
    <meta name="browsermode" content="application">
    <!-- QQ应用模式 -->
    <meta name="x5-page-mode" content="app">
    <!-- windows phone 点击无高光 -->
    <meta name="msapplication-tap-highlight" content="no">

    <link rel="shortcut icon" href="/icon/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/icon/60.png" type="image/png">

    <link rel="apple-touch-icon" href="/icon/57.png"/>
    <link rel="apple-touch-icon" sizes="72x72" href="/icon/72.png"/>
    <link rel="apple-touch-icon" sizes="114x114" href="/icon/114.png"/>
    <link rel="apple-touch-icon" sizes="144x144" href="/icon/144.png"/>
</head>
<body style="margin:0;overflow:hidden;overflow-x:hidden;overflow-y:hidden;background-color: #000;">
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    window.gameInit = function (obj, initCallback) {
        window.$initCallback = initCallback.bind(obj);
    };
    window.serverUrl="${serverUrl}";

    wx.config({
        appId: ${weixinConfig.appId}, // 必填，公众号的唯一标识
        timestamp: ${weixinConfig.timestamp}, // 必填，生成签名的时间戳
        nonceStr: ${weixinConfig.nonceStr}, // 必填，生成签名的随机串
        signature: ${weixinConfig.signature},// 必填，签名，见附录1
        jsApiList: ['onMenuShareTimeline',
            'onMenuShareAppMessage', 'onMenuShareQQ',
            'onMenuShareWeibo', 'onMenuShareQZone'
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });

    wx.ready(function () {
        console.log("微信接口就绪");
    });

    wx.error(function (res) {
        alert("微信接口错误！" + res);
    });


    var siteUrl = "http://" + window.location.host + "/";

    window.initInviteUser = function (roomId) {
        var argsObj = {
            title: '房间:' + roomId + ",邀请你进行一局麻将", // 分享标题
            link: siteUrl + "?roomId=" + roomId, // 分享链接
            imgUrl: siteUrl + "icon/144.png", // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        };
        wx.onMenuShareTimeline(argsObj);
        wx.onMenuShareAppMessage(argsObj);
        wx.onMenuShareQQ(argsObj);
        wx.onMenuShareWeibo(argsObj);
        wx.onMenuShareQZone(argsObj);
    };

    window.weixinStartRecord = function () {
        wx.startRecord();
    };

    window.weixinEndRecord = function (callback) {
        wx.stopRecord({
            success: function (res) {
                var localId = res.localId;

                wx.uploadVoice({
                    localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        var serverId = res.serverId; // 返回音频的服务器端ID
                        callback(serverId);
                    }
                });
            }
        });
    };

    window.weixinPlayRecord = function (serverId) {
        wx.downloadVoice({
            serverId: serverId, // 需要下载的音频的服务器端ID，由uploadVoice接口获得
            isShowProgressTips: 0, // 默认为1，显示进度提示
            success: function (res) {
                var localId = res.localId; // 返回音频的本地ID
                wx.playVoice({
                    localId: '' // 需要播放的音频的本地ID，由stopRecord接口获得
                });
            }
        });
    };

    window.shareInviteUser = function (roomId) {
        var shareModel = document.getElementById("shareModel");
        shareModel.style.display = "block";
        var shareLevel = document.getElementById("shareLevel");
        shareLevel.style.display = "block";
        var onClick = function () {
            shareModel.style.display = "none";
            shareLevel.style.display = "none";
        };
        shareModel.onclick = onClick;
        shareLevel.onclick = onClick;
    };
<#if userEncrypt??>
        window.userEncrypt = "${userEncrypt}";
</#if>
    window.weixinLogin = function () {
        window.location.replace("/weixinLogin.html");
    };
</script>
<script src="myLaya.min.js" loader="laya"></script>
<script>
    <#--Laya.URL.customFormat = function (url, basePath) {-->
        <#--if (url == null) {-->
            <#--return null;-->
        <#--}-->
        <#--if (url.indexOf("$v") > -1) {-->
            <#--return url;-->
        <#--}-->
        <#--return url + "?$v=${version}";-->
    <#--};-->

    if (window.$initCallback) {
        window.$initCallback();
    }
</script>
<div id="shareModel"
     style="position: fixed; width: 100%;height: 100%; background-color: #000; opacity:0.7; display: none">
</div>
<div id="shareLevel" style="position: fixed; width: 100%;height: 100%; display: none;">
    <img src="invite.png" width="85" style="float: right;padding: 10px">
    <div style="font-size: 16px; color: #fff; text-align: left;float: right;padding: 10px; opacity: 0.5">
        <p>请点击右上角菜单</p>
        <p>选择发送给朋友</p>
    </div>
</div>
</body>
</html>
