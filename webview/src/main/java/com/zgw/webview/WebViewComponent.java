package com.zgw.webview;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;

public class WebViewComponent implements IComponent {

    @Override
    public String getName() {
        return "webView";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "WebViewActivity":
                CCUtil.navigateTo(cc, WebViewActivity.class);
                CC.sendCCResult(cc.getCallId(), CCResult.success());
                break;
            default:
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }

        return false;
    }
}
